package com.android.commonlib.okhttp.bean;

import java.util.List;

public class TypeBean {

    private String code;
    private List<TypeBean> children;
    private String name;

    public TypeBean(String code, List<TypeBean> children, String name) {
        this.code = code;
        this.children = children;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TypeBean> getChildren() {
        return children;
    }

    public void setChildren(List<TypeBean> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return name;
    }
}
