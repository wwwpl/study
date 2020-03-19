package com.example.study.thread;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class Word extends BaseRowModel {
    @ExcelProperty(value = "word", index = 1)
    private String word;
    @ExcelProperty(value = "商品", index = 2)
    private int typeSp;
    @ExcelProperty(value = "功能服务", index = 3)
    private int typeGf;
    @ExcelProperty(value = "借钱", index = 4)
    private int typeJq;
    @ExcelProperty(value = "基金", index = 5)
    private int typeJj;
    @ExcelProperty(value = "股票", index = 6)
    private int typeGp;
    @ExcelProperty(value = "理财", index = 7)
    private int typeLc;
    @ExcelProperty(value = "高端理财", index = 8)
    private int typeGdlc;
    @ExcelProperty(value = "保险", index = 9)
    private int typeBx;
    @ExcelProperty(value = "信用卡", index = 10)
    private int typeXyk;
    @ExcelProperty(value = "优惠券", index = 11)
    private int typeYhq;
}
