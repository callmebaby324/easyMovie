package springboot.modal.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_movie
 * @author 
 */
@Data
public class Movie implements Serializable {
    private Integer id;

    /**
     * 电影名称
     */
    private String name;
    private String info;

    /**
     * 海报
     */
    private String headImg;

    /**
     * 导演
     */
    private String director;

    /**
     * 演员
     */
    private String actor;

    /**
     * 分类：爱情/动作
     */
    private String catogery;

    /**
     * 国别
     */
    private String country;

    /**
     * 评分
     */
    private String point;

    /**
     * 上映日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date year;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    /**
     * 内容
     */
    private String content;

    private String download;

    private static final long serialVersionUID = 1L;

}