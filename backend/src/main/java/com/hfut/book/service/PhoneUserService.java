package com.hfut.book.service;

import com.hfut.book.component.AliyunSmsComponent;
import com.hfut.book.component.FastdfsComponent;
import com.hfut.book.mapper.*;
import com.hfut.book.model.PhoneUser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 手机用户的业务层
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Service
public class PhoneUserService {

    final boolean IS_AVATAR_URL = true;
    final boolean IS_NICK_NAME = false;

    @Resource
    AliyunSmsComponent aliyunSmsComponent;

    @Resource
    FastdfsComponent fastdfsComponent;

    @Resource
    PhoneUserMapper phoneUserMapper;

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

    @Resource
    RedisTemplate<String, String> redisTemplate;

    public Boolean sendAuthCode(String phoneNumber) {
        return aliyunSmsComponent.obtainAuthCode(phoneNumber);
    }

    /**
     * 进行用户验证
     *
     * @param phoneNumber 手机号码
     * @param authCode    验证码
     * @return 是否是合法用户
     */
    public Boolean userVerify(String phoneNumber, String authCode) {
        String redisAuthCode = redisTemplate.opsForValue().get(phoneNumber);
        return authCode.equals(redisAuthCode);
    }


    @Transactional(rollbackFor = Exception.class)
    public Boolean preservePhoneUser(String phoneNumber) {
        try {
            String nickName = phoneUserMapper.findNickNameByPhoneNumber(phoneNumber);
            if (nickName == null || "".equals(nickName)) {
                PhoneUser phoneUser = new PhoneUser();
                String avatarUrl = fastdfsComponent.uploadFile("static/default-avatar.png");
                phoneUser.setAvatarUrl(avatarUrl);
                phoneUser.setPhoneNumber(phoneNumber);
                phoneUserMapper.savePhoneUser(phoneUser);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Cacheable(value = "queryPhoneUserByPhoneNumber", key = "#phoneNumber")
    public PhoneUser queryPhoneUserByPhoneNumber(String phoneNumber) {
        return phoneUserMapper.findPhoneUserByPhoneNumber(phoneNumber);
    }

    @Cacheable(value = "queryAvatarUrlAndNickNameByPhoneNumber", key = "#phoneNumber")
    public List<String> queryAvatarUrlAndNickNameByPhoneNumber(String phoneNumber) {
        List<String> avatarUrlAndNickName = new ArrayList<>();
        avatarUrlAndNickName.add(phoneUserMapper.findAvatarUrlByPhoneNumber(phoneNumber));
        avatarUrlAndNickName.add(phoneUserMapper.findNickNameByPhoneNumber(phoneNumber));
        return avatarUrlAndNickName;
    }

    /**
     * 删除旧的头像，上传新的头像，并取得新头像的地址
     *
     * @param multipartFile 头像文件
     * @param phoneNumber   手机号码
     * @return 新头像的地址
     */
    private String acquireAvatarUrl(MultipartFile multipartFile, String phoneNumber) {
        String avatarUrl = phoneUserMapper.findAvatarUrlByPhoneNumber(phoneNumber);
        Boolean result = fastdfsComponent.deleteFile(avatarUrl);
        if (result) {
            return fastdfsComponent.uploadMultipartFile(multipartFile);
        }
        return "";
    }

    /**
     * 更新评论信息的用户头像
     *
     * @param uid       用户id
     * @param message   更新的信息内容
     * @param avatarUrl true:是头像信息   false:是昵称信息
     */
    private void renewalCommentByUid(String uid, String message, Boolean avatarUrl) {
        if (avatarUrl) {
            allCommentMapper.updateAllCommentsAvatarUrlByUid(uid, message);
        } else {
            allCommentMapper.updateAllCommentsNickNameByUid(uid, message);
        }
        List<String> types = allCommentMapper.findAllCommentsTypeByUid(uid);
        for (String type : types) {
            renewalCommentByType(uid, message, avatarUrl, type);
        }
    }

    /**
     * 根据所评论图书的类型来更行用户头像
     *
     * @param uid       用户id
     * @param avatarUrl 用户头像
     * @param type      所评论图书的类型
     */
    private void renewalCommentByType(String uid, String message, Boolean avatarUrl, String type) {
        switch (type) {
            case "文学综合馆":
                new LiteratureCommentService().deleteLiteratureCommentCache();
                if (avatarUrl) {
                    literatureCommentMapper.updateLiteratureCommentsAvatarUrlByUid(uid, message);
                } else {
                    literatureCommentMapper.updateLiteratureCommentsNickNameByUid(uid, message);
                }
                break;
            case "童书馆":
                new ChildrenCommentService().deleteChildrenCommentCache();
                if (avatarUrl) {
                    childrenCommentMapper.updateChildrenCommentsAvatarUrlByUid(uid, message);
                } else {
                    childrenCommentMapper.updateChildrenCommentsNickNameByUid(uid, message);
                }
                break;
            case "教育馆":
                new EducationCommentService().deleteEducationCommentCache();
                if (avatarUrl) {
                    educationCommentMapper.updateEducationCommentsAvatarUrlByUid(uid, message);
                } else {
                    educationCommentMapper.updateEducationCommentsNickNameByUid(uid, message);
                }
                break;
            case "人文社科馆":
                new HumanityCommentService().deleteHumanityCommentCache();
                if (avatarUrl) {
                    humanityCommentMapper.updateHumanityCommentsAvatarUrlByUid(uid, message);
                } else {
                    humanityCommentMapper.updateHumanityCommentsNickNameByUid(uid, message);
                }
                break;
            case "经管综合馆":
                new EconomicsCommentService().deleteEconomicsCommentCache();
                if (avatarUrl) {
                    economicsCommentMapper.updateEconomicsCommentsAvatarUrlByUid(uid, message);
                } else {
                    economicsCommentMapper.updateEconomicsCommentsNickNameByUid(uid, message);
                }
                break;
            case "励志成功馆":
                new MotivationalCommentService().deleteLiteratureCommentCache();
                if (avatarUrl) {
                    motivationalCommentMapper.updateMotivationalCommentsAvatarUrlByUid(uid, message);
                } else {
                    motivationalCommentMapper.updateMotivationalCommentsNickNameByUid(uid, message);
                }
                break;
            case "生活馆":
                new LifeCommentService().deleteLifeCommentCache();
                if (avatarUrl) {
                    lifeCommentMapper.updateLifeCommentsAvatarUrlByUid(uid, message);
                } else {
                    lifeCommentMapper.updateLifeCommentsNickNameByUid(uid, message);
                }
                break;
            case "艺术馆":
                new ArtCommentService().deleteArtCommentCache();
                if (avatarUrl) {
                    artCommentMapper.updateArtCommentsAvatarUrlByUid(uid, message);
                } else {
                    artCommentMapper.updateArtCommentsNickNameByUid(uid, message);
                }
                break;
            case "科技馆":
                new TechnologyCommentService().deleteTechnologyCommentCache();
                if (avatarUrl) {
                    technologyCommentMapper.updateTechnologyCommentsAvatarUrlByUid(uid, message);
                } else {
                    technologyCommentMapper.updateTechnologyCommentsNickNameByUid(uid, message);
                }
                break;
            default:
                new ComputerCommentService().deleteComputerCommentCache();
                if (avatarUrl) {
                    computerCommentMapper.updateComputerCommentsAvatarUrlByUid(uid, message);
                } else {
                    computerCommentMapper.updateComputerCommentsNickNameByUid(uid, message);
                }
        }
    }

    @CacheEvict(value = {"queryAvatarUrlAndNickNameByPhoneNumber", "queryPhoneUserByPhoneNumber",
            "queryAllCommentsByUid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean renewalAvatarUrlByPhoneNumber(String phoneNumber, MultipartFile multipartFile) {
        try {
            String avatarUrl = acquireAvatarUrl(multipartFile, phoneNumber);
            if (avatarUrl != null && !"".equals(avatarUrl)) {
                renewalCommentByUid(phoneNumber, avatarUrl, IS_AVATAR_URL);
                phoneUserMapper.updateAvatarUrlByPhoneNumber(phoneNumber, avatarUrl);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @CacheEvict(value = {"queryAvatarUrlAndNickNameByPhoneNumber", "queryPhoneUserByPhoneNumber",
            "queryAllCommentsByUid"},
            allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean renewalPhoneUserByPhoneNumber(PhoneUser phoneUser) {
        try {
            String phoneNumber = phoneUser.getPhoneNumber();
            String nickName = phoneUser.getNickName();
            renewalCommentByUid(phoneNumber, nickName, IS_NICK_NAME);
            phoneUserMapper.updatePhoneUserByPhoneNumber(phoneUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
