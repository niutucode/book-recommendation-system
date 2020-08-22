/**
 * 对deatil页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"

const searchnetwork = {
  getSearchResult: function(params) {
    var content = params.content;
    wx.request({
      url: globalUrl.searchUrl() + content,
      success: function(res) {
        var subject = res.data;
        if (params.success) {
          params.success(subject);
        }
      }
    })
  },

  saveSearchResult: function(params) {
    wx.request({
      url: globalUrl.saveSearchUrl(),
      data: {
         "uid": params.uid,
         "bid": params.bid,
         "bookTitle": params.bookTitle,
         "bookType": params.bookType
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      method: 'POST'
    })
  }
}

export {
  searchnetwork
}