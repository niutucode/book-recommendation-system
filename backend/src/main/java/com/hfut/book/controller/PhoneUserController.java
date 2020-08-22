package com.hfut.book.controller;

import com.hfut.book.model.PhoneUser;
import com.hfut.book.service.PhoneUserService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 手机用户的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class PhoneUserController {

    @Resource
    PhoneUserService phoneUserService;

    @PostMapping("/authcode/{phoneNumber}")
    public String sendAuthCode(@PathVariable("phoneNumber") String phoneNumber) {
        Boolean sendResult = phoneUserService.sendAuthCode(phoneNumber);
        return sendResult ? ResponseCode.send(true) : ResponseCode.send(false);
    }

    @PostMapping("/userverify/{phoneNumber}&{authCode}")
    public String userVerify(@PathVariable("phoneNumber") String phoneNumber,
                            @PathVariable("authCode") String authCode) {
        Boolean verifyResult = phoneUserService.userVerify(phoneNumber, authCode);
        return verifyResult ? ResponseCode.verify(true) : ResponseCode.verify(false);
    }

    @PostMapping("/save/phoneuser/{phoneNumber}")
    public String conservePhoneUser(@PathVariable("phoneNumber") String phoneNumber) {
        Boolean conserveResult = phoneUserService.preservePhoneUser(phoneNumber);
        return conserveResult ? ResponseCode.insert(true) : ResponseCode.insert(false);
    }

    @PostMapping(value = "/update/avatarurl", consumes = "multipart/form-data")
    public String renovateAvatarUrl(MultipartFile multipartFile, String phoneNumber) {
        Boolean renovateResult = phoneUserService.renewalAvatarUrlByPhoneNumber(
                phoneNumber, multipartFile);
        return renovateResult ? ResponseCode.update(true) : ResponseCode.update(false);
    }

    @GetMapping("/phoneuser/{phoneNumber}")
    public PhoneUser getPhoneUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return phoneUserService.queryPhoneUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("/avatarurl/nickname/{phoneNumber}")
    public List<String> getAvatarUrlAndNickNameByPhoneNumber(@PathVariable("phoneNumber")
                                                                     String phoneNumber) {
        return phoneUserService.queryAvatarUrlAndNickNameByPhoneNumber(phoneNumber);
    }

    @PutMapping(value = "/update/phoneuser", consumes = "application/x-www-form-urlencoded")
    public String renovatePhoneUser(PhoneUser phoneUser) {
        Boolean renovateResult = phoneUserService.renewalPhoneUserByPhoneNumber(phoneUser);
        return renovateResult ? ResponseCode.update(true) : ResponseCode.update(false);
    }
}
