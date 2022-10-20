package com.example.gtproject.view.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.example.gtproject.MyApplication;
import com.example.gtproject.R;
import com.example.gtproject.model.sql.table.UserBean;
import com.example.gtproject.view.activity.MainActivity;
import com.example.gtproject.viewModel.AllFragmentViewModel;
import com.gsls.gt.GT;
import com.gsls.gt_databinding.annotation.GT_DataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @模块类型： View(Fragment)
 * @描述： 这里用到了 公共的 ViewModel 类，就是那个今后会有很多很多代码的那个类， 可以看AllFragmentViewModel类为何代码变的越来越多，但依旧很好维护
 */
@GT_DataBinding(setLayout = "fragment_login", setBindingType = GT_DataBinding.Fragment)
@GT.Annotations.GT_AnnotationFragment(R.layout.fragment_login)
public class LoginFragment extends LoginFragmentBinding<AllFragmentViewModel<?>> {// 继承 LoginFragmentBinding 类后，会自动获取组件并支持 ViewModel

    //这条链接是一个 gif 动态图
    String imgUrl = "https://s1.chu0.com/src/img/gif/08/087ea3c232c344a98badfe5728d444f8.gif" +
            "?%7Cwatermark/3/image/aHR0cHM6Ly9zMS5haWdlaS5jb20vd2F0ZXJtYXJrLzYwLTEucG5nP2U9M" +
            "TczNTQ4ODAwMCZ0b2tlbj1QN1MyWHB6ZnoxMXZBa0FTTFRrZkhON0Z3LW9PWkJlY3FlSmF4eXBMOmZT" +
            "YlRIZ1Q2aGhxSnQ4bGczaWZ1dWlVWldNQT0=/dissolve/20/gravity/NorthWest/dx/8/dy/10/ws/" +
            "0.0/wst/0&e=1735488000&token=1srnZGLKZ0Aqlz6dk7yF4SkiYf4eP-YrEOdM1sob:hxo-avTski0ZxD-6NVCzGsjx5i4=";

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        //自动赋值最后一次登录成功的账号
        String user = MyApplication.sp.query("user", String.class);//使用GT库封装的 SharedPreferences存储
        if (user != null) {
            et_user.setText(user);
        }

        //GT库 加载网络动态图片
        GT.Glide.with(this)
                .asGif()//是否加载 gif，可以尝试注释掉这行,再看看有什么变化
                .load(imgUrl)//加载网络图
                .into(iv_gif);

        //添加要申请的权限
        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //添加 权限提示语句
        list.add(
                "读写权限申请" + MyApplication.SEPARATOR +   //标题
                        "是否允许“冒险岛”读写本地数据\n\n" + //内容
                        "允许后，此应用将缓存账号数据\n方便快速登录等相关操作" + MyApplication.SEPARATOR +
                        "读写权限未授予，将影响缓存功能正常使用！"//拒绝授权提示
        );
        GT.EventBus.getDefault().post(list, MainActivity.class, "addPermission");//传递要申请的权限到 这个类的这个方法处理
    }

    //单击事件
    @GT.Annotations.GT_Click({R.id.btn_login, R.id.btn_register, R.id.btn_queryAll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登录
                viewModel.login(getUserBean());
                break;
            case R.id.btn_register://注册
                viewModel.register(getUserBean());
                break;
            case R.id.btn_queryAll://测试用的
                List<UserBean> userBeans = viewModel.queryAll();
                GT.logt("------------ 当前所有账号 --------------");
                for (UserBean userBean : userBeans) {
                    GT.logt(userBean);
                }
                GT.logt("------------ 当前账号结尾 --------------");
                break;
        }
    }

    //该方法的数据由 公共的 ViewModel 提供反馈
    @Override
    public void onViewModeFeedback(Object... obj) {
        super.onViewModeFeedback(obj);
        switch (String.valueOf(obj[0])) {
            case "登录账号":
                UserBean userBean = (UserBean) obj[1];
                if (userBean == null) {
                    MyApplication.toast("账号或密码错误！");
                    break;
                }
                MyApplication.sp.save("user", et_user.getText().toString());//使用GT库封装的 SharedPreferences存储 保存登录账号
                MyApplication.toast("欢迎回来 " + userBean.getName() + (userBean.getSex().equals("男") ? " 殿下" : " 公主"));
                startFragment(MainFragment.class);//启动 主页面
                break;
            case "注册账号":
                boolean isSuccess = Boolean.parseBoolean(String.valueOf(obj[1]));
                if (!isSuccess) {
                    MyApplication.toast("该用户已被注册");
                    break;
                }
                break;
        }
    }

    //获取登录、注册数据，并(简单)检验数据是否合法
    public UserBean getUserBean() {
        String user = et_user.getText().toString();
        String pass = et_pass.getText().toString();
        if (user.length() == 0 || pass.length() == 0) {
            MyApplication.toast("账号密码不能为空！");
            return null;
        }
        return new UserBean(user, pass);
    }

    @Override
    protected boolean onBackPressed() {
        GT.logt("返回被监听");
        return true;
    }
}
