package com.hfut.book.mapper;

import com.hfut.book.model.BookTag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 图书标签的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface BookTagMapper {

    /**
     * 通过图书id查询图书的标签
     *
     * @param bid 图书id
     * @return 查询的结果
     */
    @Select("SELECT label FROM book_tag WHERE bid = #{bid}")
    String findLabelsByBid(Integer bid);

    /**
     * 查询所有图书
     *
     * @return 查询的结果
     */
    @Select("SELECT bid, label FROM book_tag")
    List<BookTag> findAllBookTagsExceptNum();
}
