package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.HumanityCommentMapper;
import com.hfut.book.model.Comment;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 人文社科类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class HumanityCommentService {

    @Resource
    HumanityCommentMapper humanityCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeHumanityCommentsByBid", key = "#bid")
    public List<Comment> queryThreeHumanityCommentsByBid(Integer bid) {
        return humanityCommentMapper.findThreeHumanityCommentsByBid(bid);
    }

    @Cacheable(value = "queryHumanityCommentsByBid", key = "#start+#bid")
    public List<Object> queryHumanityCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> humanityCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> humanityCommentPage = humanityCommentMapper.findHumanityCommentsByBid(bid);
        humanityCommentList.add(page.getPages());
        humanityCommentList.add(humanityCommentPage);
        return humanityCommentList;
    }

    @CacheEvict(value = {"queryThreeHumanityCommentsByBid", "queryHumanityCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveHumanityComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            humanityCommentMapper.saveHumanityComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryThreeHumanityCommentsByBid", "queryHumanityCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeHumanityComment(String uid, String pubdate, String score, String summary) {
        try {
            humanityCommentMapper.deleteHumanityCommentByUidPubdateScoreSummary(
                    uid, pubdate, score, summary);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @CacheEvict(value = {"queryThreeHumanityCommentsByBid", "queryHumanityCommentsByBid"},
            beforeInvocation = true)
    public void deleteHumanityCommentCache() {
        System.out.println("======");
    }
}
