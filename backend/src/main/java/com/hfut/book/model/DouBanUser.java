package com.hfut.book.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 豆瓣用户的实体
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Data
public class DouBanUser implements Serializable {

    private String uid;
    private String avatar;
    private String name;
}
