package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.MotivationalCommentMapper;
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
 * 励志成功类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class MotivationalCommentService {

    @Resource
    MotivationalCommentMapper motivationalCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeMotivationalCommentsByBid", key = "#bid")
    public List<Comment> queryThreeMotivationalCommentsByBid(Integer bid) {
        return motivationalCommentMapper.findThreeMotivationalCommentsByBid(bid);
    }

    @Cacheable(value = "queryMotivationalCommentsByBid", key = "#start+#bid")
    public List<Object> queryMotivationalCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> motivationalCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> motivationalCommentPage = motivationalCommentMapper.findMotivationalCommentsByBid(bid);
        motivationalCommentList.add(page.getPages());
        motivationalCommentList.add(motivationalCommentPage);
        return motivationalCommentList;
    }

    @CacheEvict(value = {"queryThreeMotivationalCommentsByBid", "queryMotivationalCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveMotivationalComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            motivationalCommentMapper.saveMotivationalComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryThreeMotivationalCommentsByBid", "queryMotivationalCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeMotivationComment(String uid, String pubdate, String score, String summary) {
        try {
            motivationalCommentMapper.deleteMotivationalCommentByUidPubdateScoreSummary(
                    uid, pubdate, score, summary);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @CacheEvict(value = {"queryThreeLiteratureCommentsByBid", "queryLiteratureCommentsByBid"},
            beforeInvocation = true)
    public void deleteLiteratureCommentCache() {
        System.out.println("======");
    }
}
