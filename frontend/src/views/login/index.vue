<template>
  <i-layout class="login-wrapper">
    <i-content class="d-flex justify-content-center align-items-center">
      <i-card shadow
              class="login-card"
              :padding="50">
        <i-form ref="loginForm" class="login-form"
                :model="user" :rules="rules">
          <i-form-item class="login-header">
            <h2 class="title">
              <img class="logo" src="./image/logo.png" alt="Teamnote">
              Teamnote
            </h2>
          </i-form-item>
          <i-form-item prop="username">
            <i-input placeholder="Email / Mobile"
                     v-model="user.username"
                     :maxlength="20">
            </i-input>
          </i-form-item>
          <i-form-item prop="password">
            <i-input type="password" placeholder="Password"
                     v-model="user.password"
                     :maxlength="20">
            </i-input>
            <div class="d-flex justify-content-end">
              <a class="forgot-password-btn" href="javascript:">忘记密码？</a>
            </div>
          </i-form-item>
          <i-form-item class="login-footer">
            <i-button ghost
                      class="login-btn" type="error"
                      @click="login">
              登 录
            </i-button>
          </i-form-item>
        </i-form>
      </i-card>
    </i-content>
  </i-layout>
</template>

<script>
  import { mapActions } from 'vuex'

  export default {
    name: 'login',
    data () {
      return {
        user: {
          username: '15002715747',
          password: '123456'
        },
        rules: {
          username: [
            {
              required: true,
              message: ' ',
              trigger: 'validate'
            }
          ],
          password: [
            {
              required: true,
              message: ' ',
              trigger: 'validate'
            }
          ]
        }
      }
    },
    methods: {
      ...mapActions([
        'setToken'
      ]),
      login () {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            this.$api.login.login({
              username: this.user.username,
              password: this.user.password
            }).then((res) => {
              // 记录Token
              this.setToken(res.data)

              // 前往组织页
              this.$router.push('/organizations')
            })
          }
        })
      }
    }
  }
</script>

<style scoped lang="stylus">
  @import "~assets/stylus/variable.styl"

  .login-wrapper
    width 100%
    height 100%
    background-image url("./image/bg.svg")
    background-position center
    background-size cover
    background-repeat no-repeat
    .login-card
      min-width 400px
      border-top solid 5px $error-color
      &:hover
        border-top solid 5px $error-color
      .login-form
        .forgot-password-btn
          text-align right
        .login-header
          .title
            font-size $huge-size
            font-weight 600
            display flex
            justify-content center
            align-items center
            .logo
              width 35px
              height 35px
              margin-right 10px
        .login-footer
          margin 0
          & >>> .ivu-form-item-content
            display flex
            justify-content center
            .login-btn
              border-width 2px
              padding-left 30px
              padding-right 30px
              &:hover
                background-color $error-color
                color $white-color
                border none
</style>