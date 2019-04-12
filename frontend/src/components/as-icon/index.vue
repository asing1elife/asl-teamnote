<template>
  <div ref="asIcon" id="asIcon" class="as-icon" :class="{ghost: ghost}"
       @click="clickEvent($event)">
    <span ref="jam" class="jam" :class="`jam-${name}`"></span>
    <p ref="tip" class="tip-text" v-if="text">{{text}}</p>
  </div>
</template>

<script>
  import { addClass } from 'assets/scripts/dom'

  // 图标大小默认范围
  const SIZES = {
    small: 15,
    normal: 20,
    middle: 25,
    large: 30,
    huge: 35
  }

  // 图标默认颜色
  const COLORS = {
    info: ['rgba(0, 185, 251, .5)', '#2db7f5'],
    success: ['rgba(0, 194, 97, .5)', '#19be6b'],
    warning: ['rgba(255, 148, 0, .5)', '#ff9900'],
    error: ['rgba(255, 38, 0, .5)', '#ed4014']
  }

  export default {
    name: 'asIcon',
    props: {
      // 图标颜色，来自jam-icons
      name: {
        type: String,
        default: 'plus'
      },
      // 图标文字
      text: {
        type: String,
        default: null
      },
      // 图标大小
      size: {
        type: [Number, String],
        default: SIZES.normal,
        validator (val) {
          if (typeof val === 'number') {
            // 尺寸最小支持11
            return val > 10
          }

          let size = SIZES[val]

          // 字符串必须是默认范围之内的
          return !!size
        }
      },
      // 图标颜色
      color: {
        type: String
      },
      // 文字方向
      direction: {
        type: String,
        default: 'bottom'
      },
      // 禁止点击
      stop: {
        type: Boolean,
        default: false
      },
      // 幽灵模式
      ghost: {
        type: Boolean,
        default: false
      }
    },
    mounted () {
      this._initIconSize()
      this._initIconColor()
      this._initIconTextDirection()
    },
    methods: {
      // 初始化图标大小
      _initIconSize () {
        let size

        if (typeof this.size === 'number') {
          // 数字类型直接赋值
          size = this.size
        } else {
          // 字符串类型从默认范围中获取
          size = SIZES[this.size]
        }

        this.$refs.jam.style.fontSize = `${size}px`

        // 只有需要显示文字是才有效
        if (this.text) {
          this.$refs.tip.style.fontSize = `${size / 2}px`
        }
      },
      // 初始化图标颜色
      _initIconColor () {
        if (this.color === undefined) {
          return
        }

        let defaultColor = COLORS[this.color][0]
        let lightColor = COLORS[this.color][1]

        // 默认颜色
        this.$refs.asIcon.style.color = defaultColor

        // 鼠标悬浮变色
        this.$refs.asIcon.addEventListener('mouseover', () => {
          this.$refs.asIcon.style.color = lightColor
        })

        // 鼠标离开恢复
        this.$refs.asIcon.addEventListener('mouseout', () => {
          this.$refs.asIcon.style.color = defaultColor
        })
      },
      // 初始化图标文字位置
      _initIconTextDirection () {
        addClass(this.$refs.asIcon, this.direction)
      },
      // 对外开放点击接口
      clickEvent (event) {
        if (event && this.stop) {
          event.stopPropagation()
        }

        this.$emit('click')
      }
    }
  }
</script>

<style scoped lang="stylus">
  @import "~assets/plugins/jam-icons/css/jam.min.css"
  @import "~assets/stylus/variable.styl"

  #asIcon
    display inline-flex
    color $sub-color
    transition color .3s
    cursor pointer
    &.ghost
      border-radius 50%
      background-color $content-color
      color $white-color
      opacity .7
    &.top
      flex-direction column-reverse
    &.right
      flex-direction row
      justify-content center
      align-items normal
      .tip-text
        margin-left 5px
    &.bottom
      flex-direction column
      align-items center
      .tip-text
        margin-top 5px
    &.left
      flex-direction row-reverse
    &:hover
      color $l-primary-color
    .jam
      font-size $large-size
    .tip-text
      display flex
      justify-content center
      align-items center
      font-size $normal-size
</style>