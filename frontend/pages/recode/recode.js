// pages/recode/recode.js
import {
  commentnetwork
} from "../../utils/commentnetwork.js";

var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    start: 1,
    allComments: [],
    hasMore: {
      type: Boolean,
      value: true,
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    if (app.globalData.phoneNumber === "") {
      that.setData({
        uid: app.globalData.userInfo.uid,
      })
    } else {
      that.setData({
        uid: app.globalData.phoneNumber,
      })
    }

    that.getAllCommentsByUid(that.data.uid, that.data.start);
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    var that = this;
    var start = that.data.start;
    that.getAllCommentsByUid(that.data.uid, start + 1);
  },

  getAllCommentsByUid(uid, start) {
    var that = this;
    wx.showLoading({
      title: '正在加载...',
    });
    commentnetwork.getAllCommentByUid({
      uid: uid,
      start: start,
      success: function(res) {
        console.log(res);
        if (res.length === 0) {
          that.setData({
            hasMore: false,
          });
          wx.hideLoading();
          return;
        }
        for (var i = 0; i < res.length; i++) {
          that.data.allComments.push(res[i]);
        }
        that.setData({
          start: start,
          allComments: that.data.allComments,
        });
        wx.hideLoading();
      }
    })
  },

  deleteComment: function(e) {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定要删除吗？',
      success: function(sm) {
        if (sm.confirm) {
          commentnetwork.deleteCommentByUid({
            uid: that.data.uid,
            pubdate: e.currentTarget.dataset.pubdate,
            score: e.currentTarget.dataset.score,
            summary: e.currentTarget.dataset.summary,
            success: function(res) {
              console.log(res);
              if (res == 200) {
                that.data.allComments.splice(e.currentTarget.dataset.index, 1);
              }
              that.setData({
                allComments: that.data.allComments
              });
            }
          });
        } else if (sm.cancel) {
          console.log('用户点击取消')
        }
      }
    });
  }
})