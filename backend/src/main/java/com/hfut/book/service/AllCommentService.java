package com.hfut.book.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hfut.book.mapper.*;
import com.hfut.book.model.Comment;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 所有图书相关评论的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class AllCommentService {

    @Resource
    AllCommentMapper allCommentMapper;

    @Resource
    ArtCommentService artCommentService;

    @Resource
    ChildrenCommentService childrenCommentService;

    @Resource
    ComputerCommentService computerCommentService;

    @Resource
    EconomicsCommentService economicsCommentService;

    @Resource
    EducationCommentService educationCommentService;

    @Resource
    HumanityCommentService humanityCommentService;

    @Resource
    LifeCommentService lifeCommentService;

    @Resource
    LiteratureCommentService literatureCommentService;

    @Resource
    MotivationalCommentService motivationalCommentService;

    @Resource
    TechnologyCommentService technologyCommentService;

    /**
     * 根据用户id分页查询所有评论
     *
     * @param start 开始页
     * @param limit 每页显示数目
     * @param uid   用户id
     * @return 查询的结果
     */
    @Cacheable(value = "queryAllCommentsByUid", key = "#start+#uid")
    public Page<Comment> queryAllCommentsByUid(Integer start, Integer limit, String uid) {
        PageHelper.startPage(start, limit);
        return allCommentMapper.findAllCommentsByUid(uid);
    }

    /**
     * 根据所评论书籍的类型来删除当前用户的评论
     *
     * @param uid     用户id
     * @param pubdate 评论发表日期
     * @param score   评分
     * @param summary 评论内容
     * @param type    所评论书籍的类型
     */
    @CacheEvict(value = "queryAllCommentsByUid", allEntries = true)
    public Boolean removeCommentByType(String uid, String pubdate, String score, String summary,
                                     String type) {
        Boolean result;
        switch (type) {
            case "文学综合馆":
                result = literatureCommentService.removeLiteratureComment(uid, pubdate, score, summary);
                break;
            case "童书馆":
                result = childrenCommentService.removeChildrenComment(uid, pubdate, score, summary);
                break;
            case "教育馆":
                result = educationCommentService.removeEducationComment(uid, pubdate, score, summary);
                break;
            case "人文社科馆":
                result = humanityCommentService.removeHumanityComment(uid, pubdate, score, summary);
                break;
            case "经管综合馆":
                result = economicsCommentService.removeEconomicsComment(uid, pubdate, score, summary);
                break;
            case "励志成功馆":
                result = motivationalCommentService.removeMotivationComment(uid, pubdate, score, summary);
                break;
            case "生活馆":
                result = lifeCommentService.removeLifeComment(uid, pubdate, score, summary);
                break;
            case "艺术馆":
                result = artCommentService.removeArtComment(uid, pubdate, score, summary);
                break;
            case "科技馆":
                result = technologyCommentService.removeTechnologyComment(uid, pubdate, score, summary);
                break;
            default:
                result =computerCommentService.removeComputerComment(uid, pubdate, score, summary);
        }
        return result;
    }

    @CacheEvict(value = "queryAllCommentsByUid", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeAllCommentByUidPubdateScoreSummary(
            String uid, String pubdate, String score, String summary) {
        try {
            Comment comment = allCommentMapper.findAllCommentTypeBidByUidPubdateScoreSummary(uid,
                    pubdate, score, summary);
            // 删除当前用户的评论信息
            allCommentMapper.deleteAllCommentByUidPubdateScoreSummary(uid, pubdate, score, summary);
            // 删除对应种类的书籍评论信息
            return removeCommentByType(uid, pubdate, score, summary, comment.getType());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
