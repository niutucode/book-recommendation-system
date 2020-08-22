package com.hfut.book.controller;

import com.hfut.book.service.BookTagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 图书标签的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class BookTagController {

    @Resource
    BookTagService bookTagService;

    @GetMapping("/detail/label/{bid}")
    public String getLabelsByBid(@PathVariable("bid") Integer bid) {
        return bookTagService.queryLabelsByBid(bid);
    }
}
