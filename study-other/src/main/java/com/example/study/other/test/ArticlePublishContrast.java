package com.example.study.other.test;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class ArticlePublishContrast {
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 抓取表
     */
    private String dataSource;
    /**
     * 业务分类字典值
     */
    private String busiType;
    /**
     * 抓取平台字典值
     */
    private Long crawSourceDictId;
    /**
     * 版权字典值
     */
    private Long coprDictId;
    /**
     * 原库主键
     */
    private String oldId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 显示标题
     */
    private String showTitle;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片地址
     */
    private String focusImgUrl;
    /**
     * 运营标签
     */
    private String tag;
    /**
     * 资讯作者
     */
    private String author;
    /**
     * 是否有版权 1有 0没有
     */
    private Integer copyRight;
    /**
     * 发布主体：牛人id/服务号id
     */
    private String serviceId;
    /**
     * 用户pin
     */
    private String pin;
    /**
     * 推送渠道
     */
    private String pushChannel;
    /**
     * 推送概要
     */
    private String pushSummary;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 资讯url
     */
    private String articleUrl;
    /**
     * 定是时间
     */
    private Date timer;
    /**
     * 显示状态0:隐藏 1:显示
     */
    private Integer showStatus;
    /**
     * 发布状态0:待审 1:已发布
     */
    private Integer publishStatus;
    /**
     * 发布人
     */
    private String publishUser;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String modifiedUser;
    /**
     * 修改时间
     */
    private Date modifiedTime;

    /**
     * 逻辑删除1、已删除0、未删除
     */
    private Integer isDeleted;
    /**
     * 老运营同步id
     */
    private String syncId;
    /**
     * 安全状态
     */
    private Integer secureStatus;
    /**
     * 内容类型
     */
    private Integer contentType;
    /**
     * 发布载体类型
     */
    private Integer serviceType;
    /**
     * 评论状态
     */
    private Integer commentStatus;

    private Integer approveStatus;
    /**
     * 安全审核内容
     */
    private String secureDetail;
    /**
     * 流程状态
     */
    private Integer processStatus;
    /**
     * 来自
     */
    private String sceneId;
    /**
     * 来自场景类型
     */
    private Integer sceneType;
    /**
     * 终端
     */
    private Integer endPoint;
    /**
     * 场景名称
     */
    private String sceneName;


}
