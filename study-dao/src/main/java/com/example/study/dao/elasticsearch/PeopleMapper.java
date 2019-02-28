package com.example.study.dao.elasticsearch;


import com.example.study.domain.elasticsearch.People;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 测试dao类
 *
 * @author wangfei52
 * @date 2019年2月26日16:03:06
 */
@Mapper
public interface PeopleMapper {

    List<People> selectPeopleAll();
}
