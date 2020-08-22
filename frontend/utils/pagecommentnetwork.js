/**
 * 对deatil页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"

const pagecommentnetwork = {
  getItemPageComment: function(params) {
    var that = this;
    var url = "";
    switch (params.type) {
      case "文学综合馆":
        url = globalUrl.pageLiteratureCommentUrl();
        break;
      case "艺术馆":
        url = globalUrl.pageArtCommentUrl();
        break;
      case "童书馆":
        url = globalUrl.pageChildrenCommentUrl();
        break;
      case "经管综合馆":
        url = globalUrl.pageEconomicsCommentUrl();
        break;
      case "计算机馆":
        url = globalUrl.pageComputerCommentUrl();
        break;
      case "教育馆":
        url = globalUrl.pageEducationCommentUrl();
        break;
      case "人文社科馆":
        url = globalUrl.pageHumanityCommentUrl();
        break;
      case "生活馆":
        url = globalUrl.pageLifeCommentUrl();
        break;
      case "科技馆":
        url = globalUrl.pageTechnologyCommentUrl();
        break;
      default:
        url = globalUrl.pageMotivationalCommentUrl();
    }
    wx.request({
      url: url + params.start + "&" + 20 + "&" + params.id,
      success: function(res) {
        var pageComments = res.data;
        console.log(pageComments);
        if (params.success) {
          for (var i = 0; i < pageComments[1].length; i++) {
            pageComments[1][i].pubdate = that.formDate(pageComments[1][i].pubdate);
          }
          params.success(pageComments);
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
  pagecommentnetwork
}