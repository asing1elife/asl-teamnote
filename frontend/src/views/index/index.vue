<template>
  <i-layout id="indexContainer">
    <i-header>
      <h2 class="header-title" @click="toIndex">{{currentOrganization}}</h2>
    </i-header>
    <i-content>
      <router-view/>
    </i-content>
  </i-layout>
</template>

<script>
  import { mapGetters, mapMutations } from 'vuex'
  import { SET_CURRENT_ORGANIZATION } from 'store/mutation-types'

  export default {
    name: 'index',
    computed: {
      ...mapGetters([
        'currentOrganization'
      ])
    },
    methods: {
      ...mapMutations({
        SET_CURRENT_ORGANIZATION
      }),
      // 返回首页
      toIndex () {
        // 当前页不是首页才返回
        if (this.$route.name !== 'organization') {
          // 顶部标题重置
          this.SET_CURRENT_ORGANIZATION()

          // 直接返回根目录是因为会自动跳转到组织页
          this.$router.push('/')
        }
      }
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
        font-size $huge-size
        font-weight 600
        cursor pointer
        overflow hidden
        white-space nowrap
        text-overflow: ellipsis
    .ivu-layout-content
      display flex
      overflow-y hidden
</style>