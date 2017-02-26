package tianhao.agoto.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import tianhao.agoto.Activity.MainActivity;

/**
 * Created by zhyan on 2017/2/14.
 */

public class XinoDeviceReceiver extends BroadcastReceiver {
    /*要接收的intent源*/
    static final String ACTION= "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent){
        if ( intent. getAction(). equals( ACTION)) {
            context. startService(new Intent( context, MainActivity.class ) ) ; //启动PS2服务

            Toast. makeText( context,"PS2 device monitor service has started!" , Toast. LENGTH_LONG). show( ) ;
        }
    }
}