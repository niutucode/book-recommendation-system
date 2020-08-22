import {
  indexnetwork
} from "../../utils/indexnetwork.js";

import {
  weixinusernetwork
} from "../../utils/weixinusernetwork.js";


var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isAuthorizationed: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    // 权限验证
    if (options.isAuthorizationed){
      that.setData({
        isAuthorizationed: true
      });
      indexnetwork.getRecommendBookList({
        uid: app.globalData.phoneNumber,
        success: function (recommendation) {
          that.setData({
            recommendation: recommendation,
          })
        }
      });
    } else {
      wx.getSetting({
        success: function (res) {
          if (res.authSetting['scope.userInfo']) {
            that.getUserAuth();
          } else {
            that.setData({
              isAuthorizationed: false
            })
          }
        }
      });
    }
    
    // 文学综合馆
    indexnetwork.getLiteratureList({
      success: function(literature) {
        that.setData({
          literature: literature,
        });
      }
    });

    // 童书馆
    indexnetwork.getChildList({
      success: function(child) {
        that.setData({
          child: child,
        });
      }
    });

    // 教育馆
    indexnetwork.getEducationList({
      success: function(education) {
        that.setData({
          education: education,
        });
      }
    });

    // 人文社科馆
    indexnetwork.getHumanityList({
      success: function(humanity) {
        that.setData({
          humanity: humanity,
        });
      }
    });

    // 经管综合馆
    indexnetwork.getEconomicsList({
      success: function(economics) {
        that.setData({
          economics: economics,
        });
      }
    });

    // 励志成功馆
    indexnetwork.getMotivationalList({
      success: function(motivational) {
        that.setData({
          motivational: motivational,
        });
      }
    });

    // 生活馆
    indexnetwork.getLifeList({
      success: function(life) {
        that.setData({
          life: life,
        });
      }
    });

    // 艺术馆
    indexnetwork.getArtList({
      success: function(art) {
        that.setData({
          art: art,
        });
      }
    });

    // 科技馆
    indexnetwork.getTechnologyList({
      success: function(technology) {
        that.setData({
          technology: technology,
        });
      }
    });

    // 计算机馆
    indexnetwork.getComputerList({
      success: function(computer) {
        that.setData({
          computer: computer,
        });
      }
    });
  },

  getUserAuth() {
    var that = this;
    wx.getUserInfo({
      success: function(res) {
        that.setData({
          isAuthorizationed: true
        });
        app.globalData.phoneNumber = "";
        app.globalData.userInfo = res.userInfo;
        wx.login({
          success: function(res) {
            if (res.code) {
              wx.request({
                url: 'https://api.weixin.qq.com/sns/jscode2session',
                data: {
                  appid: 'wx9f68d4fa08d44307',
                  secret: 'eb24c07936d98b3ff7da13318e7a290f',
                  js_code: res.code,
                  grant_type: 'authorization_code',
                },
                success: function(res) {
                  app.globalData.userInfo["uid"] = res.data.openid;
                  weixinusernetwork.updateWeiXinUser({
                    userInfo: app.globalData.userInfo 
                  });
                  indexnetwork.getRecommendBookList({
                    uid: app.globalData.userInfo.uid,
                    success: function(recommendation) {
                      that.setData({
                        recommendation: recommendation,
                      })
                    }
                  });
                }
              })
            };
          }
        });
      }
    });
  },

  getUser(res) {
    var that = this;
    if (res.detail.userInfo) {
      that.setData({
        scope: 'scope.userInfo',
        isAuthorizationed: true
      })
    };
    app.globalData.phoneNumber = "";
    app.globalData.userInfo = res.detail.userInfo;
    wx.login({
      success: function(res) {
        if (res.code) {
          wx.request({
            url: 'https://api.weixin.qq.com/sns/jscode2session',
            data: {
              appid: 'wx9f68d4fa08d44307',
              secret: 'eb24c07936d98b3ff7da13318e7a290f',
              js_code: res.code,
            },
            success: function(res) {
              app.globalData.userInfo["uid"] = res.data.openid;

              //存储微信用户
              weixinusernetwork.saveWeiXinUser({
                userInfo: app.globalData.userInfo
              });


              //推荐系统
              indexnetwork.getRecommendBookList({
                uid: app.globalData.userInfo.uid,
                success: function (recommendation) {
                  that.setData({
                    recommendation: recommendation,
                  })
                }
              });
            }
          })
        }
      }
    });
  }
})