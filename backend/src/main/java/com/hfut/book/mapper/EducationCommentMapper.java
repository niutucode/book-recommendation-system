package com.hfut.book.mapper;

import com.github.pagehelper.Page;
import com.hfut.book.model.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 教育类书籍相关评论的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface EducationCommentMapper {

    /**
     * 通过书本id来查询前3个评论
     *
     * @param bid 书籍id
     * @return 查询的结果
     */
    @Select("SELECT * FROM education_comment WHERE bid = #{bid} LIMIT 0, 3")
    List<Comment> findThreeEducationCommentsByBid(Integer bid);

    /**
     * 通过书本id进行分页且按照发表时间降序查询所有教育类书籍的评论
     *
     * @param bid 书籍id
     * @return 查询的结果
     */
    @Select("SELECT * FROM education_comment WHERE bid = #{bid} ORDER BY pubdate DESC")
    Page<Comment> findEducationCommentsByBid(Integer bid);

    /**
     * 存储教育类书籍评论的信息
     *
     * @param comment 评论内容
     */
    @Insert("INSERT INTO education_comment(bid, uid, avatarUrl, nickName, score, pubdate, summary) " +
            "VALUES(#{bid}, #{uid}, #{avatarUrl}, #{nickName}, #{score}, #{pubdate}, #{summary})")
    void saveEducationComment(Comment comment);

    /**
     * 根据用户id更新用户所发评论的头像和昵称
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     * @param nickName  用户昵称
     */
    @Update("UPDATE education_comment SET avatarUrl = #{avatarUrl}, nickName = #{nickName} " +
            "WHERE uid = #{uid}")
    void updateEducationCommentsAvatarUrlAndNickNameByUid(String uid, String avatarUrl, String nickName);
    
    /**
     * 根据用户id更新用户所发评论的头像
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     */
    @Update("UPDATE education_comment SET avatarUrl = #{avatarUrl} WHERE " +
            "uid = #{uid}")
    void updateEducationCommentsAvatarUrlByUid(String uid, String avatarUrl);
    
    /**
     * 根据用户id更新用户所发评论的昵称
     *
     * @param uid      用户id
     * @param nickName 用户昵称
     */
    @Update("UPDATE education_comment SET nickName = #{nickName} WHERE " +
            "uid = #{uid}")
    void updateEducationCommentsNickNameByUid(String uid, String nickName);

    /**
     * 删除用户的评论
     *
     * @param uid     用户id
     * @param pubdate 用户评论时间
     * @param score   用户评分
     * @param summary 用户评论内容
     */
    @Delete("DELETE FROM education_comment WHERE uid = #{uid} AND pubdate = #{pubdate} AND " +
            "score = #{score} AND summary = #{summary}")
    void deleteEducationCommentByUidPubdateScoreSummary(
            String uid, String pubdate, String score, String summary);
}
