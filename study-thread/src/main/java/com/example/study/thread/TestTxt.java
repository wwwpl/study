package com.example.study.thread;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;

import java.util.List;


public class TestTxt {
    public static List<Word> readCsv(String path) {
        List<Word> list = new ArrayList<>();

        InputStream ins = null;
        BufferedReader reader = null;
        try {
            Resource resource = new ClassPathResource(path);
            ins = resource.getInputStream();
            reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            String line = "";
            int index = 0;
            while ((line = reader.readLine()) != null) {
                index++;
                if (NumberUtils.isNumber(line)) {
                    continue;
                }
                if (line.contains(",")||line.contains("http:")||line.contains("https:")){
                    continue;
                }
                System.out.println(line);
                Word word = new Word();
                word.setTypeYhq(0);
                word.setTypeXyk(0);
                word.setTypeBx(0);
                word.setTypeGdlc(0);
                word.setTypeLc(0);
                word.setTypeGp(0);
                word.setTypeGf(0);
                word.setTypeJq(0);
                word.setTypeSp(0);
                word.setTypeJj(0);
                word.setWord(line.toLowerCase());
                if (list!=null&&list.contains(word)){
                    continue;
                }
                if (index >= 100000) {
                    break;
                }
                list.add(word);
            }
            System.out.println("数据数据:" + index);
        } catch (Exception e) {

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
                if (ins != null) {
                    ins.close();
                    ins = null;
                }
            } catch (IOException e) {

            }
        }
        return list;
    }

    public static void main(String[] args) {
        OutputStream out = null;
        try {
            out = new FileOutputStream("D://text.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ExcelWriter writer = EasyExcelFactory.getWriter(out);

        // 写仅有一个 Sheet 的 Excel 文件, 此场景较为通用
        Sheet sheet1 = new Sheet(1, 0, Word.class);

        // 第一个 sheet 名称
        sheet1.setSheetName("第一个sheet");

        // 写数据到 Writer 上下文中
        // 入参1: 创建要写入的模型数据
        // 入参2: 要写入的目标 sheet
        List<Word> list = readCsv("txt/1.txt");
        System.out.println(list.size());
        writer.write(list, sheet1);

        // 将上下文中的最终 outputStream 写入到指定文件中
        writer.finish();

        // 关闭流
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
