package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.ComputerCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 计算机类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class ComputerCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    ComputerCommentService computerCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/computer/comment/{bid}")
    public List<Comment> getThreeComputerCommentsByBid(@PathVariable("bid") Integer bid) {
        return computerCommentService.queryThreeComputerCommentsByBid(bid);
    }

    @GetMapping("/page/computer/comment/{start}&{limit}&{bid}")
    public List<Object> getComputerCommentsByBid(@PathVariable("start") Integer start,
                                                 @PathVariable("limit") Integer limit,
                                                 @PathVariable("bid") Integer bid) {
        return computerCommentService.queryComputerCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/computer", consumes = "application/x-www-form-urlencoded")
    public String conserveComputerComment(Comment comment) {
        Boolean conserveResult = computerCommentService.preserveComputerComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
