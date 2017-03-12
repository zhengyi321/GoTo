package tianhao.agoto.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

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

import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLauchParam;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.utils.OpenClientUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import tianhao.agoto.Adapter.HelpMeBuyShoppingMenuRecyclerViewAdapter;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Bean.OrderDetail;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.ShouDongShuRuDialog;
import tianhao.agoto.Common.DialogPopupWindow.PopupOnClickEvents;
import tianhao.agoto.Common.Widget.DB.ContactInjfoDao;
import tianhao.agoto.Common.Widget.DB.XCCacheManager.xccache.XCCacheManager;
import tianhao.agoto.R;
import tianhao.agoto.ThirdPay.ZhiFuBao.AuthResult;
import tianhao.agoto.ThirdPay.ZhiFuBao.OrderInfoUtil2_0;
import tianhao.agoto.ThirdPay.ZhiFuBao.PayResult;
import tianhao.agoto.Utils.PriceUtil;
import tianhao.agoto.Utils.TimeUtil;

/**
 *
 * http://blog.csdn.net/gaomatrix/article/details/6732336
 * 帮我买页面
 * Created by zhyan on 2017/2/12.
 */

public class HelpMeBuyActivity extends Activity  {
    ShouDongShuRuDialog shouDongShuRuDialog;


    private XCCacheManager xcCacheManager;
    private OrderDetail orderDetail;
    private List<String> checkList = new ArrayList<String>();
    private List<TextView> addRemarkList = new ArrayList<TextView>();
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

    /*价格*/
    @BindView(R.id.tv_helpmebuy_bottombar_fee)
    TextView tvHelpMeBuyBottomBarFee;
    /*价格*/

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

    /*价格未知*/
    @BindView(R.id.cb_helpmebuy_content_pricecheck)
    CheckBox cbHelpMeBuyContentPriceCheck;
    /*价格未知*/
    private List<CheckBox> remarkList = new ArrayList<CheckBox>();

    /*购物清单*/
    @BindView(R.id.rv_helpmebuy_content_shoppingmenu)
    RecyclerView rvHelpMeBuyContentShoppingMenu;
    List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
     HelpMeBuyShoppingMenuRecyclerViewAdapter helpMeBuyShoppingMenuRecyclerViewAdapter /*= new HelpMeBuyShoppingMenuRecyclerViewAdapter(this,goodsBeanList)*/;

    /*购物清单*/
    /*价格未知按钮*/
    @BindView(R.id.lly_helpmebuy_content_price)
    LinearLayout llyHelpMeBuyContentPrice;
    /*价格未知按钮*/

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
    /*备注*/
    @BindView(R.id.lly_helpmebuy_content_orderremark)
    LinearLayout llyHelpMeBuyContentOrderRemark;
    @BindView(R.id.lly_helpmebuy_content_newadd)
    LinearLayout llyHelpMeBuyContentNewAdd;
    /*备注*/
    private final int RESULT_BUY = 10;//购买地址
    private double blat=0,rlat=0,blon=0,rlon=0;
    private final int RESULT_RECE = 11;//收件人信息
    private final int RESULT_FOODSMENU = 12;//菜单
    /*百度骑行引擎*/
    private RoutePlanSearch mSearch;
    BikeNaviLauchParam param;
    private final String LTAG = "BaiduQiXing导航引擎";
    private static boolean isPermissionRequested = false;
    /*百度骑行引擎*/

    private String goodsName,price;
    private int dis = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpmebuy_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);

        initShoppingMenu(goodsBeanList);
        initRoutePlanDisNavi();
    }

    /*百度骑行导航初始化http://lbsyun.baidu.com/index.php?title=androidsdk/guide/bikenavi   http://wiki.lbsyun.baidu.com/cms/androidsdk/doc/v4_2_1/index.html
    * http://lbsyun.baidu.com/index.php?title=androidsdk/guide/tool里面的步行导航中有骑行导航  再加上doc文档即可解决
    * */
    private void initRoutePlanDisNavi(){
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener(){

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
                if(bikingRouteResult != null){
                    List<BikingRouteLine> bikingRouteLineList = bikingRouteResult.getRouteLines();
                    if(bikingRouteLineList != null) {
                        int count = bikingRouteLineList.size();
                        int min = bikingRouteLineList.get(0).getDistance();
                        for (int i = 0; i < count; i++) {
                            if (min > bikingRouteLineList.get(i).getDistance()) {
                                min = bikingRouteLineList.get(i).getDistance();
                            }
                            continue;
                        }
                        dis = min;
                    /*Toast.makeText(getBaseContext(),"两地骑行距离onGetBikingRouteResult:"+min,Toast.LENGTH_LONG).show();*/
                        getPrice();
                    }
                }
            }
        });


    }
    /*获取价格*/
    private void getPrice(){
        PriceUtil priceUtil = new PriceUtil();
        float dist = dis/1000;
        price = priceUtil.gotoHelpMeBuylFee(dist,helpMeBuyShoppingMenuRecyclerViewAdapter.getItemCount());
        tvHelpMeBuyBottomBarFee.setVisibility(View.VISIBLE);
        tvHelpMeBuyBottomBarFee.setText(price);
    }
    /*获取价格*/
    private void startBikeNaviSearch(){
        /*Toast.makeText(getBaseContext(),"两地骑行距离startBikeNaviSearchBegin:"+blat+" "+blon+" "+rlat+" "+rlon,Toast.LENGTH_SHORT).show();*/
        if((blat != 0) &&(blon != 0)&&(rlat !=0)&&(rlon != 0)) {
            /*Toast.makeText(getBaseContext(),"两地骑行距离startBikeNaviSearchMiddle",Toast.LENGTH_SHORT).show();*/
            LatLng blal = new LatLng(blat, blon);
            LatLng elal = new LatLng(rlat, rlon);
            PlanNode stNode = PlanNode.withLocation(blal);
            PlanNode enNode = PlanNode.withLocation(elal);
            mSearch.bikingSearch((new BikingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));
        }
    }


    /*百度骑行导航初始化http://lbsyun.baidu.com/index.php?title=androidsdk/guide/bikenavi   http://wiki.lbsyun.baidu.com/cms/androidsdk/doc/v4_2_1/index.html*/

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

            Intent intent = new Intent(this,ShoppingListActivity.class);
            if((goodsBeanList.size() > 0)){
              /*  goodsBeanList.clear();*/

                Log.i("helpmebuyActivGoodSize:",""+goodsBeanList.size());
                /*Log.i("HelpMeBuySize",helpMeBuyShoppingMenuRecyclerViewAdapter.getItemCount()+"");*/
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("foodsList",(ArrayList<GoodsBean>)helpMeBuyShoppingMenuRecyclerViewAdapter.getGoodsBeanList());
                intent.putExtras(bundle);
            }


            startActivityForResult(intent,RESULT_FOODSMENU);
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
                String lat = b.getString("blat");
                String lon = b.getString("blon");
                /*Toast.makeText(getBaseContext(),"RESULT_BUY:"+lat+" "+lon+" ",Toast.LENGTH_SHORT).show();*/

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
                String latt = r.getString("rlat");
                String lonn = r.getString("rlon");
                /*Toast.makeText(getBaseContext(),"RESULT_RECE:"+name+" "+addr+" "+tel+" "+latt+" "+lonn,Toast.LENGTH_SHORT).show();*/
               /* Toast.makeText(getBaseContext(),"RESULT_BUY:"+latt+" "+lonn+" ",Toast.LENGTH_SHORT).show();*/
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
                if(foodsb != null) {
                    List<GoodsBean> goodsBeanArrayList = foodsb.getParcelableArrayList("foodsMenu");

                    if((goodsBeanArrayList != null)) {
                        Log.i("helpmebuy,goodsBeanArr",goodsBeanArrayList.size()+"");
                        /*initShoppingMenu(goodsBeanArrayList);*/
                       /* goodsBeanList.clear();*/
                        helpMeBuyShoppingMenuRecyclerViewAdapter.setGoodsBeanList(goodsBeanArrayList);
                       /* goodsBeanList.clear();
                        goodsBeanList.addAll(goodsBeanArrayList);*/
                    }
                }
                break;
            default:
                break;
        }
    }

    /*初始化购物清单*/
    private void initShoppingMenu(List<GoodsBean> goodsBeanLists){
        if(goodsBeanLists != null) {
            /*goodsBeanList.clear();
            goodsBeanList.addAll(goodsBeanLists);*/
            helpMeBuyShoppingMenuRecyclerViewAdapter = new HelpMeBuyShoppingMenuRecyclerViewAdapter(this,goodsBeanList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
            rvHelpMeBuyContentShoppingMenu.setLayoutManager(gridLayoutManager);
            rvHelpMeBuyContentShoppingMenu.setAdapter(helpMeBuyShoppingMenuRecyclerViewAdapter);
        }
    }

    /*初始化购物清单*/














    /*价格未知按钮*/
    @OnClick(R.id.lly_helpmebuy_content_price)
    public void llyHelpMeBuyContentPriceOnclick(){
        if(cbHelpMeBuyContentPriceCheck.isChecked()){
            cbHelpMeBuyContentPriceCheck.setChecked(false);
        }else{
            cbHelpMeBuyContentPriceCheck.setChecked(true);
        }
        /*startBikeNaviSearch();*/
    }
    /*价格未知按钮*/




    /*支付*/
    @OnClick(R.id.rly_helpmebuy_bottom_topay)
    public void rlyHelpMeBuyBottomToPayOnclick(){
       /* Intent intent = new Intent(this, PayConfirmPopup.class);
        startActivity(intent);*/
        PopupOnClickEvents popupOnClickEvents = new PopupOnClickEvents(this);
        goodsName = "走兔订单号";
        initOrderDetail();
        popupOnClickEvents.PayConfirm(llyHelpMeBuy,orderDetail);
    }

    private void initOrderDetail(){
        orderDetail = new OrderDetail();

        xcCacheManager = XCCacheManager.getInstance(this);
        String usid = xcCacheManager.readCache("usid");
        Toast.makeText(this,"usid:"+usid,Toast.LENGTH_LONG).show();
        orderDetail.setUserUsid(usid);
        System.out.println(orderDetail.getUserUsid());
        orderDetail.setClientaddrAddr(tvHelpMeBuyContentAddress.getText().toString() + " "+tvHelpMeBuyContentAddressDetail.getText().toString());
        orderDetail.setClientaddrAddr1(tvHelpMeBuyContentReceiveName.getText().toString()+" "+tvHelpMeBuyContentReceiveTel.getText().toString()+" "+tvHelpMeBuyContentReceiveAddressDetail.getText().toString());
        String remark = "";
        if((addRemarkList != null)&&(addRemarkList.size() > 0)) {
            for (int i=0; i<addRemarkList.size();i++){
                remark += addRemarkList.get(i).getText().toString()+";";
            }
        }
        if((checkList != null)&&(checkList.size() > 0)){
            for(int i=0;i<checkList.size();i++){
                remark += checkList.get(i)+";";
            }
        }
        orderDetail.setOrderRemark(remark);
        orderDetail.setOrderOrderprice(price);
        orderDetail.setOrderMileage(""+dis);
        List<GoodsBean> goodsBeanList1 =  helpMeBuyShoppingMenuRecyclerViewAdapter.getGoodsBeanList();
        String menu = "";
        if((goodsBeanList1 != null)&&(goodsBeanList1.size() > 0)){
            for(int i=0;i<goodsBeanList1.size();i++){
                menu += goodsBeanList1.get(i).getName()+"x"+goodsBeanList1.get(i).getNum()+";";
            }
        }
        orderDetail.setDetailsGoodsname(menu);
    }










    /*不辣*/
    @OnClick(R.id.cb_helpmebuy_content_nochilli)
    public void cbHelpMeBuyContentNoChilliOnclick(){
        if(cbHelpMeBuyContentNoChilli.isChecked()){
            cbHelpMeBuyContentNoChilli.setTextColor(getResources().getColor(R.color.red));
            checkList.add("不辣");
        }else{
            cbHelpMeBuyContentNoChilli.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("nochilli");
        }
    }
    /*微辣*/
    @OnClick(R.id.cb_helpmebuy_content_smallchilli)
    public void cbHelpMeBuyContentSmallChilliOnclick(){
        if(cbHelpMeBuyContentSmallChilli.isChecked()){
            cbHelpMeBuyContentSmallChilli.setTextColor(getResources().getColor(R.color.red));
            checkList.add("微辣");
        }else{
            cbHelpMeBuyContentSmallChilli.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("微辣");
        }
    }
    /*中辣*/
    @OnClick(R.id.cb_helpmebuy_content_mediachilli)
    public void cbHelpMeBuyContentMediaChilliOnclick(){
        if(cbHelpMeBuyContentMediaChilli.isChecked()){
            cbHelpMeBuyContentMediaChilli.setTextColor(getResources().getColor(R.color.red));
            checkList.add("中辣");
        }else{
            cbHelpMeBuyContentMediaChilli.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("中辣");
        }
    }
    /*特辣*/
    @OnClick(R.id.cb_helpmebuy_content_specialchilli)
    public void cbHelpMeBuyContentSpecialChilliOnclick(){
        if(cbHelpMeBuyContentSpecialChilli.isChecked()){
            cbHelpMeBuyContentSpecialChilli.setTextColor(getResources().getColor(R.color.red));
            checkList.add("特辣");
        }else{
            cbHelpMeBuyContentSpecialChilli.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("特辣");
        }
    };
    /*放葱*/
    @OnClick(R.id.cb_helpmebuy_content_haveonion)
    public void cbHelpMeBuyContentHaveOnionOnclick(){
        if(cbHelpMeBuyContentHaveOnion.isChecked()){
            cbHelpMeBuyContentHaveOnion.setTextColor(getResources().getColor(R.color.red));
            checkList.add("放葱");
        }else{
            cbHelpMeBuyContentHaveOnion.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("放葱");
        }
    }

    /*不放葱*/
    @OnClick(R.id.cb_helpmebuy_content_noonion)
    public void cbHelpMeBuyContentNoOnionOnclick(){
        if(cbHelpMeBuyContentNoOnion.isChecked()){
            cbHelpMeBuyContentNoOnion.setTextColor(getResources().getColor(R.color.red));
            checkList.add("不放葱");
        }else{
            cbHelpMeBuyContentNoOnion.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("不放葱");
        }
    }

    /*放香菜*/
    @OnClick(R.id.cb_helpmebuy_content_havecaraway)
    public void cbHelpMeBuyContentHaveCarawayOnclick(){
        if(cbHelpMeBuyContentHaveCaraway.isChecked()){
            cbHelpMeBuyContentHaveCaraway.setTextColor(getResources().getColor(R.color.red));
            checkList.add("放香菜");
        }else{
            cbHelpMeBuyContentHaveCaraway.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("放香菜");
        }
    }

    /*不放香菜*/
    @OnClick(R.id.cb_helpmebuy_content_nocaraway)
    public void cbHelpMeBuyContentNoCarawayOnclick(){
        if(cbHelpMeBuyContentNoCaraway.isChecked()){
            cbHelpMeBuyContentNoCaraway.setTextColor(getResources().getColor(R.color.red));
            checkList.add("不放香菜");
        }else{
            cbHelpMeBuyContentNoCaraway.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("不放香菜");
        }
    }

    /*放醋*/
    @OnClick(R.id.cb_helpmebuy_content_havevinegar)
    public void cbHelpMeBuyContentHaveVinegarOnclick(){
        if(cbHelpMeBuyContentHaveVinegar.isChecked()){
            cbHelpMeBuyContentHaveVinegar.setTextColor(getResources().getColor(R.color.red));
            checkList.add("放醋");
        }else{
            cbHelpMeBuyContentHaveVinegar.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("放醋");
        }
    }

    /*不放醋*/
    @OnClick(R.id.cb_helpmebuy_content_novinegar)
    public void cbHelpMeBuyContentNoVinegarOnclick(){
        if(cbHelpMeBuyContentNoVinegar.isChecked()){
            cbHelpMeBuyContentNoVinegar.setTextColor(getResources().getColor(R.color.red));
            checkList.add("不放醋");
        }else{
            cbHelpMeBuyContentNoVinegar.setTextColor(getResources().getColor(R.color.colorHelpMeBuyActivityBottomPayWordBlackBg));
            checkList.remove("不放醋");
        }
    }

    /*手动输入*/
    @OnClick(R.id.cb_helpmebuy_content_manualinput)
    public void cbHelpMeBuyContentManualinputOnclick(){
        shouDongShuRuDialog = new ShouDongShuRuDialog(this).Build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setPositiveButton("确认",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setCallBackListener(new ShouDongShuRuDialog.DialogCallBackListener() {
            @Override
            public void callBack(String msgName) {
                TextView textView = new TextView(getBaseContext());
                textView.setText(msgName);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(3,0,3,0);
                textView.setLayoutParams(layoutParams);
                textView.setPadding(2, 6, 2, 6);
                textView.setTextColor(getResources().getColor(R.color.red));
                textView.setTextSize(12);

                textView.setBackgroundResource(R.drawable.activity_gray_bg_half_round_radius);
                addRemarkList.add(textView);
                llyHelpMeBuyContentNewAdd.addView(textView);
            }
        }).build(this);
        showDialog();
    }
    public void showDialog() {
        if (shouDongShuRuDialog != null && !shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.show();
    }

    public void dissmissDialog() {
        if (shouDongShuRuDialog != null && shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.dismiss();
    }
    /*手动输入*/
    protected void onResume(){
        super.onResume();
        startBikeNaviSearch();
    }
    protected void onPause(){
        super.onPause();

    }
    protected void onStop(){
        super.onStop();
    }
    protected void onDestroy(){
        super.onDestroy();
        BaiduMapNavigation.finish(this);
        mSearch.destroy();
        Log.d(LTAG, "引擎初始化成功");
        Log.d(LTAG, "引擎初始化失败");
    }


}
