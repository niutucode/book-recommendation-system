package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.EducationCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教育类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class EducationCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    EducationCommentService educationCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/education/comment/{bid}")
    public List<Comment> getThreeEducationCommentsByBid(@PathVariable("bid") Integer bid) {
        return educationCommentService.queryThreeEducationCommentsByBid(bid);
    }

    @GetMapping("/page/education/comment/{start}&{limit}&{bid}")
    public List<Object> getEducationCommentsByBid(@PathVariable("start") Integer start,
                                                  @PathVariable("limit") Integer limit,
                                                  @PathVariable("bid") Integer bid) {
        return educationCommentService.queryEducationCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/education", consumes = "application/x-www-form-urlencoded")
    public String conserveEducationComment(Comment comment) {
        Boolean conserveResult = educationCommentService.preserveEducationComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
