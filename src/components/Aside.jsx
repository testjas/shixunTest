import React,{useEffect,useState} from 'react';
import { Menu } from 'antd';
import { IdcardOutlined, FormOutlined } from '@ant-design/icons';
import { useNavigate,useLocation } from 'react-router-dom';


export default function Aside() {
    const navigate=useNavigate();//设置跳转
    const [chooseMenu,setChooseMenu]=useState();//设置不同权限时能看到的菜单
    const location=useLocation();
    const [defaultKey,setDefaultKey]=useState();//设置默认跳转页

    //加空数组[]是为了模仿Mounted
    useEffect(()=>{
        let path=location.pathname;//设置跳转页
        let key=path.split('/')[1];
        setDefaultKey(key);
 
        let auth=localStorage.getItem("auth");
        if(auth==="admin"){
            setChooseMenu(adminMenu);//设置为管理员时的侧菜单
        }else if(auth==="user"){
            setChooseMenu(userMenu);//设置为用户时的侧菜单
        }else if(auth==='dispatcher'){
            setChooseMenu(dispatcherMenu);//设置为任务派发员时的侧菜单
        }else{
            setChooseMenu(noLoginMenu);
        };
        
    },[])

    const handleClick = e => {
        navigate('/'+e.key);//跳转页面
    };

    const noLoginMenu = (//管理员可操作的侧菜单
        <Menu
            onClick={handleClick}
            style={{ width: 250 }}
            SelectedKeys={[defaultKey]}
            mode="inline"
            theme='dark'
            className="aside"
            
        >
            <Menu.Item key="nologin"><IdcardOutlined /> 基本信息</Menu.Item>
        </Menu>
    );


    const adminMenu = (//管理员可操作的侧菜单
        <Menu
            onClick={handleClick}
            style={{ width: 250 }}
            SelectedKeys={[defaultKey]}
            mode="inline"
            theme='dark'
            className="aside"
        >
            <Menu.Item key="adminmeans"><IdcardOutlined /> 基本信息</Menu.Item>
            <Menu.Item key="adminlist"><FormOutlined /> 成员编辑</Menu.Item>
            <Menu.Item key="adminmission"><FormOutlined /> 任务管理</Menu.Item>
        </Menu>
    );

    const userMenu=(//用户可操作的侧菜单
        <Menu
            onClick={handleClick}
            style={{ width: 250 }}
            defaultSelectedKeys={['1']}//设置默认选中
            mode="inline"
            theme='dark'
            className="aside"
        >
            <Menu.Item key="means"><IdcardOutlined /> 基本信息</Menu.Item>
            <Menu.Item selecta key="userlist"><FormOutlined /> 任务信息</Menu.Item>
            <Menu.Item key="usermission"><FormOutlined /> 任务选择</Menu.Item>
        </Menu>
    )

    const dispatcherMenu = (//任务派发员可操作的侧菜单
        <Menu
            onClick={handleClick}
            style={{ width: 250 }}
            SelectedKeys={[defaultKey]}
            mode="inline"
            theme='dark'
            className="aside"
        >
            <Menu.Item key="dispatchermeans"><IdcardOutlined /> 基本信息</Menu.Item>
            <Menu.Item key="dispatcherlist"><FormOutlined /> 成员管理</Menu.Item>
            <Menu.Item key="dispatchermission"><FormOutlined /> 任务管理</Menu.Item>
        </Menu>
    );

    return (
        <>
            {chooseMenu}
        </>    
    )
}
