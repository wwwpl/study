package com.example.study.thread;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
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
            int type = 0;
            while ((line = reader.readLine()) != null) {
                index++;
                line = line.replace(" ", "");
                if (line.contains(",")) {
                    String words = line.split(",")[0];
                    if (words.contains("//")) {
                        continue;
                    }
                    if (words.contains(",")) {
                        continue;
                    }
                    if (words.contains("，")) {
                        continue;
                    }
                    if (words.contains("!")) {
                        continue;
                    }
                    if (words.contains("！")) {
                        continue;
                    }
                    if (words.contains(".")) {
                        continue;
                    }
                    if (words.contains("。")) {
                        continue;
                    }
                    if (words.contains("？")) {
                        continue;
                    }
                    if (words.contains("?")) {
                        continue;
                    }
                    if (words.contains("、")) {
                        continue;
                    }
                    if (words.contains("|")) {
                        continue;
                    }
                    if (StringUtils.isEmpty(words)) {
                        continue;
                    }
                    String typeList = line.substring(line.split(",")[0].length() + line.split(",")[1].length() + 2
                            , line.length());
//                typeList = typeList.replace("[", "").replace("]", "");
//                System.out.println(typeList);
//                    if (list.contains(words.toLowerCase())) {
//                        continue;
//                    }

                    if (words.contains("http")) {
                        continue;
                    }
                    System.out.println(typeList);
                    if ("1,[\"商品*\"]".equals(typeList)) {
                        System.out.println(line);
                    }
                    int one = 0;
                    Word word = new Word();
                    word.setWord(words.toLowerCase());
                    if (typeList.contains("商品*") || typeList.contains("众筹*") || typeList.contains("租赁*")) {
                        word.setTypeSp(1);
                        one++;
                    }
                    if (typeList.contains("功能服务*")) {
                        word.setTypeGf(1);
                        one++;
                    }
//                    if (typeList.contains("借钱*")) {
//                        word.setTypeJq(1);
//                        one++;
//                    }
                    if (typeList.contains("基金*")) {
                        word.setTypeJj(1);
                        one++;
                    }
                    if (typeList.contains("股票*")) {
                        word.setTypeGp(1);
                        one++;
                    }
//                    if (typeList.contains("理财*")) {
//                        word.setTypeLc(1);
//                        one++;
//                    }
//                    if (typeList.contains("高端理财*")) {
//                        word.setTypeGdlc(1);
//                        one++;
//                    }
//                    if (typeList.contains("保险*")) {
//                        word.setTypeBx(1);
//                        one++;
//                    }
//                    if (typeList.contains("信用卡*")) {
//                        word.setTypeXyk(1);
//                        one++;
//                    }
//                    if (typeList.contains("优惠券*")) {
//                        word.setTypeYhq(1);
//                        one++;
//                    }
                    if (one > 0) {
                        list.add(word);
                    }
//                    List<String> list1 = Arrays.asList(typeList.split(","));
//                    System.out.println(JSONObject.toJSON(list1));
                }


            }
            System.out.println("数据数据:" + index);
            System.out.println("数据数据:" + type);
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
