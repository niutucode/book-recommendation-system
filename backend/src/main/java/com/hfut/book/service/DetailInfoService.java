package com.hfut.book.service;

import com.hfut.book.component.KnnComponent;
import com.hfut.book.mapper.BookTagMapper;
import com.hfut.book.mapper.KeyInfoMapper;
import com.hfut.book.model.BookTag;
import com.hfut.book.model.DetailInfo;
import com.hfut.book.mapper.DetailInfoMapper;
import com.hfut.book.model.KeyInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 详细信息的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class DetailInfoService {

    @Resource
    DetailInfoMapper detailInfoMapper;

    @Resource
    BookTagMapper bookTagMapper;

    @Resource
    KeyInfoMapper keyInfoMapper;

    @Resource
    KnnComponent knnComponent;

    @Cacheable(value = "queryDetailInfosByBid", key = "#bid")
    public DetailInfo queryDetailInfosByBid(Integer bid) {
        return detailInfoMapper.findDetailInfosByBid(bid);
    }

    @Cacheable(value = "acquireTenSimilarityKeyInfos", key = "#bid")
    public List<KeyInfo> acquireTenSimilarityKeyInfos(Integer bid) {
        String[] labels = bookTagMapper.findLabelsByBid(bid).split(",");
        Map<Integer, String[]> testSet = new HashMap<>(1);
        testSet.put(bid, labels);
        List<Integer> similarityBids = knnComponent.obtainTenRecommendBids(testSet);
        List<KeyInfo> keyInfoList = new ArrayList<>();
        for (Integer similarityBid : similarityBids) {
            keyInfoList.add(keyInfoMapper.findKeyInfoByBid(similarityBid));
        }
        return keyInfoList;
    }
}
