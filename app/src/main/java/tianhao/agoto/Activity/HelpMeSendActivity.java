package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.security.PublicKey;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Bean.OrderDetail;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.AlertView;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.OnItemClickListener;
import tianhao.agoto.Common.DialogPopupWindow.PopupOnClickEvents;
import tianhao.agoto.Common.Widget.DB.XCCacheManager.xccache.XCCacheManager;
import tianhao.agoto.R;
import tianhao.agoto.Utils.PhoneFormatCheckUtils;
import tianhao.agoto.Utils.PriceUtil;
import tianhao.agoto.Utils.TimeUtil;

/**
 *
 * 帮我送
 * Created by zhyan on 2017/2/12.
 */

public class HelpMeSendActivity extends Activity {


    PopupOnClickEvents popupOnClickEvents = new PopupOnClickEvents(this);
    PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
    /*缓存*/
    private XCCacheManager xcCacheManager;
    /*缓存*/
    private OrderDetail orderDetail;
    /*距离*/
    private float dis = 0;
    /*距离*/
    private String goodsName="",price="";
    /*百度骑行引擎*/
    private RoutePlanSearch mSearch;
    /*百度骑行引擎*/
    /*帮我送*/
    @BindView(R.id.lly_helpmesend)
    LinearLayout llyHelpMeSend;
    /*帮我送*/
    @BindView(R.id.rly_helpmesend_topbar_leftmenu)
    RelativeLayout rlyHelpMeSendTopBarLeftMenu;

    /*收件人信息*/
    @BindView(R.id.lly_helpmesend_content_receiverdata)
    LinearLayout llyHelpMeSendReceiverData;
    @BindView(R.id.tv_helpmesend_content_receiveraddr)
    TextView tvHelpMeSendContentReceiverAddr;
    @BindView(R.id.tv_helpmesend_content_receivername)
    TextView tvHelpMeSendContentReceiverName;
    @BindView(R.id.tv_helpmesend_content_receivertel)
    TextView tvHelpMeSendContentReceiverTel;
    /*收件人信息*/
    /*发件人信息*/
    @BindView(R.id.lly_helpmesend_content_senderdata)
    LinearLayout llyHelpMeSendContentSenderData;
    @BindView(R.id.tv_helpmesend_content_senderaddr)
    TextView tvHelpMeSendContentSenderAddr;
    @BindView(R.id.tv_helpmesend_content_sendername)
    TextView tvHelpMeSendContentSenderName;
    @BindView(R.id.tv_helpmesend_content_sendertel)
    TextView tvHelpMeSendContentSenderTel;
    /*发件人信息*/


    /*物品重量*/
    @BindView(R.id.tv_helpmesend_content_goodsweight)
    TextView tvHelpMeSendContentGoodsWeight;
    @BindView(R.id.lly_helpmesend_content_weight)
    LinearLayout llyHelpMeSendContentWeight;
    @OnClick(R.id.lly_helpmesend_content_weight)

    public void llyHelpMeSendContentWeightOnclick(){
        new AlertView.Builder().setContext(this)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("物品重量")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive("超出10公斤(超出部分需另收费)")
                .setDestructive1("10公斤以内")
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        /*Toast.makeText(getBaseContext(),"pos"+position,Toast.LENGTH_SHORT).show();*/
                        switch (position){
                            case 0:
                                tvHelpMeSendContentGoodsWeight.setText("超出10公斤");

                                break;
                            case 1:
                                tvHelpMeSendContentGoodsWeight.setText("10公斤以内");

                                break;
                            case -1:
                                break;
                        }
                    }
                })
                .build()
                .show();
        startBikeNaviSearch();

 /*       popupOnClickEvents.GoodsWeightSelect(llyHelpMeSend,tvHelpMeSendContentGoodsWeight);
        popupOnClickEvents.setOnGoodsWeightSelectListener(new PopupOnClickEvents.OnGoodsWeightSelectListener() {
            @Override
            public void select() {
                *//*Toast.makeText(getBaseContext(),"here is onclick",Toast.LENGTH_SHORT).show();*//*
                startBikeNaviSearch();
            }
        });*/
    }


    /*物品重量*/

    /*配送里程*/
    @BindView(R.id.tv_helpmesend_content_senddis)
    TextView tvHelpMeSendContentSendDis;
    /*配送里程*/

    /*物品种类*/
    @BindView(R.id.lly_helpmesend_content_goodstype)
    LinearLayout llyHelpMeSendContentGoodsType;
    @OnClick(R.id.lly_helpmesend_content_goodstype)
    public void llyHelpMeSendContentGoodsTypeOnclick(){
        popupOnClickEvents.GoodsTypeSelect(llyHelpMeSend,tvHelpMeSendContentGoodsType);
    }
    @BindView(R.id.tv_helpmesend_content_goodstype)
    TextView tvHelpMeSendContentGoodsType;
    /*物品种类*/

    /*收件时间*/
    @BindView(R.id.lly_helpmesend_content_rectime)
    LinearLayout llyHelpMeSendContentRecTime;
    @BindView(R.id.tv_helpmesend_content_rectime)
    TextView tvHelpMeSendContentRecTime;
    @OnClick(R.id.lly_helpmesend_content_rectime)
    public void llyHelpMeSendContentRecTimeOnclick(){

        popupOnClickEvents.TimeSelect(llyHelpMeSend,tvHelpMeSendContentRecTime);
    }
    /*收件时间*/
    /*备注*/
    @BindView(R.id.et_helpmesend_content_remark)
    EditText etHelpMeSendContentRemark;
    /*备注*/
    /*底部栏的该付款*/
    @BindView(R.id.tv_helpmesend_bottombar_price)
    TextView tvHelpMeSendBottomBarPrice;
    /*底部栏的该付款*/

    /*底部栏支付*/
    @BindView(R.id.rly_helpmesend_bottombar_payconfirm)
    RelativeLayout rlyHelpMeSendBottomBarPayConfirm;
    @OnClick(R.id.rly_helpmesend_bottombar_payconfirm)
    public void rlyHelpMeSendBottomBarPayConfirmOnclick(){
        try {
            initOrderDetail();
            if (orderDetail.getUserUsid().isEmpty() || orderDetail.getClientaddrAddr().isEmpty() || orderDetail.getClientaddrAddr1().isEmpty()   || orderDetail.getOrderTimeliness().isEmpty()) {
                Toast.makeText(this, "信息输入不全", Toast.LENGTH_LONG).show();
                return;
            }

            PopupOnClickEvents popupOnClickEvents = new PopupOnClickEvents(this);

            popupOnClickEvents.PayConfirm(llyHelpMeSend, orderDetail);
        }catch (Exception e){

        }
    }
    /*费用说明*/
    @BindView(R.id.rly_helpmesend_bottombar_feecontent)
    RelativeLayout rlyHelpMeSendBottomBarFeeContent;
    @OnClick(R.id.rly_helpmesend_bottombar_feecontent)
    public void rlyHelpMeSendBottomBarFeeContentOnclick(){
        Intent intent = new Intent(this,ShouFeiBiaoZhunActivity.class);
        startActivity(intent);
    }
    /*费用说明*/
    /*底部栏支付*/
    private String usid = "";
    private String userName = "";
    /*当登录的时候 百度自动定位*/
/*    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();
    private boolean isFirstLocation = true;*/
    /*当登录的时候 百度自动定位*/
    private final int RESULT_SENDER = 10;
    private final int RESULT_RECEIVER = 11;

    private double blat=0.0;
    private double rlat=0.0;
    private double blon=0.0;
    private double rlon=0.0;
    private String  bclientaddrThings1 = "";
    private String  rclientaddrThings1 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpmesend_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
        initWeightAndReceiveTime();

        initIsLogin();
        initRoutePlanDisNavi();
    }
    private void initWeightAndReceiveTime(){
        tvHelpMeSendContentGoodsWeight.setText("10公斤以内");
        tvHelpMeSendContentRecTime.setText("立即收件");
    }

    private void initIsLogin(){
        xcCacheManager = XCCacheManager.getInstance(this);
        usid = xcCacheManager.readCache("usid");
        userName = xcCacheManager.readCache("userName");
        if(usid == null){
            usid = "";
        }
        if(userName == null){
            userName = "";
        }
/*        if(!usid.isEmpty()){
            tvHelpMeSendContentSenderName.setText("尊敬的先生/女士");
            tvHelpMeSendContentSenderTel.setText(userName);
            locationClient=new LocationClient(getApplicationContext());
            locationClient.registerLocationListener(locationListener);
            initLocation();
            locationClient.start();
        }*/
    }
  /*  private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//返回地址
        option.setIsNeedLocationDescribe(true);//返回地址周边描述
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);
    }

    *//**接收异步返回的定位结果**//*
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            if(isFirstLocation) {
                tvHelpMeSendContentSenderAddr.setText(location.getAddrStr() + location.getLocationDescribe());
                blat = location.getLatitude();
                blon = location.getLongitude();
                Toast.makeText(getBaseContext(),"blat:"+blat+" blon:"+blon,Toast.LENGTH_SHORT).show();
                isFirstLocation = false;
            }
            *//*showCurrentPosition(location);*//*
        }
    }*/

    /*后退到主界面*/
    @OnClick(R.id.rly_helpmesend_topbar_leftmenu)
    public void rlyHelpMeBuyTopBarLeftMenuOnclick(){
        /*Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();*/
        finish();
    }
    /*后退到主界面*/
    /*收件人信息*/
    @OnClick(R.id.lly_helpmesend_content_receiverdata)
    public void llyHelpMeSendReceiverDataOnclick(){
        xcCacheManager.writeCache("addressManageType","send");
        Bundle bundle = new Bundle();
        bundle.putString("receiver",""+RESULT_RECEIVER);
        Intent intent = new Intent(this,AddressManageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,RESULT_RECEIVER);
    }
    /*收件人信息*/

    /*发件人*/
    @OnClick(R.id.lly_helpmesend_content_senderdata)
    public void llyHelpMeSendSenderDataOnclick(){
        xcCacheManager.writeCache("addressManageType","send");
        Bundle bundle = new Bundle();
        bundle.putString("sender",""+RESULT_SENDER);
        Intent intent = new Intent(this,AddressManageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,RESULT_SENDER);
        /*startActivity(intent);*/
    }
    /*发件人*/

    /*百度地图定位结果*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_SENDER:
                getDataFromAddActivity(data,true);
                break;
            case RESULT_RECEIVER:
                getDataFromAddActivity(data,false);


                break;
            default:
                break;
        }
    }
    /*百度地图定位结果*/
    private void getDataFromAddActivity(Intent data,boolean isSender){
        if(data != null){
            Bundle b=data.getExtras(); //data为B中回传的Intent
            String nameCall=b.getString("nameCall");//str即为回传的值
            String address=b.getString("address");//str即为回传的值
            String telphone = b.getString("tel");
            String clientaddrThings1 = b.getString("clientaddrThings1");
            String lat = b.getString("lat");
            String lon = b.getString("lon");

            if(isSender){
                if((lat != null) && (lon != null)) {
                    blat = Double.parseDouble(lat);
                    blon = Double.parseDouble(lon);
                }
                bclientaddrThings1 = clientaddrThings1;
                tvHelpMeSendContentSenderName.setText(nameCall);
                tvHelpMeSendContentSenderAddr.setText(address);
                tvHelpMeSendContentSenderTel.setText(telphone);
            }else{
                rclientaddrThings1 = clientaddrThings1;
                tvHelpMeSendContentReceiverName.setText(nameCall);
                tvHelpMeSendContentReceiverAddr.setText(address);
                tvHelpMeSendContentReceiverTel.setText(telphone);
                if((lat != null) && (lon != null)) {
                    rlat = Double.parseDouble(lat);
                    rlon = Double.parseDouble(lon);
                }
            }
            /*Toast.makeText(getBaseContext(),"lat:"+lat+" blat:"+blat+" rlat:"+rlat,Toast.LENGTH_SHORT).show();*/
            /*Toast.makeText(this,"this is helpmesend:lat"+lat+",lon:"+lon,Toast.LENGTH_SHORT).show();*/
        }
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
                        dis = min/1000;

                        /*Toast.makeText(getBaseContext(),"两地骑行距离onGetBikingRouteResult:"+min,Toast.LENGTH_LONG).show();*/
                        getPrice();
                        /*Toast.makeText(getBaseContext(),"here is onGetBikingRouteResult:"+dis,Toast.LENGTH_SHORT).show();*/
                    }
                }
            }
        });


    }
    /*获取价格*/
    private void getPrice(){
        PriceUtil priceUtil = new PriceUtil(this);

        tvHelpMeSendContentSendDis.setText(""+dis+"km");
        price = priceUtil.gotoHelpMeSendlFee(dis);
        /*
        if((tvHelpMeSendContentGoodsWeight.getText().toString() != null)&&(!tvHelpMeSendContentGoodsWeight.getText().toString().isEmpty())) {
            String weight = tvHelpMeSendContentGoodsWeight.getText().toString();
            int index = weight.indexOf("k");
            if(index >0){
                weight = weight.substring(0,index);
                if(phoneFormatCheckUtils.isDouble(weight)){
                    float tempWeight = Float.parseFloat(weight);
                    *//*price = priceUtil.gotoHelpMeSendlFee(dis, tempWeight);*//*
                    price = priceUtil.gotoHelpMeSendlFee(dis);
                }else {
                    Toast.makeText(this, "请正确输入重量", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(phoneFormatCheckUtils.isDouble(weight)){
                    float tempWeight = Float.parseFloat(weight);
                    *//*price = priceUtil.gotoHelpMeSendlFee(dis, tempWeight);*//*
                    price = priceUtil.gotoHelpMeSendlFee(dis);
                }else {
                    Toast.makeText(this, "请正确输入重量", Toast.LENGTH_SHORT).show();
                }
            }
            *//*float weight = Float.parseFloat(weigh);*//*

            *//*Toast.makeText(getBaseContext(),"here is getPrice:"+price,Toast.LENGTH_SHORT).show();*//*
        }*/
        if(!price.isEmpty()) {
            tvHelpMeSendBottomBarPrice.setVisibility(View.VISIBLE);
        /*Toast.makeText(getBaseContext(),"here is getPrice tvHelpMeSendBottomBarPrice:"+price,Toast.LENGTH_SHORT).show();*/
            tvHelpMeSendBottomBarPrice.setText("￥" + price);
        }
    }
    /*获取价格*/
    /*开始骑行路线规划*/
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
    /*开始骑行路线规划*/

    /*百度骑行导航初始化http://lbsyun.baidu.com/index.php?title=androidsdk/guide/bikenavi   http://wiki.lbsyun.baidu.com/cms/androidsdk/doc/v4_2_1/index.html*/

















    private void initOrderDetail(){
        orderDetail = new OrderDetail();

        xcCacheManager = XCCacheManager.getInstance(this);
        String usid = xcCacheManager.readCache("usid");
        if(usid == null){
            usid = "";
        }
        /*Toast.makeText(this,"usid:"+usid,Toast.LENGTH_LONG).show();*/
        orderDetail.setUserUsid(usid);
        System.out.println(orderDetail.getUserUsid());
        orderDetail.setClientaddrAddr(tvHelpMeSendContentSenderName.getText().toString() + ";"+tvHelpMeSendContentSenderTel.getText().toString() + ";"+tvHelpMeSendContentSenderAddr.getText().toString());
        orderDetail.setClientaddrAddr1(tvHelpMeSendContentReceiverName.getText().toString()+";"+tvHelpMeSendContentReceiverTel.getText().toString()+";"+tvHelpMeSendContentReceiverAddr.getText().toString());
        orderDetail.setOrderOrderprice(Double.parseDouble(price));
        orderDetail.setOrderLong((float)blon);
        orderDetail.setOrderLat((float)blat);
        orderDetail.setOrderDlat((float)rlat);
        orderDetail.setOrderDlong((float)rlon);
        orderDetail.setOrderMileage((double)dis);
        orderDetail.setClientaddrThings1(bclientaddrThings1);
        orderDetail.setClientaddr1Things1(rclientaddrThings1);

        orderDetail.setDetailsGoodsname("");
        orderDetail.setOrderName(tvHelpMeSendContentGoodsType.getText().toString());
        String time = tvHelpMeSendContentRecTime.getText().toString();
        TimeUtil timeUtil = new TimeUtil();
        if(time.equals("立即收件")){
            time = timeUtil.getCurrentDateTime();
            time = time.replace(" ","");
        }
        orderDetail.setOrderTimeliness(time);
        String remark = etHelpMeSendContentRemark.getText().toString();
        if((remark == null)||(remark.isEmpty())){
            remark = "";
        }
        orderDetail.setOrderRemark(remark);
        orderDetail.setClientaddrArea("");
        /*Toast.makeText(this,"helpmesend:"+tvHelpMeSendContentGoodsWeight.getText().toString(),Toast.LENGTH_SHORT).show();*/

        /*orderDetail.setOrderHeight(tvHelpMeSendContentGoodsWeight.getText().toString());*/
        /*orderDetail.setOrderHeight("10公斤以内");*/

    }


    protected void onResume(){
        super.onResume();
        /*Toast.makeText(this,"here is onResume",Toast.LENGTH_SHORT).show();*/
        startBikeNaviSearch();
    }
    protected void onStart(){
        super.onStart();
        /*Toast.makeText(this,"here is onResume",Toast.LENGTH_SHORT).show();*/
        startBikeNaviSearch();
    }
    protected void onStop(){
        super.onStop();


    }

    protected void onDestroy(){
        super.onDestroy();
        BaiduMapNavigation.finish(this);
/*        locationClient.unRegisterLocationListener(locationListener);*/
        mSearch.destroy();
    }
}