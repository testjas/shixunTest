import React, { useState } from "react";
import { message, Form, Input, Button } from "antd";
import less from "../less/Login.less";
import { useNavigate, Link } from "react-router-dom";
import logoImg from "../../assets/img/logo.png";
import { UserOutlined, LockOutlined } from "@ant-design/icons";
import { RegisterApi } from "../../request/api";
import ReactSimpleVerify from "react-simple-verify";
import "react-simple-verify/dist/react-simple-verify.css";

export default function Register() {
  const navigate = useNavigate(); //页面跳转的hook
  const [verify, setVerify] = useState(true);

  const success = () => {
    setVerify(false);
  };

  const onFinish = (values) => { 
    RegisterApi({
      username: values.username, //传递注册用户信息
      password: values.password,
    }).then((res) => {
      if (res.flag === true) {
        message.success(res.message); //登陆成功后的提示
        setTimeout(() => {
          navigate("/login");
        }, 1500); //注册成功1.5秒后跳转
      } else {
        message.error(res.message);
      }
    });
  };

  return (
    <div className="login">
      <div className="login_box">
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
                message: "请输入正确的用户名!",
                pattern:/^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/
              },
            ]}
          >
            <Input
              size="large"
              maxLength={5}
              prefix={<UserOutlined />}
              placeholder="请输入用户名"
            />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[
              {
                required: true,
                message: "请输入密码!",
              },
            ]}
          >
            <Input.Password
              maxLength={20}
              size="large"
              prefix={<LockOutlined />}
              placeholder="请输入密码"
            />
          </Form.Item>

          <Form.Item
            name="confirm"
            dependencies={["password"]}
            hasFeedback
            rules={[
              {
                required: true,
                message: "请输入密码!",
              },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue("password") === value) {
                    return Promise.resolve();
                  }

                  return Promise.reject(
                    new Error("两次输入的密码不同，请重试!")
                  );
                },
              }),
            ]}
          >
            <Input.Password
              maxLength={20}
              size="large"
              prefix={<LockOutlined />}
              placeholder="请再次确认密码"
            />
          </Form.Item>

          <Form.Item
            name="verify"
            className="verify"
            rules={[
              {
                required: verify,
                message: "请完成滑块验证",
              },
            ]}
          >
            <ReactSimpleVerify
              tips="请滑动验证"
              reset
              movedColor="#8ed3f5"
              ref="verify"
              success={() => success()}
            />
          </Form.Item>

          <Form.Item>
            <Link to="/login">已有账号?前去登录吧！</Link>
          </Form.Item>

          <Form.Item>
            <Button size="large" type="primary" htmlType="submit" block>
              立即注册
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
}
