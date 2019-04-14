import Vue from "vue";
import App from "./App";
import router from "./router";
import Antd from "ant-design-vue";
import "ant-design-vue/dist/antd.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
import SlideVerify from "vue-monoplasty-slide-verify";
import axios from "./http.js";
Vue.config.productionTip = false;
Vue.use(Antd);
Vue.use(SlideVerify);
Vue.prototype.$ajax = axios;
new Vue({
  el: "#app",
  router,
  components: { App },
  template: "<App/>"
});
