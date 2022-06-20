import {
  ProForm,
  ModalForm,
  ProFormText,
  ProFormSelect,
  ProFormDigit,
  ProFormDateTimeRangePicker,
  ProFormTextArea,
} from "@ant-design/pro-components";
import { Button, message } from "antd";
import React from "react";
import { AddDisMission } from "../../../request/api";
import { PlusOutlined } from "@ant-design/icons";
import moment from "moment";

export default function AddUser(props) {
  const waitTime = (time = 100) => {
    //设置等待时间
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(true);
      }, time);
    });
  };

  const addMission = (values) => {
    AddDisMission({
      missionName: values.mname, //传递注册用户信息
      description: values.description,
      userNum: values.unum,
      startTime: values.missionTime[0],
      endTime: values.missionTime[1],
      missionType: values.missionType,
      createTime: moment(new Date()).utcOffset(8).format("YYYY-MM-DD HH:mm:ss"),
      belong:localStorage.getItem("username")
    }).then((res) => {
      console.log(res);
      if (res.flag === true) {
        message.success(res.message); //登陆成功后的提示
        props.setUp(1); //插入用户后实时更新父组件
      } else {
        message.error(res.message);
      }
    });
  };

  return (
    <ModalForm
      title="添加用户"
      trigger={
        <Button type="primary">
          <PlusOutlined />
          添加新任务
        </Button>
      }
      submitter={{
        searchConfig: {
          submitText: "确认",
          resetText: "取消",
        },
      }}
      onFinish={async (values) => {
        await waitTime(1500);
        console.log(values);
        addMission(values);
        message.success("提交成功");
        return true;
      }}
    >
      <ProForm.Group>
        <ProFormText
          width="md"
          name="mname"
          label="任务名称"
          tooltip="最长为20位"
          placeholder="请输入任务名称"
          rules={[
            {
              required: true,
              max: 20,
            },
          ]}
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormDigit
          width="md"
          name="unum"
          label="可参与人数"
          placeholder="人数"
          min={1}
          max={999}
        />
        <ProFormSelect
          request={async () => [
            {
              value: "作业",
              label: "作业",
            },
            {
              value: "签到",
              label: "签到",
            },
          ]}
          width="md"
          name="missionType"
          label="任务类型"
          rules={[
            {
              required: true,
            },
          ]}
        />
      </ProForm.Group>

      <ProForm.Group>
        <ProFormDateTimeRangePicker
          width="md"
          name="missionTime"
          label="任务时间范围"
          rules={[
            {
              required: true,
            },
          ]}
        />
      </ProForm.Group>
      <ProFormTextArea
        name="description"
        width="md"
        label="任务描述"
        tooltip="最少输入一位，最多255个字"
        rules={[
          {
            required: true,
            min: 1,
            max: 255,
          },
        ]}
      />
    </ModalForm>
  );
}
