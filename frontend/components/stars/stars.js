// components/stars/stars.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    rate: {
      type: Number,
      value: 0,
      observer(newVal, oldVal, changePath) {
        this.updateRate();
      },
    },
    starSize: {
      type: Number,
      value: 20,
    },
    fontSize: {
      type: Number,
      value: 20,
    },
    fontColor: {
      type: String,
      value: "#494949",
    },
    isText: {
      type: Boolean,
      value: true,
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    updateRate: function() {
      var that = this;
      var rate = that.properties.rate;
      var intRate = parseInt(rate);
      var light = parseInt(intRate / 2);
      var half = intRate % 2;
      var gray = 5 - light - half;
      var lights = [];
      var halfs = [];
      var grays = [];
      for (var index = 0; index < light; index++) {
        lights.push(index);
      }
      for (var index = 0; index < half; index++) {
        halfs.push(index);
      }
      for (var index = 0; index < gray; index++) {
        grays.push(index);
      }
      var rateText = rate && rate > 0 ? rate.toFixed(1) : "未评分";
      that.setData({
        lights: lights,
        halfs: halfs,
        grays: grays,
        rateText: rateText,
      });
    }
  },

  lifetimes: {
    attached: function() {
      this.updateRate();
    }
  }
})