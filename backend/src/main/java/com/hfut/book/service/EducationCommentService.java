package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.EducationCommentMapper;
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
 * 教育类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class EducationCommentService {

    @Resource
    EducationCommentMapper educationCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeEducationCommentsByBid", key = "#bid")
    public List<Comment> queryThreeEducationCommentsByBid(Integer bid) {
        return educationCommentMapper.findThreeEducationCommentsByBid(bid);
    }

    @Cacheable(value = "queryEducationCommentsByBid", key = "#start+#bid")
    public List<Object> queryEducationCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> educationCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> educationCommentPage = educationCommentMapper.findEducationCommentsByBid(bid);
        educationCommentList.add(page.getPages());
        educationCommentList.add(educationCommentPage);
        return educationCommentList;
    }

    @CacheEvict(value = {"queryThreeEducationCommentsByBid", "queryEducationCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveEducationComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            educationCommentMapper.saveEducationComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryThreeEducationCommentsByBid", "queryEducationCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeEducationComment(String uid, String pubdate, String score, String summary) {
        try {
            educationCommentMapper.deleteEducationCommentByUidPubdateScoreSummary(
                    uid, pubdate, score, summary);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @CacheEvict(value = {"queryThreeEducationCommentsByBid", "queryEducationCommentsByBid"},
            beforeInvocation = true)
    public void deleteEducationCommentCache() {
        System.out.println("======");
    }
}
