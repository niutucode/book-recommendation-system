/**
 * 存储用户
 */
import {
  globalUrl
} from "urls.js"

const weixinusernetwork = {
  saveWeiXinUser: function(params) {
    wx.request({
      url: globalUrl.saveWeiXinUserUrl(),
      data: {
        'uid': params.userInfo.uid,
        'nickName': params.userInfo.nickName,
        'avatarUrl': params.userInfo.avatarUrl,
        'gender': params.userInfo.gender,
        'country': params.userInfo.country,
        'province': params.userInfo.province,
        'city': params.userInfo.city,
        'language': params.userInfo.language,
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      method: 'POST',
      success: function(res) {
        console.log(res);
      }
    })
  },

  updateWeiXinUser: function(params) {
    wx.request({
      url: globalUrl.updateWeiXinUserUrl(),
      data: {
        'uid': params.userInfo.uid,
        'nickName': params.userInfo.nickName,
        'avatarUrl': params.userInfo.avatarUrl,
        'gender': params.userInfo.gender,
        'country': params.userInfo.country,
        'province': params.userInfo.province,
        'city': params.userInfo.city,
        'language': params.userInfo.language,
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      method: 'PUT',
      success: function (res) {
        console.log(res);
      }
    })
  }
}

export {
  weixinusernetwork
}
