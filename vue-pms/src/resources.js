const debug = process.env.NODE_ENV === "development" ? true : false;
// 测试环境地址
if (debug) {
  var host = "http://localhost:8080/ssm-PersonnelManagementSystem";
} else {
  //正式环境地址
  var host = "http://localhost:8080/ssm-PersonnelManagementSystem";
}

let resources = {
  schoolId: sessionStorage.getItem("schoolId") || 0,
  host: host,
  getUsers() {
    return `${host}\/users\/select`;
  },
  getUser() {
    return `${host}\/user\/select`;
  },
  checkLoginNameRepeat() {
    return `${host}\/user\/check`;
  },
  addUser(){
    return `${host}\/user\/add`;
  },
  autoAdd(){
    return `${host}\/user\/autoadd`;
  },
  deleteUser(){
    return `${host}\/user\/delete`;
  },
  deleteUsers(){
    return `${host}\/users\/delete`;
  },
  updateUser(){
    return `${host}\//user\/update`;
  },
  searchUsers(){
    return `${host}\//users\/search`;
  },
  test(){
    return `${host}\/test`;
  },
  getDept(){
    return `${host}\/dept\/select`;
  },
  getDepts(){
    return `${host}\/depts\/select`;
  },
  addDept(){
    return `${host}\/dept\/add`;
  },
  checkDeptNameRepeat(){
    return `${host}\/dept\/check`;
  },
  deletDept(){
    return `${host}\/dept\/delete`;
  },
  deleteDepts(){
    return `${host}\/depts\/delete`;
  },
  updateDept(){
    return `${host}\/dept\/update`;
  },
  getJob(){
    return `${host}\/job\/select`;
  },
  getJobs(){
    return `${host}\/jobs\/select`;
  },
  addJob(){
    return `${host}\/job\/add`;
  },
  checkJobNameRepeat(){
    return `${host}\/job\/check`;
  },
  deletJob(){
    return `${host}\/job\/delete`;
  },
  deleteJobs(){
    return `${host}\/jobs\/delete`;
  },
  updateJob(){
    return `${host}\/job\/update`;
  },
  getEmp(){
    return `${host}\/emp\/select`;
  },
  getEmps(){
    return `${host}\/emps\/select`;
  },
  addEmp(){
    return `${host}\/emp\/add`;
  },
  deletEmp(){
    return `${host}\/emp\/delete`;
  },
  deleteEmps(){
    return `${host}\/emps\/delete`;
  },
  updateEmp(){
    return `${host}\/emp\/update`;
  },
  getMessage(){
    return `${host}\/emp\/show`;
  },
  searchEmps(){
    return `${host}\//emps\/search`;
  },
  getNotice(){
    return `${host}\/notice\/select`;
  },
  getNotices(){
    return `${host}\/notices\/select`;
  },
  addNotice(){
    return `${host}\/notice\/add`;
  },
  deletNotice(){
    return `${host}\/notice\/delete`;
  },
  deleteNotices(){
    return `${host}\/notices\/delete`;
  },
  updateNotice(){
    return `${host}\/notice\/update`;
  },
  getDocument(){
    return `${host}\/document\/select`;
  },
  getDocuments(){
    return `${host}\/documents\/select`;
  },
  deletDocument(){
    return `${host}\/document\/delete`;
  },
  deleteDocuments(){
    return `${host}\/documents\/delete`;
  },
  updateDocument(){
    return `${host}\/document\/update`;
  },
  uploadDocument(){
    return `${host}\/document\/upload`;
  },
  getFile(){
    return `${host}\/document\/download`;
  },
  getAttendances(){
    return `${host}\/attendances\/select`;
  },
  submitAttendances(){
    return `${host}\/attendances\/add`;
  },
  updateAttendances(){
    return `${host}\/attendances\/update`;
  },
  getWorkdays(){
    return `${host}\/workdays\/select`;
  },
  addWorkdays(){
    return `${host}\/workdays\/add`;
  },
  getLeaves(){
    return `${host}\/leaves\/select`;
  },
  getUnCheckLeaves(){
    return `${host}\/uncheckleaves\/select`;
  },
  updateLeave(){
    return `${host}\/leave\/update`;
  },
  deleteLeaves(){
    return `${host}\/leaves\/delete`;
  },
  getLeavesByMonth(){
    return `${host}\/leavesofmonth\/select`;
  },
  getOvertimes(){
    return `${host}\/overtimes\/select`;
  },
  getUnCheckOvertimes(){
    return `${host}\/uncheckovertimes\/select`;
  },
  updateOvertime(){
    return `${host}\/overtime\/update`;
  },
  deleteOvertimes(){
    return `${host}\/overtimes\/delete`;
  },
  getOvertimesByMonth(){
    return `${host}\/overtimesofmonth\/select`;
  }
  
};
export default resources;
