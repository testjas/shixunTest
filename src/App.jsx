import React from "react";
import { Outlet } from "react-router-dom";
import { Layout } from "antd";
import Header from "./components/header";
import Footer from "./components/footer";
import Aside from "./components/Aside";
import Bread from "./components/Bread";
import { useState } from "react";

const { Content } = Layout;
export default function App() {
  return (
    <Layout id="app_page">
      <Header />
      <div className="container">
        <Aside />
        <div className="scroll_box">
          <div className="container_box">
            <Bread />
            <div className="container_content">
              <Outlet />
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </Layout>
  );
}
