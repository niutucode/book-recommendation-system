package com.hfut.book.model;

import lombok.Data;
import java.io.Serializable;


/**
 * 关键信息实体
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Data
public class KeyInfo implements Serializable {

    private Integer bid;
    private String title;
    private String type;
    private String cover;
    private Float score;
}
