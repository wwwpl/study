package com.example.study.sentinel.utils;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.example.study.domain.vo.ResultVO;
import com.google.common.collect.Lists;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 熔断降级  平均响应时间&&异常比例
 *
 * @author F.W
 * @date 2019/3/11 11:07
 */
public class SentinelUtils {

    static Double timeCount = 200D;
    static Double radioCount = 0.2;

    static Boolean isOpenTime = true;
    static Boolean isOpenRadio = true;

    static Integer timeWindow = 5;
    static Integer radioWindow = 5;

    static Integer maxCpuLoad = 0;

    final static Integer CPU = -1;


    static String updateKey = "online";
    static String preUpdateKey = "";

    private static Map<String, String> keyMaps = new ConcurrentHashMap<String, String>();

    /**
     * 降级规则
     *
     * @param key 资源
     */
    public static void initDegradeRule(String key, String methodKey) {

        String mapKey = key + "-" + methodKey;
        // 检测是否开启系统保护
        if (!updateKey.equals(preUpdateKey)) {
            synchronized (SentinelUtils.class) {
                if (!updateKey.equals(preUpdateKey)) {
                    initSystemRule();
                    //DegradeRuleManager.loadRules(new ArrayList<DegradeRule>());
                    preUpdateKey = updateKey;
                }
            }
        }
        // 判断这个方法是否已经启动规则
        if (keyMaps.containsKey(mapKey) && updateKey.equals(keyMaps.get(mapKey))) {
            return;
        }
        synchronized (SentinelUtils.class) {

            if (keyMaps.containsKey(mapKey) && updateKey.equals(keyMaps.get(mapKey))) {
                return;
            }

            List<DegradeRule> rules = DegradeRuleManager.getRules();
            if (isOpenTime) {
                // 平均响应时间
                DegradeRule degradeRuleTime = new DegradeRule();
                // 资源名，即限流规则的作用对象
                degradeRuleTime.setResource(key);
                // 降级模式，根据 RT 降级还是根据异常比例降级
                degradeRuleTime.setGrade(RuleConstant.DEGRADE_GRADE_RT);
                // 阈值
                degradeRuleTime.setCount(timeCount);
                // 降级时间
                degradeRuleTime.setTimeWindow(timeWindow);

                degradeRuleTime.setLimitApp("平均RT超过" + timeCount);
                rules.add(degradeRuleTime);
            }
            if (isOpenRadio) {
                // 异常比例
                DegradeRule degradeRuleRadio = new DegradeRule();
                // 资源名，即限流规则的作用对象
                degradeRuleRadio.setResource(key);
                // 降级模式，根据 RT 降级还是根据异常比例降级
                degradeRuleRadio.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
                // 阈值
                degradeRuleRadio.setCount(radioCount);
                // 降级时间
                degradeRuleRadio.setTimeWindow(radioWindow);
                degradeRuleRadio.setLimitApp("异常比率超过" + radioCount);
                rules.add(degradeRuleRadio);
            }
            DegradeRuleManager.loadRules(rules);
            keyMaps.put(mapKey, updateKey);
        }

    }

    /**
     * 系统保护
     */
    public static void initSystemRule() {
        // 系统保护
        SystemRule systemRule = new SystemRule();
        if (maxCpuLoad > 0) {
            systemRule.setHighestSystemLoad(maxCpuLoad);
        } else if (maxCpuLoad == 0) {
            // 可用处理器的Java虚拟机的数量
            systemRule.setHighestSystemLoad(Runtime.getRuntime().availableProcessors() * 2.5);
        } else {
            systemRule.setHighestSystemLoad(CPU);
        }
        SystemRuleManager.loadRules(Collections.singletonList(systemRule));

    }

    public ResultVO methodInvoke(String url, String request) {
        ResultVO resultVO = new ResultVO();
        initDegradeRule(url, "methodInvoke");
        Entry entry = null;
        try {
            entry = SphU.entry(url, EntryType.IN);
            HttpUtils.timeoutMethod(3000);
        } catch (BlockException blockException) {
            System.out.println("[MethodInvoke]请求被熔断降级" + url + "    " + blockException.getRuleLimitApp());
        } catch (Throwable throwable) {
            System.out.println("methodInvoke请求异常");
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }

        return null;
    }

    public static void main(String[] args) {
//        final SentinelUtils sentinelUtils = new SentinelUtils();
//
//        for (int i = 0; i < 10; i++) {
//            final int fi = i;
//            Thread entryThread = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    int count = 0;
//                    while (true) {
//                        count++;
//                        try {
//                            sentinelUtils.updateKey = "456";
//                            sentinelUtils.methodInvoke("https://jd.com" + fi, "11111");
//                            //serviceProxy.methodInvoke("aaa", new EncryRequest());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            });
//            entryThread.setName("working-thread-" + i);
//            entryThread.start();
//        }
//        sentinelUtils.updateKey = "123";
    }


}
