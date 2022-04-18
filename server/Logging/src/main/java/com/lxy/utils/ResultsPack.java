package com.lxy.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

//用来统一给前端传值的格式
@Data
@NoArgsConstructor
public class ResultsPack {
    private boolean flag;
    private Object data;
    private String message;

    public ResultsPack(boolean flag){
        this.flag=flag;
    }//传递是否查询成功等

    public ResultsPack(boolean flag,String message){//传递查询包含是否成功和数据等
        this.flag=flag;
        this.message=message;
    }

    public ResultsPack(boolean flag,Object data,String message){//传递成功失败时的消息提示
        this.flag=flag;
        this.data=data;
        this.message=message;
    }


}
