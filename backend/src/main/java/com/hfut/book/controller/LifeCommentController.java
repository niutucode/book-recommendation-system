package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.LifeCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 生活类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class LifeCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    LifeCommentService lifeCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/life/comment/{bid}")
    public List<Comment> getThreeLifeCommentsByBid(@PathVariable("bid") Integer bid) {
        return lifeCommentService.queryThreeLifeCommentsByBid(bid);
    }

    @GetMapping("/page/life/comment/{start}&{limit}&{bid}")
    public List<Object> getLifeCommentsByBid(@PathVariable("start") Integer start,
                                             @PathVariable("limit") Integer limit,
                                             @PathVariable("bid") Integer bid) {
        return lifeCommentService.queryLifeCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/life", consumes = "application/x-www-form-urlencoded")
    public String conserveLifeComment(Comment comment) {
        Boolean conserveResult = lifeCommentService.preserveLifeComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
