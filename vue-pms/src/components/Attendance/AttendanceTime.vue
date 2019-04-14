<template>
  <div>
     <div class="title">考勤时间</div>
      <hr>
      <a-card title="固定班次">
        <a-button @click="handleAddClicked">添加</a-button>
        <a-button @click="handleDeleted">删除</a-button>
        <a-button @click="handleSubmit" v-if="!status">提交</a-button>
        <a-button @click="handleUpdate" v-if="status">修改</a-button>
        <br><br>
        <a-table  size="small" :columns="columns" :dataSource="attendances" bordered :pagination="false">
          <template slot="intime" slot-scope="text, record">
            <a-time-picker @change="e=>onChange1(e,record)" :defaultValue="moment(record.intime, 'HH:mm:ss')" />
          </template>
          <template slot="outtime" slot-scope="text, record">
            <a-time-picker @change="e=>onChange2(e,record)" :defaultValue="moment(record.outtime, 'HH:mm:ss')" />
          </template>
        </a-table>
      </a-card>
      <a-card title="特殊班次" style="margin-top:20px">
        <a-table  size="small" :columns="special" bordered :pagination="false"></a-table>
      </a-card>
      <a-card title="计算出勤小时数扣除规则" style="margin-top:20px">
        <a-radio-group name="radioGroup" :defaultValue="1">
          <a-radio :value="1">若有签到，签退遗漏，则当天出勤小时数自动记为0</a-radio>
          <br><br>
          <a-radio :value="2">若有签到，签退遗漏，则当天出勤小时数按应出勤小时数核算</a-radio>
        </a-radio-group>
      </a-card>
      <p class="text-center">
        <a-button type="primary" icon="cloud" size="large" style="margin-top:50px;align:center">上传</a-button>
      </p>
  </div>
</template>
<script>
import moment from 'moment';
import {formatDate} from '@/date'
import resources from '@/resources'
const columns = [{
  title: '班次',
  dataIndex: 'id',
  width:"20%",
  scopedSlots: { customRender: 'id' },
},{
  title: '上班考勤时间',
  dataIndex: 'intime',
  width:"40%",
  scopedSlots: { customRender: 'intime' },
},{
  title: '下班考勤时间',
  dataIndex: 'outtime',
  width:"40%",
  scopedSlots: { customRender: 'outtime' },
}]
const special = [{
  title: '班次',
  dataIndex: 'id',
  width:"20%",
  scopedSlots: { customRender: 'id' },
},{
  title: '要求工作时长',
  dataIndex: 'time',
  width:"40%",
  scopedSlots: { customRender: 'time' },
},{
  title: '有效打卡时间',
  dataIndex: 'validtime',
  width:"40%",
  scopedSlots: { customRender: 'validtime' },
}]
export default {
   filters:{
    formatDate(time){
        let date = new Date(time);
        return formatDate(date,'hh:mm:ss');
    }
  },
  data () {
    return {
      columns,
      special,
      attendances:[],
      status:"",
    }
  },
  methods:{
    moment,
    handleAddClicked(){
      var attendance = {}
      attendance.id = this.attendances.length + 1
      attendance.key = this.attendances.length + 1
      attendance.index = this.attendances.length
      attendance.intime = '00:00:00'
      attendance.outtime = '00:00:00'
      this.attendances.push(attendance)
    },
    handleDeleted(){
      this.attendances.pop()
    },
    handleSubmit(){
      this.submitAttendances()
    },
    handleUpdate(){
      this.updateAttendances()
    },
    onChange1(e,record){
      this.attendances[record.index].intime = formatDate(new Date(e),'hh:mm:ss')
      console.log(this.attendances)
    },
    onChange2(e,record){
      this.attendances[record.index].outtime = formatDate(new Date(e),'hh:mm:ss')
      console.log(this.attendances)
    },
    submitAttendances(){
      let that = this;
      let url = resources.submitAttendances();
      this.$ajax
      .post(url,this.attendances)
      .then(res => {
        this.getAttendances()
      })
      .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息："+ error);
          console.log(error);
      });
    },
    updateAttendances(){
      let that = this;
      let url = resources.updateAttendances();
      this.$ajax
      .post(url,this.attendances)
      .then(res => {
        this.getAttendances()
      })
      .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息："+ error);
          console.log(error);
      });
    },
    getAttendances(){
      let that = this;
      let url = resources.getAttendances();
      this.$ajax
      .get(url)
      .then(res => {
          console.log(res.data.extend)
          that.attendances = res.data.extend.attendances
          if(res.data.extend.attendances.length != 0){
            this.status = true
          }else{
            this.status = false
          }
          for(var i=0;i<that.attendances.length;i++){
              that.attendances[i].key = that.attendances[i].id
              that.attendances[i].index = i
          } 
      })
      .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息："+ error);
          console.log(error);
      });
    }
  },
  mounted:function() {
      this.getAttendances();
  }
}
</script>

