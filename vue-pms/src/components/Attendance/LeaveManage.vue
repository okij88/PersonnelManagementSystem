<template>
  <div>
    <div class="title">请假管理</div>
    <hr>
    <a-tabs v-model="activeKey" @change="showAllLeave">
      <a-tab-pane tab="待处理请假事物" key="1">
        <a-table :columns="columns" :dataSource="uncheckleaves" bordered>
          <template slot="subtime" slot-scope="text">{{text | formatDate}}</template>
          <template slot="status" slot-scope="text,record">
            <a-select :defaultValue="text" style="width:60%" @change="e=>solveLeave(record,e)">
              <a-select-option :key="item.key" v-for="item in allstatus">{{item.status}}</a-select-option>
            </a-select>
          </template>
        </a-table>
      </a-tab-pane>
      <a-tab-pane tab="全部请假记录" key="2">
        <div style="margin-bottom:10px">
          <a-month-picker placeholder="选择月份" @change="getLeavesByMonth"/>
          <a-button @click="handleChoseDeleted">批量删除</a-button>
          <a-button @click="handleShowAll">查看全部记录</a-button>
        </div>
        <a-table :rowSelection="rowSelection" :columns="columns" :dataSource="leaves" bordered>
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
    title: "请假日期",
    dataIndex: "leavetime",
    scopedSlots: { customRender: "leavetime" }
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
      leaves: [],
      uncheckleaves: [],
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
    solveLeave(record, status) {
      var leave = { id: record.id, status: status, subtime: record.subtime };
      console.log(leave);
      let that = this;
      let url = resources.updateLeave();
      this.$ajax
        .post(url, leave)
        .then(res => {
          this.getUnCheckLeaves();
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    showAllLeave() {
      if (this.activeKey == "2") {
        this.getLeaves();
      }
    },
    handleChoseDeleted() {
      this.deleteLeaves(this.selectedRowKeys);
    },
    handleShowAll(){
      this.getLeaves()
    },
    deleteLeaves(selectedRowKeys) {
      let that = this;
      let url = resources.deleteLeaves();
      this.$ajax
        .get(url, {
          params: {
            idList: selectedRowKeys
          }
        })
        .then(res => {
          console.log("delete success");
          this.getLeaves();
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    getUnCheckLeaves() {
      let that = this;
      let url = resources.getUnCheckLeaves();
      this.$ajax
        .get(url)
        .then(res => {
          console.log(res.data.extend);
          that.uncheckleaves = res.data.extend.leaves;
          for (var i = 0; i < that.uncheckleaves.length; i++) {
            that.uncheckleaves[i].key = that.uncheckleaves[i].id;
            that.uncheckleaves[i].leavetime =
              that.uncheckleaves[i].year +
              "年" +
              that.uncheckleaves[i].month +
              "月" +
              that.uncheckleaves[i].day +
              "日";
          }
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    getLeavesByMonth(e) {
      var year = formatDate(new Date(e), "yyyy");
      var month = formatDate(new Date(e), "M");
      let that = this;
      let url = resources.getLeavesByMonth();
      this.$ajax
        .get(url, {
          params: {
            year:year,
            month:month
          }
        })
        .then(res => {
          console.log(res.data.extend);
          that.leaves = res.data.extend.leaves;
          for (var i = 0; i < that.leaves.length; i++) {
            that.leaves[i].key = that.leaves[i].id;
            that.leaves[i].leavetime =
              that.leaves[i].year +
              "年" +
              that.leaves[i].month +
              "月" +
              that.leaves[i].day +
              "日";
          }
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    getLeaves() {
      let that = this;
      let url = resources.getLeaves();
      this.$ajax
        .get(url)
        .then(res => {
          console.log(res.data.extend);
          that.leaves = res.data.extend.leaves;
          for (var i = 0; i < that.leaves.length; i++) {
            that.leaves[i].key = that.leaves[i].id;
            that.leaves[i].leavetime =
              that.leaves[i].year +
              "年" +
              that.leaves[i].month +
              "月" +
              that.leaves[i].day +
              "日";
          }
        })
        .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息：" + error);
          console.log(error);
        });
    },
    getUncheckLeaves() {
      let that = this;
      let url = resources.getLeaves();
      this.$ajax
        .get(url)
        .then(res => {
          console.log(res.data.extend);
          that.leaves = res.data.extend.leaves;
          for (var i = 0; i < that.leaves.length; i++) {
            that.leaves[i].key = that.leaves[i].id;
            that.leaves[i].leavetime =
              that.leaves[i].year +
              "年" +
              that.leaves[i].month +
              "月" +
              that.leaves[i].day +
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
    this.getUnCheckLeaves();
  }
};
</script>
<style scoped>
</style>
