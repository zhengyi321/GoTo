package tianhao.agoto.Application;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 *
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        /*极光推送*/
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
       /*极光推送*/

        /*友盟第三方登录*/
        ShareSDK.initSDK(this);
        /*友盟第三方登录*/
    }



    public static   MyApplication getInstance() {
        return instance;
    }

    public static   MyApplication getContext() {
        instance = getContext();
        return instance;
    }

}

