package com.hfut.book.controller;

import com.hfut.book.model.DetailInfo;
import com.hfut.book.service.SearchRecodeService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 搜索记录的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class SearchRecodeController {

    @Resource
    SearchRecodeService searchRecodeService;

    @GetMapping("/search/{content}")
    public List<DetailInfo> getDetailInfosByTitleLike(@PathVariable("content") String content) {
        return searchRecodeService.queryDetailInfosByTitleLike(content);
    }

    @PostMapping(value = "/search/save", consumes = "application/x-www-form-urlencoded")
    public String conserveSearchRecode(String uid, Integer bid, String bookTitle, String bookType) {
        Boolean conserveResult = searchRecodeService.preserveSearchRecode(uid, bid, bookTitle, bookType);
        return conserveResult ? ResponseCode.insert(true) : ResponseCode.insert(false);
    }
}
