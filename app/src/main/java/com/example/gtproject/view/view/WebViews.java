package com.example.gtproject.view.view;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.gtproject.view.activity.MainActivity;
import com.gsls.gt.GT;

public class WebViews extends GT.GT_WebView.AnnotationWebView {

    /**
     * 构建 JS Web
     *
     * @param context   上下文
     * @param viewGroup 装 Web 容器
     */
    public WebViews(Context context, ViewGroup viewGroup) {
        super(context, viewGroup);
    }

    @Override
    public void onFeedbackHtmlCode(int progress, String code) {
        super.onFeedbackHtmlCode(progress, code);//code 是 html源码
//        GT.logt("progress:" + progress);//网页加载进度条
        if (!"https://www.17sucai.com/pins/47757.html".equals(url)) return;//过滤掉游戏入口html
        if (progress == 100) {//待网页加载完毕后
//            GT.logt("code:" + code);

            //解析html
            GT.Observable.getDefault().execute(new GT.Observable.RunJavaR<String>() {
                @Override
                public String run() {
                    String codeHtmlKey1 = "https://www.17sucai.com/pins/demo-show";
                    int i1 = code.indexOf(codeHtmlKey1);
                    int i2 = code.indexOf("><i class=", i1);
                    if (i1 != -1 && i2 > i1) {
                        String url = code.substring(i1, i2 - 1);
                        return url;
                    }
                    return null;
                }
            }).execute(new GT.Observable.RunAndroidV<String>() {
                @Override
                public void run(String url) {
                    if (url == null) {//如果网址源码有变，出现异常，那就加载本地游戏
                        loadAsset("game/index.html");//加载本地游戏
                    } else {//否则还是加载 网页游戏
                        loadWeb(url);//加载网页游戏
                        GT.Thread.runJava(() -> {
                            GT.Thread.sleep(1000);
                            GT.Thread.runAndroid(() -> {

                                GT.EventBus.getDefault().post(false, MainActivity.class, "loadProgressBar");//隐藏加载条
                                //因为最初始的 网页上是有遐思的，上面有黑色边框影响游戏体验，在这里我将使用 GT库封装的 黑科技来解决掉这些遐思
                                beginTransaction()//开始事务
                                        .findViewById("switcher")//获取 html网页里的 黑色边框(id为 switcher 的元素)
                                        .setHide()//设置隐藏属性
                                        .commit();//提交事务执行
                            });
                        });
                    }
                }
            });

        }
    }

    @Override
    protected void initView(Context context, WebView webView) {
        super.initView(context, webView);
        GT.logt("与H5通讯的路径:" + jsToAndroidName);
        GT.EventBus.getDefault().post(true, MainActivity.class, "loadProgressBar");//显示加载条
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GT.logt("Web已被销毁");
    }
}
