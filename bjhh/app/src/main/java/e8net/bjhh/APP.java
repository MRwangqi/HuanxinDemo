package e8net.bjhh;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by Administrator on 2016/9/7.
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        EMOptions options = new EMOptions();
//// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//初始化
//        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源

        EaseUI.getInstance().init(this, null);
       // EMClient.getInstance().setDebugMode(true);
    }
}
