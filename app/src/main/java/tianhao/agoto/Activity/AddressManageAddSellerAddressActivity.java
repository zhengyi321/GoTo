package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 *
 * 地址管理:新建商家地址
 * Created by zhyan on 2017/2/18.
 */

public class AddressManageAddSellerAddressActivity extends Activity  implements OnGetGeoCoderResultListener,OnGetPoiSearchResultListener {

    /*百度地图定位*/
    @BindView(R.id.mv_addressmanageaddselleraddress_content_map)
    MapView mvAddressManageAddSellerAddressContentMap;
    @BindView(R.id.iv_addressmanageaddselleraddress_content_centerloc)
    ImageView ivAdressManageAddSellerAddressContentCenterLoc;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();

    private String addressLocation = "";
    private Boolean isFirst = true;
    private final int RESULT_SEARCH = 15;
    /*地址搜索*/
    @BindView(R.id.rly_addressmanageaddselleraddress_content_addresssearch)
    RelativeLayout rlyAddressManageAddSellerAddressContentAddRessSearch;
    /*地址搜索*/
    /*百度地图定位*/
    private BaiduMap.OnMapTouchListener mapTouchListener;
    private LatLng currentPt = new LatLng(0,0);
    private double blat,blon;
    private  final int accuracyCircleFillColor = 0xAAFFFF88;
    private  final int accuracyCircleStrokeColor = 0xAA00FF00;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private boolean isAddress = true;
    /*地名转换经纬度*/
    /*商家名称 地址*/
    @BindView(R.id.et_addressmanageaddselleraddress_content_addr)
    EditText etAddressManageAddSellerAddressContentAddr;
    @BindView(R.id.et_addressmanageaddselleraddress_content_name)
    EditText etAddressManageAddSellerAddressContentName;
    /*商家名称 地址*/
    /*后退*/
    @BindView(R.id.rly_addressmanageaddselleraddress_topbar_leftmenu)
    RelativeLayout rlyAddressManageAddSellerAddressTopBarLeftMenu;
    @OnClick(R.id.rly_addressmanageaddselleraddress_topbar_leftmenu)
    public void rlyAddressManageAddSellerAddressTopBarLeftMenuOnclick(){
        finish();
    }
    /*后退*/
    /*确认*/
    /*确认*/
    private GeoCoder search=null;
    /*关键字poi检索*/
    private PoiSearch poiSearch;
    /*关键字poi检索*/
    private String city;
    /*地名转换经纬度*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*initBaiDuSDK();*/
        setContentView(R.layout.activity_addressmanageaddselleraddress_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        initEditText();
        initMap();
    }

    private void initMap(){

             /*监听输入框的变化*/
        /*监听输入框的变化*/
        initPoiSearch();
        mBaiduMap = mvAddressManageAddSellerAddressContentMap.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        initOverlyWithMapView();

       /*地理编码初始化*/
        search = GeoCoder.newInstance();
        /*地理编码初始化*/
        /*设置编码监听者*/
        search.setOnGetGeoCodeResultListener(this);
        /*设置编码监听者*/
        initLocation();
        locationClient.start();
    }

    /*poi城市内检索*/
    private void initPoiSearch(){
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);

    }

    private void initEditText(){

        etAddressManageAddSellerAddressContentAddr.setHorizontallyScrolling(false);
        etAddressManageAddSellerAddressContentAddr.setMaxLines(Integer.MAX_VALUE);
    }
    /*地图移动坐标不动*/
    private void initOverlyWithMapView(){
        mapTouchListener = new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                  /*滑动动作的时候设置为滑动状态*/

                /*滑动动作的时候设置为滑动状态*/
            /*Toast.makeText(getBaseContext(),"here is ontouch",Toast.LENGTH_SHORT).show();*/
                //http://blog.csdn.net/sjf0115/article/details/7306284 获取控件在屏幕上的坐标
                int[] location = new int[2];
                ivAdressManageAddSellerAddressContentCenterLoc.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Point point = new Point(x, y);
            /*Toast.makeText(getBaseContext(),"x:"+x+"y:"+y,Toast.LENGTH_SHORT).show();*/
                //http://blog.csdn.net/sjf0115/article/details/7306284 获取控件在屏幕上的坐标
                currentPt = mBaiduMap.getProjection().fromScreenLocation(point);
                search.reverseGeoCode(new ReverseGeoCodeOption().location(currentPt));
                blat = currentPt.latitude;
                blon = currentPt.longitude;
            }
        };
        mBaiduMap.setOnMapTouchListener(mapTouchListener);


    }
    /*地图移动坐标不动*/




    /**配置定位SDK参数**/
    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//返回地址
        option.setIsNeedLocationDescribe(true);//返回地址周边描述
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);
    }



    /**接收异步返回的定位结果**/
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            if (location == null || mvAddressManageAddSellerAddressContentMap == null) {
                return;
            }

            if(isFirst){
                showCurrentPosition(location);
                isFirst = false;
            }
            /*showCurrentPosition(location);*/
        }
    }


    /**定位**/
    private void showCurrentPosition(BDLocation location){

        TextView textView = new TextView(this);
        Drawable drawable1 = getResources().getDrawable(R.drawable.arrow);
        drawable1.setBounds(0, 0, 40, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textView.setCompoundDrawables(drawable1,null,null,null);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromView(textView);
        /*定位蓝色点*/
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
        /*定位蓝色点*/
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        location(latLng);
        poiBeginSearch(latLng);
        /*etHelpMeBuyAddSellerAddressContentAddress.setText(location.getAddrStr() + location.getBuildingName() +location.getFloor()+location.getStreet()+location.getStreetNumber());*/
        addressLocation=location.getAddrStr()+" "+location.getLocationDescribe();
        etAddressManageAddSellerAddressContentAddr.setText(addressLocation);
        beginSearchLalByAddress(addressLocation);


    }
    /*输入地址poi附近检索*/
    private void poiBeginSearch(LatLng latLng){
        /*Toast.makeText(getBaseContext(),"poiBeginSearch",Toast.LENGTH_SHORT).show();*/
        etAddressManageAddSellerAddressContentName.setOnEditorActionListener(new MyEditorActionListener(latLng,true));
        etAddressManageAddSellerAddressContentAddr.setOnEditorActionListener(new MyEditorActionListener(latLng,false));

    }

    /*软键盘监听*/
    public class MyEditorActionListener implements TextView.OnEditorActionListener {
        private boolean isNameCall;
        private LatLng latLng;
        public MyEditorActionListener (LatLng lal,Boolean isName){

            isNameCall = isName;
            latLng = lal;
        }
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(isNameCall) {

                    String keyword = "";
                    keyword = v.getText().toString();
                    if((!keyword.isEmpty())&&(latLng != null)) {
                        isAddress = false;
                        poiSearchNearBy(keyword, latLng);
                    }else {
                        mBaiduMap.clear();
                    }
                    //写你要做的事情
                    /*Toast.makeText(getBaseContext(), "" + keyword, Toast.LENGTH_SHORT).show();*/
                    /*poiSearch.searchNearby((new PoiNearbySearchOption())
                            .location(latLng)
                            .radius(600000)
                            .keyword(keyword)
                            .pageNum(0).pageCapacity(30));*/

                }else{
                    isAddress = true;
                    String address = "";
                    address = v.getText().toString();
                    beginSearchLalByAddress(address);

                    /*beginSearchLalByAddress(address);*/
                }
                hideInput(AddressManageAddSellerAddressActivity.this);//隐藏软键盘

                return true;
            }
            return false;
        }
    }
    private InputMethodManager manager;
    private void hideInput(Activity activity) {
        // 输入法管理器 用户隐藏软键盘
        if(manager==null){
            manager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        }

        manager.hideSoftInputFromWindow(( activity)
                        .getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /*软键盘监听*/


    /*搜索附近的关键词*/
    private void getPoisFromKeyWordSearch(final List<PoiInfo> poiInfoList){
        /*ArrayList<MarkerOptions> markerOptionsList = new ArrayList<MarkerOptions>();*/
        Log.i("getPoisFromKeyWordSearch","this is getPoi");
        final List<Marker> markerList = new ArrayList<>();
        mBaiduMap.clear();
        if((poiInfoList != null)&&(poiInfoList.size() > 0)) {
            Log.i("getPoisFromKeyWordSearch",poiInfoList.size()+"");
            for(int i =0;i<poiInfoList.size();i++) {
                TextView textView = new TextView(this);
                Drawable drawable1 = getResources().getDrawable(R.drawable.nearly);
                drawable1.setBounds(0, 0, 45, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
                textView.setCompoundDrawables(drawable1, null, null, null);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);
                LatLng ll = new LatLng(poiInfoList.get(i).location.latitude,poiInfoList.get(i).location.longitude);
                /*BitmapDescriptor bitmap = null;*/
                //准备 marker option 添加 marker 使用
                MarkerOptions markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(ll);
                //获取添加的 marker 这样便于后续的操作

                markerList.add((Marker) mBaiduMap.addOverlay(markerOptions));

                /*markerOptionsList.add(markerOptions);*/
            }

            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    for(int i=0;i<markerList.size();i++) {
                        if(((Marker)markerList.get(i)) == marker) {
                            Button button = new Button(getBaseContext());
                            button.setText(poiInfoList.get(i).address);
                            button.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    mBaiduMap.hideInfoWindow();
                                }
                            });
                            LatLng ll = marker.getPosition();
                            blat = poiInfoList.get(i).location.latitude;
                            blon = poiInfoList.get(i).location.longitude;
                            InfoWindow mInfoWindow = new InfoWindow(button, ll, -47);
                            etAddressManageAddSellerAddressContentAddr.setText(poiInfoList.get(i).address);
                            mBaiduMap.showInfoWindow(mInfoWindow);
                        }
                    }
                    return false;
                }
            });
            /*mBaiduMap.addOverlays(markerOptionsList);*/
        }

    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if(result.getAllPoi() != null) {
            if(!isAddress) {
            /*找到关键词所标注的地方*/
                getPoisFromKeyWordSearch(result.getAllPoi());
            /*找到关键词所标注的地方*/
            }
            isAddress = true;
/*
            Toast.makeText(this,"this is onGetPoiResult"+result.getAllAddr().get(0).address,Toast.LENGTH_LONG).show();*/


        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult.getLocation() != null) {
            location(geoCodeResult.getLocation());
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(AddressManageAddSellerAddressActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
     /*   mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));*/
        addressLocation = result.getAddress()+ "  " + result.getSematicDescription();
        etAddressManageAddSellerAddressContentAddr.setText(addressLocation);
    }

    @OnClick(R.id.rly_addressmanageaddselleraddress_content_addresssearch)
    public void rlyAddressManageAddSellerAddressContentAddRessSearchOnclick(){

        Intent intent = new Intent(this,BaiduAddressSearchSuggestActivity.class);
        startActivityForResult(intent,RESULT_SEARCH);
    }

    /*poi附近检索*/
    private void poiSearchNearBy(String keyword,LatLng latLng){
        int indexBlank = keyword.indexOf(" ");
        if(indexBlank > 0){
            if((latLng != null)&&(keyword!=null)) {
                keyword = keyword.substring(0, indexBlank);
                poiSearch.searchNearby((new PoiNearbySearchOption())
                        .location(latLng)
                        .radius(9000)
                        .keyword(keyword)
                        .pageNum(0).pageCapacity(30));
            }
        }else{
            if((latLng != null)&&(keyword!=null)) {
                poiSearch.searchNearby((new PoiNearbySearchOption())
                        .location(latLng)
                        .radius(9000)
                        .keyword(keyword)
                        .pageNum(0).pageCapacity(30));
            }
        }
    }
    /*根据地名开始查找经纬度*/
    private void beginSearchLalByAddress(String address){
       /* String address = etHelpMeBuyAddSellerAddressContentAddress.getText().toString();*/
        int index = address.indexOf("市");
        try {
            if (index > 0) {
                String city = address.substring(0, index);
                address = address.substring(index, address.length());
                search.geocode(new GeoCodeOption().city(city).address(address));
            } else {
                search.geocode(new GeoCodeOption().city("温州市").address(address));
            }
        }catch (Exception e){

        }
    }

    /*根据地名开始查找经纬度*/

    /**经纬度地址动画显示在屏幕中间  有关mark网站的出处http://blog.csdn.net/callmesen/article/details/40540895**/
    private void location(LatLng ll){

        /*无论哪个调用此动画 都将经纬度赋值*/
        blat = ll.latitude;
        blon = ll.longitude;
        /*无论哪个调用此动画 都将经纬度赋值*/
       /* mBaiduMap.clear();*/
        //定义地图状态
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_SEARCH:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String address=b.getString("address");//str即为回传的值
                String lat = b.getString("lat");
                String lon = b.getString("lon");
                /*Toast.makeText(getBaseContext(),"RESULT_SEARCH:"+lat+" "+lon,Toast.LENGTH_SHORT).show();*/
                if((lat != null) && (lon != null)) {
                     /*blat = Double.parseDouble(lat);
                     blon = Double.parseDouble(lon);*/
                    LatLng ll = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                    location(ll);
                    etAddressManageAddSellerAddressContentAddr.setText(address);
                }


                break;
            default:
                break;
        }
    }


    protected void onDestroy(){
        mBaiduMap.clear();
        search.destroy();
        isFirst = true;
        mvAddressManageAddSellerAddressContentMap.onDestroy();
        poiSearch.destroy();
        locationClient.unRegisterLocationListener(locationListener);
        if(locationClient!=null){
            locationClient.stop();
        }

        super.onDestroy();

    }

    protected void onResume(){
        /*init();*/
        super.onResume();
        mvAddressManageAddSellerAddressContentMap.onResume();
    }
    protected void onPause(){
        super.onPause();
        mvAddressManageAddSellerAddressContentMap.onPause();

    }

}
