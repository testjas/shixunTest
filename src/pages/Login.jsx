import React from 'react';
import { Form, Input, Button, message } from 'antd';
import less from './less/Login.less'
import { Link , useNavigate} from 'react-router-dom';
import logoImg from '../assets/img/logo.png';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { LoginApi } from '../request/api';

export default function Login() {
    const navigate = useNavigate();

    const onFinish = (values) => {
      LoginApi({
        username:values.username,
        password:values.password
      }).then(res=>{
        if(res.flag===true){
          message.success(res.message);//登陆成功后的提示
          localStorage.setItem("username",res.data.username);//存储后端登陆成功后返回的数据
          localStorage.setItem("auth",res.data.auth);
          setTimeout(()=>{navigate('/')},1500);//注册成功1.5秒后跳转
        }else{
          message.error(res.message);
        }
      })
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
