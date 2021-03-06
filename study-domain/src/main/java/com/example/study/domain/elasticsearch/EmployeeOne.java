package com.example.study.domain.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author wangfei
 * @date 2019/2/28 17:37
 */
@Getter
@Setter
@Document(indexName = "test-1", type = "employee", shards = 1, replicas = 0, refreshInterval = "-1")
public class EmployeeOne {

    @Id
    private String id;
   // @Field
    private String firstName;
    //  @Field
    private String lastName;
    //  @Field
    private Integer age = 0;
    // @Field
    private String more;
}
