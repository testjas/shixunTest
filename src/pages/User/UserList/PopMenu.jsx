import {
  DrawerForm,
  ProForm,
  ProFormDateRangePicker,
  ProFormSelect,
  ProFormText,
} from "@ant-design/pro-components";
import { Button, message } from "antd";
import React, { useRef } from "react";

export default function PopMenu(props) {
  const waitTime = (time = 100) => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(true);
        console.log(props.values);
      }, time);
    });
  };

  const formRef = useRef();

  return (
    <DrawerForm
      title="修改"
      formRef={formRef}
      trigger={<Button type="primary">修改</Button>}
      autoFocusFirstInput
      drawerProps={{
        destroyOnClose: true,
      }}
      submitTimeout={1000}
      onFinish={async () => {
        await waitTime(1000);
        message.success("修改成功");
        // 不返回不会关闭弹框
        return true;
      }}
    >
      <ProForm.Group>
        <ProFormText
          name="name"
          width="md"
          label="用户名称"
          tooltip="最长为 5 位"
          placeholder="请输入名称"
        />
        <ProFormText
          width="md"
          name="company"
          label="用户的归属"
          placeholder="请输入归属"
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormText
          width="md"
          name="contract"
          label="合同名称"
          placeholder="请输入名称"
        />
        <ProFormDateRangePicker name="contractTime" label="合同生效时间" />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormSelect
          options={[
            {
              value: "chapter",
              label: "盖章后生效",
            },
          ]}
          width="xs"
          name="useMode"
          label="合同约定生效方式"
        />
        <ProFormSelect
          width="xs"
          options={[
            {
              value: "time",
              label: "履行完终止",
            },
          ]}
          name="unusedMode"
          label="合同约定失效效方式"
        />
      </ProForm.Group>
      <ProFormText width="sm" name="id" label="主合同编号" />
      <ProFormText
        name="project"
        disabled
        label="项目名称"
        initialValue="xxxx项目"
      />
      <ProFormText
        width="xs"
        name="mangerName"
        disabled
        label="商务经理"
        initialValue="启途"
      />
    </DrawerForm>
  );
}
