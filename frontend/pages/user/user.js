// pages/user/user.js
import {
  phoneusernetwork
} from "../../utils/phoneusernetwork.js";

const app = getApp();

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
    phoneusernetwork.getPhoneUser({
      phoneNumber: app.globalData.phoneNumber,
      success: function(res) {
        console.log(res);
        that.setData({
          phoneNumber: res.data[0].phoneNumber,
          nickName: res.data[0].nickName,
          male: res.data[0].gender === "男" ? true : false,
          female: res.data[0].gender === "女" ? true : false,
          country: res.data[0].country,
          province: res.data[0].province,
          city: res.data[0].city,
          language: res.data[0].language
        })
      }
    })
  },

  submit: function(e) {
    phoneusernetwork.updatePhoneUser({
      phoneNumber: e.detail.value.phoneNumber,
      nickName: e.detail.value.nickName,
      gender: e.detail.value.gender,
      country: e.detail.value.country,
      province: e.detail.value.province,
      city: e.detail.value.city,
      language: e.detail.value.language,
      success: function(res) {
        if(res === 200) {
          wx.showToast({
            title: '修改成功',
            icon: 'success',
            duration: 2000
          });
          setTimeout(function () {
            wx.navigateBack({
              delta: 1
            });
          }, 1000);
        }
      }
    })
  }
})