import {
    DrawerForm,
    ProFormTextArea
  } from "@ant-design/pro-components";
  import { Button, message, Input } from "antd";
  import React, { useEffect, useRef } from "react";
  import { useState } from "react";
  import {FinishTask } from "../../../request/api";
  
  export default function TaskFinshMenu(props) {
    let { mid, uid,description,info } = props.values;
    
    const waitTime = (time = 100) => {
      return new Promise((resolve) => {
        setTimeout(() => {
          resolve(true);
        }, time);
      });
    };

    const handleTask = (e) =>{
        FinishTask({
            mid: mid,
            uid: uid,
            task: e.missionInfo
          }).then((res) => {
            if(res.flag===true){
              message.success(res.message);
              props.setUpdate(1);
            }else{
              message.error(res.message);
            }
          });
    }
  
    const formRef = useRef();
    
    function isFinish(e){//判断是否做过
        if (
            e.isdelete === 2 ||
            e.timeStatus === "已结束" ||
            e.timeStatus === "未开始"  ||
            e.finish === 1
          ) {
            return 1;
          } else {
            return 0;
          }
    }
  
    return (
      <DrawerForm
        title="修改"
        formRef={formRef}
        trigger={
          <Button size="small" type="primary">
            完成任务
          </Button>
        }
        autoFocusFirstInput
        drawerProps={{
          destroyOnClose: true,
        }}
        submitTimeout={100}
        onFinish={async (e) => {
          await waitTime(100);
          handleTask(e)
          // 不返回不会关闭弹框
          return true;
        }}
      >
        <ProFormTextArea
            name="missionInfo"
            width="md"
            label="任务填写"
            tooltip="文字任务哦，快快提交填写吧~"
            placeholder={description}
            value={info}
            disabled={isFinish(props.values)}
          />
      </DrawerForm>
    );
  }
  