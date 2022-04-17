import React from 'react'
import { Form, Input, Button, Checkbox } from 'antd';
import less from './less/Login.less'
import { Link } from 'react-router-dom';
import logoImg from '../assets/img/logo.png'
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { RegisterApi } from '../request/api';

export default function register() {
    const onFinish = (values) => {
      console.log('Success:', values);
    };
  
    const onFinishFailed = (errorInfo) => {
      console.log('Failed:', errorInfo);
    };
  return (
    <div className="login">
      <div className='login_box'>
        <img src={logoImg} alt="如你所视这是个Logo" />
        <Form
          name="basic"
          initialValues={{
            remember: true,
          }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
          autoComplete="off"
        >
          <Form.Item
            name="username"
            rules={[
              {
                required: true,
                message: '请输入用户名!',
              },
            ]}
          >
            <Input size='large' maxLength={5} prefix={<UserOutlined/>} placeholder="请输入用户名"/>
          </Form.Item>

          <Form.Item
            name="password"
            rules={[
              {
                required: true,
                message: '请输入密码!',
              },
            ]}
          >
            <Input.Password maxLength={20} size='large' prefix={<LockOutlined/>} placeholder="请输入密码"/>
          </Form.Item>

          <Form.Item
        name="confirm"
        dependencies={['password']}
        hasFeedback
        rules={[
          {
            required: true,
            message: '请输入密码!',
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue('password') === value) {
                return Promise.resolve();
              }

              return Promise.reject(new Error('两次输入的密码不同，请重试!'));
            },
          }),
        ]}
        >
        <Input.Password maxLength={20} size='large' prefix={<LockOutlined/>} placeholder="请再次确认密码" />
        </Form.Item>

          <Form.Item>
            <Link to="/login">已有账号?前去登录吧！</Link>
          </Form.Item>

          <Form.Item
          >
            <Button size='large' type="primary" htmlType="submit" block>
              立即注册
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  )
    
    
}
