package com.hfut.book.mapper;

import com.hfut.book.model.DetailInfo;
import org.apache.ibatis.annotations.Select;


/**
 * 详细信息的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface DetailInfoMapper {

    /**
     * 通过书本id查询书籍的详细信息
     *
     * @param bid 书籍的编号
     * @return 查询的结果
     */
    @Select("SELECT * FROM detail_info WHERE bid = #{bid}")
    DetailInfo findDetailInfosByBid(Integer bid);
}
