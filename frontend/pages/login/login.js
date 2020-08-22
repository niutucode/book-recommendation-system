// pages/login.js
import {
  loginnetwork
} from "../../utils/loginnetwork.js";

import {
  phoneusernetwork
} from "../../utils/phoneusernetwork.js";


var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    sendColor: "#37aeb4",
    sendTime: "获取验证码",
    smsMsgWait: 60,
    smsFlag: true
  },

  submit: function(e) {
    loginnetwork.userAuth({
      phoneNumber: e.detail.value.phoneNumber,
      authNumber: e.detail.value.authNumber,
      success: function(res) {
        if (res === 200) {
          app.globalData.phoneNumber = e.detail.value.phoneNumber;
          phoneusernetwork.savePhoneUser({
            phoneNumber: app.globalData.phoneNumber
          })
          wx.redirectTo({
            url: '/pages/index/index?isAuthorizationed=true',
          })
        } else {
          wx.showModal({
            content: '验证码错误'
          })
        }
      }
    })
  },

  getAuthNumber: function() {
    var that = this;
    if (that.data.smsFlag) {
      var inter = setInterval(function() {
        this.setData({
          smsFlag: false,
          sendColor: '#cccccc',
          sendTime: this.data.smsMsgWait + 's后重发',
          smsMsgWait: this.data.smsMsgWait - 1
        });
        if (this.data.smsMsgWait < 0) {
          clearInterval(inter)
          this.setData({
            sendColor: '#37aeb4',
            sendTime: '获取验证码',
            smsMsgWait: 60,
            smsFlag: true
          });
        }
      }.bind(this), 1000);
      loginnetwork.getAuthNumber({
        phoneNumber: that.data.phoneNumber
      })
    }
  },

  getPhoneNumber: function(e) {
    var that = this;
    that.setData({
      phoneNumber: e.detail.value
    });
    if (that.data.phoneNumber.toString().length == 11) {
      that.setData({
        phone: true
      })
    } else {
      that.setData({
        phone: false
      })
    }
  }
})