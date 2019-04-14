<template>
  <div>
     <div class="title">考勤详情</div>
      <hr>
      <a-table :columns="columns" :dataSource="data" bordered :pagination="false">
        
      </a-table>
  </div>
</template>
<script>
const columns = [{
  title: '班次',
  dataIndex: 'id',
  scopedSlots: { customRender: 'id' },
},{
  title: '上班考勤时间',
  dataIndex: 'intime',
  scopedSlots: { customRender: 'intime' },
},{
  title: '下班考勤时间',
  dataIndex: 'outtime',
  scopedSlots: { customRender: 'outtime' },
}]
const data =[{
  id:1,
  intime:1,
  outtime:1
}]
export default {
  data () {
    return {
      columns,
      data
    }
  }
}
</script>

