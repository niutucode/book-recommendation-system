package com.hfut.book.service;

import com.hfut.book.mapper.CommentTotalMapper;
import com.hfut.book.model.CommentTotal;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 评论数的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class CommentTotalService {

    @Resource
    CommentTotalMapper commentTotalMapper;

    @Cacheable(value = "queryCommentTotalByBid", key = "#bid")
    public Integer queryCommentTotalByBid(Integer bid) {
        return commentTotalMapper.findCommentTotalByBid(bid);
    }

    /**
     * 更新评论总数
     *
     * @param bid      书籍id
     * @param increase true:增加    false:减少
     */
    @CacheEvict(value = "queryCommentTotalByBid", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean renewalCommentTotalByBid(Integer bid, Boolean increase) {
        try {
            int count = commentTotalMapper.findCommentTotalByBid(bid);
            if (count > 0) {
                count = increase ? count + 1 : count - 1;
                CommentTotal commentTotal = new CommentTotal();
                commentTotal.setBid(bid);
                commentTotal.setCount(count);
                commentTotalMapper.updateCommentTotalByBid(commentTotal);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
