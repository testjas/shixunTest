import React from 'react'
import { Form, Input, Button, Checkbox } from 'antd';
import less from './less/Login.less'
import { Link } from 'react-router-dom';
import logoImg from '../assets/img/logo.png'
import { UserOutlined, LockOutlined } from '@ant-design/icons';

export default function Login() {
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
                message: 'Please input your username!',
              },
            ]}
          >
            <Input size='large' prefix={<UserOutlined/>} placeholder="请输入用户名"/>
          </Form.Item>

          <Form.Item
            name="password"
            rules={[
              {
                required: true,
                message: 'Please input your password!',
              },
            ]}
          >
            <Input.Password size='large' prefix={<LockOutlined/>} placeholder="请输入密码"/>
          </Form.Item>

          <Form.Item>
            <Link to="/register">还没账号？去注册吧！</Link>
          </Form.Item>

          <Form.Item
          >
            <Button size='large' type="primary" htmlType="submit" block>
              登陆
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  )
    
    
}
