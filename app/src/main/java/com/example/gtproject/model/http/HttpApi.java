package com.example.gtproject.model.http;

import com.example.gtproject.MyApplication;
import com.example.gtproject.model.Model;
import com.example.gtproject.model.sql.SQLApi;
import com.example.gtproject.view.activity.MainActivity;
import com.gsls.gt.GT;

import java.util.HashMap;
import java.util.Map;

/**
 * @param <T>
 * @模块名称：网络请求Model 公共的Model (独立的一个单向调用model)
 * @描述： 下面代码仅仅是个 demo演示，可以按你喜欢的网络框架进行修改，但继承 SQLApi<T> 格式不能变
 */
public abstract class HttpApi<T> extends SQLApi<T> {

    //总域名路径
    public static String URL = "https://apis.map.qq.com/ws/";
    //获取当前地址信息接口 url
    private final static String getAddress = "geocoder/v1/";
    //是否显示请求加载框
    public static boolean isShow = true;

    /**
     * 获取当前地址信息
     *
     * @param location
     * @param key
     * @param get_poi
     * @param onLoadData
     */
    public void getAddress(String location, String key, int get_poi, GT.HttpUtil.OnLoadData onLoadData) {
        Map<String, Object> map = getMap(false);
        map.put("location", location);
        map.put("key", key);
        map.put("get_poi", get_poi);
        requests(getAddress, map, onLoadData);
    }

    /**
     * 快速获取 Map 请求参数
     *
     * @param isAddUserData
     * @return
     */
    public static Map<String, Object> getMap(boolean... isAddUserData) {
        HashMap<String, Object> map = new HashMap<>();
        if (isAddUserData.length == 0 || isAddUserData[0]) {

        }
        return map;
    }

    public static Map<String, Object> getMapHead() {
        HashMap<String, Object> map = new HashMap<>();
//        map.put("head", "头部信息");
//        map.put("userId", UserBean.userId);
        return map;
    }

    private static HttpApi httpApi;
    private static GT.HttpUtil httpUtil;
    private static String httpType = "POST";
    private static final int time = 60 * 3;//断网请求挂起时长

    public static GT.HttpUtil getInstance() {
        if (httpUtil == null) httpUtil = new GT.HttpUtil();
        return httpUtil;
    }

    public static HttpApi getHttpApi(String... httpTypes) {
        if (httpApi == null) {
            httpApi = new Model();
        }
        if (httpTypes.length != 0) {
            httpType = httpTypes[0];
        }
        return httpApi;
    }

    //用于开启关闭 加载条的
    protected void loadProgressBar(boolean isShow) {
        GT.EventBus.posts(isShow, MainActivity.class, "loadProgressBar");
    }

    /**
     * 默认使用的请求参数
     *
     * @param url
     * @param onLoadData
     */
    private void requests(String url, GT.HttpUtil.OnLoadData<T> onLoadData) {
        requests(url, getMap(), onLoadData);
    }

    /**
     * 核心请求方法 (这里一般定好型后就不会去修改了)
     *
     * @param url
     * @param map
     * @param onLoadData
     */
    private void requests(String url, Map<String, Object> map, GT.HttpUtil.OnLoadData<T> onLoadData) {
        if (map == null || map.size() == 0) return;
        getInstance();
        MyApplication.instance.execute(new Runnable() {
            @Override
            public void run() {
                loadProgressBar(true);
                //最最核心的网络请求
                httpUtil.httpRequest(URL + url, map, getMapHead(), new GT.HttpUtil.OnLoadData<T>() {
                    @Override
                    public void onSuccess(String response, T t) {
                        super.onSuccess(response, t);
                        try {
                            loadProgressBar(false);
//                            GT.logt("\n请求参数:" + URL + url + " mapHead:" + getMapHead() + " map:" + map + "\n请求结果:" + response);
                            if (onLoadData != null) {
                                Class<?> aClass = GT.AnnotationAssist.abstractClasstoGenericity(onLoadData.getClass());
                                t = (T) GT.JSON.fromJson2(response, aClass);
                                if (t != null)
                                    onLoadData.onSuccess(response, t);
                            }
                        } catch (Exception e) {
                            GT.errt("捕获到错误:" + e);
                            //TODO 注意该处的错误信息，并不一定是网络请求的异常，
                            // 可能是其他页面，在请求成功后的方法里，错误操作导致的异常，这里捕获防止app崩溃
                            MyApplication.toast("网络繁忙，请稍后再试...");
                            return;
                        }
                    }

                    @Override
                    public void onError(String response, T t) {
                        super.onError(response, t);
                        GT.logt("请求错误:" + response);
                        loadProgressBar(false);
                        onLoadData.onError(response, t);
                        MyApplication.toast(response);
                    }
                }, httpType);

                httpType = "POST";//默认切换成 POST
            }
        });
    }

}
