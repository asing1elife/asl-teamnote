<template>
  <div id="homePanel" class="container">
    <i-menu ref="menu" mode="horizontal" :active-name="defaultMenu">
      <i-menu-item v-for="menu in menus" :key="menu.name" :name="menu.name" :to="menu.link">{{menu.label}}</i-menu-item>
    </i-menu>
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: 'project',
    props: {
      organizationId: {
        type: String,
        default: '-1'
      }
    },
    data () {
      return {
        defaultMenu: 'project',
        menus: [
          {
            name: 'project',
            label: '项目',
            link: `/organizations/${this.organizationId}/projects`
          },
          {
            name: 'daily',
            label: '日志',
            link: `/organizations/${this.organizationId}/dailies`
          }
        ]
      }
    },
    created () {
      this._initDefaultMenu()
    },
    methods: {
      _initDefaultMenu () {
        this.$router.push(this.menus[0].link)
      }
    }
  }
</script>

<style scoped lang="stylus">
  #homePanel
    flex-direction column
    padding 0
    overflow hidden
    .ivu-menu
      flex 0 0 40px
      height 40px
      line-height 40px
      display flex
      justify-content center
</style>