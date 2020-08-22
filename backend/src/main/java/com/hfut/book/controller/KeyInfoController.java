package com.hfut.book.controller;

import com.github.pagehelper.Page;
import com.hfut.book.model.KeyInfo;
import com.hfut.book.service.KeyInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 关键信息显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class KeyInfoController {

    @Resource
    KeyInfoService keyInfoService;

    @GetMapping("/index/get/{type}")
    public List<KeyInfo> getTenKeyInfosByType(@PathVariable("type") String type) {
        return keyInfoService.queryTenKeyInfosByType(type);
    }

    @GetMapping("/index/recommend/{uid}")
    public List<KeyInfo> getTenRecommendKeyInfos(@PathVariable("uid") String uid) {
        return keyInfoService.acquireTenRecommendKeyInfos(uid);
    }

    @GetMapping("/list/get/{start}&{limit}&{type}")
    public Page<KeyInfo> getKeyInfosByType(@PathVariable("start") Integer start,
                                           @PathVariable("limit") Integer limit,
                                           @PathVariable("type") String type) {
        return keyInfoService.queryAllKeyInfosByType(start, limit, type);
    }
}
