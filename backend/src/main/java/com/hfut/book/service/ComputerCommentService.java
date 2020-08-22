package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.ComputerCommentMapper;
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
 * 计算机类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class ComputerCommentService {

    @Resource
    ComputerCommentMapper computerCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeComputerCommentsByBid", key = "#bid")
    public List<Comment> queryThreeComputerCommentsByBid(Integer bid) {
        return computerCommentMapper.findThreeComputerCommentsByBid(bid);
    }

    @Cacheable(value = "queryComputerCommentsByBid", key = "#start+#bid")
    public List<Object> queryComputerCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> computerCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> computerCommentPage = computerCommentMapper.findComputerCommentsByBid(bid);
        computerCommentList.add(page.getPages());
        computerCommentList.add(computerCommentPage);
        return computerCommentList;
    }

    @CacheEvict(value = {"queryThreeComputerCommentsByBid", "queryComputerCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveComputerComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            computerCommentMapper.saveComputerComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryThreeComputerCommentsByBid", "queryComputerCommentsByBid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeComputerComment(String uid, String pubdate, String score, String summary) {
        try {
            computerCommentMapper.deleteComputerCommentByUidPubdateScoreSummary(
                    uid, pubdate, score, summary);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @CacheEvict(value = {"queryThreeComputerCommentsByBid", "queryComputerCommentsByBid"},
            beforeInvocation = true)
    public void deleteComputerCommentCache() {
        System.out.println("======");
    }
}
