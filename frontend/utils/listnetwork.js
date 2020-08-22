/**
 * 对list页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"

const listnetwork = {

  // 获取文学综合馆所有內容
  getLiteratureAllList: function(start, params) {
    params.type = "literature";
    this.getTypeList(start, params);
  },

  // 获取童书馆所有內容
  getChildAllList: function(start, params) {
    params.type = "child";
    this.getTypeList(start, params);
  },

  // 获取教育馆所有內容
  getEducationAllList: function(start, params) {
    params.type = "education";
    this.getTypeList(start, params);
  },

  // 获取人文社科馆所有內容
  getHumanityAllList: function(start, params) {
    params.type = "humanity";
    this.getTypeList(start, params);
  },

  // 获取经管综合馆所有內容
  getEconomicsAllList: function(start, params) {
    params.type = "economics";
    this.getTypeList(start, params);
  },

  // 获取励志成功馆所有內容
  getMotivationalAllList: function(start, params) {
    params.type = "motivational";
    this.getTypeList(start, params);
  },

  // 获取生活馆所有內容
  getLifeAllList: function(start, params) {
    params.type = "life";
    this.getTypeList(start, params);
  },

  // 获取艺术馆所有內容
  getArtAllList: function(start, params) {
    params.type = "art";
    this.getTypeList(start, params);
  },

  // 获取科技馆所有內容
  getTechnologyAllList: function(start, params) {
    params.type = "technology";
    this.getTypeList(start, params);
  },

  // 获取计算机馆所有內容
  getComputerAllList: function(start, params) {
    params.type = "computer";
    this.getTypeList(start, params);
  },

  /******************************************************************************** */

  // 根据图书种类对详情页URL封装
  getTypeList: function(start, params) {
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
    var url = globalUrl.allListUrl();
    url = url + start + "&" + 51 + "&" + globalUrl.kind;

    wx.request({
      url: url,
      success: function(res) {
        var items = res.data;
        var itemsCount = items.length;
        var left = itemsCount % 3;
        if (left === 2) {
          items.push(null);
        }
        if (params && params.success) {
          params.success(items);
        }
      }
    });
  }
}

export {
  listnetwork
}