<template>
  <div>
    <div class="title">加班管理</div>
    <hr>
    <a-tabs v-model="activeKey" @change="showAllOvertime">
      <a-tab-pane tab="待处理加班事物" key="1">
        <a-table :columns="columns" :dataSource="uncheckovertimes" bordered>
          <template slot="subtime" slot-scope="text">{{text | formatDate}}</template>
          <template slot="status" slot-scope="text,record">
            <a-select :defaultValue="text" style="width:60%" @change="e=>solveOvertime(record,e)">
              <a-select-option :key="item.key" v-for="item in allstatus">{{item.status}}</a-select-option>
            </a-select>
          </template>
        </a-table>
      </a-tab-pane>
      <a-tab-pane tab="全部加班记录" key="2">
        <div style="margin-bottom:10px">
          <a-month-picker placeholder="选择月份" @change="getOvertimesByMonth"/>
          <a-button @click="handleChoseDeleted">批量删除</a-button>
          <a-button @click="handleShowAll">查看全部记录</a-button>
        </div>
        <a-table :rowSelection="rowSelection" :columns="columns" :dataSource="overtimes" bordered>
          <template slot="subtime" slot-scope="text">{{text | formatDate}}</template>
          <template slot="status" slot-scope="text">{{text | judgeStatus}}</template>
        </a-table>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>
<script >
import resources from "@/resources";
import { formatDate } from "@/date";
import moment from "moment";
const columns = [
  {
    title: "序号",
    dataIndex: "id",
    scopedSlots: { customRender: "id" }
  },
  {
    title: "用户名",
    dataIndex: "user.loginname",
    scopedSlots: { customRender: "user.loginname" }
  },
  {
    title: "员工名",
    dataIndex: "emp.name",
    scopedSlots: { customRender: "emp.name" }
  },
  {
    title: "加班日期",
    dataIndex: "overtimetime",
    scopedSlots: { customRender: "overtimetime" }
  },
  {
    title: "请求时间",
    dataIndex: "subtime",
    scopedSlots: { customRender: "subtime" }
  },
  {
    title: "原因",
    dataIndex: "reason",
    scopedSlots: { customRender: "reason" }
  },
  {
    title: "状态",
    dataIndex: "status",
    scopedSlots: { customRender: "status" }
  }
];
export default {
  filters: {
    formatDate(time) {
      let date = new Date(time);
      return formatDate(date, "yyyy-MM-dd hh:mm");
    },
    formatOnlyDate(time) {
      let date = new Date(time);
      return formatDate(date, "yyyy-MM-dd");
    },
    judgeStatus(status) {
      if (status == 1) {
        return "批准";
      } else if (status == 2) {
        return "拒绝";
      } else {
        return "待处理";
      }
    }
  },
  data() {
    return {
      columns,
      overtimes: [],
      uncheckovertimes: [],
      selectedRowKeys: [],
      allstatus: [
        {
          key: 0,
          status: "待处理",
          color: "yellow"
        },
        {
          key: 1,
          status: "批准",
          color: "green"
        },
        {
          key: 2,
          status: "拒绝",
          color: "red"
        }
      ],
      activeKey: "1",
      selectMonth: ""
    };
  },
  methods: {
    solveOvertime(record, status) {
      var overtime = { id: record.id, status: status, subtime: record.subtime };
      console.log(overtime);
      let that = this;
      let url = resources.updateOvertime();
      this.$ajax
        .post(url, overtime)
        .then(res => {
          this.getUnCheckOvertimes();
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    showAllOvertime() {
      if (this.activeKey == "2") {
        this.getOvertimes();
      }
    },
    handleChoseDeleted() {
      this.deleteOvertimes(this.selectedRowKeys);
    },
    handleShowAll(){
      this.getOvertimes()
    },
    deleteOvertimes(selectedRowKeys) {
      let that = this;
      let url = resources.deleteOvertimes();
      this.$ajax
        .get(url, {
          params: {
            idList: selectedRowKeys
          }
        })
        .then(res => {
          console.log("delete success");
          this.getOvertimes();
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    getUnCheckOvertimes() {
      let that = this;
      let url = resources.getUnCheckOvertimes();
      this.$ajax
        .get(url)
        .then(res => {
          console.log(res.data.extend);
          that.uncheckovertimes = res.data.extend.overtimes;
          for (var i = 0; i < that.uncheckovertimes.length; i++) {
            that.uncheckovertimes[i].key = that.uncheckovertimes[i].id;
            that.uncheckovertimes[i].overtimetime =
              that.uncheckovertimes[i].year +
              "年" +
              that.uncheckovertimes[i].month +
              "月" +
              that.uncheckovertimes[i].day +
              "日";
          }
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    getOvertimesByMonth(e) {
      var year = formatDate(new Date(e), "yyyy");
      var month = formatDate(new Date(e), "M");
      let that = this;
      let url = resources.getOvertimesByMonth();
      this.$ajax
        .get(url, {
          params: {
            year:year,
            month:month
          }
        })
        .then(res => {
          console.log(res.data.extend);
          that.overtimes = res.data.extend.overtimes;
          for (var i = 0; i < that.overtimes.length; i++) {
            that.overtimes[i].key = that.overtimes[i].id;
            that.overtimes[i].overtimetime =
              that.overtimes[i].year +
              "年" +
              that.overtimes[i].month +
              "月" +
              that.overtimes[i].day +
              "日";
          }
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    getOvertimes() {
      let that = this;
      let url = resources.getOvertimes();
      this.$ajax
        .get(url)
        .then(res => {
          console.log(res.data.extend);
          that.overtimes = res.data.extend.overtimes;
          for (var i = 0; i < that.overtimes.length; i++) {
            that.overtimes[i].key = that.overtimes[i].id;
            that.overtimes[i].overtimetime =
              that.overtimes[i].year +
              "年" +
              that.overtimes[i].month +
              "月" +
              that.overtimes[i].day +
              "日";
          }
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    getUncheckOvertimes() {
      let that = this;
      let url = resources.getOvertimes();
      this.$ajax
        .get(url)
        .then(res => {
          console.log(res.data.extend);
          that.overtimes = res.data.extend.overtimes;
          for (var i = 0; i < that.overtimes.length; i++) {
            that.overtimes[i].key = that.overtimes[i].id;
            that.overtimes[i].overtimetime =
              that.overtimes[i].year +
              "年" +
              that.overtimes[i].month +
              "月" +
              that.overtimes[i].day +
              "日";
          }
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    onSelectChange(selectedRowKeys) {
      console.log("selectedRowKeys changed: ", selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys;
    }
  },
  computed: {
    rowSelection() {
      const { selectedRowKeys } = this;
      return {
        selectedRowKeys,
        onChange: this.onSelectChange,
        hideDefaultSelections: true,
        selections: [
          {
            key: "all-data",
            text: "Select All Data",
            onSelect: () => {
              this.selectedRowKeys = [...Array(46).keys()]; // 0...45
            }
          },
          {
            key: "odd",
            text: "Select Odd Row",
            onSelect: changableRowKeys => {
              let newSelectedRowKeys = [];
              newSelectedRowKeys = changableRowKeys.filter((key, index) => {
                if (index % 2 !== 0) {
                  return false;
                }
                return true;
              });
              this.selectedRowKeys = newSelectedRowKeys;
            }
          },
          {
            key: "even",
            text: "Select Even Row",
            onSelect: changableRowKeys => {
              let newSelectedRowKeys = [];
              newSelectedRowKeys = changableRowKeys.filter((key, index) => {
                if (index % 2 !== 0) {
                  return true;
                }
                return false;
              });
              this.selectedRowKeys = newSelectedRowKeys;
            }
          }
        ],
        onSelection: this.onSelection
      };
    }
  },
  mounted: function() {
    this.getUnCheckOvertimes();
  }
};
</script>
<style scoped>
</style>
