<template>
	<div>
		<div class="title">用户管理</div>
    <hr>
    <div style="margin-bottom:10px">
      <a-button @click="handleAddClicked">添加</a-button>
      <a-button @click="handleAutoAddClicked">一键添加</a-button>
      <a-button @click="handleChoseDeleted">批量删除</a-button>
      <a-input-search
        placeholder="输入要查询的内容"
        v-model = "searchText"
        @search="onSearch"
        style="width: 200px;float:right"
        enterButton
      />
    </div>
    <a-table :rowSelection="rowSelection" :columns="columns" :dataSource="users" bordered :pagination="false">
      <template slot="createdate" slot-scope="text, record, index">
          {{ text | formatDate}}
      </template>
      <template slot="operation" slot-scope="text, record, index" width="100px">
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
        title="添加"
        v-model="visibleAdd"
        @ok="handleAddOk"
      >
        <a-form style="margin-right:15%" >
          <a-form-item
            v-bind="formItemLayout"
            label="登录名"
            :validate-status="checkUserAddLoginName.status"
            :has-feedback="checkUserAddLoginName.feedback"
            :help="checkUserAddLoginName.help"
          >
            <a-input @blur="checkLoginNameRepeat" placeholder="loginName" v-model="userAdd.loginname"/>
          </a-form-item>
          <a-form-item
            v-bind="formItemLayout"
            label="密码"
            :validate-status="checkUserAddPassword.status"
            :has-feedback="checkUserAddPassword.feedback"
            :help="checkUserAddPassword.help"
          >
            <a-input @blur="checkLoginPassword" placeholder="password" v-model="userAdd.password"/>
          </a-form-item>
          <a-form-item
            v-bind="formItemLayout"
            label="用户名"
          >
             <a-select v-model="userAdd.username"> 
              <a-select-option :key="item.status" v-for="item in userName">
                {{item.username}}
              </a-select-option>
            </a-select>
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
      <a-form style="margin-right:15%">
        <a-form-item
          v-bind="formItemLayout"
          label="登录名"
        >
          <span class="ant-form-text" >
            {{user.loginname}}
          </span>
        </a-form-item>
        <a-form-item
          v-bind="formItemLayout"
          label="密码"
          :validate-status="checkUserUpdatePassword.status"
          :has-feedback="checkUserUpdatePassword.feedback"
          :help="checkUserUpdatePassword.help"
        >
          <a-input  @blur="checkUpdatePassword" v-model="user.password"/>
        </a-form-item>
        <a-form-item
          v-bind="formItemLayout"
          label="用户名"
        >
          <a-select v-model="user.username">
            <a-select-option :key="item.status" v-for="item in userName">
                {{item.username}}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
      <template slot="footer">
        <a-button key="submit" type="primary" @click="handleUpdateOk">提交</a-button>
        <a-button key="back" @click="handleUpdateCancel">关闭</a-button>
      </template>
    </a-modal>
	</div>
</template>

<script>
import resources from '@/resources'
import {formatDate} from '@/date'
const columns = [{
  title: '序号',
  dataIndex: 'id',
  scopedSlots: { customRender: 'id' },
},{
  title: '登录名',
  dataIndex: 'loginname',
  scopedSlots: { customRender: 'loginname' },
}, {
  title: '密码',
  dataIndex: 'password',
  scopedSlots: { customRender: 'password' },
}, {
  title: '用户名',
  dataIndex: 'username',
  scopedSlots: { customRender: 'username' },
}, {
  title: '状态',
  dataIndex: 'userstatus',
  scopedSlots: { customRender: 'email' },
}, {
  title: '创建时间',
  dataIndex: 'createdate',
  scopedSlots: { customRender: 'createdate' },
},{
  title: '员工名',
  dataIndex: 'employee.name',
  scopedSlots: { customRender: 'employee.name' },
}, {
  title: '操作',
  dataIndex: 'operation',
  width: '10%',
  scopedSlots: { customRender: 'operation' },
}]

export default {
  filters:{
    formatDate(time){
        let date = new Date(time);
        return formatDate(date,'yyyy-MM-dd hh:mm');
    }
  },
  data () {
    return {
      visibleAdd:false,
      visibleUpdate:false,
      columns,
      code:'',
      users:[],
      user:{},
      userName:[{
        status:2,
        username:"超级管理员"
      },{
        status:1,
        username:"测试用户"
      }],
      checkUserAddLoginName:{
        status:"",
        feedback:false,
        help:"",
        AddLoginNameFlag:false
      },
      checkUserAddPassword:{
        status:"",
        feedback:false,
        help:"",
        AddPasswordFlag:false
      },
      checkUserUpdatePassword:{
        status:"",
        feedback:false,
        help:"",
        UpdatePasswordFlag:true
      },
      current:1,
      searchText:'',
      searchStatus:false,
      page:{},
      userAdd:{},
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
  methods: {
    onSearch(e){
      console.log(e)
      if(e == '' || e == undefined || e == null){
        this.getUsers(1)
        this.searchStatus = false
      }else{
        this.searchUsers(e)
      }
    },
    handleAddClicked(){
      this.visibleAdd = true
      this.userAdd = {}
      this.checkUserAddLoginName.status = ""
      this.checkUserAddLoginName.feedback = false
      this.checkUserAddLoginName.help = ""
      this.checkUserAddLoginName.AddLoginNameFlag = false
      this.checkUserAddPassword.status = ""
      this.checkUserAddPassword.feedback = false
      this.checkUserAddPassword.help = ""
      this.checkUserAddPassword.AddPasswordFlag = false
    },
    handleAddOk(){
      var checkUserName = this.checkAddUserName()
      console.log(this.checkUserAddLoginName.AddLoginNameFlag,this.checkUserAddPassword.AddPasswordFlag,checkUserName)
      if(this.checkUserAddLoginName.AddLoginNameFlag && this.checkUserAddPassword.AddPasswordFlag && checkUserName){
        if(this.userAdd.username == 2){
          this.userAdd.userstatus = 2
          this.userAdd.username = "超级管理员"
        }
        else{
          this.userAdd.userstatus = 1
          this.userAdd.username = "测试用户"
        }
        console.log("ok")
        this.addUser(this.userAdd)
        this.userAdd= {}
      }
      else{
        this.$message.error('验证未通过，请检查是否全部填写');
        return;
      }
    },
    handleAddCancel(){
      this.visibleAdd = false
    },
    handleChoseDeleted(){
      this.deleteUsers(this.selectedRowKeys);
    },
    edit (id) {
      console.log("用户Id："+id)
      this.getUser(id)
      this.visibleUpdate = true
      this.checkUserUpdatePassword.status = ""
      this.checkUserUpdatePassword.feedback = false
      this.checkUserUpdatePassword.help = ""
      this.checkUserUpdatePassword.UpdatePasswordFlag = true
    },
    handleUpdateOk(){
      if(this.checkUserUpdatePassword.UpdatePasswordFlag == true){
        if(this.user.username == 2){
          this.user.username = "超级管理员"
          this.user.userstatus = 2
        }
        else{
          this.user.username = "测试用户"
          this.user.userstatus = 1
        }
        console.log(this.user)
        this.updateUser(this.user);
      }
      else{
        this.$message.error('验证未通过，请检查是否合法修改');
        return;
      }
    },
    handleUpdateCancel(){
      this.visibleUpdate = false
    },
    handleAutoAddClicked(){
      this.autoAdd()
    },
    onDelete(id){
      this.deleteUser(id)
    },
    checkAddloginName(){
      console.log(this.userAdd.loginname)
      var regName = /(^[a-zA-Z0-9_-]{6,16}$)/
      if(this.userAdd.loginname == null || this.userAdd.loginname == '' || this.userAdd.loginname == undefined){
        this.checkUserAddLoginName.status = "error"
        this.checkUserAddLoginName.feedback = true
        this.checkUserAddLoginName.help = "登录名不能为空"
        this.checkUserAddLoginName.AddLoginNameFlag = false
      }
      else if(this.code == 200){
        this.checkUserAddLoginName.status = "error"
        this.checkUserAddLoginName.feedback = true
        this.checkUserAddLoginName.help = "已存在登录名"
        this.checkUserAddLoginName.AddLoginNameFlag = false
      }
      else if(!regName.test(this.userAdd.loginname)){
        this.checkUserAddLoginName.status = "error"
        this.checkUserAddLoginName.feedback = true
        this.checkUserAddLoginName.help = "登录名为6-16位英文和数字的组合"
        this.checkUserAddLoginName.AddLoginNameFlag = false
      }
      else{
        this.checkUserAddLoginName.status = "success"
        this.checkUserAddLoginName.feedback = true
        this.checkUserAddLoginName.help = ""
        this.checkUserAddLoginName.AddLoginNameFlag = true
      }
      this.code = ''
    },
    checkLoginPassword(){
      console.log(this.userAdd.password)
      var regName = /(^[a-zA-Z0-9_-]{6,16}$)/
      if(this.userAdd.password == null || this.userAdd.password == '' || this.userAdd.password == undefined){
        this.checkUserAddPassword.status = "error"
        this.checkUserAddPassword.feedback = true
        this.checkUserAddPassword.help = "密码不能为空"
        this.checkUserAddPassword.AddPasswordFlag = false
      }
      else if(!regName.test(this.userAdd.password)){
        this.checkUserAddPassword.status = "error"
        this.checkUserAddPassword.feedback = true
        this.checkUserAddPassword.help = "密码为6-16位英文和数字的组合"
        this.checkUserAddPassword.AddPasswordFlag = false
      }
      else{
        this.checkUserAddPassword.status = "success"
        this.checkUserAddPassword.feedback = true
        this.checkUserAddPassword.help = ""
        this.checkUserAddPassword.AddPasswordFlag = true
      }
    },
    checkUpdatePassword(){
      console.log(this.userAdd.password)
      var regName = /(^[a-zA-Z0-9_-]{6,16}$)/
      if(this.user.password == null || this.user.password == '' || this.user.password == undefined){
        this.checkUserUpdatePassword.status = "error"
        this.checkUserUpdatePassword.feedback = true
        this.checkUserUpdatePassword.help = "密码不能为空"
        this.checkUserUpdatePassword.UpdatePasswordFlag = false
      }
      else if(!regName.test(this.user.password)){
        this.checkUserUpdatePassword.status = "error"
        this.checkUserUpdatePassword.feedback = true
        this.checkUserUpdatePassword.help = "密码为6-16位英文和数字的组合"
        this.checkUserUpdatePassword.UpdatePasswordFlag = false
      }
      else{
        this.checkUserUpdatePassword.status = "success"
        this.checkUserUpdatePassword.feedback = true
        this.checkUserUpdatePassword.help = ""
        this.checkUserUpdatePassword.UpdatePasswordFlag = true
      }
    },
    checkAddUserName(){
      if(this.userAdd.username == null || this.userAdd.username == '' || this.userAdd.username == undefined){
        return false
      }
      else{
        return true
      }
    },
    checkLoginNameRepeat(){
      let that = this;
      let url = resources.checkLoginNameRepeat();
      this.$ajax
      .get(url,{
        params:{
          loginname:this.userAdd.loginname
        }
      })
      .then(res => {
       console.log(res.data)
        this.code = res.data.code
        this.checkAddloginName()
      })
      .catch(function(error) {
        alert("网络请求出错啦，请联系管理员！异常信息："+ error);
        console.log(error);
      });
    },
    pageChange(e){
      this.getUsers(e)
    },
    updateUser(user){
      let that = this;
      let url = resources.updateUser();
      this.$ajax
      .post(url,user)
      .then(res => {
        this.visibleUpdate = false
        this.getUsers(this.current)
      })
      .catch(function(error) {
        alert("网络请求出错啦，请联系管理员！异常信息："+ error);
        console.log(error);
      });
    },
    deleteUser(id){
      let that = this;
      let url = resources.deleteUser();
      this.$ajax
      .get(url,{
        params:{
          id:id
        }
      })
      .then(res => {
        console.log("delete success")
        this.getUsers(this.current)
      })
      .catch(function(error) {
        alert("网络请求出错啦，请联系管理员！异常信息："+ error);
        console.log(error);
      });
    },
    deleteUsers(selectedRowKeys){
      let that = this;
      let url = resources.deleteUsers();
      this.$ajax
      .get(url,{
        params:{
          idList:selectedRowKeys
        }
      })
      .then(res => {
        console.log("delete success")
        this.getUsers(this.current)
      })
      .catch(function(error) {
        alert("网络请求出错啦，请联系管理员！异常信息："+ error);
        console.log(error);
      });
    },
    getUser(id){
      let that = this;
      let url = resources.getUser();
      this.$ajax
      .get(url,{
        params:{
          id:id
        }
      })
      .then(res => {
        that.user = res.data.extend.user
        console.log(that.user)
      })
      .catch(function(error) {
        alert("网络请求出错啦，请联系管理员！异常信息："+ error);
        console.log(error);
      });
    },
    getUsers(current){
      let that = this;
      let url = resources.getUsers();
      this.$ajax
      .get(url,{
        params:{
          pn:current
        }
      })
      .then(res => {
        console.log(res.data.extend)
        console.log(res.data.extend.pageInfo)
        that.page = res.data.extend.pageInfo
        that.users = res.data.extend.pageInfo.list
        var size = res.data.extend.pageInfo.size
        for(var i=0;i<size;i++){
          that.users[i].key = res.data.extend.pageInfo.list[i].id
        }  
      })
      .catch(function(error) {
        alert("网络请求出错啦，请联系管理员！异常信息："+ error);
        console.log(error);
      });
    },
    addUser(userAdd){
      let that = this;
      let url = resources.addUser();
      this.$ajax
      .post(url,userAdd)
      .then(res => {
        console.log(res)
        if(res.data.code == 200){
          this.$message.error("请通过合法的方式上传！！！")
        }
        else{
          this.visibleAdd = false
          this.getUsers(Math.ceil((this.page.total+1)/this.page.pageSize))
          this.current = Math.ceil((this.page.total+1)/this.page.pageSize)
        }
      })
      .catch(function(error) {
        alert("网络请求出错啦，请联系管理员！异常信息："+ error);
        console.log(error);
      });
    },
    autoAdd(){
      let that = this;
      let url = resources.autoAdd();
      this.$ajax
      .get(url)
      .then(res => {
          console.log(this.page.total)
          this.getUsers(Math.ceil((this.page.total+10)/this.page.pageSize))
          this.current = Math.ceil((this.page.total+10)/this.page.pageSize)
      })
      .catch(function(error) {
        alert("网络请求出错啦，请联系管理员！异常信息："+ error);
        console.log(error);
      });
    },
    searchUsers(e){
      let that = this;
      let url = resources.searchUsers();
      this.$ajax
      .get(url,{
        params:{
          e:e
        }
      })
      .then(res => {
        console.log(res)
        if(res.data.extend.users.length != 0){
          that.users = res.data.extend.users
          var size = res.data.extend.users.length
          for(var i=0;i<size;i++){
            that.users[i].key = res.data.extend.users[i].id
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
    onSelectChange (selectedRowKeys) {
      console.log('selectedRowKeys changed: ', selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys
    },
  },
  mounted:function() {
      this.getUsers(1);
  }
}
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>