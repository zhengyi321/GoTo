package tianhao.agoto.ThirdPay.WeiXin.service;

/**
 * Created by zhyan on 2017/3/12.
 */
import android.app.Activity;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import tianhao.agoto.ThirdPay.WeiXin.Constants;
import tianhao.agoto.ThirdPay.WeiXin.bean.Order;
import tianhao.agoto.ThirdPay.WeiXin.utils.HttpKit;
import tianhao.agoto.ThirdPay.WeiXin.utils.JPay;


public class IPayLogic {
    private static  IPayLogic mIPayLogic;
    private Activity mContext;

    private IPayLogic(Activity context) {
        mContext = context;
    }

    public static IPayLogic getIntance(Activity context){
        if (mIPayLogic == null) {
            synchronized(IPayLogic.class){
                if (mIPayLogic == null) {
                    mIPayLogic = new IPayLogic(context);
                }
            }
        }
        return mIPayLogic;
    }

    /**
     * 获取预付订单
     * @param order
     * @return
     */
    public String WXPay(Order order){
        String body = order.getBody();
        String attach = order.getAttach();
        double total_fee = order.getTotalFee();
        String notify_url = order.getNofityUrl();
        String device_info = order.getDeviceInfo();


        Map<String, String> queryParas = new HashMap<String,String>();
        queryParas.put("body", body);
        queryParas.put("attach", attach);
        queryParas.put("total_fee", total_fee*100+"");
        queryParas.put("notify_url", notify_url);
        queryParas.put("device_info", device_info);
        Constants constants = new Constants();
        String result= HttpKit.get(constants.WXPAY_URL, queryParas);
        return result;
    }





		/*Alipay.getInstance(mContext).startAliPay(orderInfo, new JPay.JPayListener() {
			@Override
			public void onPaySuccess() {
			}
			@Override
			public void onPayError(int error_code, String message) {
			}
			@Override
			public void onPayCancel() {
			}
		});*/



    /**
     * 调起支付
     * @param appId
     * @param partnerId
     * @param prepayId
     * @param nonceStr
     * @param timeStamp
     * @param sign
     */
    public void startWXPay(String appId,String partnerId,String prepayId,
                           String nonceStr,String timeStamp,String sign){

        JPay.getIntance(mContext).toWxPay(appId, partnerId, prepayId, nonceStr, timeStamp, sign, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>"+error_code+" "+ message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }
        });

		/*JPay.getIntance(mContext).toPay(JPay.PayMode.WXPAY, payParameters, new JPay.JPayListener() {
			@Override
			public void onPaySuccess() {
				Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show()
			}
			@Override
			public void onPayError(int error_code, String message) {
				Toast.makeText(mContext, "支付失败>"+error_code+" "+ message, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onPayCancel() {
				Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
			}
		});*/

    }
}