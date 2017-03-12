package tianhao.agoto.Common.DialogPopupWindow;

import android.app.Activity;
import android.content.Context;
import android.database.Observable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import tianhao.agoto.Bean.BaseBean;
import tianhao.agoto.Bean.OrderDetail;
import tianhao.agoto.NetWorks.HelpMeSendBuyNetWorks;
import tianhao.agoto.R;
import tianhao.agoto.ThirdPay.WeiXin.Constants;
import tianhao.agoto.ThirdPay.WeiXin.asyncTask.WXPayTask;
import tianhao.agoto.ThirdPay.WeiXin.bean.Order;
import tianhao.agoto.ThirdPay.ZhiFuBao.ZhiFuBaoUtil;


/**
 * Created by zhyan on 2017/2/20.
 */

public class PayConfirmPopup extends PopupWindow {

    private HelpMeSendBuyNetWorks helpMeSendBuyNetWorks;

    /*跑腿费用*/
    @BindView(R.id.tv_popup_thirdpay_payconfirm_fee)
    TextView tvPopupThirdPayPayConfirmFee;
    /*跑腿费用*/

    /*费用计算公式*/
    @BindView(R.id.tv_popup_thirdpay_payconfirm_feedescri)
    TextView tvPopupThirdPayPayConfirmFeeDescri;
    /*费用计算公式*/
    private String select = "";

    /*本地支付*/
    @BindView(R.id.lly_popup_thirdpay_payconfirm_local)
    LinearLayout llyPopupPayConfirmLocal;
    @BindView(R.id.cb_popup_thirdpay_payconfirm_local)
    CheckBox cbPopupPayConfirmLocal;
    @OnClick(R.id.lly_popup_thirdpay_payconfirm_local)
    public void llyPopupPayConfirmLocalOnclick(){
        select =initPayMethod("local");

    }
    /*本地支付*/
    /*微信支付*/
    @BindView(R.id.lly_popup_thirdpay_payconfirm_wx)
    LinearLayout llyPopupPayConfirmWX;
    @BindView(R.id.cb_popup_thirdpay_payconfirm_wx)
    CheckBox cbPopupPayConfirmWX;
    @OnClick(R.id.lly_popup_thirdpay_payconfirm_wx)
    public void llyPopupPayConfirmWXOnclick(){
        select = initPayMethod("wx");
    }
    /*微信支付*/
    /*支付宝支付*/
    @BindView(R.id.lly_popup_thirdpay_payconfirm_zfb)
    LinearLayout llyPopupPayConfirmZFB;
    @BindView(R.id.cb_popup_thirdpay_payconfirm_zfb)
    CheckBox cbPopupPayConfirmZFB;
    @OnClick(R.id.lly_popup_thirdpay_payconfirm_zfb)
    public void llyPopupPayConfirmZFBOnclick(){
        select = initPayMethod("zfb");
    }

    /*支付宝支付*/
    /*确认支付*/
    @BindView(R.id.rly_popup_thirdpay_payconfirm_querypay)
    RelativeLayout rlyPopupThirdPayPayConfirmQueryPay;
    @OnClick(R.id.rly_popup_thirdpay_payconfirm_querypay)
    public void rlyPopupPayConfirmQueryPayOnclick(){
        /*zhiFuBaoUtil.authV2(mPopView);*/
        switch (select){
            case "local":

                break;
            case "wx":
                wxPay();
                break;
            case "zfb":
                zhiFuBaoPay();
                break;
            default:
                break;
        }
    }
    /*确认支付*/
    /*弹出窗口关闭*/
    @BindView(R.id.rly_popup_thirdpay_payconfirm_closewin)
    RelativeLayout rlyPopupThirdPayPayConfirmCloseWin;
    @OnClick(R.id.rly_popup_thirdpay_payconfirm_closewin)
    public void rlyPopupThirdPayPayConfirmCloseWinOnclick(){
        dismiss();
    }
    /*弹出窗口关闭*/


    private View mPopView;
    private Activity activity;
    /*微信支付*/
    private PayReq req;
     IWXAPI msgApi/* = WXAPIFactory.createWXAPI(activity, null)*/;
    /*微信支付*/
    private ZhiFuBaoUtil zhiFuBaoUtil;
    private String goodsName,price;
    private OrderDetail orderDetail;
    public PayConfirmPopup(Activity activity, OrderDetail orderDetail){

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView= inflater.inflate(R.layout.dialogpopup_payconfirm_lly, null);
        this.activity = activity;
        msgApi = WXAPIFactory.createWXAPI(activity, "wxf180adf1575a69e0");
        goodsName = "走兔";
        orderDetail = orderDetail;
        if(orderDetail.getOrderOrderprice() != null) {
            price = orderDetail.getOrderOrderprice();
        }
        init();

    }
    private void init(){
        ButterKnife.bind(this,mPopView);
        popWindowInit();
        zhiFuBaoUtil = new ZhiFuBaoUtil(activity);
    }

    /*付款方式*/

    private String initPayMethod(String method){
        switch (method){
            case "local":
                cbPopupPayConfirmLocal.setChecked(true);
                cbPopupPayConfirmLocal.setBackgroundResource(R.drawable.paywayselect);
                cbPopupPayConfirmWX.setChecked(false);
                cbPopupPayConfirmWX.setBackgroundResource(R.drawable.paywaynormal);
                cbPopupPayConfirmZFB.setChecked(false);
                cbPopupPayConfirmZFB.setBackgroundResource(R.drawable.paywaynormal);
                break;
            case "wx":
                cbPopupPayConfirmLocal.setChecked(false);
                cbPopupPayConfirmLocal.setBackgroundResource(R.drawable.paywaynormal);
                cbPopupPayConfirmWX.setChecked(true);
                cbPopupPayConfirmWX.setBackgroundResource(R.drawable.paywayselect);
                cbPopupPayConfirmZFB.setChecked(false);
                cbPopupPayConfirmZFB.setBackgroundResource(R.drawable.paywaynormal);
                break;
            case "zfb":
                cbPopupPayConfirmLocal.setChecked(false);
                cbPopupPayConfirmLocal.setBackgroundResource(R.drawable.paywaynormal);
                cbPopupPayConfirmWX.setChecked(false);
                cbPopupPayConfirmWX.setBackgroundResource(R.drawable.paywaynormal);
                cbPopupPayConfirmZFB.setChecked(true);
                cbPopupPayConfirmZFB.setBackgroundResource(R.drawable.paywayselect);
                break;

        }
        return method;
    }
    /*付款方式*/

    private void popWindowInit(){
        this.setContentView(mPopView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        tvPopupThirdPayPayConfirmFee.setText(price);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);


    }


    /*微信支付*/
    public void wxPay(){
        /*Toast.makeText(activity, "测试", Toast.LENGTH_SHORT).show();*/
        req = new PayReq();
        Constants constants = new Constants();
        msgApi.registerApp(constants.APP_ID);
        Order order = new Order();
        order.setAppId("wxf180adf1575a69e0");
        order.setBody("会员充值中心");
        order.setParaTradeNo(System.currentTimeMillis()+"");
        order.setTotalFee(0.1);
        order.setAttach("json");//附加参数
        order.setNofityUrl("http://www.baidu.com");//支付成功服务端回调通知的地址
        order.setDeviceInfo("");
        PayReq request = new PayReq();
        request.appId = "wxf180adf1575a69e0";
        request.partnerId = "1900000109";
        request.prepayId= "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr= "1101000000140429eb40476f8896f4c9";
        request.timeStamp= "1398746574";
        request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        msgApi.sendReq(req);
       /* req.appId = "wxf180adf1575a69e0";


        msgApi.sendReq(req);*/
        /*new WXPayTask(activity).execute(order);*/
    }
    /*微信支付*/
    /*支付宝支付*/
    public void zhiFuBaoPay(){
        if(orderDetail == null){
            Toast.makeText(activity,"请登录",Toast.LENGTH_SHORT).show();
            return;
        }
        if(orderDetail.getUserUsid() == null || orderDetail.getUserUsid().isEmpty()  ){
            Toast.makeText(activity,"请登录",Toast.LENGTH_SHORT).show();
            return;
        }
        if((goodsName!= null)&&(price != null)) {
            price = price.substring(1,price.length());
            zhiFuBaoUtil.payV2(mPopView, goodsName, price);
            zhiFuBaoUtil.setOnPaySuccessfulListener(new ZhiFuBaoUtil.OnPaySuccessfulListener() {
                @Override
                public void isSuccessful(boolean isSuccessful) {
                    if(isSuccessful){
                        helpMeSendBuyNetWorks = new HelpMeSendBuyNetWorks();
                        if(orderDetail != null) {
                            helpMeSendBuyNetWorks.orderUpdate(orderDetail.getUserUsid(),orderDetail.getClientaddrAddr(),orderDetail.getClientaddrAddr1(),orderDetail.getOrderHeight(),orderDetail.getOrderName(),orderDetail.getOrderTimeliness(),orderDetail.getOrderRemark(),orderDetail.getOrderOrderprice(),orderDetail.getOrderMileage(),orderDetail.getClientaddrArea(),orderDetail.getDetailsGoodsname(), new Observer<BaseBean>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(activity,"网络君凯旋失败啦！！快检查你的账号和密码吧",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onNext(BaseBean baseBean) {
                                    Toast.makeText(activity,baseBean.getResult()+" ",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }
            });
        }
    }
    /*支付宝支付*/

}