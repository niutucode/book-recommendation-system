package com.hfut.book.controller;

import com.hfut.book.model.WeiXinUser;
import com.hfut.book.service.WeiXinUserService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 微信用户的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class  WeiXinUserController {

    @Resource
    WeiXinUserService weiXinUserService;

    @PostMapping(value = "/save/weixinuser", consumes = "application/x-www-form-urlencoded")
    public String conserveWeiXinUser(WeiXinUser weiXinUser) {
        Boolean conserveResult = weiXinUserService.preserveWeiXinUser(weiXinUser);
        return conserveResult ? ResponseCode.insert(true) : ResponseCode.insert(false);
    }

    @PutMapping(value = "/update/weixinuser", consumes = "application/x-www-form-urlencoded")
    public String renovateWeiXinUser(WeiXinUser weiXinUser) {
        Boolean renovateResult = weiXinUserService.renewalWeiXinUser(weiXinUser);
        return renovateResult ? ResponseCode.update(true) : ResponseCode.update(false);
    }
}
