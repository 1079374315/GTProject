package com.example.gtproject.viewModel;

import com.example.gtproject.MyApplication;
import com.example.gtproject.model.Model;
import com.example.gtproject.model.http.bean.JsonRootBean;
import com.example.gtproject.model.sql.table.UserBean;
import com.gsls.gt.GT;

import java.util.List;

/**
 * @param <T>
 * @模块类型: 公共的 ViewModel (所有Fragment的 ViewModel)
 * @描述： 为啥说是 所有Fragment 的 ViewModel 呢？
 * 因为 extends GT.GT_Fragment.DataBindingFragment 这行代码就指定了
 * 这个 ViewModel 只让 继承了 DataBindingFragment 的Fragment才能使用
 * TODO 注意,一般 公共的 ViewModel 是很少会有改动的，几乎不用改动，但一旦修改了公共的 ViewModel 那么引用了 ViewModel 里方法的 View层就会收到影响，而没有引用到的 View则不会有任何影响
 */
public class AllFragmentViewModel<T extends GT.GT_Fragment.DataBindingFragment> extends GT.Frame.GT_BindingViewModel<T, Model<?>> {

    //登录账号 （向数据库Model拿数据）
    public boolean login(UserBean userBean) {
        if (userBean == null) return false;
        //数据库查询 (推荐写法)
        MyApplication.instance.execute(() -> {//将数据库操作放在线程池里
            UserBean login = bindingModel.login(userBean);
            bindingView.onViewModeFeedback("登录账号", login);//按照 这个格式主动反馈数据给 View层 展示
        });
        return false;
    }


    //注册账号 （向数据库Model拿数据）
    public void register(UserBean userBean) {
        if (userBean == null) return;

        //随机数据,为了演示好看，随便介绍宣传一下GT库的 随机函数
        userBean.setAge(GT.GT_Random.getInt(0, 100));//使用GT库的随机函数，进行随机年龄
        userBean.setName(GT.GT_Random.getName(GT.GT_Random.getInt(2, 4)));//使用GT库的随机函数，进行随机2-4个字的名称
        userBean.setSex(GT.GT_Random.getInt(0, 100) % 2 == 0 ? "男" : "女");//使用GT库的随机函数，进行随机性别

        boolean register = bindingModel.register(userBean);
        if (register) {
            MyApplication.toast("恭喜您，玩家 " + userBean.getName() + " 注册成功！");
            return;
        }
        bindingView.onViewModeFeedback("注册账号", register);//按照 这个格式主动反馈数据给 View层 展示
    }


    //注册账号 （向数据库Model拿数据）
    public List<UserBean> queryAll() {
        return bindingModel.queryAll();
    }


    //TODO 获取地址 (向网络请求Model拿数据)
    public void getAddress(String location, String key, int get_poi) {
        bindingModel.getAddress(location, key, get_poi, new GT.HttpUtil.OnLoadData<JsonRootBean>() {
            @Override
            public void onSuccess(String response, JsonRootBean jsonRootBean) {
                super.onSuccess(response, jsonRootBean);
                JsonRootBean.Result result = jsonRootBean.getResult();
                if (response == null) return;
                bindingView.onViewModeFeedback("获取地址", result.getAddress());
            }
        });
    }


    /**
     * @描述: 下面是项目变大后添加越来越多的功能方法的情况，
     * 为啥这里代码变多了，最容易维护的还是 公共的 ViewModel，
     * 因为公共的 ViewModel层 的职能 非常专一，仅仅是 View层 找他要谁的数据，viewModel层就给它谁数据
     * 所有的方法均是独立的一个功能，而且都规定了固定的返回格式，
     * 当任意一个功能方法出现问题时，仅仅只需要修改那个单独的功能即可，所以维护起来非常方便
     * TODO 注意，ViewModel 里的方法尽量不要去相互调用，不便于维护！！！
     */

    //未来 其他方法
    public void function1(UserBean userBean) {
        bindingView.onViewModeFeedback("其他方法1", "数据值1");//这是 ViewModel 返回给View UI层 的固定格式，第一个是数据类型，后面的是具体的数据值
    }

    //未来 其他方法
    public void function2(UserBean userBean) {
        bindingView.onViewModeFeedback("其他方法2", "数据值1", "数据值2");
    }

    //未来 其他方法
    public void function3(UserBean userBean) {
        bindingView.onViewModeFeedback("其他方法3", "数据值1", 222);
    }

    //未来 其他方法
    public void function4(UserBean userBean) {
        bindingView.onViewModeFeedback("其他方法4", "数据值1", 222, true);
    }

    //未来 其他方法
    public void function5(UserBean userBean) {
        bindingView.onViewModeFeedback("其他方法5", "");
    }

}
