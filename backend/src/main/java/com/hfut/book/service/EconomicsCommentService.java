package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.EconomicsCommentMapper;
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
 * 经管类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class EconomicsCommentService {

    @Resource
    EconomicsCommentMapper economicsCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeEconomicsCommentsByBid", key = "#bid")
    public List<Comment> queryThreeEconomicsCommentsByBid(Integer bid) {
        return economicsCommentMapper.findThreeEconomicsCommentsByBid(bid);
    }

    @Cacheable(value = "queryEconomicsCommentsByBid", key = "#start+#bid")
    public List<Object> queryEconomicsCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> economicsCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> economicsCommentPage = economicsCommentMapper.findEconomicsCommentsByBid(bid);
        economicsCommentList.add(page.getPages());
        economicsCommentList.add(economicsCommentPage);
        return economicsCommentList;
    }

    @CacheEvict(value = {"queryThreeEconomicsCommentsByBid", "queryEconomicsCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveEconomicsComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            economicsCommentMapper.saveEconomicsComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryThreeEconomicsCommentsByBid", "queryEconomicsCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeEconomicsComment(String uid, String pubdate, String score, String summary) {
        try {
            economicsCommentMapper.deleteEconomicsCommentByUidPubdateScoreSummary(
                    uid, pubdate, score, summary);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @CacheEvict(value = {"queryThreeEconomicsCommentsByBid", "queryEconomicsCommentsByBid"},
            beforeInvocation = true)
    public void deleteEconomicsCommentCache() {
        System.out.println("======");
    }
}
