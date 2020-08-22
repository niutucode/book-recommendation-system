package com.hfut.book.mapper;

import com.hfut.book.model.CommentTotal;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 评论数的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface CommentTotalMapper {

    /**
     * 根据bid查询评论数
     *
     * @param bid 书籍id
     * @return 查询的结果
     */
    @Select("SELECT count FROM comment_total WHERE bid = #{bid}")
    Integer findCommentTotalByBid(Integer bid);

    /**
     * 根据bid更新评论数
     * @param commentTotal 评论数
     */
    @Update("UPDATE comment_total SET count = #{count} WHERE bid = #{bid}")
    void updateCommentTotalByBid(CommentTotal commentTotal);
}
