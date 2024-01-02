<template>
  <div id="dailyPanel" class="real-content">
    <div class="year-section section">
      <div class="section-title">
        <h4>年份</h4>
      </div>
      <div class="section-content">
        <div ref="year" class="section-item"
             v-for="year in years"
             :key="year"
             @click="getMonthsOfYear($event.target, year)">
          <span>{{year}}</span>
          <i-dropdown trigger="click">
            <a href="javascript:">
              <as-icon name="more-horizontal-f"></as-icon>
            </a>
            <i-dropdown-menu slot="list">
              <i-dropdown-item name="report">
                <as-icon name="drupal" text="年报" direction="right"
                         @click="openYearReportModal"></as-icon>
              </i-dropdown-item>
            </i-dropdown-menu>
          </i-dropdown>
        </div>
      </div>
    </div>
    <div class="month-section section">
      <div class="section-title">
        <h4>月份</h4>
      </div>
      <div class="section-content">
        <div ref="month" class="section-item" v-for="month in months"
             :key="month.id"
             @click="getDaysOfMonth($event.target, month.id)">
          {{month.month}}
        </div>
      </div>
    </div>
    <div class="day-section section">
      <div class="section-title">
        <h4>日期</h4>
        <i-tag color="red">{{calcWorkNum(days)}}</i-tag>
      </div>
      <div class="section-content">
        <div ref="day" class="section-item" v-for="day in days"
             :key="day.id"
             @click="getTasksOfDay($event.target, day)">
          {{generateDayWeek(day)}}
          <div class="day-tip">
            <i-tag color="green" v-show="day.extra">加</i-tag>
            <i-tag color="purple" v-show="day.repay">还</i-tag>
            <i-tag color="orange" v-show="day.rest">休</i-tag>
          </div>
        </div>
      </div>
      <div class="section-footer d-flex justify-content-end">
        <i-tag color="green">{{calcExtraNum(days)}}</i-tag>
        <i-tag color="purple">{{calcRepayNum(days)}}</i-tag>
      </div>
    </div>
    <div class="record-section section">
      <div class="section-title">
        <h4>日志详情</h4>
        <div class="record-operate">
          <i-switch size="large" v-model="dailyRecord.extra"
                    :loading="loading"
                    @on-change="changeExtra">
            <span slot="open">加班</span>
            <span slot="close">加班</span>
          </i-switch>
          <i-switch size="large" v-model="dailyRecord.repay"
                    :loading="loading"
                    @on-change="changeRepay">
            <span slot="open">还班</span>
            <span slot="close">还班</span>
          </i-switch>
          <i-switch size="large" v-model="dailyRecord.rest"
                    :loading="loading"
                    @on-change="changeRest">
            <span slot="open">休息</span>
            <span slot="close">休息</span>
          </i-switch>
        </div>
      </div>
      <div class="section-content">
        <div class="task-item"
             v-for="(task, index) in dailyRecord.tasks"
             :key="task.id">
          <i-tooltip placement="right" max-width="170">
            <div slot="content">
              <p>{{task.beginDate}}</p>
              <p v-show="task.finishDate">{{task.finishDate}}</p>
            </div>
            <i-tag class="task-item-index" type="border" color="primary">{{index + 1}}.</i-tag>
            <i-button class="task-item-del-btn" type="error" icon="md-close"
                      @click="delDailyRecordTaskRelate(dailyRecord, task)"></i-button>
            <i-tag :color="task.taskTag.color">{{task.taskTag.name}}</i-tag>
            <i-dropdown @on-click="updateTaskStatus($event, task)">
              <i-button size="small"
                        :type="getTaskStatusColor(task.status.code)">
                {{task.status.name}}
                <i-icon type="ios-arrow-down"></i-icon>
              </i-button>
              <i-dropdown-menu slot="list">
                <i-dropdown-item v-for="status in taskStatus"
                                 :key="status.code" :name="status.code">
                  {{status.name}}
                </i-dropdown-item>
              </i-dropdown-menu>
            </i-dropdown>
            <i-tag color="cyan">{{task.project.name}}</i-tag>
            <i-tag type="border" color="purple"
                   @click.native="copyToClipboard(task.name)">{{task.name}}
            </i-tag>
          </i-tooltip>
        </div>
      </div>
      <div class="section-footer">
        <i-button type="primary"
                  @click="generateDailyReport">生成日报
        </i-button>
      </div>
    </div>
    <as-modal footer-hide
              class-name="daily-report-modal"
              v-model="dailyReportShow">
      <div slot="header" class="ivu-modal-header-inner">
        日报
        <small>点击面板可以直接复制内容</small>
      </div>
      <i-card :bordered="false"
              @click.native="copyToClipboard(todayFinishTaskContent)">
        <p slot="title">今日完成工作</p>
        <div v-html="todayFinishTaskContent"></div>
      </i-card>
      <i-card :bordered="false"
              @click.native="copyToClipboard(todayImplTaskContent)">
        <p slot="title">未完成工作</p>
        <div v-html="todayImplTaskContent"></div>
      </i-card>
    </as-modal>
    <as-modal footer-hide
              class-name="year-report-modal"
              width="70%"
              v-model="yearReportShow">
      <div slot="header" class="ivu-modal-header-inner">
        {{report.name}}
      </div>
      <i-alert show-icon>
        汇总数据
        <as-icon stop
                 name="pie-chart" size="large" color="info" slot="icon"></as-icon>
        <div slot="desc">
          <p>今年共计工作 {{report.monthNum}} 个月， {{report.dayNum}} 天，其中有 {{report.dayExtraNum}} 天在加班，加班率达到 {{report.dayExtraPercent}}%</p>
          <p>今年的 {{report.projectNum}} 个项目，共计创建了 {{report.taskNum}} 个任务，已完成 {{report.taskFinishNum}} 个，完成率达到 {{report.taskFinishPercent}}%</p>
        </div>
      </i-alert>
      <i-alert show-icon
               type="success">
        项目数据
        <as-icon stop
                 name="podcast" size="large" color="success" slot="icon"></as-icon>
        <div slot="desc"
             v-html="report.projectMemo">
        </div>
      </i-alert>
      <i-alert show-icon
               type="success">
        任务数据
        <as-icon stop
                 name="snapchat-circle" size="large" color="success" slot="icon"></as-icon>
        <div slot="desc"
             v-html="report.taskTagMemo">
        </div>
      </i-alert>
      <i-alert show-icon
               type="warning">
        月度数据
        <as-icon stop
                 name="smiley-f" size="large" color="warning" slot="icon"></as-icon>
        <div slot="desc"
             v-html="report.monthMemo">
        </div>
      </i-alert>
      <i-alert show-icon
               type="error">
        日数据
        <as-icon stop
                 name="spotify" size="large" color="error" slot="icon"></as-icon>
        <div slot="desc">
          <p v-html="report.dayMemo"/>
          <p>今年最早开始工作的一天是 {{report.minExtraDay}}</p>
          <p>今年最晚还在工作的一天是 {{ report.maxExtraDay}}</p>
        </div>
      </i-alert>
    </as-modal>
  </div>
</template>

<script>
  import asIcon from 'components/as-icon'
  import asModal from 'components/as-modal'
  import { activeCurrentItem, isTargetTag } from 'assets/scripts/dom'
  import dictionary, { getColor } from 'model/dictionary'
  import DailyRecord from 'model/dailyRecord'
  import Report from 'model/report'
  import _ from 'lodash'

  export default {
    name: 'daily',
    data () {
      return {
        loading: false,
        dailyReportShow: false,
        yearReportShow: false,
        currentYear: null,
        currentMonthId: null,
        currentDay: null,
        todayFinishTaskContent: '',
        todayImplTaskContent: '',
        dailyRecord: new DailyRecord(-1),
        report: new Report(-1),
        dailies: [],
        years: [],
        months: [],
        days: []
      }
    },
    computed: {
      taskStatus () {
        return dictionary.taskStatus.all()
      }
    },
    created () {
      this._getDailies()
    },
    methods: {
      // 获取当前组织所有日志
      _getDailies () {
        this.$api.daily.list({
          organizationId: this.$parent.organizationId
        }).then((res) => {
          this.dailies = res.data

          // 从数据中获取年份
          for (let year in this.dailies) {
            this.years.push(year)
          }

          // 设置当前年份
          this._setCurrentYear()
        })
      },
      // 获取指定月份所有日期
      _getDailyRecords (dailyId, refreshDay = true) {
        this.$api.dailyRecord.list({dailyId}).then((res) => {
          this.days = res.data

          if (refreshDay) {
            // 设置当前日期
            this._setCurrentDay()
          }
        })
      },
      // 获取正确的日期
      _getCurrentDay (recordId) {
        return this.days.find(day => {
          return day.id === recordId
        })
      },
      // 设置当前年份
      _setCurrentYear () {
        this.$nextTick(() => {
          if (this.years.length === 0) {
            return
          }

          let currentItem = this.$refs.year[this.$refs.year.length - 1]
          let currentYear = this.years[this.years.length - 1]

          // 默认激活最近一个年份
          this.getMonthsOfYear(currentItem, currentYear)
        })
      },
      // 设置当前月份
      _setCurrentMonth () {
        this.$nextTick(() => {
          if (this.months.length === 0) {
            return
          }

          let currentItem = this.$refs.month[this.$refs.month.length - 1]
          let currentMonthId = this.months[this.months.length - 1].id

          // 默认激活最近一个月份
          this.getDaysOfMonth(currentItem, currentMonthId)
        })
      },
      // 设置当前日期
      _setCurrentDay () {
        this.$nextTick(() => {
          if (this.days.length === 0) {
            return
          }

          let currentItem = this.$refs.day[this.$refs.day.length - 1]
          let currentDay = this.days[this.days.length - 1]

          // 默认激活最近一个日期
          this.getTasksOfDay(currentItem, currentDay)
        })
      },
      // 获取指定年份的月份列表
      getMonthsOfYear (item, year) {
        // 激活当前项
        activeCurrentItem(this.$refs.year, item)

        // 将被点击的年份设为当前年份
        this.currentYear = year

        // 获取当前年份的所有月份
        this.months = this.dailies[year]

        // 设置当前月份
        this._setCurrentMonth()
      },
      // 获取指定月份的日期列表
      getDaysOfMonth (item, monthId) {
        // 防止点击扩展菜单时触发事件
        if (!isTargetTag(item, 'div')) {
          return
        }

        // 激活当前项
        activeCurrentItem(this.$refs.month, item)

        // 将被点击的月份设为当前月份
        this.currentMonthId = monthId

        // 获取月份对应日志列表
        this._getDailyRecords(monthId)
      },
      // 获取指定日期的所有任务列表
      getTasksOfDay (item, day) {
        // 激活当前项
        activeCurrentItem(this.$refs.day, item)

        // 将被点击的日期设为当前日期
        this.currentDay = day

        this.dailyRecord = new DailyRecord(day)
      },
      // 获取任务状态颜色
      getTaskStatusColor (code) {
        return getColor(code)
      },
      updateTaskStatus (code, task) {
        this.$api.task.status(task.id, {
          organizationId: this.$parent.organizationId,
          statusCode: code
        }).then(() => {
          this.$Message.success('状态更新成功')

          task.status = dictionary.taskStatus.get(code)
        })
      },
      // 获取任务时间段
      getTaskDateInterval (task) {
        let dateRange = `${task.beginDate} `

        if (task.finishDate) {
          dateRange += task.finishDate
        }

        return dateRange
      },
      // 更新加班状态
      changeExtra (val) {
        this.loading = true
        this.$api.dailyRecord.extra(this.dailyRecord.id, val).then((res) => {
          this.loading = !res.success

          this.currentDay.extra = val
        })
      },
      // 更新还班状态
      changeRepay (val) {
        this.loading = true
        this.$api.dailyRecord.repay(this.dailyRecord.id, val).then((res) => {
          this.loading = !res.success

          this.currentDay.repay = val
        })
      },
      // 更新休息状态
      changeRest (val) {
        this.loading = true

        this.$api.dailyRecord.rest(this.dailyRecord.id, val).then((res) => {
          this.loading = !res.success

          this.currentDay.rest = val
        })
      },
      // 复制到剪贴板
      copyToClipboard (content) {
        // 转换内容
        content = content.replace(/<br>/g, '\n')

        this.$copyText(content).then(() => {
          this.$Message.success('已复制')
        }, () => {
          this.$Message.error('复制失败')
        })
      },
      // 生成日期+星期的内容
      generateDayWeek (day) {
        let year = day.daily.year
        let month = `${day.daily.month}`
        let date = `${day.day}`

        // 月份和日期如果只有一位数字，需要补0，因为safari无法识别单数字日期
        month = month.length === 1 ? `0${month}` : month
        date = date.length === 1 ? `0${date}` : date

        // 将获取的具体日期转换为日期格式
        let currentDate = new Date(`${year}-${month}-${date}`)

        // 待匹配的星期数组
        let week = ['Sun.', 'Mon.', 'Tues.', 'Wed.', 'Thur.', 'Fri.', 'Sat.']

        // 获取正确的星期数之后拼接上日期
        return `${week[currentDate.getDay()]}${date}`
      },
      // 生成日报
      generateDailyReport () {
        this.dailyReportShow = true

        // 重置
        this.todayFinishTaskContent = ''
        this.todayImplTaskContent = ''

        // 获取当天的任务列表
        let tasks = this.currentDay.tasks
        // 已完成任务数量
        let finishTaskNum = 0
        // 进行中任务数量
        let implTaskNum = 0

        // 先按项目名称排序
        let orderTasks = _.orderBy(tasks, ['project.name', 'asc'])

        // 轮询任务列表
        for (let key in orderTasks) {
          // 获取当前任务
          let task = tasks[key]

          // 当前任务已完成
          if (task.status.code === dictionary.taskStatus.finish.code) {
            // 已完成数量累加
            finishTaskNum++

            let finishTaskContent = `${finishTaskNum}. [ ${task.project.name} ] ${task.name} <br>`

            this.todayFinishTaskContent += finishTaskContent
          }

          // 当前任务进行中
          if (task.status.code === dictionary.taskStatus.impl.code) {
            // 进行中数量累加
            implTaskNum++

            let implTaskContent = `${implTaskNum}. [ ${task.project.name} ] ${task.name} <br>`

            this.todayImplTaskContent += implTaskContent
          }
        }
      },
      // 计算上班数量
      calcWorkNum (days) {
        let workNum = days.length

        _.forEach(days, (value) => {
          if (value.rest) {
            workNum--
          }
        })

        return workNum
      },
      // 计算加班数量
      calcExtraNum (days) {
        let extraNum = 0

        _.forEach(days, (value) => {
          if (value.extra) {
            extraNum++
          }
        })

        return extraNum
      },
      // 计算还班数量
      calcRepayNum (days) {
        let repayNum = 0

        _.forEach(days, (value) => {
          if (value.repay) {
            repayNum++
          }
        })

        return repayNum
      },
      // 删除日志与任务的关联
      delDailyRecordTaskRelate (dailyRecord, task) {
        this.$Modal.confirm({
          title: '操作确认',
          content: '确认将该任务从这一天的日志中删除？',
          onOk: () => {
            // 获取该任务在当前日志中的任务列表中的索引
            let taskIndex = _.indexOf(dailyRecord.tasks, task)

            // 从当前日志的任务列表中移除该任务
            dailyRecord.tasks.splice(taskIndex, 1)

            this.$api.dailyRecord.save(dailyRecord).then((res) => {
              if (res.success) {
                this.$Message.success('任务关联删除成功')
              }
            })
          }
        })
      },
      openYearReportModal () {
        this.$api.report.year(this.currentMonthId).then((res) => {
          if (!_.isString(res.data)) {
            this.report = new Report(res.data)
            this.yearReportShow = true
          }
        })
      }
    },
    components: {
      asIcon,
      asModal
    }
  }
</script>

<style scoped lang="stylus">
  @import "~assets/stylus/variable.styl"

  #dailyPanel
    margin 15px
    background-color $white-color
    border-radius $radius-size
    flex-grow 1
    overflow-y hidden
    .year-section
    .month-section
      min-width 150px
      overflow hidden
    .day-section
      min-width 200px
      overflow hidden
      .section-title
        display flex
        justify-content space-between
      .section-item
        display flex
        justify-content space-between
    .record-section
      flex-grow 1
      .section-title
        display flex
        justify-content space-between
        .ivu-switch
          margin-right 15px
      .section-content
        margin 15px 0
        padding 0 15px
        .task-item
          margin-bottom 10px
          font-size $normal-size
          &:hover
            .task-item-index
              display none
            .task-item-del-btn
              display inline
              margin-right 4px
          .task-item-del-btn
            font-size 14px
            width auto
            height 24px
            padding 0 6px
            margin 2px 4px 2px 0
            display none
          & >>> .ivu-tooltip-inner-with-width
            white-space normal
          .ivu-dropdown
            margin-right 4px
    .section-content
      overflow-x auto

    .year-section
      .section-content
        .section-item
          display flex
          justify-content space-between
          padding-right 10px
          .ivu-dropdown
            display none
            #asIcon
              width 100%
          &:hover
            .ivu-dropdown
              display block

  .daily-report-modal
    .ivu-modal-header
      small
        padding-left 5px
        color $sub-color
        font-size $small-size
    .ivu-card
      margin-bottom 15px
      cursor pointer
</style>