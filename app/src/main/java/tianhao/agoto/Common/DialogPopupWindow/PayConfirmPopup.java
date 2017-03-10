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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.R;
import tianhao.agoto.ThirdPay.ZhiFuBao.OrderInfoUtil2_0;
import tianhao.agoto.ThirdPay.ZhiFuBao.ZhiFuBaoUtil;


/**
 * Created by zhyan on 2017/2/20.
 */

public class PayConfirmPopup extends PopupWindow {

    /*跑腿费用*/
    @BindView(R.id.tv_popup_thirdpay_payconfirm_fee)
    TextView tvPopupThirdPayPayConfirmFee;
    /*跑腿费用*/

    /*费用计算公式*/
    @BindView(R.id.tv_popup_thirdpay_payconfirm_feedescri)
    TextView tvPopupThirdPayPayConfirmFeeDescri;
    /*费用计算公式*/


    /*本地支付*/
    @BindView(R.id.lly_popup_thirdpay_payconfirm_local)
    LinearLayout llyPopupPayConfirmLocal;
    @BindView(R.id.cb_popup_thirdpay_payconfirm_local)
    CheckBox cbPopupPayConfirmLocal;
    @OnClick(R.id.lly_popup_thirdpay_payconfirm_local)
    public void llyPopupPayConfirmLocalOnclick(){
        initPayMethod("local");

    }
    /*本地支付*/
    /*微信支付*/
    @BindView(R.id.lly_popup_thirdpay_payconfirm_wx)
    LinearLayout llyPopupPayConfirmWX;
    @BindView(R.id.cb_popup_thirdpay_payconfirm_wx)
    CheckBox cbPopupPayConfirmWX;
    @OnClick(R.id.lly_popup_thirdpay_payconfirm_wx)
    public void llyPopupPayConfirmWXOnclick(){
        initPayMethod("wx");
    }
    /*微信支付*/
    /*支付宝支付*/
    @BindView(R.id.lly_popup_thirdpay_payconfirm_zfb)
    LinearLayout llyPopupPayConfirmZFB;
    @BindView(R.id.cb_popup_thirdpay_payconfirm_zfb)
    CheckBox cbPopupPayConfirmZFB;
    @OnClick(R.id.lly_popup_thirdpay_payconfirm_zfb)
    public void llyPopupPayConfirmZFBOnclick(){
        initPayMethod("zfb");
    }

    /*支付宝支付*/
    /*确认支付*/
    @BindView(R.id.rly_popup_thirdpay_payconfirm_querypay)
    RelativeLayout rlyPopupThirdPayPayConfirmQueryPay;
    @OnClick(R.id.rly_popup_thirdpay_payconfirm_querypay)
    public void rlyPopupPayConfirmQueryPayOnclick(){
        /*zhiFuBaoUtil.authV2(mPopView);*/
        if((goodsName!= null)&&(price != null)) {
            price = price.substring(1,price.length());
            zhiFuBaoUtil.payV2(mPopView, goodsName, price);
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
    private ZhiFuBaoUtil zhiFuBaoUtil;
    private String goodsName,price;
    public PayConfirmPopup(Activity activity,String goodsName1,String price1){

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView= inflater.inflate(R.layout.dialogpopup_payconfirm_lly, null);
        this.activity = activity;
        goodsName = goodsName1;
        price = price1;
        init();

    }
    private void init(){
        ButterKnife.bind(this,mPopView);
        popWindowInit();
        zhiFuBaoUtil = new ZhiFuBaoUtil(activity);
    }

    /*付款方式*/

    private void initPayMethod(String method){
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





}