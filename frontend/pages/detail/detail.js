// pages/detail/detail.js
import {
  detailnetwork
} from "../../utils/detailnetwork.js";

import {
  tagnetwork
} from "../../utils/tagnetwork.js";

import {
  commentnetwork
} from "../../utils/commentnetwork.js";

import {
  totalnetwork
} from "../../utils/totalnetwork.js";

Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    var id = options.id;
    var type = options.type;
    that.setData({
      id: id,
      type: type
    });
    detailnetwork.getItemDetail({
      id: id,
      success: function(item) {
        that.setData({
          item: item,
        });
      }
    });

    tagnetwork.getItemTag({
      id: id,
      success: function(tags) {
        that.setData({
          tags: tags,
        });
      }
    });
    commentnetwork.getItemComment({
      id: id,
      type: type,
      success: function(comments) {
        that.setData({
          comments: comments,
        });
      }
    });

    totalnetwork.getItemTotal({
      id: id,
      success: function(total) {
        that.setData({
          total: total,
        });
      }
    })
  },

  changeDetailData: function() {
    var that = this;
    commentnetwork.getItemComment({
      id: that.data.id,
      type: that.data.type,
      success: function(comments) {
        that.setData({
          comments: comments,
        });
      }
    });

    totalnetwork.getItemTotal({
      id: that.data.id,
      success: function(total) {
        that.setData({
          total: total,
        });
      }
    })
  }
})