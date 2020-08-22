package com.hfut.book.mapper;

import com.hfut.book.model.WeiXinUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 微信用户的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface WeiXinUserMapper {

    /**
     * 存储用户信息
     *
     * @param weiXinUser 微信用户信息
     */
    @Insert("INSERT INTO weixin_user(uid, nickName, avatarUrl, gender, country, province, " +
            "city, language) VALUES(#{uid}, #{nickName}, #{avatarUrl}, #{gender}, #{country}, " +
            "#{province}, #{city}, #{language})")
    void saveWeiXinUser(WeiXinUser weiXinUser);

    /**
     * 根据用户id查找微信用户
     *
     * @param uid 微信用户id
     * @return 查找的结果
     */
    @Select("SELECT avatarUrl FROM weixin_user WHERE uid = #{uid}")
    String findAvatarUrlByUid(String uid);

    /**
     * 更新微信用户信息
     *
     * @param weiXinUser 微信用户信息
     */
    @Update("UPDATE weixin_user SET nickName = #{nickName}, avatarUrl = #{avatarUrl}, " +
            "gender = #{gender}, country = #{country}, province = #{province}, city = #{city}," +
            "language = #{language} WHERE uid = #{uid}")
    void updateWeiXinUser(WeiXinUser weiXinUser);
}
