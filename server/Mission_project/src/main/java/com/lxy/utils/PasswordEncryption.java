package com.lxy.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncryption {
    private String ps;

    public String getPs() {
        return ps;
    }

    public void setPs(String ps,int id) {//数字加密,密码加上id组成新的密码
        String md5= DigestUtils.md5Hex(ps+id);
        this.ps=md5;
    }

    public String decipheringPs(String ps){//md5解密

        return null;
    }

    public Boolean verifyPs(String ps,int id,String md5){//校验密码前端传来的密码是否正确
        String md5Text=DigestUtils.md5Hex(ps+id);
        if (md5Text.equals(md5)){
            return true;
        }
        return false;
    }

}
