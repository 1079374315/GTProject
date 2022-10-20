package com.example.gtproject;

import android.content.Context;

import com.gsls.gt.GT;

/**
 * 这里为了方便演示 继承了 GT库里封装的 Application
 * 实际中，你可以用自己喜欢的
 */
public class MyApplication extends GT.GTApplication {

    public static final String SEPARATOR = "-GT-";//内部分隔符 随意定的

    @Override
    public void init(Context context) {
        super.init(context);

    }

    //吐司
    public static void toast(Object obj) {
        GT.Thread.runAndroid(() -> GT.toast(MyApplication.context, obj));
    }
}
