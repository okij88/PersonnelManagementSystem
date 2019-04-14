<template>
<a-locale-provider :locale="zh_CN">
  <a-layout  class="fullheight">
    <a-layout-header class="header"  theme="light">
        <ul class="header-title">
        <li><img :src=logoUrl alt="" class="school-logo"></li>
        <li class="school-msg">人事管理系统</li>
      </ul>
    </a-layout-header>
  <a-layout>
  <!--侧边菜单-->
  <a-layout-sider collapsible v-model="sidebarcollapsed" theme="dark" >
    <div style="padding: 1.5rem;color: #fff; font-size: 1rem;">
      <a-avatar shape="square" :size="32" icon="user" style="background:#6c7cd1;border:1px solid #eee;margin-right:10px;"/><span style="margin-top:25px;letter-spacing:4px;text-align:center;">张三</span>
    </div>
    <a-menu theme="dark" :defaultSelectedKeys="['CourseIndicator']"  mode="inline" v-model=selectedKeys @select="menuSelect">
        <template v-for="item in $router.options.routes" v-if="!item.hidden">
          <a-menu-item :key="item.key" v-if="!item.leaf"><a-icon :type="item.icon" /><span>{{item.children[0].name}}</span></a-menu-item>
          <a-sub-menu v-if="item.leaf&&item.children.length>1">
            <span slot="title"><a-icon :type="item.icon" /><span>{{item.name}}</span></span>
            <a-menu-item :key="child.key" v-for="child in item.children" v-if="!child.leaf">{{child.name}}</a-menu-item>
          </a-sub-menu>
          <a-menu-item :key="item.key" v-if="item.leaf&&item.children.length==1">
            <a-icon :type="item.icon" /><span>{{item.children[0].name}}</span>
          </a-menu-item>
        </template>
    </a-menu>
  </a-layout-sider>
  <a-layout style="overflow:auto;">
      <a-row style="height:100%;min-height: 620px;background:#fff;">
        <router-view class="m-4"></router-view>
      </a-row>
  </a-layout>
    </a-layout>
  </a-layout> 
   </a-locale-provider>
</template>
<script>
import zh_CN from 'ant-design-vue/lib/locale-provider/zh_CN';
import moment from 'moment';
import 'moment/locale/zh-cn';
moment.locale('zh-cn');
export default {
  data() {
    return {
      zh_CN,
      logoUrl: require("@/assets/img/wzulogo.png"), 
      selectedKeys:[],
      count:0,
      sidebarcollapsed:false,
    };
  },
  methods:{
    menuSelect({ item, key, selectedKeys }){
      this.$router.push(key);
    },
  }
};
</script>

<style scoped>
.ant-layout{
  background: #fff;
}
.ant-menu>:first-child{
  margin-top: 0;
}
.ant-layout-header {
  background: #fff;
  padding: 0 1rem;
  border-bottom: 0px solid #e5e6e8;
  box-shadow: 0px 1px 1px 0px #ddd !important;
  z-index: 300;
}
.header-title {
  line-height: 64px;
  list-style: none;
  padding: 0;
}
.header-title > li {
  float: left;
}
.school-logo {
  width: 64px;
  height: 64px;
  padding: 10px;
}
.school-msg {
  font-weight: 400;
  color: #333;
  font-size: 1.2rem;
}
.li-menu-item {
  margin-top: 0;
}
.ant-layout-sider {
  overflow: auto;
}
</style>
