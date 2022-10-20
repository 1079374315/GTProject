package com.example.gtproject.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.gtproject.MyApplication;
import com.example.gtproject.R;
import com.example.gtproject.view.fragment.GameFragment;
import com.example.gtproject.view.fragment.LoginFragment;
import com.example.gtproject.viewModel.MainViewModel;
import com.gsls.gt.GT;
import com.gsls.gt_databinding.annotation.GT_DataBinding;

import java.util.List;

/**
 * @模块类型： View层 (属于Activity)
 * @描述： 下面所有使用的 GT库 其他框架，都是可以替换的
 */
@GT_DataBinding(setLayout = "activity_main", setBindingType = GT_DataBinding.Activity)
@GT.Annotations.GT_AnnotationActivity(R.layout.activity_main)
public class MainActivity extends MainActivityBinding<MainViewModel> {

    //这里使用的是 GT库 的数据库框架(实际中你可以替换为你自己喜欢的数据库框架)
    @GT.Hibernate.Build(setSqlName = "demo_db", setSqlVersion = 1)//设置数据库名称 与 数据库版本号
    public static GT.Hibernate hibernate;

    //这里使用的是 GT库 Fragment 管理框架，模拟Activity支持 四种启动模式，采用栈管理的
    @GT.GT_Fragment.Build(setLayoutMain = R.id.fl)
    public GT.GT_Fragment gt_fragment;

    private ProgressDialog progressDialog;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        GT.WindowUtils.immersionMode(this);//设置沉浸式模式 (隐藏状态栏，去掉ActionBar，隐藏导航栏)
        //已被上面注解构建好了的，可直接使用(注意：其实可以直接去掉 gt_fragment. 代码的，直接用 startFragment(LoginFragment.class); 运行，为了更好的理解才这么写)
        gt_fragment.startFragment(LoginFragment.class);//直接启动 登录页面
    }

    //权限申请
    @GT.EventBus.Subscribe(threadMode = GT.EventBus.ThreadMode.MAIN)
    public void addPermission(List<String> list) {
        if (list == null || list.size() == 0) return;
        GT.logt("本次申请了" + list.size() + "个权限");
        String[] strArray = new String[list.size() - 1];
        for (int i = 0; i < list.size() - 1; i++) {
            strArray[i] = list.get(i);
        }
        viewModel.permissionRequest(strArray, list.get(list.size() - 1));
    }

    //显示全局加载条
    @GT.EventBus.Subscribe(threadMode = GT.EventBus.ThreadMode.MAIN) //这里使用了GT库的 GT.EventBus,专门用来传递数据的一个框架，支持跨进程传递
    public void loadProgressBar(boolean isShow) {
        if (!MyApplication.isFrontDesk && isShow) {//判断如果不在前台，那就不再操作加载条
            return;
        }
        if (progressDialog != null) {
            if (isShow) {
                progressDialog.show();
            } else {
                progressDialog.setMessage("玩命加载中，请稍后...");
                progressDialog.dismiss();
            }
        } else {
            progressDialog = new GT.GT_Dialog.GT_AlertDialog(this).progressDialog(R.mipmap.ic_logo, "GT库", "玩命加载中，请稍后...", false);
        }
    }


}