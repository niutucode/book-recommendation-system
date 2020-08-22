package com.hfut.book.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 搜索记录的实体
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Data
public class SearchRecode implements Serializable {

    private Integer num;
    private String uid;
    private Integer bid;
    private String bookTitle;
    private String bookType;
    private Timestamp date;
}
