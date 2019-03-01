package com.example.study.elasticsearch.controller;

import com.example.study.domain.elasticsearch.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author wangfei
 * @date 2019/2/28 17:47
 */
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {
    Employee queryEmployeeById(String id);
}
