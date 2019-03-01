package com.example.study.elasticsearch.controller;

import com.example.study.domain.elasticsearch.Employee;
import com.example.study.service.elasticsearch.TestService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangfei
 * @date 2019/2/26 15:47
 */
@Controller
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    private EmployeeRepository employeeRepository;

    Gson gson = new Gson();

    @RequestMapping("/")
    @ResponseBody
    public String test() {
        return gson.toJson(testService.getPeople());
    }

    @RequestMapping("add")
    @ResponseBody
    public String add() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFirstName("xuxu");
        employee.setLastName("zh");
        employee.setAge(26);
        employee.setAbout("i am in peking");
        employeeRepository.save(employee);
        return "success";
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public String delete() {
        Employee employee = employeeRepository.queryEmployeeById("1");
        employeeRepository.delete(employee);
        return "success";
    }

    /**
     * 局部更新
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public String update() {
        Employee employee = employeeRepository.queryEmployeeById("1");
        employee.setFirstName("哈哈");
        employeeRepository.save(employee);
        System.err.println("update a obj");
        return "success";
    }
    /**
     * 查询
     * @return
     */
    @RequestMapping("query")
    @ResponseBody
    public Employee query() {
        Employee accountInfo = employeeRepository.queryEmployeeById("1");
        System.err.println(new Gson().toJson(accountInfo));
        return accountInfo;
    }

}
