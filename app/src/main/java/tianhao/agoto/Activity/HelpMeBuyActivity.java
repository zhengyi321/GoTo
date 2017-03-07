package tianhao.agoto.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import tianhao.agoto.Adapter.HelpMeBuyShoppingMenuRecyclerViewAdapter;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Common.DialogPopupWindow.PopupOnClickEvents;
import tianhao.agoto.R;
import tianhao.agoto.ThirdPay.ZhiFuBao.AuthResult;
import tianhao.agoto.ThirdPay.ZhiFuBao.OrderInfoUtil2_0;
import tianhao.agoto.ThirdPay.ZhiFuBao.PayResult;
import tianhao.agoto.Utils.TimeUtil;

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

    /*购物清单*/
    @BindView(R.id.rv_helpmebuy_content_shoppingmenu)
    RecyclerView rvHelpMeBuyContentShoppingMenu;
    List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
    private HelpMeBuyShoppingMenuRecyclerViewAdapter helpMeBuyShoppingMenuRecyclerViewAdapter = new HelpMeBuyShoppingMenuRecyclerViewAdapter(this,goodsBeanList);

    /*购物清单*/


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
    private final int RESULT_FOODSMENU = 12;//菜单

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
           /* 判断是点击事件 不是滑动事件的解决方法http://www.cnblogs.com/wader2011/archive/2011/12/02/2271981.html
    添加商品*/
    TimeUtil timeUtil = new TimeUtil();
    String timeBegin = "";
    float xBegin = 0;
    float yBegin = 0;
    float xEnd = 0;
    float yEnd = 0;

    @OnClick(R.id.lly_helpmebuy_shoppinglist)
    public void llyHelpMeBuyShoppingListOnclick(){
       /* Intent intent = new Intent(this,ShoppingListActivity.class);
        startActivityForResult(intent,RESULT_BUY);*/
        isOnclick();
    }

    /*购物清单再次点击*/
    @OnTouch(R.id.rv_helpmebuy_content_shoppingmenu)
    public boolean llyHelpMeBuyShoppingListOnTouch(View view, MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                timeBegin = timeUtil.getCurrentDateTime();
                xBegin = event.getRawX();
                yBegin = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP: {
                xEnd = event.getRawX();
                yEnd = event.getRawY();
                int absXBegin = (int) Math.abs(xBegin);
                int absYBegin = (int) Math.abs(yBegin);
                int absXEnd = (int) Math.abs(xEnd);
                int absYEnd = (int) Math.abs(yEnd);
                int disX = (absXEnd - absXBegin);
                int disY = (absYEnd - absYBegin);
                System.out.println("this is llyShoppingListContentPiperCardItemPaperThree down xBegin:" + xBegin + ",y:" + yBegin);
                System.out.println("this is llyShoppingListContentPiperCardItemPaperThree down xEnd:" + absXEnd + ",y:" + absYEnd);
                System.out.println("this is llyShoppingListContentPiperCardItemPaperThree disX:" + disX + ",disY:" + disY);
                if((disX == 0)&&(disY == 0)){
                    isOnclick();
                    return true;
                }

                break;
                            /*isOnclick();*/
                            /*return true;*/

            }
        }
        return false;
    }

    private void isOnclick() {
        System.out.println("this is onclick");
            /*recyclerViewAdapter.addData(new GoodsBean());*/
        String currentTime = timeUtil.getCurrentDateTime();
        long timeGap = timeUtil.getSubTwoTimeBySeconds(currentTime, timeBegin);// 与现在时间相差秒数
            /*Toast.makeText(mContext,"dis:timeGap:"+timeGap,Toast.LENGTH_SHORT).show();*/
            /*时间为小于1秒则判断为点击事件不然就判断为触摸事件*/
        if (Math.abs(timeGap) < 1) {
            Bundle bundle = new Bundle();
            goodsBeanList = helpMeBuyShoppingMenuRecyclerViewAdapter.getGoodsBeanList();
            System.out.println("this is helpmebuy:"+goodsBeanList.size());
            Log.i("HelpMeBuySize",goodsBeanList.size()+"");
            bundle.putParcelableArrayList("foodsList",(ArrayList<GoodsBean>)goodsBeanList);
            Intent intent = new Intent(this,ShoppingListActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent,RESULT_BUY);
        }
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
                String lat = b.getString("lat");
                String lon = b.getString("lon");
                if((lat != null) && (lon != null)) {
                    blat = Double.parseDouble(lat);
                    blon = Double.parseDouble(lon);
                }
                tvHelpMeBuyContentAddress.setText(nameCall);
                tvHelpMeBuyContentAddressDetail.setText(address);
                break;
            case RESULT_RECE:
                Bundle r=data.getExtras(); //data为B中回传的Intent
                String name=r.getString("nameCall");//str即为回传的值
                String addr=r.getString("address");//str即为回传的值
                String tel =r.getString("tel");
                String latt = r.getString("lat");
                String lonn = r.getString("lon");
                if((latt != null) && (lonn != null)) {
                    rlat = Double.parseDouble(latt);
                    rlon = Double.parseDouble(lonn);
                }
                tvHelpMeBuyContentReceiveName.setText(name);
                tvHelpMeBuyContentReceiveTel.setText(tel);
                tvHelpMeBuyContentReceiveAddressDetail.setText(addr);
                break;

            case RESULT_FOODSMENU:
                Bundle foodsb = data.getExtras();
                List<GoodsBean> goodsBeanArrayList =  foodsb.getParcelableArrayList("foodsList");
                initShoppingMenu(goodsBeanArrayList);
                break;
            default:
                break;
        }
    }

    /*初始化购物清单*/
    private void initShoppingMenu(List<GoodsBean> goodsBeanLists){
        goodsBeanList.clear();
        goodsBeanList.addAll(goodsBeanLists);
        /*helpMeBuyShoppingMenuRecyclerViewAdapter = new HelpMeBuyShoppingMenuRecyclerViewAdapter(this,goodsBeanList);*/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,6);
        rvHelpMeBuyContentShoppingMenu.setLayoutManager(gridLayoutManager);
        rvHelpMeBuyContentShoppingMenu.setAdapter(helpMeBuyShoppingMenuRecyclerViewAdapter);

    }


    /*初始化购物清单*/



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

    protected void onPause(){
        super.onPause();

    }
}
