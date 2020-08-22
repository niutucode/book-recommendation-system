// pages/search/search.js
import {
  searchnetwork
} from "../../utils/searchnetwork.js"

var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hidden: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    wx.getStorage({
      key: 'searched',
      success: function(res) {
        var data = res.data;
        that.setData({
          histories: data,
        });
      },
    });
  },

  onSearchInputEvent: function(event) {
    var that = this;
    var value = event.detail.value;
    if (!value || value === "") {
      that.setData({
        hidden: false,
        content: null,
      });
      return;
    }
    that.setData({
      hidden: true
    });
    searchnetwork.getSearchResult({
      content: value,
      success: function(content) {
        that.setData({
          content: content,
        });
      },
    });
  },

  onItemTapEvent: function(event) {
    var that = this;
    var id = event.currentTarget.dataset.id;
    var title = event.currentTarget.dataset.title;
    var type = event.currentTarget.dataset.type;
    var histories = that.data.histories;
    var isExisted = false;
    if (histories) {
      for (var index = 0; index < histories.length; index++) {
        var history = histories[index];
        if (history.id === id) {
          isExisted = true;
          break;
        }
      }
    }
    if (!isExisted) {
      if (!histories) {
        histories = [];
      }
      histories.unshift({
        title: title,
        id: id,
        type: type
      });
      searchnetwork.saveSearchResult({
        uid: typeof (app.globalData.userInfo.uid) === "undefined" ? app.globalData.phoneNumber : app.globalData.userInfo.uid,
        bid: id,
        bookTitle: title,
        bookType: type
      });
      wx.setStorage({
        key: 'searched',
        data: histories,
      })
    }
    wx.navigateTo({
      url: "/pages/detail/detail?id=" + id + "&type=" + type,
    });
  },

  onClearEvent: function(event) {
    wx.removeStorage({
      key: 'searched',
    });
    this.setData({
      histories: null,
    })
  }
})