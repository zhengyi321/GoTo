package tianhao.agoto.Application;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 *
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }



    public static   MyApplication getInstance() {
        return instance;
    }

    public static   MyApplication getContext() {
        instance = getContext();
        return instance;
    }

}

