<template>
    <div>
		<div class="title">员工管理</div>
        <hr>
        <div style="margin-bottom:10px">
        <a-button @click="handleChoseDeleted">批量删除</a-button>
        <a-input-search
            placeholder="输入要查询的内容"
            v-model = "searchText"
            @search="onSearch"
            style="width: 200px;float:right"
            enterButton
        />
        </div>
        <a-table :rowSelection="rowSelection" :columns="columns" :dataSource="emps" bordered :pagination="false">
            <template  slot="name" slot-scope="text, record">
                <a href="javascript:;" @click="e=>showMessage(record.id)">{{text}}</a>
            </template>
            <template slot="createDate" slot-scope="text">
                {{ text | formatDate}}
            </template>
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
        <div style="margin-top:20px" align="right" v-if="!searchStatus">
            <a-pagination v-model="current" :total="page.total" :pageSize="page.pageSize"
            @change="pageChange"/>
        </div>
        <a-modal 
            title="编辑"
            v-model="visibleUpdate"
            @ok="handleUpdateOk"
        >
            <a-form style="margin-right:15%" >
                <a-form-item
                    v-bind="formItemLayout"
                    label="员工名"
                >
                    <span class="ant-form-text" >
                        {{emp.name}}
                    </span>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="部门名"
                >
                    <a-select v-model="emp.deptId"> 
                        <a-select-option :key="item.id" v-for="item in depts">
                            {{item.name}}
                        </a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="职位名"
                >
                    <a-select v-model="emp.jobId"> 
                        <a-select-option :key="item.id" v-for="item in jobs">
                            {{item.name}}
                        </a-select-option>
                    </a-select>
                </a-form-item>
            </a-form>
            <template slot="footer">
                <a-button key="submit" type="primary" @click="handleUpdateOk">提交</a-button>
                <a-button key="back" @click="handleUpdateCancel">关闭</a-button>
            </template>
        </a-modal> 
        <a-modal 
            title="个人信息"
            v-model="visibleShow"
        >
            <div margin-bottom="100px">
                <div style="float:left;align:left">
                    姓名：{{message.name}}<br><br>
                    身份证号：{{message.cardId}}<br><br>
                    学历：{{message.education}}<br><br>
                    手机号：{{message.phone}}<br><br>
                    qq号：{{message.qqNum}}<br><br>
                    邮政编码：{{message.postCode}}<br><br>
                    兴趣：{{message.hobby}}<br><br>
                    政治面貌：{{message.party}}<br><br>
                </div>
                <div style="align:left;margin-left:15rem">
                    性别：{{message.sex | sexJudge}}<br><br>
                    民族：{{message.race}}<br><br>
                    出生年月：{{message.birthday | formatOnlyDate}}<br><br>
                    电话号：{{message.tel}}<br><br>
                    邮箱：{{message.email}}<br><br>
                    家庭地址：{{message.address}}<br><br>
                    兴趣详情：{{message.remark}}<br><br>
                    特长：{{message.speciality}}<br><br>
                </div>
            </div>
            <template slot="footer">
                <a-button key="back" @click="handleShowCancel">关闭</a-button>
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
  title: '姓名',
  dataIndex: 'name',
  scopedSlots: { customRender: 'name' },
},{
  title: '部门名',
  dataIndex: 'department.name',
  scopedSlots: { customRender: 'department.name' },
},{
  title: '职位名',
  dataIndex: 'job.name',
  scopedSlots: { customRender: 'job.name' },
},{
  title: '创建时间',
  dataIndex: 'createDate',
  scopedSlots: { customRender: 'createDate' },
},{
  title: '操作',
  dataIndex: 'operation',
  width: '10%',
  scopedSlots: { customRender: 'operation' },
}]
export default{
    filters:{
        formatDate(time){
            let date = new Date(time);
            return formatDate(date,'yyyy-MM-dd hh:mm');
        },
        formatOnlyDate(time){
            let date = new Date(time);
            return formatDate(date,'yyyy-MM-dd');
        },
        sexJudge(sex){
            if(sex==0){
                return '女'
            }else{
                return '男'
            }
        }
    },
    data (){
        return { 
            visibleUpdate:false,
            visibleShow:false,
            code:'',
            columns,
            emps:[],
            emp:{},
            depts:[],
            jobs:[],
            message:[],
            current:1,
            page:{},
            searchStatus:false,
            searchText:'',
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
            checkEmpName:{
                status:"",
                feedback:false,
                help:"",
                AddNameFlag:false
            },
        }
    },
    methods:{
        onSearch(e){
            if(e == '' || e == undefined || e == null){
                this.getEmps(1)
                this.searchStatus = false
            }else{
                this.searchEmps(e)
            }
        },      
        showMessage(id){
            this.visibleShow = true 
            this.getMessage(id)
        },
        handleShowCancel(){
            this.visibleShow = false 
        },
        handleChoseDeleted(){
            this.deleteEmps(this.selectedRowKeys)
        },
        edit(id){
            this.visibleUpdate = true
            this.getEmp(id)
        },
        handleUpdateOk(){
            this.updateEmp(this.emp);
        },
        handleUpdateCancel(){
            this.visibleUpdate = false
            console.log(this.emp)
        },
        onDelete(id){
            this.deletEmp(id)
        },
        getMessage(id){
            let that = this;
            let url = resources.getMessage();
            this.$ajax
            .get(url,{
                    params:{
                        id:id
                }
            })
            .then(res => {
                that.message = res.data.extend.message
                console.log(that.message)
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        deletEmp(id){
            let that = this;
            let url = resources.deletEmp();
            this.$ajax
            .get(url,{
                    params:{
                    id:id
                }
            })
            .then(res => {
                this.getEmps()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        deleteEmps(selectedRowKeys){
            let that = this;
            let url = resources.deleteEmps();
            this.$ajax
            .get(url,{
                params:{
                    idList:selectedRowKeys
                }
            })
            .then(res => {
                console.log("delete success")
                this.getEmps()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getEmp(id){
            let that = this;
            let url = resources.getEmp();
            this.$ajax
            .get(url,{
                params:{
                    id:id
                }
            })
            .then(res => {
                console.log(res.data.extend.emp)
                that.emp = res.data.extend.emp
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getEmps(pn){
            let that = this;
            let url = resources.getEmps();
            this.$ajax
            .get(url,{
                params:{
                    pn:pn
                }
            })
            .then(res => {
                console.log(res.data.extend)
                that.page = res.data.extend.pageInfo
                that.emps = res.data.extend.pageInfo.list
                var size = res.data.extend.pageInfo.size
                for(var i=0;i<size;i++){
                    that.emps[i].key = res.data.extend.pageInfo.list[i].id
                }  
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        updateEmp(emp){
            let that = this;
            let url = resources.updateEmp();
            this.$ajax
            .post(url,emp)
            .then(res => {
                this.visibleUpdate = false
                this.getEmps()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        pageChange(e){
            console.log(e)
            this.getEmps(e)
        },
        onSelectChange (selectedRowKeys) {
            console.log('selectedRowKeys changed: ', selectedRowKeys);
            this.selectedRowKeys = selectedRowKeys
        }, 
        getDepts(){
            let that = this;
            let url = resources.getDepts();
            this.$ajax
            .get(url)
            .then(res => {
                console.log(res.data.extend)
                that.depts = res.data.extend.depts
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
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        searchEmps(e){
            let that = this;
            let url = resources.searchEmps();
            this.$ajax
            .get(url,{
                params:{
                    e:e
                }
            })
            .then(res => {
                console.log(res)
                if(res.data.extend.emps.length != 0){
                    that.emps = res.data.extend.emps
                    var size = res.data.extend.emps.length
                    for(var i=0;i<size;i++){
                        that.emps[i].key = res.data.extend.emps[i].id
                        this.searchText = ''
                        this.searchStatus = true
                    }  
                }else{
                    this.$message.info("未找到匹配项")
                    this.searchText = ''
                }
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
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
      this.getEmps(1)
      this.getDepts()
      this.getJobs()
    }
}
</script>
<style scoped>

</style>
