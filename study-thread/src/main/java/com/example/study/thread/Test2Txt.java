package com.example.study.thread;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Test2Txt {
    public static List<Word> readCsv(String path) {
        List<Word> list = new ArrayList<>();
        InputStream ins = null;
        BufferedReader reader = null;
        try {
            Resource resource = new ClassPathResource(path);
            ins = resource.getInputStream();
            reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            String line = "";
            Map mapArray = new HashMap();
            while ((line = reader.readLine()) != null) {
                Map map = new HashMap();
                line = line.replace(" ", "");
                if (line.contains(",")) {
                    String words = line.split(",")[0];
                    String typeList = line.substring(words.length() + 1, line.length());
                    if (typeList.contains(",")) {
                        words = words + typeList.split(",")[0];
                        typeList = line.substring(typeList.split(",")[0].length() + 1, typeList.length());
                    }
                    if (typeList.contains(",")) {
                        continue;
                    }
                    if (words.contains("http") || typeList.contains("[\"商品*\"],") || typeList.contains("[\"服务号*\"]") || typeList.startsWith(",")) {
                        continue;
                    }
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
                    if (words.contains("(")) {
                        continue;
                    }
                    if (words.contains("【")) {
                        continue;
                    }
                    if (words.contains("[")) {
                        continue;
                    }
                    if (StringUtils.isEmpty(words)) {
                        continue;
                    }
                    typeList = typeList.replace("*", "");
                    if (typeList.equals("商品")){
                        continue;
                    }
                    if (typeList.equals("服务号")){
                        continue;
                    }
                    if (mapArray.containsKey(words.toLowerCase())) {
                        Map valueData = (HashMap) mapArray.get(words.toLowerCase());
                        if (valueData.containsKey(typeList)) {
                            Integer sum = (Integer) valueData.get(typeList);
                            sum = sum + 1;
                            valueData.put(typeList, sum);
                        } else {
                            valueData.put(typeList, 1);
                            map.put(words.toLowerCase(), valueData);
                        }
                    } else {
                        Map value = new HashMap();
                        value.put(typeList, 1);
                        map.put(words.toLowerCase(), value);
                    }
                    mapArray.putAll(map);
                }
            }
            list = getWordList(mapArray);
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

    public static List<Word> getWordList(Map mapList) {
        List<Word> wordList = new ArrayList<>();

        Map<Word, Integer> wordIntegerMap = new LinkedHashMap<>();
        mapList.forEach((t, v) -> {
            Word word = new Word();
            Map map = (HashMap) v;
            int sum = 0;
            int index = 0;
            for (Object value : map.values()) {
                sum = sum + (int) value;
            }
            word.setWord(t.toString().toLowerCase());
            if (sum > 1 && !StringUtils.isEmpty(word)) {
                Iterator<Map.Entry<String, Integer>> entries = map.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, Integer> entry = entries.next();
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    if (value * 100 / sum > 20) {
                        index = index + 1;
//                        if (key.equals("商品")) {
//                            word.setTypeSp(1);
//                        }
                        if (key.equals("功能服务")) {
                            word.setTypeGf(1);
                        }
                        if (key.equals("众筹")) {
                          //  word.setTypeZc(1);
                        }
                        if (key.equals("基金")) {
                            word.setTypeJj(1);
                        }
//                        if (key.equals("服务号")) {
//                            word.setTypeFw(1);
//                        }
                        if (key.equals("股票")) {
                            word.setTypeGp(1);
                        }
                        if (key.equals("用户")) {
                         //   word.setTypeYh(1);
                        }
                        if (key.equals("租赁")) {
                          //  word.setTypeZl(1);
                        }

                    }
                }
            }
            if (index>0){
                wordIntegerMap.put(word, sum);
            }
        });

        Map<Word, Integer> finalOut = new LinkedHashMap<>();
        wordIntegerMap.entrySet()
                .stream()
                .sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
                .collect(Collectors.toList()).forEach(ele -> finalOut.put(ele.getKey(), ele.getValue()));
        for (Map.Entry<Word, Integer> entry : finalOut.entrySet()) {
            wordList.add(entry.getKey());
        }

        return wordList;

    }


    public static void main(String[] args) {
        OutputStream out = null;
        try {
            out = new FileOutputStream("D://text1.xlsx");
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
        List<Word> list = readCsv("txt/2.txt");
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
