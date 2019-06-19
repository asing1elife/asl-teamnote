<template>
  <div id="dailyPanel" class="real-content">
    <div class="year-section section">
      <div class="section-title">
        <h4>年份</h4>
      </div>
      <div class="section-content">
        <div ref="year" class="section-item" v-for="year in years"
             :key="year"
             @click="getMonthsOfYear($event.target, year)">
          {{year}}
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
      </div>
      <div class="section-content">
        <div ref="day" class="section-item" v-for="day in days"
             :key="day.id"
             @click="getTasksOfDay($event.target, day)">
          {{day.day}}
          <div class="day-tip">
            <i-tag color="green" v-show="day.extra">加</i-tag>
            <i-tag color="purple" v-show="day.repay">还</i-tag>
            <i-tag color="orange" v-show="day.rest">休</i-tag>
          </div>
        </div>
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
        <p class="task-item"
           v-for="(task, index) in dailyRecord.tasks"
           :key="task.id">
          <i-tooltip placement="right" max-width="140"
                     :content="getTaskDateInterval(task)">
            <i-tag type="border" color="primary">{{index + 1}}.</i-tag>
            <i-tag :color="task.taskTag.color">{{task.taskTag.name}}</i-tag>
            <i-tag :color="getTaskStatusColor(task.status.code)">{{task.status.name}}</i-tag>
            <i-tag color="cyan">{{task.project.name}}</i-tag>
            <i-tag type="border" color="purple">{{task.name}}</i-tag>
          </i-tooltip>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
  import { activeCurrentItem } from 'assets/scripts/dom'
  import { TaskStatus } from 'model/dictionary'
  import DailyRecord from 'model/dailyRecord'

  export default {
    name: 'daily',
    data () {
      return {
        loading: false,
        currentYear: null,
        currentMonthId: null,
        currentDay: null,
        dailies: [],
        years: [],
        months: [],
        days: [],
        dailyRecord: new DailyRecord(-1)
      }
    },
    created () {
      this._getDailies()
    },
    methods: {
      // 获取当前组织所有日志
      _getDailies () {
        this.$api.daily.dailies(this.$parent.organizationId).then((res) => {
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
        this.$api.dailyRecord.records(dailyId).then((res) => {
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
        return TaskStatus.getDictionaryColor(code)
      },
      // 获取任务时间段
      getTaskDateInterval (task) {
        return `${task.beginDate} ${task.finishDate || '~'}`
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
      }
    }
  }
</script>

<style lang="stylus">
  @import "~assets/stylus/variable.styl"

  #dailyPanel
    margin 15px
    background-color $white-color
    border-radius $radius-size
    flex-grow 1
    overflow-y hidden
    .year-section
    .month-section
    .day-section
      min-width 150px
      overflow hidden
    .day-section
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
    .section-content
      overflow-x auto
</style>