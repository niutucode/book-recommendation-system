package com.hfut.book.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信用户的实体
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Data
public class WeiXinUser implements Serializable {

    private String uid;
    private String nickName;
    private String avatarUrl;
    private String gender;
    private String country;
    private String province;
    private String city;
    private String language;
}
