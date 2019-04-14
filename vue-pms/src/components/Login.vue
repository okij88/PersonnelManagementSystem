<template>
  <div>
    <div class="container">
      <div class="img">
        <img src="@/assets/img/wzulogo.png" style="width: 50%">
      </div>
      <div>
        <h1 align="center">人事管理系统</h1>
      </div>
      <div class="components-input-demo-presuffix mt-3 mb-3">
        <a-input  placeholder="用户名" v-model="loginForm.username"  size="large">
          <a-icon slot="prefix" type="user"/>
        </a-input>
      </div>
      <div class="components-input-demo-presuffix mt-3 mb-3">
        <a-input placeholder="密码" v-model="loginForm.password"  size="large" type="password">
          <a-icon slot="prefix" type="lock"/>
        </a-input>
      </div>
      <button class="btn btn-md btn-primary btn-block" type="button" @click="submit">登录</button>
    </div>
    <div class="footer">
      <p class="text-muted0">&copy; 2019-2020</p>
    </div>
  </div>
</template>

<script>
import resources from "../resources";
export default {
  data() {
    return {
      loginForm: {
        username: "",
        password: ""
      }
    };
  },
  methods: {
    formVerify() {
      if (this.loginForm.username === "") {
        return false;
      }
      if (this.loginForm.password === "") {
        return false;
      }
      return true;
    },
    submit() {
      if (!this.formVerify()) {
        this.$message.error("请将表单填充完整！");
        return;
      }
      this.upload();
    },
    upload(){
      if(this.loginForm.username === "123" && this.loginForm.password === "123"){
        sessionStorage.setItem("userId",1);
        sessionStorage.setItem("empId",1);
        sessionStorage.setItem("jobId",8);
        sessionStorage.setItem("timeId",1);
        this.$router.push("/NoticeManage");
      }
      else{
        this.$message.error("用户名或密码错误");
        return;
      }

    },
    handleOk(e) {
      console.log(e);
      this.visible = false;
    }
  }
};

</script>
<style scoped>
.logo {
  width: 10rem;
  height: 10rem;
}
.components-input-demo-presuffix .anticon-close-circle {
  cursor: pointer;
  color: #ccc;
  transition: color 0.3s;
  font-size: 15px;
}
.components-input-demo-presuffix .anticon-close-circle:hover {
  color: #999;
}
.components-input-demo-presuffix .anticon-close-circle:active {
  color: #666;
}
.container {
  width: 25%;
  height: 100%;
  padding-top: 5%;
  min-width: 13em;
}
.img {
  text-align: center;
  margin-bottom: 0.5em;
}
.footer {
  position: relative;
  top: 7em;
  text-align: center;
}
</style>