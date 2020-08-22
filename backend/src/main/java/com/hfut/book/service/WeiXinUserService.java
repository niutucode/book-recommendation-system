package com.hfut.book.service;

import com.hfut.book.mapper.*;
import com.hfut.book.model.WeiXinUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信用户的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class WeiXinUserService {

    @Resource
    WeiXinUserMapper weiXinUserMapper;

    @Resource
    AllCommentMapper allCommentMapper;

    @Resource
    ArtCommentMapper artCommentMapper;

    @Resource
    ChildrenCommentMapper childrenCommentMapper;

    @Resource
    ComputerCommentMapper computerCommentMapper;

    @Resource
    EconomicsCommentMapper economicsCommentMapper;

    @Resource
    EducationCommentMapper educationCommentMapper;

    @Resource
    HumanityCommentMapper humanityCommentMapper;

    @Resource
    LifeCommentMapper lifeCommentMapper;

    @Resource
    LiteratureCommentMapper literatureCommentMapper;

    @Resource
    MotivationalCommentMapper motivationalCommentMapper;

    @Resource
    TechnologyCommentMapper technologyCommentMapper;

    @Transactional(rollbackFor = Exception.class)
    public Boolean preserveWeiXinUser(WeiXinUser weiXinUser) {
        try {
            String avatarUrl = weiXinUserMapper.findAvatarUrlByUid(weiXinUser.getUid());
            if (avatarUrl == null || "".equals(avatarUrl)) {
                String gender = weiXinUser.getGender();
                weiXinUser.setGender("1".equals(gender) ? "男" : "女");
                weiXinUserMapper.saveWeiXinUser(weiXinUser);
                return true;
            } else {
                return renewalWeiXinUser(weiXinUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据用户id更新用户评论中的头像和昵称
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     * @param nickName  用户昵称
     */
    private void renewalCommentByUid(String uid, String avatarUrl, String nickName) {
        allCommentMapper.updateAllCommentsAvatarUrlAndNickNameByUid(uid, avatarUrl, nickName);
        List<String> types = allCommentMapper.findAllCommentsTypeByUid(uid);
        for (String type : types) {
            renewalCommentByType(uid, avatarUrl, nickName, type);
        }
    }

    /**
     * 根据用户评论书籍的类型更新用户评论的头像和昵称
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     * @param nickName  用户昵称
     * @param type      图书类型
     */
    private void renewalCommentByType(String uid, String avatarUrl, String nickName, String type) {
        switch (type) {
            case "文学综合馆":
                literatureCommentMapper.updateLiteratureCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
                break;
            case "童书馆":
                childrenCommentMapper.updateChildrenCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
                break;
            case "教育馆":
                educationCommentMapper.updateEducationCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
                break;
            case "人文社科馆":
                humanityCommentMapper.updateHumanityCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
                break;
            case "经管综合馆":
                economicsCommentMapper.updateEconomicsCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
                break;
            case "励志成功馆":
                motivationalCommentMapper.updateMotivationalCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
                break;
            case "生活馆":
                lifeCommentMapper.updateLifeCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
                break;
            case "艺术馆":
                artCommentMapper.updateArtCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
                break;
            case "科技馆":
                technologyCommentMapper.updateTechnologyCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
                break;
            default:
                computerCommentMapper.updateComputerCommentsAvatarUrlAndNickNameByUid(
                        uid, avatarUrl, nickName);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean renewalWeiXinUser(WeiXinUser weiXinUser) {
        try {
            String gender = weiXinUser.getGender();
            weiXinUser.setGender("1".equals(gender) ? "男" : "女");
            String uid = weiXinUser.getUid();
            String avatarUrl = weiXinUser.getAvatarUrl();
            String nickName = weiXinUser.getNickName();
            renewalCommentByUid(uid, avatarUrl, nickName);
            weiXinUserMapper.updateWeiXinUser(weiXinUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
