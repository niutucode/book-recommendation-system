/**
 * 对index页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"

var app = getApp();

const indexnetwork = {

  getRecommendBookList: function(params) {
    var uid = params.uid;
    wx.request({
      url: globalUrl.recommendUrl() + uid,
      method: 'GET',
      success: function(res) {
        if (params && params.success) {
          params.success(res.data);
        }
      }
    })
  },


  // 获取文学综合馆列表
  getLiteratureList: function(params) {
    params.type = "literature";
    this.getTypeList(params);
  },

  // 获取童书馆列表
  getChildList: function(params) {
    params.type = "child";
    this.getTypeList(params);
  },

  // 获取教育馆列表
  getEducationList: function(params) {
    params.type = "education";
    this.getTypeList(params);
  },

  // 获取人文社科馆列表
  getHumanityList: function(params) {
    params.type = "humanity";
    params.count = 7;
    this.getTypeList(params);
  },

  // 获取经管综合馆列表
  getEconomicsList: function(params) {
    params.type = "economics";
    this.getTypeList(params);
  },

  // 获取励志成功馆列表
  getMotivationalList: function(params) {
    params.type = "motivational";
    this.getTypeList(params);
  },

  // 获取生活馆列表
  getLifeList: function(params) {
    params.type = "life";
    this.getTypeList(params);
  },

  // 获取艺术馆列表
  getArtList: function(params) {
    params.type = "art";
    this.getTypeList(params);
  },

  // 获取科技馆列表
  getTechnologyList: function(params) {
    params.type = "technology";
    this.getTypeList(params);
  },

  // 获取计算机馆列表
  getComputerList: function(params) {
    params.type = "computer";
    this.getTypeList(params);
  },

  // 根据图书种类对首页URL封装
  getTypeList: function(params) {
    switch (params.type) {
      case "literature":
        {
          globalUrl.kind = "文学综合馆";
          break;
        }
      case "child":
        {
          globalUrl.kind = "童书馆";
          break;
        }
      case "education":
        {
          globalUrl.kind = "教育馆";
          break;
        }
      case "humanity":
        {
          globalUrl.kind = "人文社科馆";
          break;
        }
      case "economics":
        {
          globalUrl.kind = "经管综合馆";
          break;
        }
      case "motivational":
        {
          globalUrl.kind = "励志成功馆";
          break;
        }
      case "life":
        {
          globalUrl.kind = "生活馆";
          break;
        }
      case "art":
        {
          globalUrl.kind = "艺术馆";
          break;
        }
      case "technology":
        {
          globalUrl.kind = "科技馆";
          break;
        }
      default:
        {
          globalUrl.kind = "计算机馆";
        }
    }

    var url = globalUrl.listUrl() + globalUrl.kind;
    wx.request({
      url: url,
      success: function(res) {
        var items = res.data;
        if (params && params.success) {
          params.success(items);
        }
      }
    });
  }
}

export {
  indexnetwork
}