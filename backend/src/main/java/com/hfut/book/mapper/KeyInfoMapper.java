package com.hfut.book.mapper;

import com.github.pagehelper.Page;
import com.hfut.book.model.KeyInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 关键信息持久层
 *
 * @author chenzh
 * @email imchenzh@foxmail.com
 */
public interface KeyInfoMapper {

    /**
     * 根据书的种类查询前10本书的关键信息
     *
     * @param type 书的种类
     * @return 查询的结果
     */
    @Select("SELECT * FROM key_info WHERE type = #{type} LIMIT 0, 10")
    List<KeyInfo> findTenKeyInfosByType(String type);

    /**
     * 根据书的种类分页查询所有图书的关键信息
     *
     * @param type 书的种类
     * @return 查询的结果
     */
    @Select("SELECT * FROM key_info WHERE type = #{type}")
    Page<KeyInfo> findAllKeyInfosByType(String type);

    /**
     * 通过书籍id查询图书的关键信息
     *
     * @param bid 书籍id
     * @return 查询的结果
     */
    @Select("SELECT * FROM key_info WHERE bid = #{bid}")
    KeyInfo findKeyInfoByBid(Integer bid);

    /**
     * 查询10个评分最高的书籍的关键信息
     *
     * @return 查询的结果
     */
    @Select("SELECT bid FROM key_info ORDER BY score DESC LIMIT 0, 10")
    List<Integer> findTenKeyInfosByScore();
}
