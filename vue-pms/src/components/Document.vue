<template>
    <div>
		<div class="title">下载中心</div>
        <hr>
        <div style="margin-bottom:10px">
        <a-button @click="handleAddClicked">添加</a-button>
        <a-button @click="handleChoseDeleted">批量删除</a-button>
        </div>
        <a-table :rowSelection="rowSelection" :columns="columns" :dataSource="documents" bordered>
            <template slot="createDate" slot-scope="text">
                {{text | formatDate}}
            </template>
            <template slot="operation" slot-scope="text, record">
                <div class='editable-row-operations'>
                <span>
                    <a @click="() => edit(record.id)" href="javascript:;">编辑</a>
                    <a-popconfirm
                    title="Sure to delete?"
                    @confirm="() => onDelete(record.id,record.realfilename)">
                    <a href="javascript:;">删除</a>
                    </a-popconfirm>
                </span>
                </div>
            </template>
            <template slot="show" slot-scope="text, record"> 
               <a-icon @click="downloadFile(record)" type="download" style="fontSize:20px;cursor:pointer"/>
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
                    label="标题"
                >
                    <a-input placeholder="documentTitle" v-model="documentAdd.title"/>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="描述"
                >
                    <a-textarea placeholder="documentContent" :rows="10" v-model="documentAdd.remark"/>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="上传"
                > 
                    <a-upload
                    :fileList="fileList"
                    :remove="handleRemove"
                    :beforeUpload="beforeUpload"
                    >
                        <a-button>
                            <a-icon type="upload" />选择文件
                        </a-button>
                    </a-upload>
                </a-form-item>      
            </a-form>
            <template slot="footer">
                <a-button
                    type="primary"
                    @click="handleAddOk"
                    :disabled="fileList.length === 0"
                    :loading="uploading"
                    style="margin-top: 16px"
                    >
                        {{uploading ? '正在上传' : '开始上传' }}
                    </a-button>
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
                    label="标题"
                >
                    <span class="ant-form-text" >
                        {{document.title}}
                    </span>
                </a-form-item>
                <a-form-item
                    v-bind="formItemLayout"
                    label="描述"
                >
                    <a-textarea :rows="5" v-model="document.remark"/>
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
import reqwest from 'reqwest'
const columns = [{
  title: '序号',
  dataIndex: 'id',
  scopedSlots: { customRender: 'id' },
},{
  title: '标题',
  dataIndex: 'title',
  scopedSlots: { customRender: 'title' },
},{
  title: '文件名',
  dataIndex: 'filename',
  scopedSlots: { customRender: 'filename' },
},{
  title: '描述',
  dataIndex: 'remark',
  scopedSlots: { customRender: 'remark' },
},{
  title: '发布时间',
  dataIndex: 'createDate',
  scopedSlots: { customRender: 'createDate' },
},{
  title: '用户名',
  dataIndex: 'user.username',
  scopedSlots: { customRender: 'user.username' },
},{
  title: '上传人',
  dataIndex: 'employee.name',
  scopedSlots: { customRender: 'employee.name' },
},{
  title: '操作',
  dataIndex: 'operation',
  width: '10%',
  scopedSlots: { customRender: 'operation' },
},{
  title: '下载',
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
            fileList: [],
            uploading: false,
            code:'',
            columns,
            documents:[],
            document:{},
            documentAdd:{},
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
        handleRemove(file) {
            const index = this.fileList.indexOf(file);
            const newFileList = this.fileList.slice();
            newFileList.splice(index, 1);
            this.fileList = newFileList
        },
        beforeUpload(file) {
            this.fileList = [...this.fileList, file]
            return false;
        },
        downloadFile(record){
            console.log(record)
            this.getFile(record.id,record.filename)
        },
        handleShowCancel(){
            this.visibleShow = false
            this.documentShow = {}
        },
        handleAddClicked(){
            this.visibleAdd = true
            this.documentAdd = {}
        },
        handleAddOk(){
            this.documentAdd.userId = sessionStorage.userId
            this.documentAdd.empId = sessionStorage.empId
            this.addDocument(this.documentAdd)
            console.log(this.documentAdd)
        },
        handleAddCancel(){
            this.visibleAdd = false
        },
        handleChoseDeleted(){
            this.deleteDocuments(this.selectedRowKeys)
            this.selectedRowKeys = []
        },
        edit(id){
            this.visibleUpdate = true
            this.getDocument(id)
        },
        handleUpdateOk(){
            this.updateDocument(this.document);
        },
        handleUpdateCancel(){
            this.visibleUpdate = false
        },
        onDelete(id,filename){
            console.log(filename)
            this.deletDocument(id,filename)
        },
        getFile(id,filename){
            let that = this;
            let url = resources.getFile();
            this.$ajax
            .get(url,{
                    params:{
                    id:id
                },
            responseType: 'blob'
            })
            .then(res => {
                var blob = new Blob([res.data])
                var downloadElement = document.createElement('a');
                var href = window.URL.createObjectURL(blob); //创建下载的链接
                downloadElement.href = href;
                downloadElement.download = filename; //下载后文件名
                document.body.appendChild(downloadElement);
                downloadElement.click(); //点击下载
                document.body.removeChild(downloadElement); //下载完成移除元素
                window.URL.revokeObjectURL(href); //释放掉blob对象 
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        addDocument(documentAdd){
            const { fileList } = this;
            const formData = new FormData();
            formData.append('title',documentAdd.title)
            formData.append('remark',documentAdd.remark)
            formData.append('empId',documentAdd.empId)
            formData.append('userId',documentAdd.userId)
            fileList.forEach((file) => {
                formData.append('files[]', file);
            });
            this.uploading = true

            // You can use any AJAX library you like
            reqwest({
                url: resources.uploadDocument(),
                method: 'post',
                processData: false,
                data: formData,
                success: () => {
                this.fileList = []
                this.documentAdd= {}
                this.uploading = false
                this.$message.success('上传成功！');
                this.getDocuments();
                },
                error: () => {
                this.uploading = false
                this.$message.error('上传失败！');
                },
            });
        },
        deletDocument(id,filename){
            let that = this;
            let url = resources.deletDocument();
            this.$ajax
            .get(url,{
                    params:{
                    id:id,
                    filename:filename
                }
            })
            .then(res => {
                this.getDocuments()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        deleteDocuments(selectedRowKeys){
            let that = this;
            let url = resources.deleteDocuments();
            this.$ajax
            .get(url,{
                params:{
                    idList:selectedRowKeys
                }
            })
            .then(res => {
                console.log("delete success")
                this.getDocuments()
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getDocument(id){
            let that = this;
            let url = resources.getDocument();
            this.$ajax
            .get(url,{
                params:{
                    id:id
                }
            })
            .then(res => {
                console.log(res.data.extend.document)
                that.document = res.data.extend.document
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        getDocuments(){
            let that = this;
            let url = resources.getDocuments();
            this.$ajax
            .get(url)
            .then(res => {
                console.log(res.data.extend)
                that.documents = res.data.extend.documents
                for(var i=0;i<that.documents.length;i++){
                    that.documents[i].key = that.documents[i].id
                } 
            })
            .catch(function(error) {
                alert("网络请求出错啦，请联系管理员！异常信息："+ error);
                console.log(error);
            });
        },
        updateDocument(document){
            let that = this;
            let url = resources.updateDocument();
            this.$ajax
            .post(url,document)
            .then(res => {
                this.visibleUpdate = false
                this.getDocuments()
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
      this.getDocuments();
    }
}
</script>
<style scoped>

</style>
