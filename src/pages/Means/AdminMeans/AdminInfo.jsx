import React from 'react'
import { InputNumber } from 'antd';
import means from '../../less/Means.less'
export default function Admininfo() {
  return (
    <div className='info'>
      <div className='container'>当前用户总数为:<InputNumber className='input' disabled value={10}/></div>
      <div className='container'>当前派发员总数为:<InputNumber className='input' disabled value={10}/></div>
    </div>
  )
}
