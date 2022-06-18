import { ModalForm, ProFormText } from '@ant-design/pro-components';
import { PlusOutlined } from '@ant-design/icons';
import { Button, message } from 'antd';
import React from 'react';

export default function AddUser() {
    const waitTime = (time = 100) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve(true);
            }, time);
        });
    };

  return (
    <ModalForm title="添加用户" trigger={<Button type="primary"><PlusOutlined />添加新用户</Button>} submitter={{
        searchConfig: {
            submitText: '确认',
            resetText: '取消',
        },
    }} onFinish={async (values) => {
        await waitTime(2000);
        console.log(values);
        message.success('提交成功');
        return true;
    }}>
    <ProFormText width="md" name="name" label="签约客户名称" tooltip="最长为 24 位" placeholder="请输入名称"/>

    <ProFormText width="md" name="company" label="我方公司名称" placeholder="请输入名称"/>
  </ModalForm>
  );
}
