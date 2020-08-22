package com.hfut.book.mapper;


import com.hfut.book.model.PhoneUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 手机用户的持久层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public interface PhoneUserMapper {

    /**
     * 存储手机用户
     *
     * @param phoneUser 手机用户
     */
    @Insert("INSERT INTO phone_user(phoneNumber, avatarUrl) VALUES(#{phoneNumber}, #{avatarUrl})")
    void savePhoneUser(PhoneUser phoneUser);

    /**
     * 通过手机号码查找用户昵称
     *
     * @param phoneNumber 用户的手机号码
     * @return 查找的结果
     */
    @Select("SELECT nickName FROM phone_user WHERE phoneNumber = #{phoneNumber}")
    String findNickNameByPhoneNumber(String phoneNumber);

    /**
     * 通过手机号码查找用户
     *
     * @param phoneNumber 用户手机号码
     * @return 查找的结果
     */
    @Select("SELECT * FROM phone_user WHERE phoneNumber = #{phoneNumber}")
    PhoneUser findPhoneUserByPhoneNumber(String phoneNumber);

    /**
     * 通过用户手机号码查询用户头像
     *
     * @param phoneNumber 用户手机号
     * @return 用户头像
     */
    @Select("SELECT avatarUrl FROM phone_user WHERE phoneNumber = #{phoneNumber}")
    String findAvatarUrlByPhoneNumber(String phoneNumber);

    /**
     * 通过用户手机号码更新用户头像
     *
     * @param phoneNumber 用户手机号码
     * @param avatarUrl   用户头像
     */
    @Update("UPDATE phone_user SET avatarUrl = #{avatarUrl} WHERE phoneNumber = #{phoneNumber}")
    void updateAvatarUrlByPhoneNumber(String phoneNumber, String avatarUrl);

    /**
     * 通过用户手机号码更新用户信息
     *
     * @param phoneUser 用户手机号
     */
    @Update("UPDATE phone_user SET nickName = #{nickName}, gender = #{gender}, " +
            "country = #{country}, province = #{province}, city = #{city}, language = #{language} " +
            "WHERE phoneNumber = #{phoneNumber}")
    void updatePhoneUserByPhoneNumber(PhoneUser phoneUser);
}
