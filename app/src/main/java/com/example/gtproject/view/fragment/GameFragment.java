package com.example.gtproject.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.gtproject.R;
import com.example.gtproject.view.view.WebViews;
import com.gsls.gt.GT;
import com.gsls.gt_databinding.annotation.GT_DataBinding;

/**
 * @模块类型： View(Fragment)
 * @描述： 这里用到了 公共的 ViewModel 类，就是那个今后会有很多很多代码的那个类， 可以看AllFragmentViewModel类为何代码变的越来越多，但依旧很好维护
 */
@GT_DataBinding(setLayout = "fragment_game", setBindingType = GT_DataBinding.Fragment)
@GT.Annotations.GT_AnnotationFragment(R.layout.fragment_game)
public class GameFragment extends GameFragmentBinding {// 继承 LoginFragmentBinding 类后，如果因为逻辑简单而不需要 ViewModel，可以不注册 ViewModel 功能

    private WebViews webViews;//使用 GT库封装的黑科技 WebView 参考文章：https://blog.csdn.net/qq_39799899/article/details/127265708?spm=1001.2014.3001.5501
    private String url = "https://blog.csdn.net/qq_39799899";//默认网页

    private String gameUrl = "https://www.17sucai.com/pins/47757.html";

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        GT.WindowUtils.immersionMode(activity);//设置沉浸式模式 (隐藏状态栏，去掉ActionBar，隐藏导航栏)
        webViews = new WebViews(activity, rl);//创建

//        webViews.loadAsset("game/index.html");//本地加载
        webViews.loadWeb(gameUrl);//网络加载 比较快，如果出现问题了就使用本地加载吧
    }

    @GT.Annotations.GT_Click({R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_exit:
                onDestroy();
                break;
        }
    }

    @Override
    protected boolean onBackPressed() {
        onDestroy();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.finish();//关闭这个 Activity
        System.exit(0);//杀掉进程
    }
}
