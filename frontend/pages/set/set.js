//index.js
import {
  setnetwork
} from "../../utils/setnetwork.js";

//获取应用实例
const app = getApp()

Page({

  data: {
  },

  onLoad: function() {
    var that = this;
    if (app.globalData.phoneNumber === "") {
      that.setData({
        phoneUser: false,
      })
      that.setData({
        avatarUrl: app.globalData.userInfo.avatarUrl,
        nickName: app.globalData.userInfo.nickName,
      });
    } else {
      that.setData({
        phoneUser: true
      });
      setnetwork.getPhoneUserAvatarUrl({
        phoneNumber: app.globalData.phoneNumber,
        success: function(res) {
          that.setData({
            avatarUrl: res[0],
            nickName: res[1]
          })
        }
      });
    }
    console.log(that.data.phoneUser);
  },

  //选择图片
  setAvatar: function() {
    var that = this;
    if(!that.data.phoneUser) {
      return;
    }
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function(res) {
        const tempFilePaths = res.tempFilePaths;
        wx.showToast({
          title: '正在上传...',
          icon: 'loading',
          mask: true,
          duration: 1000
        });
        setnetwork.updatePhoneUserAvatarUrl({
          filePath: tempFilePaths[0],
          phoneNumber: app.globalData.phoneNumber,
          success: function(res) {
            if (res == 200) {
              that.setData({
                avatarUrl: tempFilePaths
              })
            }
          }
        })
      }
    })
  }
})