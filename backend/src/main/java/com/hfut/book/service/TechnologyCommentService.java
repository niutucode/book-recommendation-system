package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.TechnologyCommentMapper;
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
 * 科技类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class TechnologyCommentService {

    @Resource
    TechnologyCommentMapper technologyCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeTechnologyCommentsByBid", key = "#bid")
    public List<Comment> queryThreeTechnologyCommentsByBid(Integer bid) {
        return technologyCommentMapper.findThreeTechnologyCommentsByBid(bid);
    }

    @Cacheable(value = "queryTechnologyCommentsByBid", key = "#start+#bid")
    public List<Object> queryTechnologyCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> technologyCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> technologyCommentPage = technologyCommentMapper.findTechnologyCommentsByBid(bid);
        technologyCommentList.add(page.getPages());
        technologyCommentList.add(technologyCommentPage);
        return technologyCommentList;
    }

    @CacheEvict(value = {"queryThreeTechnologyCommentsByBid", "queryTechnologyCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveTechnologyComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            technologyCommentMapper.saveTechnologyComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryThreeTechnologyCommentsByBid", "queryTechnologyCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeTechnologyComment(String uid, String pubdate, String score, String summary) {
        try {
            technologyCommentMapper.deleteTechnologyCommentByUidPubdateScoreSummary(
                    uid, pubdate, score, summary);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @CacheEvict(value = {"queryThreeTechnologyCommentsByBid", "queryTechnologyCommentsByBid"},
            beforeInvocation = true)
    public void deleteTechnologyCommentCache() {
        System.out.println("======");
    }
}
