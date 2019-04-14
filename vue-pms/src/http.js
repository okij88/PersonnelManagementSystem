import axios from "axios";
import { message } from "ant-design-vue";
// 超时时间
axios.defaults.timeout = 3000;
// 添加一个请求拦截器
axios.interceptors.request.use(
  function(config) {
    // 判断是否存在token，如果存在的话，则每个http header都加上token
    if (sessionStorage.getItem("token")) {
      config.headers.Authorization = sessionStorage.getItem("token");
    }
    return config;
  },
  function(error) {
      message.error('数据加载失败，请联系管理员！');
    return Promise.reject(error);
  }
);

// http响应拦截器
axios.interceptors.response.use(data => {
    return data
}, error => {
        message.error( '哎呦,网络好像有点问题,无响应数据返回～');
    return Promise.reject(error);
})
export default axios;