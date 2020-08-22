package com.hfut.book.model;

import lombok.Data;
import java.io.Serializable;

/**
 * 详细信息的实体
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Data
public class DetailInfo implements Serializable {

    private Integer bid;
    private String type;
    private String title;
    private String author;
    private String translator;
    private String publisher;
    private String pubdate;
    private String pages;
    private String introduction;
    private String cover;
    private Float score;
}
