<template>
  <i-form ref="asForm" :model="model" :rules="rules">
    <slot></slot>
    <i-button long type="primary" v-if="button"
              @click="submit">保存
    </i-button>
  </i-form>
</template>

<script>
  export default {
    name: 'asForm',
    props: {
      model: {
        type: Object,
        default: () => {}
      },
      rules: {
        type: Object,
        default: () => {}
      },
      url: {
        type: String,
        default: ''
      },
      success: {
        type: Function,
        default: () => {}
      },
      button: {
        type: Boolean,
        default: false
      }
    },
    watch: {
      'model' (val) {
        // 第一项自动聚焦
        // 第一个$children是i-form-item，第二个$children才是i-input
        this.$refs.asForm.$children[0].$children[0].focus()

        // 内容切换时清空验证状态
        if (val && val.id !== -1) {
          this.$refs.asForm.resetFields()
        }
      }
    },
    methods: {
      /**
       * 提交表单，可供外部调用
       * @param url 表单提交的链接
       * @param success 表单提交成功后执行的回调函数
       */
      submit ({url = this.url, success = this.success}) {
        // 返回验证结果
        return this.$refs.asForm.validate((valid) => {
          if (valid) {
            // 通用保存函数
            this.$api.common.save({
              url: url,
              data: this.model
            }).then((res) => {
              if (res.success) {
                // 执行外部传入的成功回调，并将返回的内容传出
                success.call(this, res)

                this.$Message.success('保存成功')
              }
            })
          } else {
            this.$Message.error('请规范填写表单内容')
          }
        })
      }
    }
  }
</script>

<style scoped lang="stylus">

</style>