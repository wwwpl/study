package com.example.study.elasticsearch.repository;

import com.example.study.domain.elasticsearch.Employee;
import com.example.study.domain.elasticsearch.EmployeeOne;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author wangfei
 * @date 2019/2/28 17:47
 */
public interface EmployeeOneRepository extends ElasticsearchRepository<EmployeeOne, String> {

}
