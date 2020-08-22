// components/indexmodule/indexmodule.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    title: {
      type: String,
      value: "",
    },
    moreUrl: {
      type: String,
      value: "",
    },
    kinds: {
      type: Array,
      value: [],
    },
    type: {
      type: String,
      value: "",
    },
    more: {
      type: Boolean,
      value: true,
    },
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