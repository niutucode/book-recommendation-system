package com.hfut.book.controller;

import com.hfut.book.service.CommentTotalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Chenzh
 */
@RestController
public class CommentTotalController {

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/comment/total/{bid}")
    public Integer getCommentTotalByBid(@PathVariable("bid") Integer bid) {
        return commentTotalService.queryCommentTotalByBid(bid);
    }
}
