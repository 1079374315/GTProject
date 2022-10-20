package com.example.gtproject.model.sql;


import com.example.gtproject.model.sql.table.UserBean;
import com.example.gtproject.view.activity.MainActivity;
import com.gsls.gt.GT;

import java.util.List;

/**
 * @param <T>
 * @模块名称：数据库Model 公共的Model (独立的一个单向调用model)
 * @描述： 下面代码仅仅是个 demo演示，可以按你喜欢的网络框架进行修改，但继承 SQLApi<T> 格式不能变
 */
public abstract class SQLApi<T> extends GT.Frame.GT_Model<T> {//这里还可以再向后面继承 其他数据 model，如果有需要的话

    //登录账号
    public UserBean login(UserBean userBean) {
        UserBean query = MainActivity.hibernate
                .where(new String[]{"user", "pass"}, new String[]{userBean.getUser(), userBean.getPass()})//置顶式方式 查询条件
                .query(UserBean.class);//指定查询的表
        return query;
    }

    //注册账号
    public boolean register(UserBean userBean) {
        //查询当前注册的账号是否存在 数据库
        UserBean isExist = MainActivity.hibernate
                .where("user = ?", userBean.getUser())//自定义语句 查询条件
                .query(UserBean.class);

        if (isExist != null) return false; //如果存在那就直接返回 false 注册失败

        //该账号不存在，则进行正常的添加数据操作
        return MainActivity.hibernate
                .save(userBean) //保存该用户数据
                .isStatus();//并返回保存是否成功
    }

    //查询所有账号数据，测试专用
    public List<UserBean> queryAll() {
        return MainActivity.hibernate.queryAll(UserBean.class);
    }

}
