package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.component.KnnComponent;
import com.hfut.book.mapper.BookTagMapper;
import com.hfut.book.mapper.SearchRecodeMapper;
import com.hfut.book.model.KeyInfo;
import com.hfut.book.mapper.KeyInfoMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 关键信息业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class KeyInfoService {

    final int LITTLE_SEARCH_RECODE = 3;
    final int NONE_SEARCH_RECODE = 0;

    @Resource
    KeyInfoMapper keyInfoMapper;

    @Resource
    SearchRecodeMapper searchRecodeMapper;

    @Resource
    BookTagMapper bookTagMapper;

    @Resource
    KnnComponent knnComponent;

    @Cacheable(value = "queryTenKeyInfosByType", key = "#type")
    public List<KeyInfo> queryTenKeyInfosByType(String type) {
        return keyInfoMapper.findTenKeyInfosByType(type);
    }

    /**
     * 根据书的种类分页查询所有图书的关键信息
     *
     * @param start 开始页 1-N
     * @param limit 每页显示信息的数量
     * @param type  书的种类
     * @return 查询的结果
     */
    @Cacheable(value = "queryAllKeyInfosByType", key = "#start + #type")
    public Page<KeyInfo> queryAllKeyInfosByType(Integer start, Integer limit, String type) {
        PageHelper.startPage(start, limit);
        return keyInfoMapper.findAllKeyInfosByType(type);
    }

    /**
     * 推荐10本书籍给用户
     *
     * @param uid 用户id
     * @return 推荐的结果
     */
    public List<KeyInfo> acquireTenRecommendKeyInfos(String uid) {
        int searchRecodeCount = searchRecodeMapper.findBidsByUid(uid).size();
        List<KeyInfo> keyInfoList = new ArrayList<>();
        // 搜索记录大于3条
        if (searchRecodeCount >= LITTLE_SEARCH_RECODE) {
            System.out.println("***** 搜索记录大于等于3条 *****");
            List<Integer> bids = searchRecodeMapper.findLatestThreeBidsByUid(uid);
            Map<Integer, String[]> testSet = new HashMap<>(3);
            for (int i = 0; i < LITTLE_SEARCH_RECODE; i++) {
                Integer bid = bids.get(i);
                String[] labels = bookTagMapper.findLabelsByBid(bid).split(",");
                testSet.put(bid, labels);
            }
            List<Integer> recommendBids = knnComponent.obtainTenRecommendBids(testSet);
            for (Integer recommendBid : recommendBids) {
                keyInfoList.add(keyInfoMapper.findKeyInfoByBid(recommendBid));
            }
        } else if (searchRecodeCount > NONE_SEARCH_RECODE) {
            System.out.println("***** 搜索记录小于3条大于0条 *****");
            Integer bid = searchRecodeMapper.findLatestBidByUid(uid);
            Map<Integer, String[]> testSet = new HashMap<>(1);
            String[] labels = bookTagMapper.findLabelsByBid(bid).split(",");
            testSet.put(bid, labels);
            List<Integer> recommendBids = knnComponent.obtainTenRecommendBids(testSet);
            for (Integer recommendBid : recommendBids) {
                keyInfoList.add(keyInfoMapper.findKeyInfoByBid(recommendBid));
            }
        } else {
            System.out.println("***** 没有搜索记录 *****");
            List<Integer> recommendBids = keyInfoMapper.findTenKeyInfosByScore();
            for (Integer recommendBid : recommendBids) {
                keyInfoList.add(keyInfoMapper.findKeyInfoByBid(recommendBid));
            }
        }
        return keyInfoList;
    }
}
