/**
 * 全局URL
 */
var globalUrl = {
  id: 0,

  //推荐URL
  recommendUrl: function() {
    return "http://192.168.1.6:8080/recommend/";
  },

  // 首页URL
  listUrl: function() {
    return "http://192.168.1.6:8080/index/";
  },

  // 列表页URL
  allListUrl: function() {
    return "http://192.168.1.6:8080/list/";
  },

  // 详情页URL
  detailUrl: function() {
    return "http://192.168.1.6:8080/detail/";
  },

  // 标签URL
  tagUrl: function() {
    return "http://192.168.1.6:8080/tag/";
  },

  // 详情页评论URL
  literatureCommentUrl: function() {
    return "http://192.168.1.6:8080/literatureComment/";
  },

  childrenCommentUrl: function() {
    return "http://192.168.1.6:8080/childrenComment/";
  },

  artCommentUrl: function() {
    return "http://192.168.1.6:8080/artComment/";
  },

  computerCommentUrl: function() {
    return "http://192.168.1.6:8080/computerComment/";
  },

  educationCommentUrl: function() {
    return "http://192.168.1.6:8080/educationComment/";
  },

  humanityCommentUrl: function() {
    return "http://192.168.1.6:8080/humanityComment/";
  },

  lifeCommentUrl: function() {
    return "http://192.168.1.6:8080/lifeComment/";
  },

  motivationalCommentUrl: function() {
    return "http://192.168.1.6:8080/motivationalComment/";
  },

  technologyCommentUrl: function() {
    return "http://192.168.1.6:8080/technologyComment/";
  },

  economicsCommentUrl: function() {
    return "http://192.168.1.6:8080/economicsComment/";
  },

  // 评论总数URL
  totalUrl: function() {
    return "http://192.168.1.6:8080/total/";
  },

  // 评论页URL
  pageLiteratureCommentUrl: function() {
    return "http://192.168.1.6:8080/page/literatureComment/";
  },

  pageChildrenCommentUrl: function() {
    return "http://192.168.1.6:8080/page/childrenComment/";
  },

  pageArtCommentUrl: function() {
    return "http://192.168.1.6:8080/page/artComment/";
  },

  pageComputerCommentUrl: function() {
    return "http://192.168.1.6:8080/page/computerComment/";
  },

  pageEducationCommentUrl: function() {
    return "http://192.168.1.6:8080/page/educationComment/";
  },

  pageHumanityCommentUrl: function() {
    return "http://192.168.1.6:8080/page/humanityComment/";
  },

  pageLifeCommentUrl: function() {
    return "http://192.168.1.6:8080/page/lifeComment/";
  },

  pageMotivationalCommentUrl: function() {
    return "http://192.168.1.6:8080/page/motivationalComment/";
  },

  pageTechnologyCommentUrl: function() {
    return "http://192.168.1.6:8080/page/technologyComment/";
  },

  pageEconomicsCommentUrl: function() {
    return "http://192.168.1.6:8080/page/economicsComment/";
  },


  // 搜索页URL
  searchUrl: function() {
    return "http://192.168.1.6:8080/search/";
  },

  saveSearchUrl: function() {
    return "http://192.168.1.6:8080/savesearch/"
  },

  // 发表留言URL
  writeLiteratureUrl: function() {
    return "http://192.168.1.6:8080/write/literature/";
  },

  writeChildrenUrl: function() {
    return "http://192.168.1.6:8080/write/children/";
  },

  writeArtUrl: function() {
    return "http://192.168.1.6:8080/write/art/";
  },

  writeComputerUrl: function() {
    return "http://192.168.1.6:8080/write/computer/";
  },

  writeEducationUrl: function() {
    return "http://192.168.1.6:8080/write/education/";
  },

  writeHumanityUrl: function() {
    return "http://192.168.1.6:8080/write/humanity/";
  },

  writeLifeUrl: function() {
    return "http://192.168.1.6:8080/write/life/";
  },

  writeMotivationalUrl: function() {
    return "http://192.168.1.6:8080/write/motivational/";
  },

  writeTechnologyUrl: function() {
    return "http://192.168.1.6:8080/write/technology/";
  },

  writeEconomicsUrl: function() {
    return "http://192.168.1.6:8080/write/economics/";
  },

  // 存储微信用户信息
  saveWeiXinUserUrl: function() {
    return "http://192.168.1.6:8080/save/weixinuser/";
  },

  updateWeiXinUserUrl: function() {
    return "http://192.168.1.6:8080/update/weixinuser/";
  },

  // 获取验证码
  getAuthNumberUrl: function() {
    return "http://192.168.1.6:8080/auth/";
  },

  userAuthUrl: function() {
    return "http://192.168.1.6:8080/userauth/";
  },

  savePhoneUserUrl: function() {
    return "http://192.168.1.6:8080/save/phoneuser/";
  },

  getPhoneUserUrl: function() {
    return "http://192.168.1.6:8080/phoneuser/";
  },

  updatePhoneUserAvatarUrl: function() {
    return "http://192.168.1.6:8080/update/avatar/";
  },

  findPhoneUserAvatarUrl: function() {
    return "http://192.168.1.6:8080/avatarurl/";
  },

  updatePhoneUserUrl: function() {
    return "http://192.168.1.6:8080/update/phoneuser/";
  },

  findAllCommentsByUid: function() {
    return "http://192.168.1.6:8080/allcomments/";
  },

  deleteCommentByUid: function() {
    return "http://192.168.1.6:8080/delete/comment/";
  }
}

export {
  globalUrl
}