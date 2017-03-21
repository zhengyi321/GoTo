package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;
import tianhao.agoto.baidumap.Api.PoiOverlay;

/**
 *
 * 点击地图 显示地址 https://zhidao.baidu.com/question/1831203103409736180.html
 * 输入地址 显示坐标地图http://blog.csdn.net/shenyuanqing/article/details/48056289
 *
 * http://blog.csdn.net/column/details/android-jacksen-map.html
 * 地址管理新建联系人
 * Created by zhyan on 2017/2/18.
 *
 * http://www.2cto.com/kf/201203/122494.html通讯录
 */

public class AddressManageAddContacterActivity extends Activity  implements OnGetGeoCoderResultListener {
    /*百度地图定位*/
    @BindView(R.id.mv_addressmanageaddcontacter_content_map)
    MapView mvAddressManageAddContacterContentMap;
    @BindView(R.id.iv_addressmanageaddcontacter_content_centerloc)
    ImageView ivAddressManageAddContacterContentCenterLoc;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();

    private String addressLocation = "";
    private Boolean isFirst = true;

    /*地名转换经纬度*/

    private GeoCoder search=null;
    private String city;
    /*地名转换经纬度*/

    /*百度地图定位*/
    /*姓名 电话 联系地址*/
    @BindView(R.id.et_addressmanageaddcontacter_content_addr)
    EditText etAddressManageAddContacterContentAddr;
    @BindView(R.id.et_addressmanageaddcontacter_content_name)
    EditText etAddressManageAddContacterContentName;
    @BindView(R.id.et_addressmanageaddcontacter_content_tel)
    EditText etAddressManageAddContacterContentTel;
    /*姓名 电话 联系地址*/
    @BindView(R.id.rly_addressmanageaddcontacter_content_addresssearch)
    RelativeLayout rlyAddressManageAddContacterContentAddressSearch;
    /*读取手机通讯录*/
    @BindView(R.id.rly_addressmanageaddcontacter_content_phonecontact)
    RelativeLayout rlyAddressManageAddContacterContentPhoneContact;
    /*读取手机通讯录*/
    /*设为默认地址*/
    @BindView(R.id.rly_addressmanageaddcontacter_bottom_total)
    RelativeLayout rlyAddressManageAddContacterBottomTotal;
    /*设为默认地址*/
    /*返回上一层*/
    @BindView(R.id.rly_addressmanageaddcontacter_topbar_leftmenu)
    RelativeLayout rlyAddressManageAddContacterTopBarLeftMenu;
    private  final int accuracyCircleFillColor = 0xAAFFFF88;
    private  final int accuracyCircleStrokeColor = 0xAA00FF00;
    private final int RESULT_SEARCH = 15;
    private final int RESULT_CONTACTER = 11;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private GeoCoder mSearch;//地理编码
    private BaiduMap.OnMapTouchListener mapTouchListener;
    private LatLng currentPt = new LatLng(0,0);
    private double blat,blon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  initBaiDuSDK();*/
        setContentView(R.layout.activity_addressmanageaddcontacter_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        initEditText();
        initMap();
    }
    private void initEditText(){

        etAddressManageAddContacterContentAddr.setHorizontallyScrolling(false);
        etAddressManageAddContacterContentAddr.setMaxLines(Integer.MAX_VALUE);
    }

    /*返回*/
    @OnClick(R.id.rly_addressmanageaddcontacter_topbar_leftmenu)
    public void rlyAddressManageAddContacterTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回*/
    @OnClick(R.id.rly_addressmanageaddcontacter_content_phonecontact)
    public void rlyAddressManageAddContacterContentPhoneContactOnclick(){
        initGetTelNumFromPhone();
    }
    int PICK_CONTACT = 22;
    private void initGetTelNumFromPhone(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);//vnd.android.cursor.dir/contact
        startActivityForResult(intent, PICK_CONTACT);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_SEARCH:
                getAddressData(data);
                break;
            case RESULT_CONTACTER :
                if (resultCode == Activity.RESULT_OK) {
                    getPhoneContracts(data);
                }
                break;
            default:
                break;
        }
    }

    private void getAddressData(Intent data){
        if(data != null) {
            Bundle b=data.getExtras(); //data为B中回传的Intent
            String address=b.getString("address");//str即为回传的值
            String latt = b.getString("lat");
            String lonn = b.getString("lon");
            if((latt != null) && (lonn != null)) {
                LatLng latLng = new LatLng(Double.parseDouble(latt), Double.parseDouble(lonn));
                location(latLng);
                etAddressManageAddContacterContentAddr.setText(address);
            }
        }
    }
    public void getPhoneContracts(Intent data){
        if(data != null) {
            Uri contactData = data.getData();
            Cursor c = managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String phoneNumber = null;
                if (hasPhone.equalsIgnoreCase("1")) {
                    hasPhone = "true";
                } else {
                    hasPhone = "false";
                }
                etAddressManageAddContacterContentName.setText(name);
                if (Boolean.parseBoolean(hasPhone)) {
                    int contactId = c.getInt(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (phoneNumber != null) {
                            etAddressManageAddContacterContentTel.setText(phoneNumber);
                        }
                    }
                    phones.close();
                }
            }
        }

    }



    private void initMap(){

        mBaiduMap = mvAddressManageAddContacterContentMap.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
       /*地理编码初始化*/
        mSearch = GeoCoder.newInstance();
        /*地理编码初始化*/
        /*设置编码监听者*/
        mSearch.setOnGetGeoCodeResultListener(this);
        /*设置编码监听者*/
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        initOverlyWithMapView();
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);
        initLocation();
        locationClient.start();
        /**滑屏触发地图状态改变监听器**/

        search=GeoCoder.newInstance();
        /**根据经纬度得到屏幕中心点地址**/
        search.setOnGetGeoCodeResultListener(this);
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
                ivAddressManageAddContacterContentCenterLoc.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Point point = new Point(x, y);
            /*Toast.makeText(getBaseContext(),"x:"+x+"y:"+y,Toast.LENGTH_SHORT).show();*/
                //http://blog.csdn.net/sjf0115/article/details/7306284 获取控件在屏幕上的坐标
                currentPt = mBaiduMap.getProjection().fromScreenLocation(point);
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(currentPt));
                blat = currentPt.latitude;
                blon = currentPt.longitude;
            }
        };
        mBaiduMap.setOnMapTouchListener(mapTouchListener);


    }

    @OnClick(R.id.rly_addressmanageaddcontacter_content_addresssearch)
    public void rlyAddressManageAddContacterAddressSearchOnclick(){
        /*getLaLoFromCity();*/
        Intent intent = new Intent(this,BaiduAddressSearchSuggestActivity.class);
        startActivityForResult(intent,RESULT_SEARCH);
    }


    /**经纬度地址动画显示在屏幕中间  有关mark网站的出处http://blog.csdn.net/callmesen/article/details/40540895**/
    private void location(LatLng latLng){

         /*只要调用画面 就能赋值*/
        blat = latLng.latitude;
        blon = latLng.longitude;

        /*无论哪个调用此动画 都将经纬度赋值*/
       /* mBaiduMap.clear();*/
        //定义地图状态
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

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
            Toast.makeText(AddressManageAddContacterActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
     /*   mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));*/
        addressLocation = result.getAddress();
        etAddressManageAddContacterContentAddr.setText(addressLocation+"  "+result.getSematicDescription());
    }



    /**接收异步返回的定位结果**/
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location


            if(isFirst){
                showCurrentPosition(location);
                isFirst = false;
            }

        }
    }

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
        poiBeginSearch();
        /*etHelpMeBuyAddSellerAddressContentAddress.setText(location.getAddrStr() + location.getBuildingName() +location.getFloor()+location.getStreet()+location.getStreetNumber());*/
        addressLocation=location.getAddrStr()+" "+location.getLocationDescribe();
        etAddressManageAddContacterContentAddr.setText(addressLocation);
        beginSearchLalByAddress(addressLocation);

    }

    /*输入地址poi附近检索*/
    private void poiBeginSearch( ){
        /*Toast.makeText(getBaseContext(),"poiBeginSearch",Toast.LENGTH_SHORT).show();*/
        etAddressManageAddContacterContentAddr.setOnEditorActionListener(new MyEditorActionListener());

    }
    /*地图移动坐标不动*/
    /*软键盘监听*/
    public class MyEditorActionListener implements TextView.OnEditorActionListener {


        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                String keyword = "";
                keyword = v.getText().toString();
                if(!keyword.isEmpty()) {
                    beginSearchLalByAddress(keyword);
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
                hideInput(AddressManageAddContacterActivity.this);//隐藏软键盘

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

    @Override
    protected void onResume(){
        super.onResume();
        mvAddressManageAddContacterContentMap.onResume();
        mBaiduMap.clear();
    }

    @Override
    protected  void onPause(){
        super.onPause();
        mvAddressManageAddContacterContentMap.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mBaiduMap.clear();
        mBaiduMap = null;
        search.destroy();
        mSearch.destroy();
        mvAddressManageAddContacterContentMap.onDestroy();
        locationClient.unRegisterLocationListener(locationListener);
        if(locationClient!=null){
            locationClient.stop();
        }


    }









    /*百度地图定位*/
}
