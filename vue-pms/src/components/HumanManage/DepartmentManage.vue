<template>
    <div>
		<div class="title">部门管理</div>
        <hr>
        <div style="margin-bottom:10px">
        <a-button @click="handleAddClicked">添加</a-button>
        <a-button @click="handleChoseDeleted">批量删除</a-button>
        </div>
        <a-table :rowSelection="rowSelection" :columns="columns" :dataSource="depts" bordered>
            <template slot="operation" slot-scope="text, record" width="100px">
                <div class='editable-row-operations'>
                <span>
                    <a @click="() => edit(record.id)" href="javascript:;">编辑</a>
                    <a-popconfirm
                    title="Sure to delete?"
                    @confirm="() => onDelete(record.id)">
                    <a href="javascript:;">删除</a>
                    </a-popconfirm>
                </span>
                </div>
            </template>
        </a-table>
        <a-modal 
            title="添加"
            v-model="visibleAdd"
            @ok="handleAddOk"
        >
            <a-form style="margin-right:15%" >
                <a-form-item
                    v-bind="formItemLayout"
                    label="部门名"
                    :validate-status="checkDeptName.status"
                    :has-feedback="checkDeptName.feedback"
                    :help="checkDeptName.help"
                >
                    <a-input @blur="checkNameRepeat" placeholder="deptName" v-model="deptAdd.name"/>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="部门信息"
                >
                    <a-input placeholder="deptInformation" v-model="deptAdd.remark"/>
                </a-form-item>
            </a-form>
            <template slot="footer">
                <a-button key="submit" type="primary" @click="handleAddOk">提交</a-button>
                <a-button key="back" @click="handleAddCancel">关闭</a-button>
            </template>
        </a-modal> 
        <a-modal 
            title="编辑"
            v-model="visibleUpdate"
            @ok="handleUpdateOk"
        >
            <a-form style="margin-right:15%" >
                <a-form-item
                    v-bind="formItemLayout"
                    label="部门名"
                >
                    <span class="ant-form-text" >
                        {{dept.name}}
                    </span>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="部门信息"
                >
                    <a-input placeholder="deptInformation" v-model="dept.remark"/>
                </a-form-item>
            </a-form>
            <template slot="footer">
                <a-button key="submit" type="primary" @click="handleUpdateOk">提交</a-button>
                <a-button key="back" @click="handleUpdateCancel">关闭</a-button>
            </template>
        </a-modal> 
    </div>
</template>
<script >
import resources from '@/resources'
import {formatDate} from '@/date'
const columns = [{
  title: '序号',
  dataIndex: 'id',
  scopedSlots: { customRender: 'id' },
},{
  title: '部门名',
  dataIndex: 'name',
  scopedSlots: { customRender: 'name' },
},{
  title: '部门信息',
  dataIndex: 'remark',
  scopedSlots: { customRender: 'remark' },
},{
  title: '操作',
  dataIndex: 'operation',
  width: '10%',
  scopedSlots: { customRender: 'operation' },
}]
export default{
    data (){
        return { 
            visibleAdd:false,
            visibleUpdate:false,
            code:'',
            columns,
            depts:[],
            dept:{},
            deptAdd:{},
            selectedRowKeys: [], 
            formItemLayout: {
                labelCol: {
                xs: { span: 24 },
                sm: { span: 8 },
                },
                wrapperCol: {
                xs: { span: 24 },
                sm: { span: 16 },
                },
            },
            checkDeptName:{
                status:"",
                feedback:false,
                help:"",
                AddNameFlag:false
            },
        }
    },
    methods:{
        handleAddClicked(){
            this.checkDeptName.status = ""
            this.checkDeptName.feedback = false
            this.checkDeptName.help = ""
            this.visibleAdd = true
            this.deptAdd = {}
        },
        handleAddOk(){
            if(this.checkDeptName.AddNameFlag == true){
                this.addDept(this.deptAdd)
                this.deptAdd= {}
            }else{
                this.$message.error("验证未通过！")
            }
        },
        handleAddCancel(){
            this.visibleAdd = false
        },
        handleChoseDeleted(){
            this.deleteDepts(this.selectedRowKeys)
        },
        edit(id){
            this.visibleUpdate = true
            this.getDept(id)
        },
        handleUpdateOk(){
            this.updateDept(this.dept);
        },
        handleUpdateCancel(){
            this.visibleUpdate = false
        },
        onDelete(id){
            this.deletDept(id)
        },
        addDept(deptAdd){
            let that = this;
            let url = resources.addDept();
            this.$ajax
            .post(url,deptAdd)
            .then(res => {
                console.log(res)
                this.visibleAdd = false
                this.getDepts()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        deletDept(id){
            let that = this;
            let url = resources.deletDept();
            this.$ajax
            .get(url,{
                    params:{
                    id:id
                }
            })
            .then(res => {
                this.getDepts()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        deleteDepts(selectedRowKeys){
            let that = this;
            let url = resources.deleteDepts();
            this.$ajax
            .get(url,{
                params:{
                    idList:selectedRowKeys
                }
            })
            .then(res => {
                console.log("delete success")
                this.getDepts()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getDept(id){
            let that = this;
            let url = resources.getDept();
            this.$ajax
            .get(url,{
                params:{
                    id:id
                }
            })
            .then(res => {
                console.log(res.data.extend.dept)
                that.dept = res.data.extend.dept
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getDepts(){
            let that = this;
            let url = resources.getDepts();
            this.$ajax
            .get(url)
            .then(res => {
                console.log(res.data.extend)
                that.depts = res.data.extend.depts
                for(var i=0;i<that.depts.length;i++){
                    that.depts[i].key = that.depts[i].id
                } 
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        updateDept(dept){
            let that = this;
            let url = resources.updateDept();
            this.$ajax
            .post(url,dept)
            .then(res => {
                this.visibleUpdate = false
                this.getDepts()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        checkNameRepeat(){
            let that = this;
            let url = resources.checkDeptNameRepeat();
            this.$ajax
            .get(url,{
                params:{
                name:this.deptAdd.name
                }
            })
            .then(res => {
                console.log(res.data)
                this.code = res.data.code
                if(this.code == 200){
                    this.checkDeptName.status = "error"
                    this.checkDeptName.feedback = true
                    this.checkDeptName.help = "部门名重复"
                    this.checkDeptName.AddNameFlag = false
                }
                else{
                    this.checkDeptName.status = "success"
                    this.checkDeptName.feedback = true
                    this.checkDeptName.help = ""
                    this.checkDeptName.AddNameFlag = true
                }
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        onSelectChange (selectedRowKeys) {
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
      this.getDepts();
    }
}
</script>
<style scoped>

</style>
