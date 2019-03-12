package com.example.study.domain.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wangfei
 * @date 2018/12/11 10:02
 */

@Getter
@Setter
public class ResultVO<T> {

    private Integer code;

    private Integer count;

    private T data;

    private String scroll_id;

    private String dsl;

    private String msg;
}
