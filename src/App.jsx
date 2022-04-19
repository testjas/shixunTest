import React from 'react'
import { Outlet } from 'react-router-dom';
import { Layout } from 'antd';
import Header from './components/header';
import Footer from './components/footer';

const { Sider, Content } = Layout;
export default function App() {
  return (
    <Layout id='app_page'>
      <Header/>
      <Layout>
        <Sider>Sider</Sider>
        <Content>
          <div>
            <Outlet />
          </div>
        </Content>
      </Layout>
      <Footer/>
    </Layout>

  )
}
