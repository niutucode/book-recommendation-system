/**
 * 对deatil页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"

const tagnetwork = {
  getItemTag: function (params) {
    wx.request({
      url: globalUrl.tagUrl() + params.id,
      success: function (res) {
        var tags = res.data.label.split(",");
        if (params.success) {
          params.success(tags);
        }
      }
    })
  }
}

export {
  tagnetwork
}