/**
 * 对deatil页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"

const detailnetwork = {
  getItemDetail: function (params) {
    wx.request({
      url: globalUrl.detailUrl() + params.id,
      success: function (res) {
        var item = res.data;
        if (params.success) {
          params.success(item);
        }
      }
    })
  }
}

export {
  detailnetwork
}