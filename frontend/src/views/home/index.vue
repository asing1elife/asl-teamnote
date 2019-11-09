<template>
  <div id="homePanel" class="container">
    <i-menu ref="menu" mode="horizontal"
            :active-name="defaultMenu">
      <i-menu-item v-show="!searchInputShow" v-for="menu in menus"
                   :key="menu.name" :name="menu.name" :to="menu.link">
        {{menu.label}}
      </i-menu-item>
      <i-button type="primary" icon="ios-search"
                v-show="!searchInputShow"
                @click="showSearchInput"></i-button>
      <i-input search
               ref="searchInput" placeholder="请输入任务名称" style="max-width: 200px"
               v-show="searchInputShow"
               v-model="taskName"
               @on-blur="hideSearchInput"
               @on-enter="searchTask"></i-input>
    </i-menu>
    <as-modal footer-hide
              v-model="searchContentShow">
      <div slot="header">搜索 {{taskName}} 的结果</div>
      <i-list>
        <i-list-item v-for="(task, index) in searchTasks"
                     :key="task.id">
          <i-list-item-meta>
            <div slot="title">
              <i-tag type="border" color="primary">{{index + 1}}.</i-tag>
              <i-tag color="cyan">{{task.project.name}}</i-tag>
              <i-tag type="border" color="purple"
                     @click.native="copyToClipboard(task.name)">{{task.name}}
              </i-tag>
            </div>
            <div slot="description">
              <i-tag :color="task.taskTag.color">{{task.taskTag.name}}</i-tag>
              <i-tag :color="getTaskStatusColor(task.status.code)">{{task.status.name}}</i-tag>
              <i-tag color="magenta"
                     v-if="task.beginDate">
                <span>{{task.beginDate}}</span>
                <span v-show="task.finishDate"> ~ {{task.finishDate}}</span>
              </i-tag>
            </div>
          </i-list-item-meta>
        </i-list-item>
      </i-list>
    </as-modal>
    <router-view/>
  </div>
</template>

<script>
  import asModal from 'components/as-modal'
  import { getColor } from 'model/dictionary'

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
        searchInputShow: false,
        searchInputFocus: false,
        searchContentShow: false,
        taskName: '',
        defaultMenu: 'project',
        searchTasks: [],
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
      },
      showSearchInput () {
        this.taskName = ''
        this.searchInputShow = true

        setTimeout(() => {
          this.$refs.searchInput.focus()
        }, 20)
      },
      hideSearchInput () {
        this.searchInputShow = false
      },
      searchTask () {
        if (!this.taskName) {
          this.$Message.warning('请输入任务名称')
          return
        }

        this.searchContentShow = true

        this.$api.task.search({
          organizationId: this.organizationId,
          taskName: this.taskName
        }).then((res) => {
          this.searchTasks = res.data
        })
      },
      getTaskStatusColor (code) {
        return getColor(code)
      }
    },
    components: {
      asModal
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
      align-items center
</style>