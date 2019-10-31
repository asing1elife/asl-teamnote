<template>
  <div id="projectPanel" class="real-content">
    <i-card class="project-item"
            v-for="project in projects"
            :key="project.id">
      <i-dropdown slot="extra">
        <a href="javascript:">
          <as-icon name="more-horizontal-f"></as-icon>
        </a>
        <i-dropdown-menu slot="list">
          <i-dropdown-item name="update">
            <as-icon name="cog" text="项目设置" direction="right"
                     @click="openProjectModal(project.id)"></as-icon>
          </i-dropdown-item>
          <i-dropdown-item name="del">
            <as-icon name="trash" text="删除项目" direction="right" color="error"
                     @click="delProject(project.id)"></as-icon>
          </i-dropdown-item>
        </i-dropdown-menu>
      </i-dropdown>
      <p slot="title">{{project.name}} · <span class="text-mute">{{getProjectTaskLength(project)}}</span></p>
      <div class="project-task-list">
        <div class="task-item"
             v-for="task in project.tasks"
             :key="task.id" :class="checkTaskFinishStatus(task) ? 'finish':''" :style="{borderLeftColor: getTaskLevelColor(task.level.code)}"
             @click="openTaskModal($event, task.id, project.id)">
          <as-icon ghost class="task-del-btn" name="close"
                   @click="delTask(task)"></as-icon>
          <div class="task-content">
            <div class="task-name">{{task.name}}</div>
            <div class="task-tag">
              <i-tag :color="task.taskTag.color">{{task.taskTag.name}}</i-tag>
              <i-dropdown @on-click="updateTaskStatus($event, task)">
                <i-button size="small"
                          :type="getTaskStatusColor(task.status.code)">
                  {{task.status.name}}
                  <i-icon type="ios-arrow-down"></i-icon>
                </i-button>
                <i-dropdown-menu slot="list">
                  <i-dropdown-item v-for="status in taskStatus"
                                   :key="status.coode" :name="status.code">
                    {{status.name}}
                  </i-dropdown-item>
                </i-dropdown-menu>
              </i-dropdown>
            </div>
          </div>
        </div>
      </div>
      <i-button class="new-task-btn" size="small"
                @click="openTaskModal($event, -1, project.id)">
        <as-icon></as-icon>
      </i-button>
    </i-card>
    <i-card class="project-item new">
      <as-icon size="large" text="创建新项目"
               @click="openProjectModal(-1)"></as-icon>
    </i-card>
    <as-modal title="项目设置" v-model="showProject"
              :ok="projectSubmit">
      <as-form ref="projectForm"
               :model="project" :rules="projectRules">
        <i-form-item prop="name" label="项目名称">
          <i-input placeholder="请输入项目名称"
                   v-model="project.name"
                   :maxlength="30"></i-input>
        </i-form-item>
      </as-form>
    </as-modal>
    <as-modal class="task-tag-modal"
              v-model="showTask"
              :enter="false"
              :title="taskTitle" :ok="taskSubmit">
      <as-form ref="taskForm"
               :model="task" :rules="taskRules">
        <i-form-item prop="name" label="任务名称">
          <i-input placeholder="请输入任务名称"
                   v-model="task.name"
                   :maxlength="50"></i-input>
        </i-form-item>
        <i-form-item prop="description" label="任务描述">
          <i-input placeholder="请输入任务描述" type="textarea"
                   v-model="task.description"
                   :maxlength="500" :rows="4"></i-input>
        </i-form-item>
        <i-form-item prop="taskTag" label="任务标签"
                     :show-message="false">
          <div class="task-tag-wrapper">
            <i-select style="width: 250px;" v-model="task.taskTag.id">
              <i-option v-for="taskTag in taskTags"
                        :key="taskTag.id" :value="taskTag.id" :label="taskTag.name"></i-option>
            </i-select>
            <i-poptip width="157"
                      v-model="showTaskTag">
              <as-icon class="add-task-tag-btn" name="plus-circle-f"></as-icon>
              <div slot="content">
                <as-form button ref="taskTagForm"
                         :model="taskTag" :rules="taskTagRules" :url="taskTagUrl" :success="refreshTaskTags">
                  <i-form-item prop="name"
                               :show-message="false">
                    <i-input placeholder="请输入标签名称"
                             v-model="taskTag.name"
                             :maxlength="10"></i-input>
                  </i-form-item>
                  <i-form-item prop="color">
                    <i-radio-group v-model="taskTag.color">
                      <as-radio v-for="color in tagColors"
                                :key="color.color" :label="color.color" :color="color.color">
                        {{color.name}}
                      </as-radio>
                    </i-radio-group>
                  </i-form-item>
                </as-form>
              </div>
            </i-poptip>
          </div>
        </i-form-item>
        <i-form-item prop="period" label="任务时间"
                     :label-width="70">
          <i-date-picker v-model="task.dateRange" type="datetimerange" format="yyyy-MM-dd HH:mm"
                         placeholder="开始时间 - 完成时间" style="width: 250px;"></i-date-picker>
        </i-form-item>
        <i-form-item prop="level" label="任务级别"
                     :label-width="70">
          <i-radio-group v-model="task.levelCode">
            <as-radio v-for="level in taskLevels"
                      :key="level.code" :label="level.code" :color="level.color">
              {{level.name}}
            </as-radio>
          </i-radio-group>
        </i-form-item>
      </as-form>
      <i-button slot="button" type="info"
                @click="saveTaskAndContinue">
        保存&继续
      </i-button>
    </as-modal>
  </div>
</template>

<script>
  import asIcon from 'components/as-icon'
  import asModal from 'components/as-modal'
  import asForm from 'components/as-form'
  import asRadio from 'components/as-radio'
  import Project from 'model/project'
  import Task from 'model/task'
  import TaskTag from 'model/taskTag'
  import dictionary, { getColor } from 'model/dictionary'
  import { isTargetTag } from 'assets/scripts/dom'

  export default {
    name: 'project',
    data () {
      const validateTaskTag = (rule, value, callback) => {
        if (!value || value.id === -1) {
          callback(new Error('请选择任务标签'))
        }

        callback()
      }
      return {
        showProject: false,
        showTask: false,
        showTaskTag: false,
        taskTagUrl: this.$api.taskTag.baseUrl,
        project: new Project(-1),
        task: new Task(-1),
        taskTag: new TaskTag(-1),
        projects: [],
        tasks: [],
        taskTags: [],
        projectRules: {
          name: [
            {
              required: true,
              message: '请输入项目名称',
              trigger: 'validate'
            }
          ]
        },
        taskRules: {
          name: [
            {
              required: true,
              message: '请输入任务名称',
              trigger: 'validate'
            }
          ],
          taskTag: [
            {
              required: true,
              validator: validateTaskTag,
              trigger: 'validate'
            }
          ]
        },
        taskTagRules: {
          name: [
            {
              required: true,
              message: '请输入任务标签名称',
              trigger: 'validate'
            }
          ]
        }
      }
    },
    computed: {
      taskTitle () {
        return `任务设置 ${this.task.project.name}`
      },
      taskLevels () {
        return dictionary.taskLevel.all()
      },
      taskStatus () {
        return dictionary.taskStatus.all()
      },
      tagColors () {
        return TaskTag.tagColors
      }
    },
    created () {
      this._getTaskTags()
    },
    mounted () {
      this._getProjects()
    },
    methods: {
      _getProjects () {
        this.$api.project.list({
          organizationId: this.$parent.organizationId
        }).then((res) => {
          this.projects = res.data

          // 异步获取项目任务列表
          this.projects.forEach((project) => {
            this._getTasksByProject(project)
          })
        })
      },
      _getTaskTags () {
        this.$api.taskTag.list().then((res) => {
          this.taskTags = res.data
        })
      },
      // 获取项目任务列表
      _getTasksByProject (project) {
        this.$api.task.list({
          projectId: project.id
        }).then((res) => {
          project.tasks = res.data
          // 用于触发DOM渲染
          project.name += ' '
        })
      },
      _getProject (projectId) {
        this.$api.project.get(projectId).then((res) => {
          this.project = new Project(res.data)

          if (projectId === -1) {
            this.project.organization.id = this.$parent.organizationId
            this.project.indexNo = this._getLastProjectIndexNo()
          }
        })
      },
      _getTask (taskId, projectId) {
        this.$api.task.get(taskId).then((res) => {
          this.task = new Task(res.data)

          if (taskId === -1) {
            this.task.project = this._getCurrentProjectName(projectId)
          }
        })
      },
      // 获取最后一个项目的索引
      _getLastProjectIndexNo () {
        let length = this.projects.length

        if (length === 0) {
          return 0
        }

        return this.projects[length - 1].indexNo + 1
      },
      // 获取正确的项目名称
      _getCurrentProjectName (projectId) {
        return this.projects.find((project) => {
          return project.id === projectId
        })
      },
      // 更新项目任务列表
      _updateProjectTasks (taskProjectId) {
        // 获取当前任务所属项目
        let project = this.projects.find((project) => {
          return project.id === taskProjectId
        })

        if (project) {
          // 更新项目任务列表
          this._getTasksByProject(project)
        }
      },
      openProjectModal (projectId) {
        this._getProject(projectId)

        this.showProject = true
      },
      openTaskModal (event, taskId, projectId) {
        // 防止更新状态或删除任务时打开窗口
        if (!isTargetTag(event.target, 'div') && taskId !== -1) {
          return
        }

        this._getTask(taskId, projectId)

        this.showTask = true
      },
      projectSubmit () {
        return this.$refs.projectForm.submit({
          url: this.$api.project.baseUrl,
          success: () => {
            this._getProjects()
          }
        })
      },
      delProject (projectId) {
        this.$Modal.confirm({
          title: '操作确认',
          content: '确认删除该项目？',
          onOk: () => {
            this.$api.project.del(projectId).then((res) => {
              if (res.success) {
                this.$Message.success('删除成功')

                this._getProjects()
              }
            })
          }
        })
      },
      // 刷新任务标签列表
      refreshTaskTags () {
        this._getTaskTags()

        this.showTaskTag = false
      },
      taskSubmit () {
        return this.$refs.taskForm.submit({
          url: this.$api.task.baseUrl,
          success: (res) => {
            let task = res.data

            // 更新项目任务列表
            this._updateProjectTasks(task.project.id)
          }
        })
      },
      // 保存并继续
      saveTaskAndContinue () {
        this.taskSubmit().then((res) => {
          if (!res) {
            return
          }

          // 记录当前任务所属的项目id
          let project = this.task.project

          // 重置任务对象
          this.task = new Task(-1)
          this.task.project = project
        })
      },
      // 获取任务级别颜色
      getTaskLevelColor (code) {
        return getColor(code)
      },
      // 获取任务状态颜色
      getTaskStatusColor (code) {
        return getColor(code)
      },
      // 获取项目任务数量
      getProjectTaskLength (project) {
        return project ? project.tasks ? project.tasks.length : 0 : 0
      },
      // 检查任务完成状态
      checkTaskFinishStatus (task) {
        return task.status.code === dictionary.taskStatus.finish.code
      },
      // 修改状态
      updateTaskStatus (name, task) {
        this.$api.task.status(task.id, {
          organizationId: this.$parent.organizationId,
          statusCode: name
        }).then(() => {
          this.$Message.success('状态更新成功')

          this._updateProjectTasks(task.project.id)
        })
      },
      // 删除任务
      delTask (task) {
        this.$Modal.confirm({
          title: '操作确认',
          content: '相关的日志记录也会一起被删除，确认删除该任务？',
          onOk: () => {
            this.$api.task.del(task.id).then((res) => {
              if (res.success) {
                this.$Message.success('删除成功')

                this._updateProjectTasks(task.project.id)
              }
            })
          }
        })
      }
    },
    components: {
      asIcon,
      asModal,
      asForm,
      asRadio
    }
  }
</script>

<style lang="stylus">
  @import "~assets/stylus/variable.styl"

  #projectPanel
    padding 15px 0
    margin 0 15px
    flex-grow 1
    overflow-y auto
    .project-item
      flex 0 0 300px
      display flex
      flex-direction column
      margin-right 15px
      &:not(.new)
        .ivu-card-body
          flex-grow 1
          display flex
          flex-direction column
          padding 0
          .project-task-list
            flex-grow 1
            overflow auto
            margin-bottom 15px
            padding-top 15px
            .task-item
              width calc(100% - 30px)
              border-radius $radius-size
              border 1px solid $base-bd-color
              border-left-width 5px
              margin-right 15px
              margin-left 15px
              margin-bottom 10px
              display flex
              padding 10px 10px 10px 0
              transition box-shadow .1s
              cursor pointer
              position relative
              .task-del-btn
                position absolute
                right -10px
                top -10px
                display none
              .task-content
                flex-grow 1
                padding-left 10px
                .task-name
                  color $title-color
                .task-tag
                  display flex
                  justify-content space-between
              &:hover
                box-shadow 0 1px 6px rgba(0, 0, 0, .2)
                .task-del-btn
                  display inline-flex
              &.finish
                border-left-color $disabled-color !important
                .task-name
                  color $disabled-color
                .task-tag
                  .ivu-tag,
                  .ivu-btn
                    background-color $disabled-color !important
                    border-color $disabled-color !important
          .new-task-btn
            flex 0 0 29px
            margin-bottom 10px
            margin-right 15px
            margin-left 15px
      &.new
        background none
        display flex
        justify-content center
        align-items center
        border-style dashed
        border-color $primary-color

  .task-tag-modal
    .task-tag-wrapper
      display flex
      align-items center
      .ivu-poptip
        margin-left 10px
        .ivu-poptip-rel
          display flex
          justify-content center
          align-items center
        .ivu-poptip-popper
          .ivu-radio-group
            width 100%
            display flex
            flex-wrap wrap
            .ivu-radio-wrapper
              margin 0 4px
</style>