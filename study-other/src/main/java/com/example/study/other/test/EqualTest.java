package com.example.study.other.test;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author F.W
 * @date 2019/5/9 14:46
 */
public class EqualTest {


    public static void main(String[] args) {
        ArticlePublishContrast articlePublishContrast =new ArticlePublishContrast();
        articlePublishContrast.setId(1L);
        articlePublishContrast.setDataSource("COMMUNITY");
        articlePublishContrast.setBusiType("POST");

        ArticlePublishContrast contrast =new ArticlePublishContrast();
        contrast.setId(1L);
        contrast.setDataSource("COMMUNITY");
        contrast.setBusiType("POST");

        ArticlePublishContrast contrastNo =new ArticlePublishContrast();
        contrastNo.setId(1L);
        contrastNo.setDataSource("COMMUNITY_NO");
        contrastNo.setBusiType("POST_NO");
        Objects.equals(contrast,contrastNo);
       // Boolean b =contrastObj(contrast,articlePublishContrast);
        Boolean c =contrastObj(contrastNo,articlePublishContrast);

     //   System.out.println("对比相同"+b);
        System.out.println("对比不同"+c);
    }

    private static boolean contrastObj(ArticlePublishContrast pojo1, ArticlePublishContrast pojo2) {
        long start = System.currentTimeMillis();
        try {
            // 根据反射获取当前对象类
            Class clazz = pojo1.getClass();
            // 获取当前对象类的参数
            Field[] fields = pojo1.getClass().getDeclaredFields();
            for (Field field : fields) {
                //获取 clazz 类型中的 propertyName 的属性描述器
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                //从属性描述器中获取get方法
                Method getMethod = pd.getReadMethod();
                //获取对象属性值
                String s1 = getMethod.invoke(pojo1) == null ? "" : getMethod.invoke(pojo1).toString();
                String s2 = getMethod.invoke(pojo2) == null ? "" : getMethod.invoke(pojo2).toString();
                if (!s1.equals(s2)) {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("解析异常" + e.getMessage());
        }
        System.out.println(System.currentTimeMillis()-start);
        return true;
    }

}
