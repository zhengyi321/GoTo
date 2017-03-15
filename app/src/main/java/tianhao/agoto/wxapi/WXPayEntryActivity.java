package tianhao.agoto.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import tianhao.agoto.R;
import tianhao.agoto.ThirdPay.WeiXin.WeChatConstans;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, WeChatConstans.APP_ID);
		api.registerApp(WeChatConstans.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		/*if(resp.getType()==ConstantsAPI.COMMAND_PAY_BY_WX){
			Log.d(TAG,"onPayFinish,errCode="+resp.errCode);
			AlertDialog.Builder builder =new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			finish();
		}*/
/*		Log.d(TAG, "onPayFinish, errStr = " + resp.errStr);
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		if (resp.errCode == 0) {//支付成功
	*//*		Intent intent = new Intent();
			intent.setAction("fbPayAction");
//          intent.setAction("goodsPayAction");
//          intent.setAction("integraPayAction");
			sendBroadcast(intent);*//*
			Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
			finish();
		}else if (resp.errCode == -1) {//支付失败

			Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
			finish();
		}else {//取消
			Toast.makeText(getApplicationContext(), "支付取消", Toast.LENGTH_SHORT).show();
			finish();
		}*/


		int result = 0;

		/*Toast.makeText(this, "baseresp.getType = " + resp.getType(), Toast.LENGTH_SHORT).show();*/

		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				result = R.string.errcode_success;
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = R.string.errcode_cancel;
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = R.string.errcode_deny;
				break;
			case BaseResp.ErrCode.ERR_UNSUPPORT:
				result = R.string.errcode_unsupported;
				break;
			default:
				result = R.string.errcode_unknown;
				break;
		}

		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		finish();
	}
}