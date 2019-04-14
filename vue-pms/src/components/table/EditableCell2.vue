<template>
<div class='editable-cell'>
  <div v-if="editable" class='editable-cell-input-wrapper'>
    <a-input
      :value="value"
      @change="handleChange"
      @pressEnter="check"
    /><a-icon
      type='check'
      class='editable-cell-icon-check'
      @click="check"
    />
  </div>
  <div v-else class='editable-cell-text-wrapper'>
    {{value || ' '}}
  </div>
</div>
</template>
<script>
export default {
  props: {
    text: String,
  },
  data () {
    return {
      value: this.text,
      id:'',
      editable: true,
    }
  },
  methods: {
    handleChange (e) {
      const value = e.target.value
      this.value = value
      var id=e.path[4].cells[0].innerText.substring(3,4)
      this.id = id
    },
    check (e) {
      var id=e.path[4].cells[0].innerText.substring(3,4)
      this.id = id
      if(this.value != ''){
        this.editable = false
        this.$emit('change', this.id , this.value)
      }
      else{
        alert("保存内容不能为空")
      }
    },
    edit () {
      this.editable = true
    },
    change(){
      this.editable = false
      console.log("change successfully")
    }
  },
}
</script>