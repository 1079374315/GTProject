package com.example.gtproject.model;

import com.example.gtproject.model.http.HttpApi;


/**
 * @模块类型：model层 公共的类 (独立的一个单向调用model)
 * @描述： 这个类是专门给 ViewModel 单向调用的
 * 一般这个类可以用来做 其他统一的业务逻辑操作
 * @param <T>
 */
public class Model<T> extends HttpApi<T> {
    public Model() {

    }

}
