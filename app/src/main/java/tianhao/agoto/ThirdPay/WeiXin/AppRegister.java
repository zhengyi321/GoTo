package tianhao.agoto.ThirdPay.WeiXin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
/*
https://github.com/Javen205/JPay
 */
public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
		Constants constants = new Constants();
		// 将该app注册到微信
		api.registerApp(constants.APP_ID);
	}
}
