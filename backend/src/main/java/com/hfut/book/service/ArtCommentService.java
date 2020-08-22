package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.AllCommentMapper;
import com.hfut.book.mapper.ArtCommentMapper;
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
 * 艺术类图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class ArtCommentService {

    @Resource
    ArtCommentMapper artCommentMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Cacheable(value = "queryThreeArtCommentsByBid", key = "#bid")
    public List<Comment> queryThreeArtCommentsByBid(Integer bid) {
        return artCommentMapper.findThreeArtCommentsByBid(bid);
    }

    @Cacheable(value = "queryArtCommentsByBid", key = "#start+#bid")
    public List<Object> queryArtCommentsByBid(Integer start, Integer limit, Integer bid) {
        List<Object> artCommentList = new ArrayList<>();
        Page<Comment> page = PageHelper.startPage(start, limit);
        Page<Comment> artCommentPage = artCommentMapper.findArtCommentsByBid(bid);
        artCommentList.add(page.getPages());
        artCommentList.add(artCommentPage);
        return artCommentList;
    }

    @CacheEvict(value = {"queryArtCommentsByBid", "queryThreeArtCommentsByBid"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveArtComment(Comment comment) {
        try {
            Date date = new Date();
            Timestamp pubdate = new Timestamp(date.getTime());
            comment.setPubdate(pubdate);
            Float score = comment.getScore();
            comment.setScore(score == 0 ? null : score);
            artCommentMapper.saveArtComment(comment);
            allCommentMapper.saveAllComment(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryArtCommentsByBid", "queryThreeArtCommentsByBid"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeArtComment(String uid, String pubdate, String score, String summary) {
        try {
            artCommentMapper.deleteArtCommentByUidPubdateScoreSummary(uid, pubdate, score, summary);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @CacheEvict(value = {"queryArtCommentsByBid", "queryThreeArtCommentsByBid"},
            beforeInvocation = true)
    public void deleteArtCommentCache() {
        System.out.println("======");
    }
}
