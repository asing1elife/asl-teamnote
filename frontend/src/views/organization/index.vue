<template>
  <div id="organizationPanel" class="container">
    <div class="real-content horiz">
      <h3 class="organization-title">组织列表</h3>
      <div class="organization-content">
        <i-card class="organization-item"
                v-for="organization in organizations"
                :key="organization.id"
                @click.native="toHome(organization)">
          <div slot="extra" class="organization-operate">
            <as-icon stop name="trash" size="small" color="error" class="del-btn"
                     @click="delOrganization(organization.id)"></as-icon>
            <as-icon stop name="cog" size="small" class="setting-btn"
                     @click="openOrganizationModal(organization.id)"></as-icon>
          </div>
          <p slot="title">{{organization.name}}</p>
          {{organization.description}}
        </i-card>
        <i-card class="organization-item new">
          <as-icon size="large" text="创建新组织"
                   @click="openOrganizationModal(-1)"></as-icon>
        </i-card>
      </div>
    </div>
    <as-modal v-model="show" title="组织设置" width="700"
              :ok="formSubmit">
      <as-form ref="organizationForm"
               :model="organization" :rules="rules">
        <i-form-item prop="name" label="组织名称">
          <i-input placeholder="请输入组织名称"
                   v-model="organization.name"
                   :maxlength="30"></i-input>
        </i-form-item>
        <i-form-item prop="description" label="组织描述">
          <i-input placeholder="请输入组织描述"
                   v-model="organization.description"
                   :maxlength="50"></i-input>
        </i-form-item>
      </as-form>
    </as-modal>
  </div>
</template>

<script>
  import asIcon from 'components/as-icon'
  import asModal from 'components/as-modal'
  import asForm from 'components/as-form'
  import Organization from 'model/organization'
  import { mapMutations } from 'vuex'
  import { SET_CURRENT_ORGANIZATION } from 'store/mutation-types'

  export default {
    name: 'organization',
    data () {
      return {
        show: false,
        organization: new Organization(-1),
        organizations: [],
        rules: {
          name: [
            {
              required: true,
              message: '请输入组织名称',
              trigger: 'validate'
            }
          ],
          description: [
            {
              required: true,
              message: '请输入组织描述',
              trigger: 'validate'
            }
          ]
        }
      }
    },
    created () {
      this._getOrganizations()
    },
    methods: {
      ...mapMutations({
        SET_CURRENT_ORGANIZATION
      }),
      _getOrganizations () {
        this.$api.organization.list().then((res) => {
          this.organizations = res.data
        })
      },
      _getOrganization (organizationId) {
        this.$api.organization.get(organizationId).then((res) => {
          this.organization = new Organization(res.data)
        })
      },
      delOrganization (organizationId) {
        this.$Modal.confirm({
          title: '操作确认',
          content: '确认删除该组织？',
          onOk: () => {
            this.$api.organization.del(organizationId).then((res) => {
              if (res.success) {
                this.$Message.success('删除成功')

                this._getOrganizations()
              }
            })
          }
        })
      },
      openOrganizationModal (organizationId) {
        this._getOrganization(organizationId)

        this.show = true
      },
      formSubmit () {
        // 调用asForm组件的提交函数
        return this.$refs.organizationForm.submit({
          url: this.$api.organization.baseUrl,
          success: () => {
            this._getOrganizations()
          }
        })
      },
      toHome (organization) {
        let organizationId = organization.id
        let organizationName = organization.name

        this.SET_CURRENT_ORGANIZATION(organizationName)

        this.$router.push(`organizations/${organizationId}`)
      }
    },
    components: {
      asIcon,
      asModal,
      asForm
    }
  }
</script>

<style scoped lang="stylus">
  @import "~assets/stylus/variable.styl"

  #organizationPanel
    background-color $white-color
    justify-content center
    .real-content
      width 810px
      overflow hidden
      .organization-title
        font-size $large-size
        font-weight 700
        margin-bottom 15px
      .organization-content
        display flex
        flex-wrap wrap
        .organization-item
          width 220px
          height 105px
          margin 0 10px 20px
          cursor pointer
          overflow hidden
          display flex
          flex-direction column
          & >>> .ivu-card-head
            flex 0 0 37px
            border-bottom none
            padding-bottom 5px
            padding-top 10px
            transition padding .3s
            p
              font-size $secondary-size
          & >>> .ivu-card-extra
            display none
            .organization-operate
              display flex
              .as-icon
                margin 0 3px
          & >>> .ivu-card-body
            padding 0 16px
            margin-bottom 16px
            overflow hidden
            display -webkit-box
            -webkit-line-clamp 3
            -webkit-box-orient vertical
            font-size $small-size
            letter-spacing 1px
          &.new
            display inline-flex
            justify-content center
            align-items center
            border-style dashed
            & >>> .ivu-card-body
              padding 0
              margin 0
          &:hover
            & >>> .ivu-card-head
              padding-right 60px
            & >>> .ivu-card-extra
              display block
</style>