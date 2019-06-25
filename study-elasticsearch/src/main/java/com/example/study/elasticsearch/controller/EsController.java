package com.example.study.elasticsearch.controller;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@Controller
public class EsController {
    /**
     * 获取ik自定义词典
     * @param request
     * @param response
     */
    @RequestMapping(value="/getCustomDict.htm")
    public void getCustomDict(HttpServletRequest request, HttpServletResponse response){
        try {
            // 读取字典文件
            String path = "D:\\data\\extend.txt";
            File file = new File(path);
            String content = "";
            if(file.exists()){
                // 读取文件内容
                FileInputStream fi = new FileInputStream(file);
                byte[] buffer = new byte[(int) file.length()];
                int offset = 0, numRead = 0;
                while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                    offset += numRead;
                }
                fi.close();
                content = new String(buffer, "UTF-8");
            }
            // 返回数据
            OutputStream out= response.getOutputStream();
            response.setHeader("Last-Modified", String.valueOf(content.length()));
            response.setHeader("ETag",String.valueOf(content.length()));
            response.setContentType("text/plain; charset=utf-8");
            out.write(content.getBytes("utf-8"));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
