import Field from '@ant-design/pro-field';
import { Divider,Descriptions, Radio, Space, Switch } from "antd";
import React,{useState} from "react";
import Means from "../less/Means.less";
import moment from 'moment';
import AdminInfo from './AdminMeans/AdminInfo.jsx';

export default function AdminMeans() {
  return (
    <div className="main">
      <div className="container">
        <div className="container_top">
            <AdminInfo />
        </div>
        <Divider plain dashed>内容</Divider>
        <div className="container_main">
          <div>main</div>
        </div>
      </div>
    </div>
  );
}
