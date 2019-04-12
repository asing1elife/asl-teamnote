<template>
  <i-layout id="indexContainer">
    <i-header>
      <h2 class="header-title" @click="toIndex">teamnote</h2>
    </i-header>
    <i-content>
      <router-view/>
    </i-content>
  </i-layout>
</template>

<script>
  export default {
    name: 'index',
    methods: {
      // 返回首页
      toIndex () {
        // 当前页不是首页才返回
        if (this.$route.name !== 'organization') {
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
    .ivu-layout-content
      display flex
</style>