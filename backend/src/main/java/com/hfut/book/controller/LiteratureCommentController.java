package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.LiteratureCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文学类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class LiteratureCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    LiteratureCommentService literatureCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/literature/comment/{bookId}")
    public List<Comment> getThreeLiteratureCommentsByBid(@PathVariable("bookId") Integer bid) {
        return literatureCommentService.queryThreeLiteratureCommentsByBid(bid);
    }

    @GetMapping("/page/literature/comment/{start}&{limit}&{bookId}")
    public List<Object> getLiteratureCommentsByBid(@PathVariable("start") Integer start,
                                                   @PathVariable("limit") Integer limit,
                                                   @PathVariable("bookId") Integer bid) {
        return literatureCommentService.queryLiteratureCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/literature", consumes = "application/x-www-form-urlencoded")
    public String conserveLiteratureComment(Comment comment) {
        Boolean conserveResult = literatureCommentService.preserveLiteratureComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
