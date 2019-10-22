<template>
  <i-modal id="asModal" v-model="visible"
           :title="title" :width="width" :footer-hide="footerHide" :class-name="className"
           @keyup.enter.native="enterTriggerOkEvent">
    <slot slot="header" name="header"></slot>
    <slot></slot>
    <!-- 原生的ok函数不论回调如何，都会直接关闭页面，所以这里对默认按钮事件进行了重写 -->
    <slot slot="footer" name="footer">
      <i-button type="text" @click="cancelEvent">{{cancelText}}</i-button>
      <i-button type="primary" @click="okEvent">{{okText}}</i-button>
    </slot>
  </i-modal>
</template>

<script>
  export default {
    name: 'asModal',
    props: {
      value: {
        type: Boolean,
        default: false
      },
      footerHide: {
        type: Boolean,
        default: false
      },
      enter: {
        type: Boolean,
        default: true
      },
      title: {
        type: String
      },
      className: {
        type: String
      },
      width: {
        type: String
      },
      okText: {
        type: String,
        default: '保存'
      },
      cancelText: {
        type: String,
        default: '取消'
      },
      ok: {
        type: Function,
        default: () => {
          return Promise.resolve()
        }
      }
    },
    data () {
      return {
        visible: false
      }
    },
    watch: {
      value (val) {
        if (val === this.show) {
          return false
        }

        this.visible = val
      },
      visible (val) {
        this.$emit('input', val)
      }
    },
    methods: {
      _close () {
        this.visible = false
      },
      enterTriggerOkEvent () {
        if (!this.enter) {
          return
        }

        this.okEvent()
      },
      okEvent () {
        // 根据回调结果判断是否关闭窗口
        this.ok().then((res) => {
          if (res) {
            this._close()
          }
        })
      },
      cancelEvent () {
        this._close()
      }
    }
  }
</script>

<style scoped lang="stylus">

</style>