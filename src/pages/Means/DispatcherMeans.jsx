import { Divider } from "antd";
import Means from "../less/Means.less";
import AdminInfo from './AdminMeans/AdminInfo.jsx';
import React, { useState, useEffect } from 'react';
import ReactDOM from 'react-dom';
import { Pie } from '@ant-design/plots';
import "@ant-design/flowchart/dist/index.css";
import {GetDisData} from '../../request/api'

export default function DispatcherMeans() {
  const [user,setUser]=useState();
  const [task,setTask]=useState();
  const [sign,setSign]=useState();

  const getData=()=>{
    GetDisData({
      username:localStorage.getItem("username")
    }).then((res)=>{
      let {sign,task,user}=res.data;
      setSign(sign);
      setTask(task);
      setUser(user);
    })
  }

  useEffect(()=>{
    getData();
  },[])

  const UserPie = () => {//饼图数据
    const data = [
      {
        type: '管理中的用户',
        value: user,
      }
    ];
    const config = {//动画设置
      appendPadding: 10,
      data,
      angleField: 'value',
      colorField: 'type',
      radius: 0.8,
      label: {
        type: 'outer',
        content: '{name} {percentage}',
      },
      interactions: [
        {
          type: 'pie-legend-active',
        },
        {
          type: 'element-active',
        },
      ],
    };
    return <Pie {...config} />;
  };

  const MissionPie = () => {//饼图数据
    const data = [
      {
        type: '作业',
        value: task,
      },
      {
        type: '签到',
        value: sign,
      }
    ];
    const config = {//动画设置
      appendPadding: 10,
      data,
      angleField: 'value',
      colorField: 'type',
      radius: 0.8,
      label: {
        type: 'outer',
        content: '{name} {percentage}',
      },
      interactions: [
        {
          type: 'pie-legend-active',
        },
        {
          type: 'element-active',
        },
      ],
    };
    return <Pie {...config} />;
  };

  return (
    <div className="main">
      <div className="container">
        <div className="container_top">
            <AdminInfo />
        </div>
        <Divider plain dashed>这里是你的基本数据</Divider>
        <div className="container_main">
          <div className='container_chart1'><UserPie /></div>
          <div className='container_chart2'><MissionPie /></div>
        </div>
      </div>
    </div>
  );
}
