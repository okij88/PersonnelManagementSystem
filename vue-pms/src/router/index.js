import Vue from "vue";
import VueRouter from "vue-router";
Vue.use(VueRouter);
import NotFound from "@/components/NotFound";
import Home from "@/components/Home";
import Test from "@/components/Test"; //预留页面
import Login from "@/components/Login";
import UserManage from "@/components/HumanManage/UserManage";
import DepartmentManage from "@/components/HumanManage/DepartmentManage";
import JobManage from "@/components/HumanManage/JobManage";
import EmployeeManage from "@/components/HumanManage/EmployeeManage";
import NoticeManage from "@/components/NoticeManage";
import Document from "@/components/Document";
import AttendanceTime from "@/components/Attendance/AttendanceTime";
import AttendanceDetails from "@/components/Attendance/AttendanceDetails";
import WorkDay from "@/components/Attendance/WorkDay";
import LeaveManage from "@/components/Attendance/LeaveManage";
import OvertimeManage from "@/components/Attendance/OvertimeManage";

let routes = [
  {
    path: "*",
    redirect: { path: "/404" },
    meta: { requireAuth: false },
    hidden: true
  },
  {
    path: "/",
    redirect: { path: "/login" },
    meta: { requireAuth: false },
    hidden: true
  },
  {
    path: "/404",
    component: NotFound,
    meta: { requireAuth: false },
    hidden: true
  },
  {
    path: "/login",
    component: Login,
    key: "Login",
    hidden: true
  },
  {
    path: "/",
    component: Home,
    icon: "file",
    leaf: false, //只有一个节点
    key: "NoticeManage",
    permit: 1,
    children: [
      {
        path: "/NoticeManage",
        key: "NoticeManage",
        component: NoticeManage,
        meta: { requireAuth: true },
        name: "公告管理"
      },
    ]
  },
  {
    path: "/",
    component: Home,
    name:"人事管理",
    icon: "setting",
    leaf: true,
    permit: 1,
    children: [
      {
        path: "/UserManage",
        key: "UserManage",
        component: UserManage,
        meta: { requireAuth: true },
        name: "用户管理"
      },
      {
        path: "/DepartmentManage",
        key: "DepartmentManage",
        component: DepartmentManage,
        meta: { requireAuth: true },
        name: "部门管理"
      },
      {
        path: "/JobManage",
        key: "JobManage",
        component: JobManage,
        meta: { requireAuth: true },
        name: "职位管理"
      },
      {
        path: "/EmployeeManage",
        key: "EmployeeManage",
        component: EmployeeManage,
        meta: { requireAuth: true },
        name: "员工管理"
      }
    ]
  },
  {
    path: "/",
    component: Home,
    name:"考勤管理",
    icon: "setting",
    leaf: true,
    permit: 1,
    children: [
      {
        path: "/WorkDay",
        key: "WorkDay",
        component: WorkDay,
        meta: { requireAuth: true },
        name: "工作日导入"
      },
      {
        path: "/AttendanceTime",
        key: "AttendanceTime",
        component: AttendanceTime,
        meta: { requireAuth: true },
        name: "考勤时间"
      },
      {
        path: "/LeaveManage",
        key: "LeaveManage",
        component: LeaveManage,
        meta: { requireAuth: true },
        name: "请假管理"
      },
      {
        path: "/OvertimeManage",
        key: "OvertimeManage",
        component: OvertimeManage,
        meta: { requireAuth: true },
        name: "加班管理"
      },
      {
        path: "/AttendanceDetails",
        key: "AttendanceDetails",
        component: AttendanceDetails,
        meta: { requireAuth: true },
        name: "考勤详情"
      },
    ]
  },
  {
    path: "/",
    component: Home,
    icon: "file",
    leaf: false, //只有一个节点
    key: "Document",
    permit: 1,
    children: [
      {
        path: "/Document",
        key: "Document",
        component: Document,
        meta: { requireAuth: true },
        name: "下载中心"
      },
    ]
  },
  {
    path: "/",
    component: Home,
    icon: "file",
    leaf: false, //只有一个节点
    key: "Test",
    permit: 1,
    children: [
      {
        path: "/Test",
        key: "Test",
        component: Test,
        meta: { requireAuth: true },
        name: "Test"
      },
    ]
  },
];
const router = new VueRouter({
  routes
});
router.beforeEach((to,from,next)=>{
  if (to.matched.some(res => res.meta.requireAuth)) {// 判断是否需要登录权限
    if (sessionStorage.getItem('userId')) {// 判断是否登录
      next()
    } else {// 没登录则跳转到登录界面
      next({
        path: '/login',
      })
    }
  } else {
    next()
  }
});
export default router;
