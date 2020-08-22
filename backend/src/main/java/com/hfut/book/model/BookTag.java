package com.hfut.book.model;

import lombok.Data;
import java.io.Serializable;

/**
 * 图书标签的实体
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */

@Data
public class BookTag implements Serializable {

    private Integer num;
    private Integer bid;
    private String label;
}
