package com.hfut.book.service;

import com.hfut.book.mapper.SearchRecodeMapper;
import com.hfut.book.model.DetailInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 搜索记录的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class SearchRecodeService {

    @Resource
    SearchRecodeMapper searchRecodeMapper;

    @Cacheable(value = "queryDetailInfosByTitleLike", key = "#content")
    public List<DetailInfo> queryDetailInfosByTitleLike(String content) {
        return searchRecodeMapper.findDetailInfosByTitleLike(content);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveSearchRecode(String uid, Integer bid, String bookTitle, String bookType) {
        try {
            Date date = new Date();
            Timestamp searchTime = new Timestamp(date.getTime());
            searchRecodeMapper.saveSearchRecode(uid, bid, bookTitle, bookType, searchTime);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
