package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.ChildrenCommentMapper;
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
 * 童书类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class ChildrenCommentService {

    @Resource
    ChildrenCommentMapper childrenCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeChildrenCommentsByBid", key = "#bid")
    public List<Comment> queryThreeChildrenCommentsByBid(Integer bid) {
        return childrenCommentMapper.findThreeChildrenCommentsByBid(bid);
    }

    @Cacheable(value = "queryChildrenCommentsByBid", key = "#start+#bid")
    public List<Object> queryChildrenCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> childrenCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> childrenCommentPage = childrenCommentMapper.findChildrenCommentsByBid(bid);
        childrenCommentList.add(page.getPages());
        childrenCommentList.add(childrenCommentPage);
        return childrenCommentList;
    }

    @CacheEvict(value = {"queryThreeChildrenCommentsByBid", "queryChildrenCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveChildrenComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            childrenCommentMapper.saveChildrenComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryThreeChildrenCommentsByBid", "queryChildrenCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeChildrenComment(String uid, String pubdate, String score, String summary) {
        try {
            childrenCommentMapper.deleteChildrenCommentByUidPubdateScoreSummary(
                    uid, pubdate, score, summary);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @CacheEvict(value = {"queryThreeChildrenCommentsByBid", "queryChildrenCommentsByBid"},
            beforeInvocation = true)
    public void deleteChildrenCommentCache() {
        System.out.println("======");
    }
}
