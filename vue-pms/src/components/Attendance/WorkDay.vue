<template>
  <div>
     <div class="title">工作日导入</div>
      <hr>
      <a-tabs v-model="activeKey">
        <a-tab-pane tab="现有工作日表" key="1">
          <a-table :rowSelection="rowSelection" :columns="columns2" :dataSource="workdays" bordered></a-table>
        </a-tab-pane>
        <a-tab-pane tab="批量导入" key="2" forceRender>
          <a-alert message="请严格遵从EXCEL模版导入！" type="warning" showIcon/>
          <br>
          <a-button class="editable-add-btn" @click="handleClick(1)">新增</a-button>
          <a-button class="editable-add-btn" :disabled="!isEditable" @click="handleClick(2)">编辑</a-button>
          <a-button class="editable-add-btn" :disabled="!isDeletable" @click="handleClick(3)">删除</a-button>
          <a-button class="float-right" @click="download()">下载EXCEL模板</a-button>
          <a-upload
            :fileList="fileList"
            @remove="handleRemove"
            :beforeUpload="beforeUpload"
            :multiple="false"
            @change="filechange"
          >
            <a-button>
              <a-icon type="upload"/>导入工作日表
            </a-button>
          </a-upload>
          <br>
          <a-table :rowSelection="rowSelection" :columns="columns" :dataSource="tbdata" bordered></a-table>
          <p class="text-center">
              <a-button class="mt-4" type="primary" icon="cloud" size="large" @click="submit()">确认导入</a-button>
          </p>
        </a-tab-pane>
      </a-tabs>
      <a-drawer
        :title="drawerTitle"
        placement="left"
        :closable="false"
        :width="300"
        :visible="drawerVisible"
        :wrapStyle="{height: 'calc(100% - 108px)',overflow: 'auto',paddingBottom: '108px'}"
      >
        <a-form layout="vertical" hideRequiredMark>
          <a-form-item label="年份">
            <a-input v-model="newData.col1"></a-input>
          </a-form-item>
          <a-form-item label="月份">
            <a-input v-model="newData.col2"></a-input>
          </a-form-item>
          <a-form-item label="工作天数">
            <a-input v-model="newData.col3"></a-input>
          </a-form-item>
        </a-form>
      <div :style="{borderTop: '1px solid #e9e9e9',padding: '1rem',background: '#fff',textAlign: 'right',}">
        <a-button :style="{marginRight: '1rem'}" @click="handleOut">取消</a-button>
        <a-button @click="solveModal()" type="primary">提交</a-button>
      </div>
    </a-drawer>
  </div>
</template>
<script>
import resources from "@/resources";
import DataSol from "./DataSol.js";
const columns = [{
  title: '序号',
  dataIndex: 'col0',
  scopedSlots: { customRender: 'col0' },
},{
  title: '年份',
  dataIndex: 'col1',
  scopedSlots: { customRender: 'col1' },
},{
  title: '月份',
  dataIndex: 'col2',
  scopedSlots: { customRender: 'col2' },
},{
  title: '工作天数',
  dataIndex: 'col3',
  scopedSlots: { customRender: 'col3' },
}]
const columns2 = [{
  title: '序号',
  dataIndex: 'id',
  scopedSlots: { customRender: 'id' },
},{
  title: '年份',
  dataIndex: 'year',
  scopedSlots: { customRender: 'year' },
},{
  title: '月份',
  dataIndex: 'month',
  scopedSlots: { customRender: 'month' },
},{
  title: '工作天数',
  dataIndex: 'workdays',
  scopedSlots: { customRender: 'workdays' },
}]
export default {
  data () {
    return {
      activeKey:'1',
      fileList: [],
      tbdata: [],
      columns,
      columns2,
      workdays:[],
      selectedRowKeys: [], 
      isEditable: false,
      isDeletable: false,
      drawerVisible: false,
      drawerTitle: "",
      drawerType: 1,
      newData:{},
      submitData:[]
    }
  },
  methods:{
    getWorkdays(){
      let that = this;
      let url = resources.getWorkdays();
      this.$ajax
      .get(url)
      .then(res => {
          console.log(res.data.extend)
          that.workdays = res.data.extend.workdays
          for(var i=0;i<that.workdays.length;i++){
              that.workdays[i].key = that.workdays[i].id
          } 
      })
      .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息："+ error);
          console.log(error);
      });
    },
    addWorkdays(){
      let that = this;
      let url = resources.addWorkdays();
      this.$ajax
      .post(url,this.submitData)
      .then(res => {
        this.$message.success("提交成功！")
        this.getWorkdays()
        this.activeKey = "1"
      })
      .catch(function(error) {
          alert("网络请求出错啦，请联系管理员！异常信息："+ error);
          console.log(error);
      });
    },
    submit(){
      for(var i=0;i<this.tbdata.length;i++){
        this.submitData.push({id:this.tbdata[i].col0,year:this.tbdata[i].col1,
        month:this.tbdata[i].col2,workdays:this.tbdata[i].col3})
      }
      this.addWorkdays();
      console.log(this.submitData)
    },
    solveModal(){
      switch (this.drawerType) {
        case 1:
          this.addData();
          break;
        case 2:
          this.editData();
          break;
        case 3:
          this.deleteData();
          break;
        default:
          break;
      }
      console.log(this.newData)
    },
    addData(){
      this.newData.col0 = this.tbdata.length +1 
      this.newData.key = this.newData.col0
      this.tbdata.push(this.newData)
      this.drawerVisible = false
      this.addData = {}
    },
    editData(){
      if (this.selectedRowKeys.length > 0) {
        for(let i = 0;i <= 4;i++){
          this.tbdata[this.selectedRowKeys[0]-1]["col"+i] = this.newData["col"+i];
        }
      }
      this.drawerVisible = false
      this.newData = {}
    },
    resetKey() {
      for (var i = 0; i < this.tbdata.length; i++) {
        this.tbdata[i].key = i+1;
        this.tbdata[i].col0 = i+1;
      }
    },
    deleteData(){
      var that = this;
      this.$confirm({
        title: that.drawerTitle,
        okText: "确定",
        okType: "danger",
        cancelText: "取消",
        onOk() {
          for (var i = 0; i < that.selectedRowKeys.length; i++) {
            for (var j = 0; j < that.tbdata.length; j++) {
              if (that.tbdata[j].key == that.selectedRowKeys[i]) {
                if (j == 0) {
                  that.tbdata.shift();
                } else {
                  that.tbdata.splice(j, 1);
                }
                console.log("deleted", " " + j);
              }
            }
          }
          that.resetKey();
          that.selectedRowKeys = [];
        }
      });
    },
    handleOut(){
      this.newData = {}
      this.drawerVisible = false
    },
    deepCopy(obj) {
      var _obj = JSON.stringify(obj);
      return JSON.parse(_obj);
    },
    handleClick(type) {
      switch (type) {
        case 1:
          this.drawerTitle = "新建条目";
          this.drawerType = type;
          this.drawerVisible = true;
          break;
        case 2:
          this.drawerTitle = "编辑";
          if (this.selectedRowKeys.length > 0) {
            var dataCloned;
            dataCloned = this.deepCopy(this.tbdata[this.selectedRowKeys[0]-1]);
            console.log(dataCloned)
            this.newData = dataCloned
            this.drawerType = type;
            this.drawerVisible = true;
          }
          break;
        case 3:
          this.drawerTitle =
            "你确定删除选中的" + this.selectedRowKeys.length + "条吗？";
          this.drawerType = type;
          this.deleteData();
          break;
          default:
          break;
      }
    },
    download(){

    },
    beforeUpload(file) {
      if (this.fileList.length == 0) {
        this.fileList.pop();
      }
      this.fileList.push(file);
      return false;
    },
    handleRemove(file) {
      const index = this.fileList.indexOf(file);
      const newFileList = this.fileList.slice();
      newFileList.splice(index, 1);
      this.fileList = newFileList;
    },
    filechange() {
      if (this.fileList.length != 1) {
        if (this.fileList.length == 0) {
          return;
        }
        this.fileList.shift();
      }
      this.tableLoading = true;
      this.tbdata = []
      var f = this.fileList[0]; /*获得文件 */
      var _this = this;
      var wb, sheetArr, tbdata, columns, col_length;
      var reader = new FileReader();
      reader.onload = function(e) {
        var data = e.target.result;
        wb = XLSX.read(btoa(DataSol.fixdata(data)), {
          //手动转化
          type: "base64"
        });
        //wb.SheetNames[0]是获取Sheets中第一个Sheet的名字
        sheetArr = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]], {
          header: 1
        });
        //转化表格数据为datatable所需的json格式
        DataSol.solveSheet(_this.tbdata, sheetArr);
        //添加导入的数据
      };
      reader.readAsArrayBuffer(f);
      this.tableLoading = false;
      //文件流转BinaryString
    },
    onSelectChange (selectedRowKeys) {
      if (selectedRowKeys.length > 0) {
        if (selectedRowKeys.length == 1) {
          this.isEditable = true;
        } else {
          this.isEditable = false;
        }
        this.isDeletable = true;
      } else {
        this.isEditable = false;
        this.isDeletable = false;
      }
      console.log('selectedRowKeys changed: ', selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys
    }, 
  },
  computed: {
    rowSelection() {
      const { selectedRowKeys } = this;
      return {
        selectedRowKeys,
        onChange: this.onSelectChange,
        hideDefaultSelections: true,
        selections: [{
        key: 'all-data',
        text: 'Select All Data',
        onSelect: () => {
            this.selectedRowKeys = [...Array(46).keys()]; // 0...45
        },
        }, {
        key: 'odd',
        text: 'Select Odd Row',
        onSelect: (changableRowKeys) => {
            let newSelectedRowKeys = [];
            newSelectedRowKeys = changableRowKeys.filter((key, index) => {
            if (index % 2 !== 0) {
                return false;
            }
            return true;
            });
            this.selectedRowKeys = newSelectedRowKeys;
        },
        }, {
        key: 'even',
        text: 'Select Even Row',
        onSelect: (changableRowKeys) => {
            let newSelectedRowKeys = [];
            newSelectedRowKeys = changableRowKeys.filter((key, index) => {
            if (index % 2 !== 0) {
                return true;
            }
            return false;
            });
            this.selectedRowKeys = newSelectedRowKeys;
        },
        }],
        onSelection: this.onSelection,
      }
    }
  },
  mounted:function() {
    this.getWorkdays()
  }
}
</script>

