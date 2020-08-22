// pages/wirte/write.js
import {
  writenetwork
} from "../../utils/writenetwork.js";

import {
  phoneusernetwork
} from "../../utils/phoneusernetwork.js";

var app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    that.setData({
      userInfo: app.globalData.userInfo
    })
    that.setData(options);
  },

  submitEvent: function(res) {
    var that = this;
    var id = that.data.id;
    var score = that.data.score;
    var summary = res.detail.value.content;
    var type = that.data.type;
    var avatar = "";
    var name = "";
    var uid = "";
    if (app.globalData.phoneNumber === "") {
      avatar = that.data.userInfo.avatarUrl;
      name = that.data.userInfo.nickName;
      uid = that.data.userInfo.uid;
      that.saveWrite(id, uid, avatar, name, score, summary, type);
    } else {
      phoneusernetwork.getPhoneUser({
        phoneNumber: app.globalData.phoneNumber,
        success: function(res) {
          avatar = res.data[0].avatarUrl;
          name = res.data[0].nickName;
          uid = phoneNumber;
          that.saveWrite(id, uid, avatar, name, score, summary, type);
        }
      });
    }
  },

  onChangeEvent: function(event) {
    var that = this;
    that.setData({
      score: event.detail.value * 2,
    });
  },

  getCount: function(res) {
    var that = this;
    that.setData({
      count: res.detail.cursor
    })
  },

  saveWrite: function(id, uid, avatar, name, score, summary, type) {
    writenetwork.saveWrite({
      id: id,
      uid: uid,
      avatar: avatar,
      name: name,
      score: typeof(score) === "undefined" ? 0 : score,
      summary: summary,
      type: type,
      success: function(code) {
        console.log(code);
        if (code === 200) {
          var pages = getCurrentPages();
          if (pages.length > 1) {
            var commentPage = pages[pages.length - 2];
            var detailPage = pages[pages.length - 3];
            detailPage.changeDetailData();
            commentPage.changeCommentData();
          }
          wx.showToast({
            title: '发表成功',
            icon: 'success',
            duration: 2000
          });
          setTimeout(function() {
            wx.navigateBack({
              delta: 1
            });
          }, 1000);

        }
      }
    });
  }
})