import { Button, Result } from 'antd';
import React from 'react';

export default function P404() {
  return (
    <Result
    status="404"
    title="404"
    subTitle="您访问的网页不存在或者没有权限访问,请重试~"

  />
  )
}
