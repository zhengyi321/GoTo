package tianhao.agoto.Common.DialogPopupWindow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import tianhao.agoto.Bean.BaseBean;
import tianhao.agoto.Bean.HelpMeBuyBean;
import tianhao.agoto.Bean.OrderDetail;
import tianhao.agoto.NetWorks.HelpMeSendBuyNetWorks;
import tianhao.agoto.R;
import tianhao.agoto.ThirdPay.WeiXin.WeChatPayService;
import tianhao.agoto.ThirdPay.ZhiFuBao.ZhiFuBaoUtil;
import tianhao.agoto.Utils.PhoneFormatCheckUtils;


/**
 * Created by zhyan on 2017/2/20.
 */

public class PayConfirmPopup extends PopupWindow {

    private HelpMeSendBuyNetWorks helpMeSendBuyNetWorks;
    private PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
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
    /**
     * 订单ID
     */
    private String orderId ="223324";

    /**
     * 最后需要支付的金额
     */
    private String fastAmount ="10";
    /**
     * 不同类型的订单
     */
    int type = 10;
    /*微信支付*/
    private ZhiFuBaoUtil zhiFuBaoUtil;
    private String goodsName,price;
    private OrderDetail orderDetail;
    public PayConfirmPopup(Activity activity, OrderDetail orderDetail1){

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView= inflater.inflate(R.layout.dialogpopup_payconfirm_lly, null);
        this.activity = activity;
        goodsName = "走兔";
        orderDetail = orderDetail1;
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
        Toast.makeText(activity,"this is wxpay",Toast.LENGTH_SHORT).show();
        String body = "测试商品不描述";
        WeChatPayService weChatPay = new WeChatPayService(activity,type, orderId, body, fastAmount);
        weChatPay.pay();
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
        if((goodsName!= null)&&(price != null)&&(price.length()> 1)) {

            /*int index = price.indexOf("￥");*/
            String first = price.substring(0,1);
            if(!phoneFormatCheckUtils.IsIntNumber(first)) {
                price = price.substring(1, price.length());
                int index = price.indexOf(".");
                if (index > 0) {
                    price = price.substring(0, index);
                }
            }
            pOrderToNet();



        }
    }
    /*支付宝支付*/

    /*下单给后台*/
    private void pOrderToNet(){
        try {
            /*判断物件重量*/
            String weight = orderDetail.getOrderHeight();
            int indexW = weight.indexOf("k");
            if(indexW > 0){
                weight = weight.substring(0,indexW);
                if(phoneFormatCheckUtils.isDouble(weight)){
                    orderDetail.setOrderHeight(weight);
                }else{
                    orderDetail.setOrderHeight("0");
                }
            }else{
                orderDetail.setOrderHeight("0");
            }
            /*判断物件重量*/
            helpMeSendBuyNetWorks = new HelpMeSendBuyNetWorks();
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("userUsid", orderDetail.getUserUsid());
            paramMap.put("clientaddrAddr", orderDetail.getClientaddrAddr());
            paramMap.put("clientaddrAddr1", orderDetail.getClientaddrAddr1());
            paramMap.put("orderHeight", orderDetail.getOrderHeight());
            paramMap.put("orderName", orderDetail.getOrderName());
            paramMap.put("orderTimeliness", orderDetail.getOrderTimeliness());
            paramMap.put("orderRemark", orderDetail.getOrderRemark());
            orderDetail.setOrderOrderprice(price);
            if (phoneFormatCheckUtils.isDouble(price)) {

                paramMap.put("orderOrderprice", price);
            } else {
                paramMap.put("orderOrderprice", "0");
            }
            /*Toast.makeText(activity,"this is price:"+paramMap.get("orderOrderprice"),Toast.LENGTH_SHORT).show();*/
            if (phoneFormatCheckUtils.isDouble(orderDetail.getOrderMileage())) {
                paramMap.put("orderMileage", orderDetail.getOrderMileage()+".0");
            } else {
                paramMap.put("orderMileage", "0");
            }
            paramMap.put("clientaddrArea", orderDetail.getClientaddrArea());
            paramMap.put("detailsGoodsname", orderDetail.getDetailsGoodsname());
            System.out.println("userUsid=" + orderDetail.getUserUsid() + "&" + "clientaddrAddr=" + orderDetail.getClientaddrAddr() + "&" + "clientaddrAddr1=" + orderDetail.getClientaddrAddr1() + "&" + "orderHeight=" + orderDetail.getOrderHeight() + "&" + "orderName=" + orderDetail.getOrderName() + "&"
                    + "orderTimeliness=" + orderDetail.getOrderTimeliness() + "&" + "orderRemark=" + orderDetail.getOrderRemark() + "&" + "orderOrderprice=" + orderDetail.getOrderOrderprice() + "&" + "orderMileage=" + orderDetail.getOrderMileage() + "&" + "clientaddrArea=" + orderDetail.getClientaddrArea() + "&"
                    + "detailsGoodsname=" + orderDetail.getDetailsGoodsname()
            );
            helpMeSendBuyNetWorks.orderUpdate(paramMap, new Observer<HelpMeBuyBean>() {
                @Override
                public void onCompleted() {
                    /*Toast.makeText(activity, " onCompleted isSuccessful:", Toast.LENGTH_LONG).show();*/
                }

                @Override
                public void onError(Throwable e) {
                    /*Toast.makeText(activity, " onError isSuccessful:" + e, Toast.LENGTH_LONG).show();*/
                }

                @Override
                public void onNext(final HelpMeBuyBean helpMeBuyBean) {
                    /*Toast.makeText(activity, " onCompleted isSuccessful:", Toast.LENGTH_SHORT).show();*/
                    goodsName = helpMeBuyBean.getOrderNo();
                    /*Toast.makeText(activity, " onCompleted mPopView:"+goodsName+price, Toast.LENGTH_LONG).show();*/
                    zhiFuBaoUtil.payV2(mPopView, goodsName, price);
                    /*去支付金钱*/
                    zhiFuBaoUtil.setOnPaySuccessfulListener(new ZhiFuBaoUtil.OnPaySuccessfulListener() {
                        @Override
                        public void isSuccessful(boolean isSuccessful) {
                    /*Toast.makeText(activity," 我成功啦 isSuccessful:"+isSuccessful,Toast.LENGTH_LONG).show();*/
                            if (isSuccessful) {
                                if (helpMeBuyBean.getPaystatusPaystatus().equals("有待支付")) {
                                    helpMeSendBuyNetWorks.orderPay(3, helpMeBuyBean.getOrderNo(), new Observer<BaseBean>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(BaseBean baseBean) {
                                            Toast.makeText(activity, "" + baseBean.getResult(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else if (helpMeBuyBean.getPaystatusPaystatus().equals("支付失败")) {
                                    helpMeSendBuyNetWorks.orderPay(2, helpMeBuyBean.getOrderNo(), new Observer<BaseBean>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(BaseBean baseBean) {
                                            Toast.makeText(activity, "" + baseBean.getResult(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                        }
                    });
                    /*去支付金钱*/
                    /*Toast.makeText(activity," 我成功啦111 isSuccessful:"+helpMeBuyBean.getOrderNo(),Toast.LENGTH_LONG).show();*/
                }
            });
        }catch (Exception e){
            Toast.makeText(activity,"下单失败",Toast.LENGTH_SHORT).show();
        }

    }
    /*下单给后台*/


}