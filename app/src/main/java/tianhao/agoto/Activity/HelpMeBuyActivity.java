package tianhao.agoto.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.Common.DialogPopupWindow.PopupOnClickEvents;
import tianhao.agoto.R;
import tianhao.agoto.ThirdPay.ZhiFuBao.AuthResult;
import tianhao.agoto.ThirdPay.ZhiFuBao.OrderInfoUtil2_0;
import tianhao.agoto.ThirdPay.ZhiFuBao.PayResult;

/**
 *
 * http://blog.csdn.net/gaomatrix/article/details/6732336
 * 帮我买页面
 * Created by zhyan on 2017/2/12.
 */

public class HelpMeBuyActivity extends Activity{

    @BindView(R.id.rly_helpmebuy_topbar_leftmenu)
    RelativeLayout rlyHelpMeBuyTopBarLeftMenu;

    @BindView(R.id.lly_helpmebuy_shoppinglist)
    LinearLayout llyHelpMeBuyShoppingList;

    @BindView(R.id.lly_helpmebuy_contacterdetail)
    LinearLayout llyHelpMeBuyContacterDetail;

    @BindView(R.id.lly_helpmebuy_sellerdetail)
    LinearLayout llyHelpMeBuySellerDetail;

    @BindView(R.id.rly_helpmebuy_bottom_topay)
    RelativeLayout rlyHelpMeBuyBottomToPay;

    @BindView(R.id.lly_helpmebuy)
    LinearLayout llyHelpMeBuy;
    /*购买地址*/
    @BindView(R.id.tv_helpmebuy_content_address)
    TextView tvHelpMeBuyContentAddress;
    /*购买详细地址*/
    @BindView(R.id.tv_helpmebuy_content_addressdetail)
    TextView tvHelpMeBuyContentAddressDetail;

    /*收件人姓名*/
    @BindView(R.id.tv_helpmebuy_content_receivename)
    TextView tvHelpMeBuyContentReceiveName;
    /*收件人电话*/
    @BindView(R.id.tv_helpmebuy_content_receivetel)
    TextView tvHelpMeBuyContentReceiveTel;
    /*收件人地址*/
    @BindView(R.id.tv_helpmebuy_content_receiveaddressdetail)
    TextView tvHelpMeBuyContentReceiveAddressDetail;


    /*不辣*/
    @BindView(R.id.cb_helpmebuy_content_nochilli)
    CheckBox cbHelpMeBuyContentNoChilli;
    /*微辣*/
    @BindView(R.id.cb_helpmebuy_content_smallchilli)
    CheckBox cbHelpMeBuyContentSmallChilli;
    /*中辣*/
    @BindView(R.id.cb_helpmebuy_content_mediachilli)
    CheckBox cbHelpMeBuyContentMediaChilli;
    /*特辣*/
    @BindView(R.id.cb_helpmebuy_content_specialchilli)
    CheckBox cbHelpMeBuyContentSpecialChilli;
    /*放葱*/
    @BindView(R.id.cb_helpmebuy_content_haveonion)
    CheckBox cbHelpMeBuyContentHaveOnion;

    /*不放葱*/
    @BindView(R.id.cb_helpmebuy_content_noonion)
    CheckBox cbHelpMeBuyContentNoOnion;

    /*放香菜*/
    @BindView(R.id.cb_helpmebuy_content_havecaraway)
    CheckBox cbHelpMeBuyContentHaveCaraway;

    /*不放香菜*/
    @BindView(R.id.cb_helpmebuy_content_nocaraway)
    CheckBox cbHelpMeBuyContentNoCaraway;

    /*放醋*/
    @BindView(R.id.cb_helpmebuy_content_havevinegar)
    CheckBox cbHelpMeBuyContentHaveVinegar;

    /*不放醋*/
    @BindView(R.id.cb_helpmebuy_content_novinegar)
    CheckBox cbHelpMeBuyContentNoVinegar;

    /*手动输入*/
    @BindView(R.id.cb_helpmebuy_content_manualinput)
    CheckBox cbHelpMeBuyContentManualinput;

    private final int RESULT_BUY = 10;//购买地址
    private double blat,rlat,blon,rlon;
    private final int RESULT_RECE = 11;//收件人信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpmebuy_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
    }
    /*后退到主界面*/
    @OnClick(R.id.rly_helpmebuy_topbar_leftmenu)
    public void rlyHelpMeBuyTopBarLeftMenuOnclick(){
        /*Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();*/
        finish();
    }
    /*后退到主界面*/

    /*帮我买购物清单*/
    @OnClick(R.id.lly_helpmebuy_shoppinglist)
    public void llyHelpMeBuyShoppingListOnclick(){
        Intent intent = new Intent(this,ShoppingListActivity.class);
        startActivityForResult(intent,RESULT_BUY);
    }
    /*帮我买购物清单*/
 /*购买地址*/
    @OnClick(R.id.lly_helpmebuy_sellerdetail)
    public void llyHelpMeBuySellerDetailOnclick(){
        Intent intent = new Intent(this,HelpMeBuyAddShopDetailActivity.class);
        startActivityForResult(intent,RESULT_BUY);
    }
    /*购买地址*/
    /*收件人信息*/
    @OnClick(R.id.lly_helpmebuy_contacterdetail)
    public void llyHelpMeBuyContacterDetailOnclick(){
        Intent intent = new Intent(this,HelpMeBuyAddReceiverDetailActivity.class);
        startActivityForResult(intent,RESULT_RECE);
    }
    /*收件人信息*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_BUY:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String nameCall=b.getString("nameCall");//str即为回传的值
                String address=b.getString("address");//str即为回传的值
                String lat = b.getString("blat");
                String lon = b.getString("blon");
                if((lat != null) && (lon != null)) {
                    blat = Double.parseDouble(lat);
                    blon = Double.parseDouble(lon);
                }
                tvHelpMeBuyContentAddress.setText(nameCall);
                tvHelpMeBuyContentAddressDetail.setText(address);
                break;
            case RESULT_RECE:

                break;
            default:
                break;
        }
    }




    /*支付*/
    @OnClick(R.id.rly_helpmebuy_bottom_topay)
    public void rlyHelpMeBuyBottomToPayOnclick(){
       /* Intent intent = new Intent(this, PayConfirmPopup.class);
        startActivity(intent);*/
        PopupOnClickEvents popupOnClickEvents = new PopupOnClickEvents();
        popupOnClickEvents.PayConfirm(this,llyHelpMeBuy,new ArrayList<String>());
    }











    /*不辣*/
    @OnClick(R.id.cb_helpmebuy_content_nochilli)
    public void cbHelpMeBuyContentNoChilliOnclick(){
        if(cbHelpMeBuyContentNoChilli.isChecked()){
            cbHelpMeBuyContentNoChilli.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentNoChilli.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    }
    /*微辣*/
    @OnClick(R.id.cb_helpmebuy_content_smallchilli)
    public void cbHelpMeBuyContentSmallChilliOnclick(){
        if(cbHelpMeBuyContentSmallChilli.isChecked()){
            cbHelpMeBuyContentSmallChilli.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentSmallChilli.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    }
    /*中辣*/
    @OnClick(R.id.cb_helpmebuy_content_mediachilli)
    public void cbHelpMeBuyContentMediaChilliOnclick(){
        if(cbHelpMeBuyContentMediaChilli.isChecked()){
            cbHelpMeBuyContentMediaChilli.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentMediaChilli.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    }
    /*特辣*/
    @OnClick(R.id.cb_helpmebuy_content_specialchilli)
    public void cbHelpMeBuyContentSpecialChilliOnclick(){
        if(cbHelpMeBuyContentSpecialChilli.isChecked()){
            cbHelpMeBuyContentSpecialChilli.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentSpecialChilli.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    };
    /*放葱*/
    @OnClick(R.id.cb_helpmebuy_content_haveonion)
    public void cbHelpMeBuyContentHaveOnionOnclick(){
        if(cbHelpMeBuyContentHaveOnion.isChecked()){
            cbHelpMeBuyContentHaveOnion.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentHaveOnion.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    }

    /*不放葱*/
    @OnClick(R.id.cb_helpmebuy_content_noonion)
    public void cbHelpMeBuyContentNoOnionOnclick(){
        if(cbHelpMeBuyContentNoOnion.isChecked()){
            cbHelpMeBuyContentNoOnion.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentNoOnion.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    }

    /*放香菜*/
    @OnClick(R.id.cb_helpmebuy_content_havecaraway)
    public void cbHelpMeBuyContentHaveCarawayOnclick(){
        if(cbHelpMeBuyContentHaveCaraway.isChecked()){
            cbHelpMeBuyContentHaveCaraway.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentHaveCaraway.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    }

    /*不放香菜*/
    @OnClick(R.id.cb_helpmebuy_content_nocaraway)
    public void cbHelpMeBuyContentNoCarawayOnclick(){
        if(cbHelpMeBuyContentNoCaraway.isChecked()){
            cbHelpMeBuyContentNoCaraway.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentNoCaraway.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    }

    /*放醋*/
    @OnClick(R.id.cb_helpmebuy_content_havevinegar)
    public void cbHelpMeBuyContentHaveVinegarOnclick(){
        if(cbHelpMeBuyContentHaveVinegar.isChecked()){
            cbHelpMeBuyContentHaveVinegar.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentHaveVinegar.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    }

    /*不放醋*/
    @OnClick(R.id.cb_helpmebuy_content_novinegar)
    public void cbHelpMeBuyContentNoVinegarOnclick(){
        if(cbHelpMeBuyContentNoVinegar.isChecked()){
            cbHelpMeBuyContentNoVinegar.setTextColor(getResources().getColor(R.color.red));
        }else{
            cbHelpMeBuyContentNoVinegar.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
        }
    }

    /*手动输入*/
    @OnClick(R.id.cb_helpmebuy_content_manualinput)
    public void cbHelpMeBuyContentManualinputOnclick(){

    }
}
