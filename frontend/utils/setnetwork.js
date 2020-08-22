/**
 * 对set页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"


const setnetwork = {
  updatePhoneUserAvatarUrl: function(params) {
    wx.uploadFile({
      url: globalUrl.updatePhoneUserAvatarUrl(),
      filePath: params.filePath,
      name: 'multipartFile',
      formData: {
        'phoneNumber': params.phoneNumber
      },
      success: function(res) {
        if (params.success) {
          params.success(res.data);
        }
      }
    })
  },

  getPhoneUserAvatarUrl: function(params) {
    wx.request({
      url: globalUrl.findPhoneUserAvatarUrl() + params.phoneNumber,
      method: 'GET',
      success: function(res) {
        if (params.success) {
          params.success(res.data);
        }
      }
    });
  }
}

export {
  setnetwork
}