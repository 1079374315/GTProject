package com.example.gtproject.viewModel;

import com.example.gtproject.MyApplication;
import com.example.gtproject.R;
import com.example.gtproject.model.Model;
import com.example.gtproject.view.activity.MainActivity;
import com.gsls.gt.GT;

/**
 * @模块类型： ViewModel
 * @描述：
 * 这里展示的含义是：ViewModel不仅仅只能规定建造一个,
 * 你喜欢的话可以为每一个 View 单独配置一个专门服务于该 View的 ViewModel，
 * 灵活运用即可
 */
public class MainViewModel extends GT.Frame.GT_BindingViewModel<MainActivity, Model<?>> {

    private String title = "权限申请";
    private String describe = "授予相应权限，不授予将影响应用正常使用！";
    private String unauthorized = "权限未全部授予，将影响部分功能正常使用！";

    /**
     * 权限申请
     * @param strArray  要申请的权限
     * @param str       权限申请的提示文字
     */
    public void permissionRequest(String[] strArray, String str) {

        String[] hts = str.split(MyApplication.SEPARATOR);
        switch (hts.length) {
            case 1:
                title = hts[0];
                break;
            case 2:
                title = hts[0];
                describe = hts[1];
                break;
            case 3:
                title = hts[0];
                describe = hts[1];
                unauthorized = hts[2];
                break;
        }

        //这里展示的是 GT库的 权限动态申请框架
        GT.AppAuthorityManagement.Permission.init(bindingView, strArray).permissions(new GT.AppAuthorityManagement.Permission.OnPermissionListener() {
            @Override
            public void onExplainRequestReason(GT.AppAuthorityManagement.Permission.PermissionDescription onPDListener) {

                //简易对话框，后期可替换成自定义对话框
                new GT.GT_Dialog.GT_AlertDialog(bindingView).dialogTwoButton(
                        R.mipmap.ic_logo,
                        title,
                        describe,
                        false,
                        "允许",
                        (dialog, which) -> {
                            onPDListener.setAcceptAdvice(true);//核心，设置同意授权
                        }, "禁止", (dialog, which) -> {
                            GT.toast(unauthorized);
                            MyApplication.instance.execute(() -> {
                                GT.Thread.sleep(2000);
                                onPDListener.setAcceptAdvice(false);//核心，设置拒绝授权
                                bindingView.finish();
                            });
                        }
                ).show();
            }

            @Override
            public boolean onForwardToSettings() {
                //特殊权限特殊处理，如：需要进入 系统设置 中或 应用信息中的代码可自定义填写
                return true;//默认是false 一定有改过来设置为 true
            }

            @Override
            public void request(boolean allGranted, String[] grantedList, String[] deniedList, String message) {
                GT.logt("allGranted:" + allGranted);
                GT.log("message", message);
                if (allGranted) {
                    //全部授权
                    GT.log("全部授权");
                } else {
                    //未全部授权
                    GT.log("grantedList:" + grantedList.length);
                    GT.log("deniedList:" + deniedList.length);
                    GT.toast(unauthorized);
                    MyApplication.instance.execute(() -> {
                        GT.Thread.sleep(2000);
                        bindingView.finish();
                    });
                }
            }
        });
    }

}
