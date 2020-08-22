package com.hfut.book.mapper;

import com.github.pagehelper.Page;
import com.hfut.book.model.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 所有评论的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface AllCommentMapper {

    /**
     * 存储评论的信息
     *
     * @param comment 评论信息
     */
    @Insert("INSERT INTO all_comment(bid, uid, type, avatarUrl, nickName, score, pubdate, " +
            "summary) VALUES(#{bid}, #{uid}, #{type}, #{avatarUrl}, #{nickName}, #{score}, #{pubdate}, " +
            "#{summary})")
    Integer saveAllComment(Comment comment);

    /**
     * 删除用户的评论
     *
     * @param uid     用户id
     * @param pubdate 用户评论时间
     * @param score   用户评分
     * @param summary 用户评论内容
     */
    @Delete("DELETE FROM all_comment WHERE uid = #{uid} AND pubdate = #{pubdate} AND score = " +
            "#{score} AND summary = #{summary}")
    void deleteAllCommentByUidPubdateScoreSummary(
            String uid, String pubdate, String score, String summary);

    /**
     * 通过用户id进行分页并按照发布时间降序查询当前用户的所有评论
     *
     * @param uid 用户id
     * @return 查询的结果
     */
    @Select("SELECT * FROM all_comment WHERE uid = #{uid} ORDER BY pubdate DESC")
    Page<Comment> findAllCommentsByUid(String uid);

    /**
     * 通过用户id查询所发评论的图书类型
     *
     * @param uid 用户id
     * @return 查询的结果
     */
    @Select("SELECT type FROM all_comment WHERE uid = #{uid}")
    List<String> findAllCommentsTypeByUid(String uid);

    /**
     * 通过用户id、评论发表日期、评分、评论内容查找所发评论的图书类型和图书id
     *
     * @param uid     用户id
     * @param pubdate 评论发表日期
     * @param score   评分
     * @param summary 评论内容
     * @return 查询的结果
     */
    @Select("SELECT type, bid FROM all_comment WHERE uid = #{uid} AND pubdate = #{pubdate} AND " +
            "score = #{score} AND summary = #{summary}")
    Comment findAllCommentTypeBidByUidPubdateScoreSummary(
            String uid, String pubdate, String score, String summary);

    /**
     * 根据用户id更新用户所发评论中的头像和昵称
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     * @param nickName  用户昵称
     */
    @Update("UPDATE all_comment SET avatarUrl = #{avatarUrl}, nickName = #{nickName} WHERE " +
            "uid = #{uid}")
    void updateAllCommentsAvatarUrlAndNickNameByUid(String uid, String avatarUrl, String nickName);

    /**
     * 根据用户id更新用户所发评论中的头像
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     */
    @Update("UPDATE all_comment SET avatarUrl = #{avatarUrl} WHERE " +
            "uid = #{uid}")
    void updateAllCommentsAvatarUrlByUid(String uid, String avatarUrl);

    /**
     * 根据uid更新用户所发评论中的昵称
     *
     * @param uid      用户id
     * @param nickName 用户昵称
     */
    @Update("UPDATE all_comment SET nickName = #{nickName} WHERE " +
            "uid = #{uid}")
    void updateAllCommentsNickNameByUid(String uid, String nickName);
}
