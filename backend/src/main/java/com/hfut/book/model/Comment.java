package com.hfut.book.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 评论的实体
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Data
public class Comment implements Serializable {

    private Integer num;
    private Integer bid;
    private String type;
    private String uid;
    private String avatarUrl;
    private String nickName;
    private Timestamp pubdate;
    private Float score;
    private String summary;
}
