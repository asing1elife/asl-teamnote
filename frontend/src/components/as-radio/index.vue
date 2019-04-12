<template>
  <i-radio ref="asRadio" :label="label" :data-color="color">
    <slot></slot>
  </i-radio>
</template>

<script>
  export default {
    name: 'asRadio',
    props: {
      label: {
        type: String,
        default: ''
      },
      text: {
        type: String
      },
      color: {
        type: String,
        default: null
      }
    },
    created () {
      this._initRadioColor()
    },
    methods: {
      _initRadioColor () {
        if (!this.color) return

        this.$nextTick(() => {
          // 单选框
          let $asRadio = this.$refs.asRadio.$el
          // 单选框圆点
          let $asRadioBtn = $asRadio.getElementsByClassName('ivu-radio-inner')[0]

          // 设置文本颜色
          $asRadio.style.color = this.color
          // 设置圆点边框色
          $asRadioBtn.style.borderColor = this.color

          // 获取单选框唯一标记，由vue-scope生成，ex:data-v-7fc10533
          let radioMark1 = $asRadio.attributes[0].name
          // 获取颜色标记
          let radioMark2 = $asRadio.getAttribute('data-color')
          // 完整标记拼接
          let fullRadioMark = `[${radioMark1}][data-color="${radioMark2}"]`

          // 修改::after样式，从而实现改变圆点颜色
          document.styleSheets[0].insertRule(`.ivu-radio-wrapper${fullRadioMark} .ivu-radio-inner::after {background-color: ${this.color}`)
        })
      }
    }
  }
</script>

<style scoped lang="stylus">

</style>