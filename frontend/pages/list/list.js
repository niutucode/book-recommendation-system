// pages/list/list.js
import {
  listnetwork
} from "../../utils/listnetwork.js"


Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasMore: {
      type: Boolean,
      value: true,
    }
  },

  /**
   * 获取后台对应种类的图书后进行处理相关信息
   */
  dispose: function(items, start, type) {
    var that = this;
    wx.hideLoading();
    if (items.length === 0) {
      that.setData({
        hasMore: false,
      });
      return;
    }
    var oldItems = that.data.items;
    var newItems = [];
    if (!oldItems || start === 0) {
      newItems = items;
    } else {
      newItems = oldItems.concat(items);
    }
    that.setData({
      items: newItems,
      start: start,
      type: type,
    });
  },

  /**
   * 对对应种类的书籍进行分类
   */
  getPageBookByType(type, start) {
    var that = this;
    var title = "";
    
    wx.showLoading({
      title: '正在加载...',
    })

    switch (type) {
      case "literature":
        {
          listnetwork.getLiteratureAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "文学综合馆";
          break;
        }
      case "child":
        {
          listnetwork.getChildAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "童书馆";
          break;
        }
      case "education":
        {
          listnetwork.getEducationAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "教育馆";
          break;
        }
      case "humanity":
        {
          listnetwork.getHumanityAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "人文社科馆";
          break;
        }
      case "economics":
        {
          listnetwork.getEconomicsAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "经管综合馆";
          break;
        }
      case "motivational":
        {
          listnetwork.getMotivationalAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "励志成功馆";
          break;
        }
      case "life":
        {
          listnetwork.getLifeAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "生活馆";
          break;
        }
      case "art":
        {
          listnetwork.getArtAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "艺术馆";
          break;
        }
      case "technology":
        {
          listnetwork.getTechnologyAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "科技馆";
          break;
        }
      default:
        {
          listnetwork.getComputerAllList(start, {
            success: function(items) {
              that.dispose(items, start, type);
            }
          });
          title = "计算机馆";
        }
    }
    wx.setNavigationBarTitle({
      title: title,
    })
  },

  onLoad: function(options) {
    this.getPageBookByType(options.type, 0);
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    var that = this;
    var start = that.data.start;
    that.getPageBookByType(that.data.type, start + 1);
  }
})