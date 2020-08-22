package com.hfut.book.component;

import com.hfut.book.mapper.BookTagMapper;
import com.hfut.book.model.BookTag;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Knn算法组件
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Component
public class KnnComponent {

    @Resource
    BookTagMapper bookTagMapper;

    /**
     * 推荐关键信息的数量
     */
    final int RECOMMEND_COUNT = 10;

    /**
     * 获取所有书本标签的样本集
     *
     * @return 样本集
     */
    private Map<Integer, String[]> obtainSampleSet() {
        // 将所有书本id和书本标签对应起来放到map集合中
        List<BookTag> bookTags = bookTagMapper.findAllBookTagsExceptNum();
        Map<Integer, String[]> sampleSet = new TreeMap<>();
        for (BookTag bookTag : bookTags) {
            String[] labels = bookTag.getLabel().split(",");
            sampleSet.put(bookTag.getBid(), labels);
        }
        return sampleSet;
    }

    /**
     * 获取10本推荐的书籍的id
     *
     * @param testSet 书籍的测试集
     * @return 10本书籍的id
     */
    public List<Integer> obtainTenRecommendBids(Map<Integer, String[]> testSet) {
        Map<Integer, String[]> sampleSet = obtainSampleSet();

        // 将测试集拆分成bid集合和label集合
        Set<String> testLabels = new HashSet<>();
        List<Integer> testBids = new ArrayList<>();
        for (Map.Entry<Integer, String[]> testSetData : testSet.entrySet()) {
            testBids.add(testSetData.getKey());
            Collections.addAll(testLabels, testSetData.getValue());
        }

        double baseCount = testLabels.size();
        System.out.println("==========================" + baseCount);

        // 将每本书的标签和测试集的标签进行比较，得到与测试集标签的重复率
        // 将当前书本的id和重复率放入map集合中
        double distance = 0;
        Map<Integer, Double> repetitiveRate = new TreeMap<>();
        for (Map.Entry<Integer, String[]> sampleSetData : sampleSet.entrySet()) {
            if (testBids.contains(sampleSetData.getKey())) {
                continue;
            }
            int count = 0;
            Set<String> tempLabels = new HashSet<>(testLabels);
            for (String sampleLabel : sampleSetData.getValue()) {
                if (!tempLabels.add(sampleLabel)) {
                    count++;
                }
            }
            distance = 1.0 - (count / baseCount);
            repetitiveRate.put(sampleSetData.getKey(), distance);
        }

        // 将距离按从近到远排序
        List<Map.Entry<Integer, Double>> repetitiveRateValue = new LinkedList<>(repetitiveRate.entrySet());
        repetitiveRateValue.sort(Map.Entry.comparingByValue());
        List<Integer> recommendBids = new ArrayList<>();
        for (int i = 0; i < RECOMMEND_COUNT; i++) {
            recommendBids.add(repetitiveRateValue.get(i).getKey());
        }
        return recommendBids;
    }
}
