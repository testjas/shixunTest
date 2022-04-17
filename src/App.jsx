import React from 'react'
import { Button } from 'antd';
import { Outlet } from 'react-router-dom';

export default function App() {
  return (
    <div>
      <Button type="primary">Primary Button</Button>你是我的哥
      <Outlet/>
    </div> 
  )
}
