package com.hfut.book.controller;

import com.hfut.book.model.Comment;
import com.hfut.book.service.ArtCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 艺术类图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class ArtCommentController {

    final boolean INCREASE_COMMENT_TOTAL = true;

    @Resource
    ArtCommentService artCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/art/comment/{bid}")
    public List<Comment> getThreeArtCommentsByBid(@PathVariable("bid") Integer bid) {
        return artCommentService.queryThreeArtCommentsByBid(bid);
    }

    @GetMapping("/page/art/comment/{start}&{limit}&{bid}")
    public List<Object> getArtCommentsByBid(@PathVariable("start") Integer start,
                                            @PathVariable("limit") Integer limit,
                                            @PathVariable("bid") Integer bid) {
        return artCommentService.queryArtCommentsByBid(start, limit, bid);
    }

    @PostMapping(value = "/write/art", consumes = "application/x-www-form-urlencoded")
    public String conserveArtComment(Comment comment) {
        Boolean conserveResult = artCommentService.preserveArtComment(comment);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                comment.getBid(), INCREASE_COMMENT_TOTAL);
        return conserveResult && renovateResult ? ResponseCode.insert(true) :
                ResponseCode.insert(false);
    }
}
