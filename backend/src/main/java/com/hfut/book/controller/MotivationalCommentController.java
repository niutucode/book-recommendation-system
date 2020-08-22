package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.MotivationalCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 励志成功类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class MotivationalCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    MotivationalCommentService motivationalCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/motivational/comment/{bookId}")
    public List<Comment> getThreeMotivationalCommentsByBid(@PathVariable("bookId") Integer bid) {
        return motivationalCommentService.queryThreeMotivationalCommentsByBid(bid);
    }

    @GetMapping("/page/motivational/comment/{start}&{limit}&{bookId}")
    public List<Object> getMotivationalCommentsByBid(@PathVariable("start") Integer start,
                                                     @PathVariable("limit") Integer limit,
                                                     @PathVariable("bookId") Integer bid) {
        return motivationalCommentService.queryMotivationalCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/motivational", consumes = "application/x-www-form-urlencoded")
    public String conserveMotivationalComment(Comment comment) {
        Boolean conserveResult = motivationalCommentService.preserveMotivationalComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
