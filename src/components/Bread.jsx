import React, { useState, useEffect } from "react";
import { Breadcrumb } from "antd";
import { HomeOutlined } from "@ant-design/icons";
import { useLocation } from "react-router-dom";

export default function Bread() {
  const { pathname } = useLocation(); //解构写法，如果加{}表示直接在useLocation中获取其中pathname的值
  const [breadName, setBreadName] = useState("");

  //不是在组件mounted时去获取路径，而是路径一旦变化就要获取相应的路径名称
  //监听路由的路径
  useEffect(() => {
    //设置面包屑的名字
    switch (pathname) {
        // 用户面包屑
      case "/userlist":
        setBreadName("任务信息");
        break;
      case "/means":
        setBreadName("基本信息");
        break;
      case "/usermission":
        setBreadName("任务选择");
        break;
        // 管理员面包屑
      case "/adminmission":
        setBreadName("任务管理");
        break;
      case "/adminlist":
        setBreadName("成员编辑");
        break;
      case "/adminmeans":
        setBreadName("基本信息");
        break;
        // 派发员面包屑
      case "/dispatchermission":
        setBreadName("任务管理");
        break;
      case "/dispatcherlist":
        setBreadName("成员管理");
        break;
      case "/dispatchermeans":
        setBreadName("基本信息");
        break;
      default:
        break;
    }
  }, [pathname]);

  return (
    <Breadcrumb>
      <Breadcrumb.Item href="/">
        <HomeOutlined />
      </Breadcrumb.Item>
      <Breadcrumb.Item>{breadName}</Breadcrumb.Item>
    </Breadcrumb>
  );
}
