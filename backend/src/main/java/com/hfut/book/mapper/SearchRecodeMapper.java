package com.hfut.book.mapper;

import com.hfut.book.model.DetailInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * 搜索记录的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface SearchRecodeMapper {

    /**
     * 通过输入的内容进行模糊查询
     *
     * @param content 键入的内容
     * @return 查询的结果
     */
    @Select("SELECT * FROM detail_info WHERE title LIKE CONCAT('%', #{content}, '%')")
    List<DetailInfo> findDetailInfosByTitleLike(String content);

    /**
     * 查询当前用户最近1次的搜索记录的书籍id值
     *
     * @param uid 用户id
     * @return 查询的结果
     */
    @Select("SELECT bid FROM search_recode WHERE uid = #{uid} ORDER BY date DESC LIMIT 0, 1")
    Integer findLatestBidByUid(String uid);

    /**
     * 查询当前用户最近3次的搜索记录的书籍id值
     *
     * @param uid 用户id
     * @return 查询的结果
     */
    @Select("SELECT bid FROM search_recode WHERE uid = #{uid} ORDER BY date DESC LIMIT 0, 3")
    List<Integer> findLatestThreeBidsByUid(String uid);

    /**
     * 按照当前用户搜索记录的书籍id值
     *
     * @param uid 用户id
     * @return 查询的结果
     */
    @Select("SELECT bid FROM search_recode WHERE uid = #{uid}")
    List<Integer> findBidsByUid(String uid);

    /**
     * 存储当前用户的查询记录
     *
     * @param uid       用户id
     * @param bid       书籍id
     * @param bookTitle 书籍名称
     * @param bookType  书籍类型
     * @param date      查询日期
     */
    @Insert("INSERT INTO search_recode(uid, bid, bookTitle, bookType, date) VALUES(" +
            "#{uid}, #{bid}, #{bookTitle}, #{bookType}, #{date})")
    void saveSearchRecode(String uid, Integer bid, String bookTitle, String bookType, Timestamp date);
}
