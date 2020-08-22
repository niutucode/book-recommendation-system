package com.hfut.book.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 手机用户的实体
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Data
public class PhoneUser implements Serializable {

    private String phoneNumber;
    private String nickName;
    private String avatarUrl;
    private String gender;
    private String country;
    private String province;
    private String city;
    private String language;
}
