package com.hfut.book.service;

import com.hfut.book.mapper.BookTagMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 图书标签的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class BookTagService {

    @Resource
    BookTagMapper bookTagMapper;

    @Cacheable(value = "queryLabelsByBid", key = "#bid")
    public String queryLabelsByBid(Integer bid) {
        return bookTagMapper.findLabelsByBid(bid);
    }
}
