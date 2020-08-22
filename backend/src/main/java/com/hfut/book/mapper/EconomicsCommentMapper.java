package com.hfut.book.mapper;

import com.github.pagehelper.Page;
import com.hfut.book.model.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 经管类书籍相关评论的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface EconomicsCommentMapper {

    /**
     * 通过书本id来查询前3个评论
     *
     * @param bid 书籍id
     * @return 查询的结果
     */
    @Select("SELECT * FROM economics_comment WHERE bid = #{bid} LIMIT 0, 3")
    List<Comment> findThreeEconomicsCommentsByBid(Integer bid);

    /**
     * 通过书本id进行分页且按照发表时间降序查询所有经管类书籍的评论
     *
     * @param bid 书籍id
     * @return 查询的结果
     */
    @Select("SELECT * FROM economics_comment WHERE bid = #{bid} ORDER BY pubdate DESC")
    Page<Comment> findEconomicsCommentsByBid(Integer bid);

    /**
     * 存储经管类书籍评论的信息
     *
     * @param comment 评论内容
     */
    @Insert("INSERT INTO economics_comment(bid, uid, avatarUrl, nickName, score, pubdate, summary) " +
            "VALUES(#{bid}, #{uid}, #{avatarUrl}, #{nickName}, #{score}, #{pubdate}, #{summary})")
    void saveEconomicsComment(Comment comment);

    /**
     * 根据用户id更新用户所发评论的头像和昵称
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     * @param nickName  用户昵称
     */
    @Update("UPDATE economics_comment SET avatarUrl = #{avatarUrl}, nickName = #{nickName} " +
            "WHERE uid = #{uid}")
    void updateEconomicsCommentsAvatarUrlAndNickNameByUid(String uid, String avatarUrl, String nickName);

    /**
     * 根据用户id更新用户所发评论的头像
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     */
    @Update("UPDATE economics_comment SET avatarUrl = #{avatarUrl} WHERE " +
            "uid = #{uid}")
    void updateEconomicsCommentsAvatarUrlByUid(String uid, String avatarUrl);

    /**
     * 根据用户id更新用户所发评论的昵称
     *
     * @param uid      用户id
     * @param nickName 用户昵称
     */
    @Update("UPDATE economics_comment SET nickName = #{nickName} WHERE " +
            "uid = #{uid}")
    void updateEconomicsCommentsNickNameByUid(String uid, String nickName);

    /**
     * 删除用户的评论
     *
     * @param uid     用户id
     * @param pubdate 用户评论时间
     * @param score   用户评分
     * @param summary 用户评论内容
     */
    @Delete("DELETE FROM economics_comment WHERE uid = #{uid} AND pubdate = #{pubdate} AND " +
            "score = #{score} AND summary = #{summary}")
    void deleteEconomicsCommentByUidPubdateScoreSummary(
            String uid, String pubdate, String score, String summary);
}
