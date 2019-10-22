<template>
  <div id="asEmptyTip" class="as-empty-tip" :style="{height: `${height}px`}">{{text}}</div>
</template>

<script>
  export default {
    name: 'asEmptyTip',
    props: {
      text: {
        type: String,
        default: '没有数据'
      },
      height: {
        type: String,
        default: '200'
      }
    }
  }
</script>

<style scoped lang="stylus">
  @import "~assets/stylus/variable.styl"

  #asEmptyTip
    width 100%
    height 100%
    display flex
    justify-content center
    align-items center
    font-size $secondary-size
    color $disabled-color
    letter-spacing 3px
    user-select none
</style>