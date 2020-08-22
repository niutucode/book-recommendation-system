package com.hfut.book.mapper;

import com.github.pagehelper.Page;
import com.hfut.book.model.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 生活类书籍相关评论的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface LifeCommentMapper {

    /**
     * 通过书本id来查询前3个评论
     *
     * @param bid 书籍id
     * @return 查询的结果
     */
    @Select("SELECT * FROM life_comment WHERE bid = #{bid} LIMIT 0, 3")
    List<Comment> findThreeLifeCommentsByBid(Integer bid);

    /**
     * 通过书本id进行分页且按照发表时间降序查询所有生活类书籍的评论
     *
     * @param bid 书籍id
     * @return 查询的结果
     */
    @Select("SELECT * FROM life_comment WHERE bid = #{bid} ORDER BY pubdate DESC")
    Page<Comment> findLifeCommentsByBid(Integer bid);

    /**
     * 存储生活类书籍评论的信息
     *
     * @param comment 评论内容
     */
    @Insert("INSERT INTO life_comment(bid, uid, avatarUrl, nickName, score, pubdate, summary) " +
            "VALUES(#{bid}, #{uid}, #{avatarUrl}, #{nickName}, #{score}, #{pubdate}, #{summary})")
    void saveLifeComment(Comment comment);

    /**
     * 根据用户id更新用户所发评论的头像和昵称
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     * @param nickName  用户昵称
     */
    @Update("UPDATE life_comment SET avatarUrl = #{avatarUrl}, nickName = #{nickName} " +
            "WHERE uid = #{uid}")
    void updateLifeCommentsAvatarUrlAndNickNameByUid(String uid, String avatarUrl, String nickName);

    /**
     * 根据用户id更新用户所发评论的头像
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     */
    @Update("UPDATE life_comment SET avatarUrl = #{avatarUrl} WHERE " +
            "uid = #{uid}")
    void updateLifeCommentsAvatarUrlByUid(String uid, String avatarUrl);

    /**
     * 根据用户id更新用户所发评论的昵称
     *
     * @param uid      用户id
     * @param nickName 用户昵称
     */
    @Update("UPDATE life_comment SET nickName = #{nickName} WHERE " +
            "uid = #{uid}")
    void updateLifeCommentsNickNameByUid(String uid, String nickName);

    /**
     * 删除用户的评论
     *
     * @param uid     用户id
     * @param pubdate 用户评论时间
     * @param score   用户评分
     * @param summary 用户评论内容
     */
    @Delete("DELETE FROM life_comment WHERE uid = #{uid} AND pubdate = #{pubdate} AND " +
            "score = #{score} AND summary = #{summary}")
    void deleteLifeCommentByUidPubdateScoreSummary(
            String uid, String pubdate, String score, String summary);
}
