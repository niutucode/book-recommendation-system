package com.hfut.book.component;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 阿里云短信服务组件
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Component
public class AliyunSmsComponent {

    /**
     * AccessID
     */
    @Value("${aliyun.sms.access.id}")
    private String id;

    /**
     * AccessSecret
     */
    @Value("${aliyun.sms.access.secret}")
    private String secret;

    @Resource
    RedisTemplate<String, String> redisTemplate;

    /**
     * API错误码
     */
    final String RESPONSE_CODE = "OK";

    /**
     * 获取验证码
     *
     * @param phoneNumber 手机号码
     * @return 验证码是否获取成功
     */
    public Boolean obtainAuthCode(String phoneNumber) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", id, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "书虫");
        request.putQueryParameter("TemplateCode", "SMS_187943866");
        // 产生随机码
        String authCode = String.valueOf((int)((Math.random() * 9 + 1) * 100000));
        request.putQueryParameter("TemplateParam", "{code: " + authCode + "}");
        try {
            // 返回响应的JSON数据
            CommonResponse response = client.getCommonResponse(request);
            JSONObject responseJson = JSONObject.parseObject(response.getData());
            String responseCode = responseJson.getString("Code");
            // 根据API错误码判断是否发送成功
            if (RESPONSE_CODE.equals(responseCode)) {
                // 存入Redis，有效期为5分钟
                redisTemplate.opsForValue().set(phoneNumber, authCode, 5, TimeUnit.MINUTES);
                return true;
            } else {
                return false;
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
