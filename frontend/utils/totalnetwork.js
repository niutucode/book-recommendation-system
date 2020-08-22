/**
 * 对deatil页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"

const totalnetwork = {
  getItemTotal: function (params) {
    wx.request({
      url: globalUrl.totalUrl() + params.id,
      success: function (res) {
        var total = res.data;
        if (params.success) {
          params.success(total);
        }
      }
    })
  }
}

export {
  totalnetwork
}