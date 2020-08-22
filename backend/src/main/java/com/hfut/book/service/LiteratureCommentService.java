package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.LiteratureCommentMapper;
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
 * 文学类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class LiteratureCommentService {

    @Resource
    LiteratureCommentMapper literatureCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeLiteratureCommentsByBid", key = "#bid")
    public List<Comment> queryThreeLiteratureCommentsByBid(Integer bid) {
        return literatureCommentMapper.findThreeLiteratureCommentsByBid(bid);
    }

    @Cacheable(value = "queryLiteratureCommentsByBid", key = "#start+#bid")
    public List<Object> queryLiteratureCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> literatureCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> literatureCommentPage = literatureCommentMapper.findLiteratureCommentsByBid(bid);
        literatureCommentList.add(page.getPages());
        literatureCommentList.add(literatureCommentPage);
        return literatureCommentList;
    }

    @CacheEvict(value = {"queryThreeLiteratureCommentsByBid", "queryLiteratureCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveLiteratureComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            literatureCommentMapper.saveLiteratureComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryThreeLiteratureCommentsByBid", "queryLiteratureCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeLiteratureComment(String uid, String pubdate, String score, String summary) {
        try {
            literatureCommentMapper.deleteLiteratureCommentByUidPubdateScoreSummary(
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
