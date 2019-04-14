<template>
    <div>
		<div class="title">公告管理</div>
        <hr>
        <div style="margin-bottom:10px">
        <a-button @click="handleAddClicked">添加</a-button>
        <a-button @click="handleChoseDeleted">批量删除</a-button>
        </div>
        <a-table :rowSelection="rowSelection" :columns="columns" :dataSource="notices" bordered>
            <template slot="createDate" slot-scope="text, record">
                {{text | formatDate}}
            </template>
            <template slot="operation" slot-scope="text, record">
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
            <template slot="show" slot-scope="text, record"> 
               <a-icon @click="showContent(record)" type="eye" style="fontSize:20px;cursor:pointer"/>
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
                    label="公告标题"
                >
                    <a-input placeholder="noticeTitle" v-model="noticeAdd.title"/>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="公告内容"
                >
                    <a-textarea placeholder="noticeContent" :rows="10" v-model="noticeAdd.content"/>
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
                    label="公告标题"
                >
                    <span class="ant-form-text" >
                        {{notice.title}}
                    </span>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="公告内容"
                >
                    <a-textarea :rows="10" v-model="notice.content"/>
                </a-form-item>
            </a-form>
            <template slot="footer">
                <a-button key="submit" type="primary" @click="handleUpdateOk">提交</a-button>
                <a-button key="back" @click="handleUpdateCancel">关闭</a-button>
            </template>
        </a-modal> 
        <a-modal 
            title="公告预览"
            v-model="visibleShow"
            width="800px"
        >
            <h1 align="center">{{noticeShow.title}}</h1>
            <p>{{noticeShow.content}}</p>
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
  title: '公告标题',
  dataIndex: 'title',
  scopedSlots: { customRender: 'title' },
},{
  title: '公告内容',
  dataIndex: 'content',
  scopedSlots: { customRender: 'content' },
},{
  title: '发布时间',
  dataIndex: 'createDate',
  scopedSlots: { customRender: 'createDate' },
},{
  title: '用户名',
  dataIndex: 'user.username',
  scopedSlots: { customRender: 'user.username' },
},{
  title: '发布人',
  dataIndex: 'employee.name',
  scopedSlots: { customRender: 'employee.name' },
},{
  title: '操作',
  dataIndex: 'operation',
  width: '10%',
  scopedSlots: { customRender: 'operation' },
},{
  title: '预览',
  dataIndex: 'show',
  width: '4%',
  scopedSlots: { customRender: 'show' },
}]
export default{
    filters:{
        formatDate(time){
            let date = new Date(time);
            return formatDate(date,'yyyy-MM-dd hh:mm');
        }
    },
    data (){
        return { 
            visibleAdd:false,
            visibleUpdate:false,
            visibleShow:false,
            code:'',
            columns,
            notices:[],
            notice:{},
            noticeAdd:{},
            noticeShow:{},
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
        }
    },
    methods:{
        showContent(record){
            this.visibleShow = true
            this.noticeShow = record
            console.log(record)
        },
        handleShowCancel(){
            this.visibleShow = false
            this.noticeShow = {}
        },
        handleAddClicked(){
            this.visibleAdd = true
            this.noticeAdd = {}
            console.log(sessionStorage.userId,sessionStorage.empId)
        },
        handleAddOk(){
            this.noticeAdd.userId = sessionStorage.userId
            this.noticeAdd.empId = sessionStorage.empId
            this.addNotice(this.noticeAdd)
            this.noticeAdd= {}
        },
        handleAddCancel(){
            this.visibleAdd = false
        },
        handleChoseDeleted(){
            this.deleteNotices(this.selectedRowKeys)
        },
        edit(id){
            this.visibleUpdate = true
            this.getNotice(id)
        },
        handleUpdateOk(){
            this.updateNotice(this.notice);
        },
        handleUpdateCancel(){
            this.visibleUpdate = false
        },
        onDelete(id){
            this.deletNotice(id)
        },
        addNotice(noticeAdd){
            let that = this;
            let url = resources.addNotice();
            this.$ajax
            .post(url,noticeAdd)
            .then(res => {
                console.log(res)
                this.visibleAdd = false
                this.getNotices()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        deletNotice(id){
            let that = this;
            let url = resources.deletNotice();
            this.$ajax
            .get(url,{
                    params:{
                    id:id
                }
            })
            .then(res => {
                this.getNotices()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        deleteNotices(selectedRowKeys){
            let that = this;
            let url = resources.deleteNotices();
            this.$ajax
            .get(url,{
                params:{
                    idList:selectedRowKeys
                }
            })
            .then(res => {
                console.log("delete success")
                this.getNotices()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getNotice(id){
            let that = this;
            let url = resources.getNotice();
            this.$ajax
            .get(url,{
                params:{
                    id:id
                }
            })
            .then(res => {
                console.log(res.data.extend.notice)
                that.notice = res.data.extend.notice
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getNotices(){
            let that = this;
            let url = resources.getNotices();
            this.$ajax
            .get(url)
            .then(res => {
                console.log(res.data.extend)
                that.notices = res.data.extend.notices
                for(var i=0;i<that.notices.length;i++){
                    that.notices[i].key = that.notices[i].id
                } 
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        updateNotice(notice){
            let that = this;
            let url = resources.updateNotice();
            this.$ajax
            .post(url,notice)
            .then(res => {
                this.visibleUpdate = false
                this.getNotices()
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
      this.getNotices();
    }
}
</script>
<style scoped>

</style>
