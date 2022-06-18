import React from 'react'
import App from "../App";
// 用户操作页面
import UserList from '../pages/User/UserList';
import Mission from "../pages/Mission/Mission";
import Means from "../pages/Means/Means";
//管理员操作页面
import AdminList from '../pages/User/AdminList';
import AdminMission from "../pages/Mission/AdminMission";
import AdminMeans from "../pages/Means/AdminMeans";
//派发员操作页面
import DispatcherList from '../pages/User/DispatcherList';
import DispatcherMission from "../pages/Mission/DispatcherMission";
import DispatcherMeans from "../pages/Means/DispatcherMeans";
//没登录时操作页面
import Nologin from '../components/NoLogin';

import Login from "../pages/Login/Login";
import Register from "../pages/Register/Register";
import { BrowserRouter as Router,Routes,Route } from "react-router-dom";
import P404 from '../components/404'

export default function BaseRouter() {

  return (
    <Router>
            <Routes>
              
                <Route path="/" element={<App/>}>
                   {/* 用户操作页面 */}
                    <Route path="/userlist" element={<UserList/>}></Route>
                    <Route path="/usermission" element={<Mission/>}></Route>
                    <Route path="/means" element={<Means/>}></Route>
                    {/* 管理员操作页面 */}
                    <Route path="/adminlist" element={<AdminList/>}></Route>
                    <Route path="/adminmission" element={<AdminMission/>}></Route>
                    <Route path="/adminmeans" element={<AdminMeans/>}></Route>
                    {/* 派发员操作页面 */}
                    <Route path="/dispatcherlist" element={<DispatcherList/>}></Route>
                    <Route path="/dispatchermission" element={<DispatcherMission/>}></Route>
                    <Route path="/dispatchermeans" element={<DispatcherMeans/>}></Route>
                    {/* 没登录时的页面 */}
                    <Route path='/nologin' element={<Nologin/>}></Route>
                </Route>
                <Route path="/login" element={<Login/>}></Route>
                <Route path="/register" element={<Register/>}></Route>
                <Route path='*' element={<P404/>}/>
            </Routes>
        </Router>
  )
}


//路由
