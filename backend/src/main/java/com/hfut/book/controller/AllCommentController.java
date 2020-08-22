package com.hfut.book.controller;

import com.github.pagehelper.Page;
import com.hfut.book.model.Comment;
import com.hfut.book.service.AllCommentService;
import com.hfut.book.service.CommentTotalService;
import com.hfut.book.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 所有图书相关评论的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class AllCommentController {

    final boolean DECREASE_COMMENT_TOTAL = false;

    @Resource
    AllCommentService allCommentService;

    @Resource
    CommentTotalService commentTotalService;

    @GetMapping("/allcomments/{start}&{limit}&{uid}")
    public Page<Comment> getAllCommentsByUid(@PathVariable("start") Integer start,
                                             @PathVariable("limit") Integer limit,
                                             @PathVariable("uid") String uid) {
        return allCommentService.queryAllCommentsByUid(start, limit, uid);
    }

    @DeleteMapping("/delete/comment/{bid}&{uid}&{score}&{pubdate}&{summary}")
    public String cancelCommentByUidPubdateScoreSummary(@PathVariable("bid") Integer bid,
                                                      @PathVariable("uid") String uid,
                                                      @PathVariable("pubdate") String pubdate,
                                                      @PathVariable("score") String score,
                                                      @PathVariable("summary") String summary) {
        Boolean cancelResult = allCommentService.removeAllCommentByUidPubdateScoreSummary(
                uid, pubdate, score, summary);
        Boolean renovateResult = commentTotalService.renewalCommentTotalByBid(
                bid, DECREASE_COMMENT_TOTAL);
        return cancelResult && renovateResult ? ResponseCode.delete(true) :
                ResponseCode.delete(false);
    }
}
