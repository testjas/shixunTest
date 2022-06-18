import React,{useEffect,useState} from 'react'
import logoImg from '../assets/img/logo.png';
import adminIcon from '../assets/img/adminIcon.svg';
import userIcon from '../assets/img/userIcon.svg';
import { Menu, Dropdown,message } from 'antd';
import { CaretDownOutlined } from '@ant-design/icons';
import { Navigate, useNavigate } from 'react-router-dom';

export default function Header() {
    const navigate=useNavigate();//设置跳转
    const [uicon,setUicon]=useState(userIcon);//设置用户头像
    const [username,setUsername]=useState("未登录");//设置用户名称
    const [chooseMenu,setChooseMenu]=useState();
    //设置登陆和未登录时的弹出菜单,因为useState先生成而定义的菜单是后生成的所有括号里不传参数

    //模拟mounted效果
    useEffect(()=>{
        let un=localStorage.getItem("username");{/*后端返回的数据存储在localStorage中，用getItem取出 */}
        let auth=localStorage.getItem("auth");
        // setUsername(un+"/管理员账户" || "未登录");//相当于if的效果
        if(un){
            if(auth==="admin"){
                setUicon(adminIcon);
                setUsername(un+"/管理员账户");
            }else if(auth==="user"){
                setUsername(un);
            }else{
                setUsername(un+"/任务派发员")
            }
            setChooseMenu(LoginMenu);//设置弹出菜单为登陆时的菜单
        }else{
            setChooseMenu(LogoutMenu);//设置弹出菜单为未登录时的菜单
        };
        
    },[])

    //退出登录事件
    const logOut=()=>{
        //退出登录后清除相关信息
        localStorage.removeItem('username');
        localStorage.removeItem('auth');
        message.success("操作成功");
        setTimeout(()=>{navigate('/login');},1000);//在1秒后跳转到登陆界面
    }

    const editInfo=()=>{
        message.success("操作成功");
        setTimeout(()=>{navigate('/means');},500);//在0.5秒后跳转到登陆界面
    }

    const LoginMenu = (//有登陆时的菜单
        <Menu>{/*弹出的菜单*/}
            <Menu.Item key={1} onClick={editInfo}>修改资料</Menu.Item>
            <Menu.Divider/>{/*分隔用的分隔线*/}
            <Menu.Item key={2} onClick={logOut}>退出登录</Menu.Item>
        </Menu>
    );
    const LogoutMenu = (//没登录时的菜单
        <Menu>{/*弹出的菜单*/}
            <Menu.Item key={1} onClick={logOut}>去登陆/注册</Menu.Item>
        </Menu>
    );

    return (
        <header>
            <img src={logoImg} alt="欢迎来到后台网站" className='logo' />
            <div className="right">
                
                <Dropdown overlay={chooseMenu} className="UserData">
                    <a className="ant-dropdown-link" onClick={e => e.preventDefault()}>
                    <img src={uicon} className="UserIcon"/>
                        <span>{username}</span> 
                        <CaretDownOutlined />
                    </a>
                </Dropdown>
            </div>
        </header>
    )
}
