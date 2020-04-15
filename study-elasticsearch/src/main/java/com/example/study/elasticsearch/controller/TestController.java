package com.example.study.elasticsearch.controller;

import com.example.study.domain.elasticsearch.Employee;
import com.example.study.domain.elasticsearch.EmployeeOne;
import com.example.study.elasticsearch.repository.EmployeeRepository;
import com.example.study.service.elasticsearch.TestService;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.wangyin.concrete.client.core.ClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author wangfei
 * @date 2019/2/26 15:47
 */
@Controller
@Slf4j
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    Gson gson = new Gson();


    @RequestMapping("add")
    @ResponseBody
    public String add() {

        Employee employee = new Employee();
        for (int i = 1; i < 10; i++) {
            employee.setId(String.valueOf(i));
            employee.setFirstName("xuxu" + i);
            employee.setLastName("zh" + i);
            employee.setAge(26 + i);
            employee.setMore("i am in peking");
            employeeRepository.save(employee);
        }

        return "success";
    }

    @RequestMapping("addMany")
    @ResponseBody
    public String addMany() {
        Set<String> set = new HashSet<>();
        try {
            FileReader fr = null;
            fr = new FileReader("D:\\data\\add.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                set.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!CollectionUtils.isEmpty(set)) {
            set.forEach(words -> {
                words.replace(" ", "");
                Map map = new HashMap();
                map.put("item_name", words);
                map.put("more2", words);
                try {
                    elasticsearchTemplate.getClient().prepareIndex("test", "test").setSource(map).get();
                } catch (Exception e) {
                    log.info("add-error:{},{}", e);
                }

            });
        }
        return "success";
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping("query")
    @ResponseBody
    public List<Employee> query(String words) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(words);
        Iterable<Employee> accountInfo = employeeRepository.search(builder);
        return Lists.newArrayList(accountInfo);
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping("queryHigh")
    @ResponseBody
    public List<Employee> queryHigh(String words) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(words);
        Iterable<Employee> accountInfo = employeeRepository.search(builder);
        return Lists.newArrayList(accountInfo);
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping("queryAll")
    @ResponseBody
    public List<Employee> queryAll() {
        Iterable<Employee> accountInfo = employeeRepository.findAll();
        return Lists.newArrayList(accountInfo);
    }


    /**
     * 查询
     *
     * @return
     */
    @RequestMapping("queryCompletion")
    @ResponseBody
    public List<String> queryCompletion(String words) {


        String suggestField = "more";//指定在哪个字段搜索
        Integer suggestMaxCount = 10;//获得最大suggest条数

        CompletionSuggestionBuilder suggestionBuilderDistrict = new CompletionSuggestionBuilder(suggestField).prefix(words).size(suggestMaxCount);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("employee_suggest", suggestionBuilderDistrict);//添加suggest

        //设置查询builder的index,type,以及建议
        SearchRequestBuilder searchRequestBuilder = elasticsearchTemplate.getClient().prepareSearch("company").setTypes("employee").suggest(suggestBuilder);
        System.out.println(searchRequestBuilder.toString());
        //设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(suggestField);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchRequestBuilder.highlighter(highlightBuilder);
        SearchResponse response = searchRequestBuilder.get();
        Suggest suggest = response.getSuggest();//suggest实体

        Set<String> suggestSet = new HashSet<>();//set
        int maxSuggest = 0;
        if (suggest != null) {
            Suggest.Suggestion result = suggest.getSuggestion("employee_suggest");//获取suggest,name任意string
            for (Object term : result.getEntries()) {
                if (term instanceof CompletionSuggestion.Entry) {
                    CompletionSuggestion.Entry item = (CompletionSuggestion.Entry) term;
                    if (!item.getOptions().isEmpty()) {
                        //若item的option不为空,循环遍历
                        for (CompletionSuggestion.Entry.Option option : item.getOptions()) {
                            String tip = option.getText().toString();
                            if (!suggestSet.contains(tip)) {
                                suggestSet.add(tip);
                                ++maxSuggest;
                            }
                        }
                    }
                }
                if (maxSuggest >= suggestMaxCount) {
                    break;
                }
            }
        }
        List<String> suggests = Arrays.asList(suggestSet.toArray(new String[]{}));
        return Lists.newArrayList(suggests);
    }
}
