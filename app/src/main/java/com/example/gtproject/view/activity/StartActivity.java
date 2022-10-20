package com.example.gtproject.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gtproject.MyApplication;
import com.example.gtproject.R;
import com.example.gtproject.model.http.bean.JsonRootBean;
import com.gsls.gt.GT;

/**
 * @模块类型： 无模块，如果没有啥特比复杂的逻辑需求，可以不引用 ViewModel
 * @描述： 这里博主其实可以写更少代码的，但想顺便宣传一下 GT库里不止这一个好用的框架。
 */
public class StartActivity extends AppCompatActivity {

    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        GT.WindowUtils.immersionMode(this);//设置沉浸式模式 (隐藏状态栏，去掉ActionBar，隐藏导航栏)

        //获取组件
        tv_time = findViewById(R.id.tv_time);

        //使用 GT库线程池 来模拟广告时间功能
        MyApplication.instance.execute(() -> {
            //第一步：循环3秒，并每次循环都更新UI
            for (int i = 3; i >= 1; i--) {
                int finalI = i;
                GT.Thread.runAndroid(() -> tv_time.setText(finalI + "秒"));
                GT.Thread.sleep(1000);
            }
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        });

    }

    //广告页返回禁止
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}