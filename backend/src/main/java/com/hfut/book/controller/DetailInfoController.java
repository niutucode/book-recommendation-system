package com.hfut.book.controller;

import com.hfut.book.component.KnnComponent;
import com.hfut.book.model.DetailInfo;
import com.hfut.book.model.KeyInfo;
import com.hfut.book.service.DetailInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 详细信息的显示层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@RestController
public class DetailInfoController {

    @Resource
    DetailInfoService detailInfoService;

    @GetMapping("/detail/get/{bid}")
    public DetailInfo getDetailInfosByBid(@PathVariable("bid") Integer bid) {
        return detailInfoService.queryDetailInfosByBid(bid);
    }

    @GetMapping("/detail/similarity/{bid}")
    public List<KeyInfo> getTenSimilarityKeyInfos(@PathVariable("bid") Integer bid) {
        return detailInfoService.acquireTenSimilarityKeyInfos(bid);
    }
}
