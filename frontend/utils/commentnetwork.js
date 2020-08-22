/**
 * 对deatil页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"

const commentnetwork = {
  getItemComment: function(params) {
    var that = this;
    var url = "";
    switch (params.type) {
      case "文学综合馆":
        url = globalUrl.literatureCommentUrl() + params.id;
        break;
      case "艺术馆":
        url = globalUrl.artCommentUrl() + params.id;
        break;
      case "童书馆":
        url = globalUrl.childrenCommentUrl() + params.id;
        break;
      case "经管综合馆":
        url = globalUrl.economicsCommentUrl() + params.id;
        break;
      case "计算机馆":
        url = globalUrl.computerCommentUrl() + params.id;
        break;
      case "教育馆":
        url = globalUrl.educationCommentUrl() + params.id;
        break;
      case "人文社科馆":
        url = globalUrl.humanityCommentUrl() + params.id;
        break;
      case "生活馆":
        url = globalUrl.lifeCommentUrl() + params.id;
        break;
      case "科技馆":
        url = globalUrl.technologyCommentUrl() + params.id;
        break;
      default:
        url = globalUrl.motivationalCommentUrl() + params.id;
    }
    wx.request({
      url: url,
      method: 'GET',
      success: function(res) {
        var comments = res.data;
        if (params.success) {
          for (var i = 0; i < comments.length; i++) {
            comments[i].pubdate = that.formDate(comments[i].pubdate);
          }
          params.success(comments);
        }
      }
    })
  },

  getAllCommentByUid: function(params) {
    console.log(params);
    wx.request({
      url: globalUrl.findAllCommentsByUid() + params.start + '&' + 10 + '&' + params.uid,
      method: 'GET',
      success: function(res) {
        if (params.success) {
          params.success(res.data);
        }
      }
    });
  },

  deleteCommentByUid: function(params) {
    wx.request({
      url: globalUrl.deleteCommentByUid() + params.uid + '&' + params.score + '&' + params.pubdate + '&' + params.summary,
      method: 'DELETE',
      success: function(res) {
        if (params.success) {
          params.success(res.data);
        }
      }
    })
  },

  formDate: function(dateForm) {
    if (dateForm === "") { //解决deteForm为空传1970-01-01 00:00:00
      return "";
    } else {
      var dateee = new Date(dateForm).toJSON();
      var date = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
      return date;
    }
  }
}

export {
  commentnetwork
}