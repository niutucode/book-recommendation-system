import {
  globalUrl
} from "urls.js"

const loginnetwork = {

  getAuthNumber: function(params) {
    console.log(params);
    wx.request({
      url: globalUrl.getAuthNumberUrl() + params.phoneNumber,
      method: 'POST',
      success: function(res) {
        console.log(res);
      }
    })
  },

  userAuth: function(params) {
    console.log(params);
    wx.request({
      url: globalUrl.userAuthUrl() + params.phoneNumber + "&" + params.authNumber,
      method: 'POST',
      success: function(res) {
        if(params.success) {
          params.success(res.data);
        }
      }
    });
  }
}

export {
  loginnetwork
}