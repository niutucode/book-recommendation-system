package com.hfut.book.model;

import lombok.Data;
import java.io.Serializable;

/**
 * 评论数的实体
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Data
public class CommentTotal implements Serializable {

    private Integer bid;
    private Integer count;
}
