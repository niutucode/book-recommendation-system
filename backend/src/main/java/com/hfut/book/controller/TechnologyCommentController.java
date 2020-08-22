package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.TechnologyCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科技类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class TechnologyCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    TechnologyCommentService technologyCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/technology/comment/{bid}")
    public List<Comment> getThreeTechnologyCommentsByBid(@PathVariable("bid") Integer bid) {
        return technologyCommentService.queryThreeTechnologyCommentsByBid(bid);
    }

    @GetMapping("/page/technology/comment/{start}&{limit}&{bid}")
    public List<Object> getTechnologyCommentsByBid(@PathVariable("start") Integer start,
                                                   @PathVariable("limit") Integer limit,
                                                   @PathVariable("bid") Integer bid) {
        return technologyCommentService.queryTechnologyCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/technology", consumes = "application/x-www-form-urlencoded")
    public String conserveTechnologyComment(Comment comment) {
        Boolean conserveResult = technologyCommentService.preserveTechnologyComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
