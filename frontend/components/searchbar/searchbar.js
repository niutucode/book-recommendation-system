// components/search-bar/search-bar.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    isNavigator: {
      type: Boolean,
      value: false,
    },

    isSet: {
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
    onInputEvent: function(event) {
      var value = event.detail.value;
      var detail = {
        "value": value
      };
      var options = {};
      this.triggerEvent("searchInput",detail, options);
    }
  }
})