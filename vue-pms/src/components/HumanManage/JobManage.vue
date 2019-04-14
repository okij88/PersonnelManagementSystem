<template>
    <div>
		<div class="title">职位管理</div>
        <hr>
        <div style="margin-bottom:10px">
        <a-button @click="handleAddClicked">添加</a-button>
        <a-button @click="handleChoseDeleted">批量删除</a-button>
        </div>
        <a-table :rowSelection="rowSelection" :columns="columns" :dataSource="jobs" bordered>
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
                    label="职位名"
                    :validate-status="checkJobName.status"
                    :has-feedback="checkJobName.feedback"
                    :help="checkJobName.help"
                >
                    <a-input @blur="checkNameRepeat" placeholder="jobName" v-model="jobAdd.name"/>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="职位信息"
                >
                    <a-input placeholder="jobInformation" v-model="jobAdd.remark"/>
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
                    label="职位名"
                >
                    <span class="ant-form-text" >
                        {{job.name}}
                    </span>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="职位信息"
                >
                    <a-input placeholder="jobInformation" v-model="job.remark"/>
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
  title: '职位名',
  dataIndex: 'name',
  scopedSlots: { customRender: 'name' },
},{
  title: '职位信息',
  dataIndex: 'remark',
  scopedSlots: { customRender: 'remark' },
},{
  title: '班次',
  dataIndex: 'adId',
  scopedSlots: { customRender: 'adId' },
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
            jobs:[],
            job:{},
            jobAdd:{},
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
            checkJobName:{
                status:"",
                feedback:false,
                help:"",
                AddNameFlag:false
            },
        }
    },
    methods:{
        handleAddClicked(){
            this.checkJobName.status = ""
            this.checkJobName.feedback = false
            this.checkJobName.help = ""
            this.visibleAdd = true
            this.jobAdd = {}
        },
        handleAddOk(){
            if(this.checkJobName.AddNameFlag == true){
                this.addJob(this.jobAdd)
                this.jobAdd= {}
            }else{
                this.$message.error("验证未通过！")
            }
        },
        handleAddCancel(){
            this.visibleAdd = false
        },
        handleChoseDeleted(){
            this.deleteJobs(this.selectedRowKeys)
        },
        edit(id){
            this.visibleUpdate = true
            this.getJob(id)
        },
        handleUpdateOk(){
            this.updateJob(this.job);
        },
        handleUpdateCancel(){
            this.visibleUpdate = false
        },
        onDelete(id){
            this.deletJob(id)
        },
        addJob(jobAdd){
            let that = this;
            let url = resources.addJob();
            this.$ajax
            .post(url,jobAdd)
            .then(res => {
                console.log(res)
                this.visibleAdd = false
                this.getJobs()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        deletJob(id){
            let that = this;
            let url = resources.deletJob();
            this.$ajax
            .get(url,{
                    params:{
                    id:id
                }
            })
            .then(res => {
                this.getJobs()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        deleteJobs(selectedRowKeys){
            let that = this;
            let url = resources.deleteJobs();
            this.$ajax
            .get(url,{
                params:{
                    idList:selectedRowKeys
                }
            })
            .then(res => {
                console.log("delete success")
                this.getJobs()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getJob(id){
            let that = this;
            let url = resources.getJob();
            this.$ajax
            .get(url,{
                params:{
                    id:id
                }
            })
            .then(res => {
                console.log(res.data.extend.job)
                that.job = res.data.extend.job
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getJobs(){
            let that = this;
            let url = resources.getJobs();
            this.$ajax
            .get(url)
            .then(res => {
                console.log(res.data.extend)
                that.jobs = res.data.extend.jobs
                for(var i=0;i<that.jobs.length;i++){
                    that.jobs[i].key = that.jobs[i].id
                } 
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        updateJob(job){
            let that = this;
            let url = resources.updateJob();
            this.$ajax
            .post(url,job)
            .then(res => {
                this.visibleUpdate = false
                this.getJobs()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        checkNameRepeat(){
            let that = this;
            let url = resources.checkJobNameRepeat();
            this.$ajax
            .get(url,{
                params:{
                name:this.jobAdd.name
                }
            })
            .then(res => {
                console.log(res.data)
                this.code = res.data.code
                if(this.code == 200){
                    this.checkJobName.status = "error"
                    this.checkJobName.feedback = true
                    this.checkJobName.help = "职位名重复"
                    this.checkJobName.AddNameFlag = false
                }
                else{
                    this.checkJobName.status = "success"
                    this.checkJobName.feedback = true
                    this.checkJobName.help = ""
                    this.checkJobName.AddNameFlag = true
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
      this.getJobs();
    }
}
</script>
<style scoped>

</style>
