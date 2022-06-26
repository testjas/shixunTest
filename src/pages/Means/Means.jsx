import { Divider } from "antd";
import MeansLess from "../less/Means.less";
import AdminInfo from './AdminMeans/AdminInfo.jsx';
import React, { useState, useEffect } from 'react';
import ReactDOM from 'react-dom';
import { Pie } from '@ant-design/plots';
import "@ant-design/flowchart/dist/index.css";
import {GetUserData} from '../../request/api'

export default function Means() {

  const [yes,setYes]=useState();
  const [no,setNo]=useState();
  const [task,setTask]=useState();
  const [sign,setSign]=useState();

  const getData=()=>{
    GetUserData({
      username:localStorage.getItem("username")
    }).then((res)=>{
      let {yes,sign,task,no}=res.data;
      setYes(yes)
      setSign(sign);
      setTask(task);
      setNo(no)
    })
  }

  useEffect(()=>{
    getData();
  },[])

  const UserPie = () => {//饼图数据
    const data = [
      {
        type: '已完成',
        value: yes,
      },
      {
        type: '未完成',
        value: no,
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
        <Divider plain dashed>下面是你的可视化数据哦</Divider>
        <div className="container_main">
          <div className='container_chart1'><UserPie /></div>
          <div className='container_chart2'><MissionPie /></div>
        </div>
      </div>
    </div>
  );
}
