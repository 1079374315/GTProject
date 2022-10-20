package com.example.gtproject.model.sql.table;

import com.gsls.gt.GT;

//用户信息表
@GT.Hibernate.GT_Bean
public class UserBean {

    @GT.Hibernate.GT_Key        //自增ID
    private int userId;         //用户 ID
    private String user;        //用户账号
    private String pass;        //用户密码
    private String name;        //用户名称
    private int age;            //用户年龄
    private String sex;         //用户性别

    @GT.Hibernate.GT_Property(setNotInit = true)//设置该字段不进行映射到表中
    private String headImg;     //头像

    //必须提供的空构造方法
    public UserBean() {
        super();
    }

    public UserBean(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public UserBean(String user, String pass, String name, int age, String sex, String headImg) {
        this.user = user;
        this.pass = pass;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.headImg = headImg;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
