/**
 * 存储用户
 */
import {
  globalUrl
} from "urls.js"

const phoneusernetwork = {
  savePhoneUser: function(params) {
    console.log(params);
    wx.request({
      url: globalUrl.savePhoneUserUrl() + params.phoneNumber,
      method: 'POST',
      success: function(res) {
        console.log(res);
      }
    })
  },

  getPhoneUser: function(params) {
    wx.request({
      url: globalUrl.getPhoneUserUrl() + params.phoneNumber,
      method: 'GET',
      success: function(res) {
        if (params.success) {
          params.success(res);
        }
      }
    })
  },

  updatePhoneUser: function(params) {
    console.log(params);
    wx.request({
      url: globalUrl.updatePhoneUserUrl(),
      data: {
        "phoneNumber": params.phoneNumber,
        "nickName": params.nickName,
        "gender": params.gender,
        "country": params.country,
        "province": params.province,
        "city": params.city,
        "language": params.language,
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      method: 'PUT',
      success: function(res) {
        if(params.success) {
          params.success(res.data);
        }
      }
    });
  }
}

export {
  phoneusernetwork
}