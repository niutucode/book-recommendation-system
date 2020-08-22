// components/loadingmore/loadingmore.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    loadingText: {
      type: String,
      value: "正在加载..."
    },
    loadedText: {
      type: String,
      value: "没有更多数据"
    },
    hasMore: {
      type: Boolean,
      value: true
    },
    height: {
      type: Number,
      value: 40
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

  }
})
