package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.HumanityCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 人文社科类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class HumanityCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    HumanityCommentService humanityCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/humanity/comment/{bid}")
    public List<Comment> getThreeHumanityCommentsByBid(@PathVariable("bid") Integer bid) {
        return humanityCommentService.queryThreeHumanityCommentsByBid(bid);
    }

    @GetMapping("/page/humanity/comment/{start}&{limit}&{bid}")
    public List<Object> getHumanityCommentsByBid(@PathVariable("start") Integer start,
                                                 @PathVariable("limit") Integer limit,
                                                 @PathVariable("bid") Integer bid) {
        return humanityCommentService.queryHumanityCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/humanity", consumes = "application/x-www-form-urlencoded")
    public String conserveHumanityComment(Comment comment) {
        Boolean conserveResult = humanityCommentService.preserveHumanityComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
