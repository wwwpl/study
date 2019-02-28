package com.example.study.service.elasticsearch.impl;

import com.example.study.dao.elasticsearch.PeopleMapper;
import com.example.study.domain.elasticsearch.People;
import com.example.study.service.elasticsearch.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangfei
 * @date 2019/2/26 16:20
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    PeopleMapper peopleMapper;

    @Override
    public List<People> getPeople() {
        List<People> list= peopleMapper.selectPeopleAll();
        return list;
    }
}
