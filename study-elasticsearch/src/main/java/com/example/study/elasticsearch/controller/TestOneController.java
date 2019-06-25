package com.example.study.elasticsearch.controller;

import com.example.study.domain.elasticsearch.Employee;
import com.example.study.domain.elasticsearch.EmployeeOne;
import com.example.study.elasticsearch.repository.EmployeeOneRepository;
import com.example.study.elasticsearch.repository.EmployeeRepository;
import com.example.study.service.elasticsearch.TestService;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangfei
 * @date 2019/2/26 15:47
 */
@Controller
@RequestMapping("one")
public class TestOneController {

    @Autowired
    TestService testService;

    @Autowired
    private EmployeeOneRepository employeeOneRepository;

    Gson gson = new Gson();

    @RequestMapping("/")
    @ResponseBody
    public String test() {
        return gson.toJson(testService.getPeople());
    }

    @RequestMapping("add")
    @ResponseBody
    public String add() {
        EmployeeOne employee = new EmployeeOne();
        for (int i = 1; i < 10; i++) {
            employee.setId(String.valueOf(i));
            employee.setFirstName("xuxu" + i);
            employee.setLastName("zh" + i);
            employee.setAge(26 + i);
            employee.setMore("i am in peking");
            employeeOneRepository.save(employee);
        }

        return "success";
    }

    @RequestMapping("addMany")
    @ResponseBody
    public String addMany() {
        try {
            ArrayList<String> arrayList = new ArrayList<>();
            FileReader fr = new FileReader("D:\\data\\add.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
            for (int i = 10; i < arrayList.size() + 10; i++) {
                EmployeeOne employee = new EmployeeOne();
                employee.setId(String.valueOf(i));
                employee.setFirstName("xuxu" + i);
                employee.setLastName("zh" + i);
                employee.setAge(26 + i);
                employee.setMore(arrayList.get(i - 10));
                employeeOneRepository.save(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    public List<EmployeeOne> query(String words) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(words);
        Iterable<EmployeeOne> accountInfo = employeeOneRepository.search(builder);
        return Lists.newArrayList(accountInfo);
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping("queryAll")
    @ResponseBody
    public List<EmployeeOne> queryAll() {
        Iterable<EmployeeOne> accountInfo = employeeOneRepository.findAll();
        return Lists.newArrayList(accountInfo);
    }


}
