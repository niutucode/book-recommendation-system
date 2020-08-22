package com.hfut.book.util;

/**
 * 响应码
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
public class ResponseCode {

    /**
     * 无参私有构造器
     */
    private ResponseCode() {}

    /**
     * 更新
     *
     * @param status 更新状态
     * @return 是否更新成功
     */
    public static String update(Boolean status) {
        return status ? "success" : "failed";
    }

    /**
     * 添加
     *
     * @param status 添加状态
     * @return 是否添加成功
     */
    public static String insert(Boolean status) {
        return status ? "success" : "failed";
    }

    /**
     * 删除
     *
     * @param status 删除状态
     * @return 是否删除成功
     */
    public static String delete(Boolean status) {
        return status ? "success" : "failed";
    }

    /**
     * 发送信息
     *
     * @param status 是否已获得验证码
     * @return 发送信息是否成功
     */
    public static String send(Boolean status) {
        return status ? "success" : "failed";
    }

    /**
     * 用户验证
     *
     * @param status 用户验证码是否输入正确
     * @return 是否是合法用户
     */
    public static String verify(Boolean status) {
        return status ? "success" : "failed";
    }
}
