<template>
  <i-layout id="indexContainer">
    <i-header>
      <h2 class="header-title" @click="toIndex">{{currentOrganization}}</h2>
      <i-dropdown placement="bottom-end">
        <i-avatar>{{firstChar}}</i-avatar>
        <i-dropdown-menu slot="list">
          <i-dropdown-item>{{user.nickname}}</i-dropdown-item>
          <i-dropdown-item divided>
            <as-icon name="log-out" text="退出" direction="right"
                     @click="logout"></as-icon>
          </i-dropdown-item>
        </i-dropdown-menu>
      </i-dropdown>
    </i-header>
    <i-content>
      <router-view/>
    </i-content>
  </i-layout>
</template>

<script>
  import asIcon from 'components/as-icon'
  import { mapGetters, mapMutations, mapActions } from 'vuex'
  import { SET_CURRENT_ORGANIZATION, SET_CURRENT_USER } from 'store/mutation-types'
  import User from 'model/user'

  export default {
    name: 'index',
    data () {
      return {
        user: new User(1)
      }
    },
    computed: {
      ...mapGetters([
        'currentOrganization',
        'currentUser',
        'token'
      ]),
      firstChar () {
        return this.user.nickname.substring(0, 1).toUpperCase()
      }
    },
    created () {
      this._getUser()
    },
    methods: {
      ...mapActions([
        'setToken'
      ]),
      ...mapMutations({
        SET_CURRENT_ORGANIZATION,
        SET_CURRENT_USER
      }),
      // 获取用户信息
      _getUser () {
        // 缓存中已经存在当前用户
        if (this.currentUser) {
          // 直接获取
          this.user = new User(this.currentUser)

          return
        }

        // 缓存中没有当前用户，则向服务器请求
        this.$api.user.token(this.token).then((res) => {
          this.user = new User(res.data)

          // 缓存当前用户
          this.SET_CURRENT_USER(this.user)
        })
      },
      // 返回首页
      toIndex () {
        // 当前页不是首页才返回
        if (this.$route.name !== 'organization') {
          // 顶部标题重置
          this.SET_CURRENT_ORGANIZATION()

          // 直接返回根目录是因为会自动跳转到组织页
          this.$router.push('/organizations')
        }
      },
      // 退出
      logout () {
        this.$Modal.confirm({
          title: '操作确认',
          content: '确定退出？',
          onOk: () => {
            // 清除Token
            this.setToken(undefined)

            // 返回登录页
            this.$router.replace('/login')
          }
        })
      }
    },
    components: {
      asIcon
    }
  }
</script>

<style scoped lang="stylus">
  @import "~assets/stylus/variable.styl"

  #indexContainer
    height 100%
    .ivu-layout-header
      height 50px
      background-color $white-color
      box-shadow 0 0 8px 0 rgba(0, 0, 0, .1)
      position relative
      z-index 1
      display flex
      justify-content center
      align-items center
      .header-title
        height 100%
        font-size $huge-size
        font-weight 600
        line-height 50px
        cursor pointer
        overflow hidden
        white-space nowrap
        text-overflow ellipsis
      .ivu-dropdown
        position absolute
        right 0
        margin-right 10px
        .ivu-avatar
          background-color $primary-color
          cursor pointer
        #asIcon
          width 100%
    .ivu-layout-content
      display flex
      overflow-y hidden
</style>