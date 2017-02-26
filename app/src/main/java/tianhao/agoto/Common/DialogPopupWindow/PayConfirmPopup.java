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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.R;
import tianhao.agoto.ThirdPay.ZhiFuBao.ZhiFuBaoUtil;


/**
 * Created by zhyan on 2017/2/20.
 */

public class PayConfirmPopup extends PopupWindow {

    @BindView(R.id.lly_dialogpopup_not_pop_payconfirm)
    LinearLayout llyDialogPopupNotPopPayConfirm;
    @BindView(R.id.lly_popup_payconfirm_local)
    LinearLayout llyPopupPayConfirmLocal;
    @BindView(R.id.lly_popup_payconfirm_wx)
    LinearLayout llyPopupPayConfirmWX;
    @BindView(R.id.lly_popup_payconfirm_zfb)
    LinearLayout llyPopupPayConfirmZFB;
    @BindView(R.id.cb_popup_payconfirm_local)
    CheckBox cbPopupPayConfirmLocal;
    @BindView(R.id.cb_popup_payconfirm_wx)
    CheckBox cbPopupPayConfirmWX;
    @BindView(R.id.cb_popup_payconfirm_zfb)
    CheckBox cbPopupPayConfirmZFB;
    @BindView(R.id.rly_popup_payconfirm_querypay)
    RelativeLayout rlyPopupPayConfirmQueryPay;

    private View mPopView;
    private Activity activity;
    private ZhiFuBaoUtil zhiFuBaoUtil;
    public PayConfirmPopup(Activity activity, List<String> dataList){

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView= inflater.inflate(R.layout.dialogpopup_payconfirm_lly, null);
        this.activity = activity;
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

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        llyDialogPopupNotPopPayConfirm.setAlpha((float) 0.8);
        llyDialogPopupNotPopPayConfirm.setBackgroundResource(R.color.black);

    }
    @OnClick(R.id.lly_dialogpopup_not_pop_payconfirm)
    public void llyDialogPopupNotPopPayConfirmOnclick(){

        dismiss();
    }
    @OnClick(R.id.lly_popup_payconfirm_local)
    public void llyPopupPayConfirmLocalOnclick(){
        initPayMethod("local");

    }
    @OnClick(R.id.lly_popup_payconfirm_wx)
    public void llyPopupPayConfirmWXOnclick(){
        initPayMethod("wx");
    }
    @OnClick(R.id.lly_popup_payconfirm_zfb)
    public void llyPopupPayConfirmZFBOnclick(){
        initPayMethod("zfb");
    }

    @OnClick(R.id.rly_popup_payconfirm_querypay)
    public void rlyPopupPayConfirmQueryPayOnclick(){
        /*zhiFuBaoUtil.authV2(mPopView);*/
        zhiFuBaoUtil.payV2(mPopView);
    }
}