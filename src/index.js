// import ReactDOM from "react-dom";
import Router from './router'
import "./assets/base.less"
import {createRoot} from 'react-dom/client'

const container=document.getElementById('root');
const root =createRoot(container);
root.render(<Router/>)
// ReactDOM.render(
//   <Router/>
//   ,document.getElementById('root'))