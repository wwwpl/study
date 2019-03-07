package com.example.study.sentinel.utils;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * InvokeService Proxy
 *
 * @author Zhiguo.Chen
 */
@Component
public class InvokeServiceProxy {

    public final static Logger logger = LoggerFactory.getLogger(InvokeServiceProxy.class);



    @Value("#{updateKey}")
    String updateKey = "origin";

    @Value("#{grade0Enable}")
    boolean grade0Enable = true;

    @Value("#{grade0Count}")
    Double grade0Count = 3000D;

    @Value("#{grade0TimeWindow}")
    Integer grade0TimeWindow = 5;

    @Value("#{grade1Enable}")
    boolean grade1Enable = true;

    @Value("#{grade1Count}")
    Double grade1Count = 0.2;

    @Value("#{grade1TimeWindow}")
    Integer grade1TimeWindow = 5;

    @Value("#{maxCpuLoad}")
    Integer maxCpuLoad = 0;

    @Value("#{avgRt}")
    Integer avgRt = -1;

    private String preUpdateKey = "";

    /**
     * Map<String, String> - key: url, value: updateKey
     */
    private Map<String, String> ruleMaps = new ConcurrentHashMap<String, String>();

    @Value("#{vipUrlStr}")
    String vipUrlStr;

    List<String> vipUrls;

    /**
     * Load degrade rule for every url
     *
     * @param key
     * @param methodKey
     */
    private void initDegradeRule(String key, String methodKey) {
        // Skip vip urls
        if (!CollectionUtils.isEmpty(vipUrls) && vipUrls.contains(key)) {
            return;
        }
        String mapKey = key + "-" + methodKey;
        // Build or rebuild System Rule.
        if (!updateKey.equals(preUpdateKey)) {
            synchronized (InvokeServiceProxy.class) {
                if (!updateKey.equals(preUpdateKey)) {
                    initSystemRule();
                    DegradeRuleManager.loadRules(new ArrayList<DegradeRule>());
                    vipUrls = Lists.newArrayList(vipUrlStr.split(","));
                    preUpdateKey = updateKey;
                }
            }
        }

        // Check if you need to rebuild the rules.
        if (ruleMaps.containsKey(mapKey) && updateKey.equals(ruleMaps.get(mapKey))) {
            //logger.info("[DegradeRule]Total rule size = {}", DegradeRuleManager.getRules().size());
            return;
        }

        synchronized (InvokeServiceProxy.class) {
            if (ruleMaps.containsKey(mapKey) && updateKey.equals(ruleMaps.get(mapKey))) {
                return;
            }
            List<DegradeRule> rules = DegradeRuleManager.getRules();
            FlowRule flowRule = new FlowRule();
            if (grade0Enable) {
                logger.info("[DegradeRule]DEGRADE_GRADE_RT is enable! Add key:{}", key);
                DegradeRule responseTimeRule = new DegradeRule();
                responseTimeRule.setResource(key);
                // set threshold rt, 2000 ms
                responseTimeRule.setCount(grade0Count);
                responseTimeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
                responseTimeRule.setTimeWindow(grade0TimeWindow);
                responseTimeRule.setLimitApp("平均RT超过" + grade0Count);
                rules.add(responseTimeRule);
            }

            if (grade1Enable) {
                logger.info("[DegradeRule]DEGRADE_GRADE_EXCEPTION_RATIO is enable! Add key:{}", key);
                DegradeRule exceptionRadio = new DegradeRule();
                exceptionRadio.setResource(key);
                // set threshold rt, 0.05
                exceptionRadio.setCount(grade1Count);
                exceptionRadio.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
                exceptionRadio.setTimeWindow(grade1TimeWindow);
                exceptionRadio.setLimitApp("异常比例超过" + grade1Count);
                rules.add(exceptionRadio);
            }
            logger.info("[DegradeRule]Total rule size = {}", rules.size());
            DegradeRuleManager.loadRules(rules);
            ruleMaps.put(mapKey, updateKey);
        }
    }

    /**
     * Load System dynamic rule
     */
    private void initSystemRule() {
        logger.info("Start loading system dynamic rule...");
        SystemRule rule = new SystemRule();
        logger.info("[SystemRule]This server cpu cores is: {}, set max cpu load is: {}", Runtime.getRuntime().availableProcessors(), maxCpuLoad);
        if (maxCpuLoad > 0) {
            rule.setHighestSystemLoad(maxCpuLoad);
        } else if (maxCpuLoad == 0) {
            // System rule will be enable when max load > cpu cores * 2.5
            rule.setHighestSystemLoad(Runtime.getRuntime().availableProcessors() * 2.5);
        } else {
            // System rule will be disable
            rule.setHighestSystemLoad(-1);
        }
        // System rule will enable when avgRT > set value
        rule.setAvgRt(avgRt);
        logger.info("[SystemRule]This server's SystemRule avgRt will be set: {}", avgRt);
        //rule.setHighestSystemLoad(1);
        SystemRuleManager.loadRules(Collections.singletonList(rule));
    }

    /**
     * 单个方法调用(代理)
     *
     * @param frontUrl
     * @param requestParams
     * @return
     */
    public ObjectResponse methodInvoke(String frontUrl, EncryRequest requestParams) {
        logger.info("[MethodInvoke]InvokeServiceProxy frontUrl:{}", frontUrl);
        ObjectResponse objectResponse = new ObjectResponse();
        initDegradeRule(frontUrl, "methodInvoke");
        Entry entry = null;
        try {
            entry = SphU.entry(frontUrl, EntryType.IN);
            objectResponse = invokeService.methodInvoke(frontUrl, requestParams);
            //logger.info("[MethodInvoke]本次请求返回包大小:{}, url:{}", JsonUtil.toJson(objectResponse).length(), frontUrl);
        } catch (BlockException blockException) {
            logger.error("[MethodInvoke]请求被熔断降级，URL:{}, 熔断因素:{}, requestParams:{}", frontUrl, blockException.getRuleLimitApp(), JsonUtil.toJson(requestParams));
            objectResponse.setResultMsg("您访问的该接口目前处于不稳定状态，请稍后再试！");
            objectResponse.setSuccess(false);
            objectResponse.setResultCode(-1);
            Profiler.businessAlarm("System_Degrade_Happened", System.currentTimeMillis(), "[JRGateway MethodInvoke]：该接口被熔断！URL：" + frontUrl + "，熔断因素：" + blockException.getRuleLimitApp());
        } catch (Throwable t) {
            // 统计异常比例
            Tracer.trace(t);
            logger.error("[MethodInvoke]请求异常：{}", t.getMessage(), t);
            objectResponse.setResultMsg("您访问的该接口出现异常！Message:" + t.getMessage());
            objectResponse.setSuccess(false);
            objectResponse.setResultCode(-1);
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
        return objectResponse;
    }

    /**
     * 组调用(代理)
     *
     * @param frontUrl
     * @param requestParams
     * @return
     */
    public ObjectResponse groupInvoke(String frontUrl, EncryRequest requestParams) {
        ObjectResponse objectResponse = new ObjectResponse();
        initDegradeRule(frontUrl, "groupInvoke");
        Entry entry = null;
        try {
            entry = SphU.entry(frontUrl, EntryType.IN);
            // token acquired
            objectResponse = invokeService.groupInvoke(frontUrl, requestParams);
            //logger.info("[GroupInvoke]本次请求返回包大小:{}, url:{}", JsonUtil.toJson(objectResponse).length(), frontUrl);
        } catch (BlockException blockException) {
            logger.error("[GroupInvoke]请求被熔断降级，URL:{}, 熔断因素:{}, requestParams:{}", frontUrl, blockException.getRuleLimitApp(), JsonUtil.toJson(requestParams));
            objectResponse.setResultData("您访问的该接口目前处于不稳定状态，请稍后再试！");
            objectResponse.setSuccess(false);
            objectResponse.setResultCode(-1);
            Profiler.businessAlarm("System_Degrade_Happened", System.currentTimeMillis(), "[JR Gateway GroupInvoke]：该接口被熔断！URL：" + frontUrl + "，熔断因素：" + blockException.getRuleLimitApp());
        } catch (Throwable t) {
            // 统计异常比例
            Tracer.trace(t);
            logger.error("[GroupInvoke]请求异常：{}", t.getMessage(), t);
            objectResponse.setResultData("您访问的该接口出现异常！Message:" + t.getMessage());
            objectResponse.setSuccess(false);
            objectResponse.setResultCode(-1);
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
        return objectResponse;
    }

    public static void main(String[] args) {
        final InvokeServiceProxy serviceProxy = new InvokeServiceProxy();
        //serviceProxy.initDegradeRule("aaa", "methodInvoke");

        for (int i = 0; i < 10; i++) {
            //serviceProxy.initDegradeRule("aaa"+ i, "methodInvoke");
            final int fi = i;
            Thread entryThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    int count = 0;
                    while (true) {
                        count++;
                        try {
                            serviceProxy.updateKey = "456";
                            serviceProxy.methodInvoke("aaa" + fi, new EncryRequest());
                            //serviceProxy.methodInvoke("aaa", new EncryRequest());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
            entryThread.setName("working-thread-" + i);
            entryThread.start();
        }
        serviceProxy.updateKey = "123";
        serviceProxy.methodInvoke("aaa1", new EncryRequest());
    }
}
