package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.LifeCommentMapper;
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
 * 生活类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class LifeCommentService {

    @Resource
    LifeCommentMapper lifeCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeLifeCommentsByBid", key = "#bid")
    public List<Comment> queryThreeLifeCommentsByBid(Integer bid) {
        return lifeCommentMapper.findThreeLifeCommentsByBid(bid);
    }

    @Cacheable(value = "queryLifeCommentsByBid", key = "#start+#bid")
    public List<Object> queryLifeCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> lifeCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> lifeCommentPage = lifeCommentMapper.findLifeCommentsByBid(bid);
        lifeCommentList.add(page.getPages());
        lifeCommentList.add(lifeCommentPage);
        return lifeCommentList;
    }

    @CacheEvict(value = {"queryThreeLifeCommentsByBid", "queryLifeCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveLifeComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            lifeCommentMapper.saveLifeComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryThreeLifeCommentsByBid", "queryLifeCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeLifeComment(String uid, String pubdate, String score, String summary) {
        try {
            lifeCommentMapper.deleteLifeCommentByUidPubdateScoreSummary(
                    uid, pubdate, score, summary);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @CacheEvict(value = {"queryThreeLifeCommentsByBid", "queryLifeCommentsByBid"},
            beforeInvocation = true)
    public void deleteLifeCommentCache() {
        System.out.println("======");
    }
}
