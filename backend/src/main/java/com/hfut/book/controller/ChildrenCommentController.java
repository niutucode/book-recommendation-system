package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.ChildrenCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 童书类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class ChildrenCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    ChildrenCommentService childrenCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/children/comment/{bid}")
    public List<Comment> getThreeChildrenCommentsByBid(@PathVariable("bid") Integer bid) {
        return childrenCommentService.queryThreeChildrenCommentsByBid(bid);
    }

    @GetMapping("/page/children/comment/{start}&{limit}&{bid}")
    public List<Object> getChildrenCommentsByBid(@PathVariable("start") Integer start,
                                                 @PathVariable("limit") Integer limit,
                                                 @PathVariable("bid") Integer bid) {
        return childrenCommentService.queryChildrenCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/children", consumes = "application/x-www-form-urlencoded")
    public String conserveChildrenComment(Comment comment) {
        Boolean conserveResult = childrenCommentService.preserveChildrenComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
