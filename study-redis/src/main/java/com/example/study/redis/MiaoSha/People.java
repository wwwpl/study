package com.example.study.redis.MiaoSha;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author F.W
 * @date 2019/5/31 10:49
 */
@Getter
@Setter
public class People {
    Integer clientName;

    public People(Integer clientName) {
        this.clientName = clientName;
    }
}
