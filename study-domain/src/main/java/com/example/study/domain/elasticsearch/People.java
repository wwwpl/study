package com.example.study.domain.elasticsearch;

/**
 * 测试mysql 实体类
 *
 * @author wangfei52
 * @date 2019年2月26日16:01:39
 */
public class People {
    private Integer id;

    private String name;

    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}