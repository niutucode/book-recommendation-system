/**
 * 对write页面进行封装网络请求
 */
import {
  globalUrl
} from "urls.js"

const writenetwork = {
  saveWrite: function(params) {
    var url = "";
    switch (params.type) {
      case "文学综合馆":
        url = globalUrl.writeLiteratureUrl();
        break;
      case "艺术馆":
        url = globalUrl.writeArtUrl();
        break;
      case "童书馆":
        url = globalUrl.writeChildrenUrl();
        break;
      case "经管综合馆":
        url = globalUrl.writeEconomicsUrl();
        break;
      case "计算机馆":
        url = globalUrl.writeComputerUrl();
        break;
      case "教育馆":
        url = globalUrl.writeEducationUrl();
        break;
      case "人文社科馆":
        url = globalUrl.writeHumanityUrl();
        break;
      case "生活馆":
        url = globalUrl.writeLifeUrl();
        break;
      case "科技馆":
        url = globalUrl.writeTechnologyUrl();
        break;
      default:
        url = globalUrl.writeMotivationalUrl();
    }
    wx.request({
      url: url,
      data: {
        'bid': params.id,
        'uid': params.uid,
        'avatarUrl': params.avatar,
        'nickName': params.name,
        'score': params.score,
        'summary': params.summary,
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      method: 'POST',
      success: function(res) {
        var code = res.data;
        if(params.success) {
          params.success(code);
        }
      }
    })
  },
}

export {
  writenetwork
}