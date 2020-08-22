// pages/comment/comment.js
import {
  pagecommentnetwork
} from "../../utils/pagecommentnetwork.js"

Page({
  
  /**
   * 页面的初始数据
   */
  data: {
    start: 1,
    oldStart: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    that.setData(options);
    that.getComments();
  },

  onItemTapEvent: function(event) {
    wx.navigateBack({});
  },

  getComments: function() {
    var that = this;
    var id = that.data.id;
    var type = that.data.type;
    if (that.data.oldStart <= that.data.start) {
      that.setData({
        nextLoading: true,
      })
    } else {
      that.setData({
        preLoading: true,
      })
    }
    pagecommentnetwork.getItemPageComment({
      id: id,
      type: type,
      start: that.data.start,
      success(pageComments) {
        that.setData({
          data: pageComments[1],
          totalPages: pageComments[0],
          preLoading: false,
          nextLoading: false
        })
      }
    });
    wx.pageScrollTo({
      scrollTop: 0,
    });
  },

  onPrePageTap(event) {
    var that = this;
    if (that.data.start > 1) {
      that.setData({
        oldStart: that.data.start,
        start: that.data.start - 1
      });
      that.getComments(that.data.start);
    }
  },

  onNextPageTap(event) {
    var that = this;
    if (that.data.start < that.data.totalPages)
      that.setData({
        oldStart: that.data.start,
        start: that.data.start + 1
      });
    that.getComments(that.data.start);
  },

  changeCommentData: function() {
    var that = this;
    that.setData({
      total: parseInt(that.data.total) + 1
    })
    this.getComments();
  }
})