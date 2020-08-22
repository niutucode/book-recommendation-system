package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.EconomicsCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 经管类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class EconomicsCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    EconomicsCommentService economicsCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/economics/comment/{bid}")
    public List<Comment> getThreeEconomicsCommentsByBid(@PathVariable("bid") Integer bid) {
        return economicsCommentService.queryThreeEconomicsCommentsByBid(bid);
    }

    @GetMapping("/page/economics/comment/{start}&{limit}&{bid}")
    public List<Object> getEconomicsCommentsByBid(@PathVariable("start") Integer start,
                                                  @PathVariable("limit") Integer limit,
                                                  @PathVariable("bid") Integer bid) {
        return economicsCommentService.queryEconomicsCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/economics", consumes = "application/x-www-form-urlencoded")
    public String conserveEconomicsComment(Comment comment) {
        Boolean conserveResult = economicsCommentService.preserveEconomicsComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
