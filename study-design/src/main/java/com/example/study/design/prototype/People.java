package com.example.study.design.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author F.W
 * @date 2019/5/15 15:20
 */
@Data
@AllArgsConstructor
public class People implements Cloneable {

    private String name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
