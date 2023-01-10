package com.example.gtproject.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.gtproject.R;
import com.example.gtproject.viewModel.ViewModelAll;
import com.gsls.gt.GT;
import com.gsls.gt_databinding.annotation.GT_DataBinding;

/**
 * @模块类型： View(Fragment)
 * @描述： 这里用到了 公共的 ViewModel 类，就是那个今后会有很多很多代码的那个类， 可以看AllFragmentViewModel类为何代码变的越来越多，但依旧很好维护
 */
@GT_DataBinding(setLayout = "fragment_main", setBindingType = GT_DataBinding.Fragment)
@GT.Annotations.GT_AnnotationFragment(R.layout.fragment_main)
public class MainFragment extends MainFragmentBinding<ViewModelAll<?>> {// 继承 LoginFragmentBinding 类后，会自动获取组件并支持 ViewModel

    //开始游戏按钮
    private String imgUrl = "https://s1.chu0.com/src/img/png/60/606dd756a10d402ebfade10fad163eca.png" +
            "?imageMogr2/auto-orient/thumbnail/!132x49r/gravity/Center/crop/132x49/quality/85/" +
            "&e=1735488000&token=1srnZGLKZ0Aqlz6dk7yF4SkiYf4eP-YrEOdM1sob:0vYDFeqytGCuRZx3WkhN7keK7Zg=";

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        GT.Glide.with(this).load(R.drawable.ic_main_start).into(iv);//加载 项目资源 图片
        GT.Glide.with(this).load(imgUrl).into(iv_start);//加载 网络 图片

        //调用 获取地址 网络请求接口
        viewModel.getAddress("22.5948,114.3069163","J6HBZ-N3K33-D2B3V-YH7I4-37AVE-NJFMT",1);

        //使用原生的单击事件
        iv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startFragment(GameFragment.class);//跳转到 游戏开始界面
            }
        });

        iv_start.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewModeFeedback(Object... obj) {
        super.onViewModeFeedback(obj);
        switch (String.valueOf(obj[0])) {
            case "获取地址":
                String address = String.valueOf(obj[1]);
                GT.logt("address:" + address);
                tv_Address.setText("岛区:" + address);
                iv_start.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected boolean onBackPressed() {
        GT.logt("返回被监听");
        return true;
    }

}
