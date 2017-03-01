package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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

public class AddressManageAddContacterActivity extends Activity  implements BaiduMap.OnMapStatusChangeListener,OnGetGeoCoderResultListener {
    /*百度地图定位*/
    @BindView(R.id.mv_addressmanageaddcontacter_content)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();
    private double latitude,latitudeLocation;
    private double longitude,longitudeLocation;
    private String addressLocation = "";
    private Boolean isFirst = true;

    /*地名转换经纬度*/
    @BindView(R.id.et_addressmanageaddcontacter_content)
    EditText etAddressManageAddContacterContent;
    private GeoCoder search=null;
    private String city;
    /*地名转换经纬度*/

    /*百度地图定位*/

    /*读取手机通讯录*/
    @BindView(R.id.rly_addressmanageaddcontacter_phonecontact)
    RelativeLayout rlyAddressManageAddContacterPhoneContact;
    /*读取手机通讯录*/
    /*返回上一层*/
    @BindView(R.id.rly_addressmanageaddcontacter_topbar_leftmenu)
    RelativeLayout rlyAddressManageAddContacterTopBarLeftMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  initBaiDuSDK();*/
        setContentView(R.layout.activity_addressmanageaddcontacter_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        initMap();
    }
    /*返回*/
    @OnClick(R.id.rly_addressmanageaddcontacter_topbar_leftmenu)
    public void rlyAddressManageAddContacterTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回*/
    @OnClick(R.id.rly_addressmanageaddcontacter_phonecontact)
    public void rlyAddressManageAddContacterPhoneContactOnclick(){
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
/*
        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String phoneNumber = null;
                        if ( hasPhone.equalsIgnoreCase("1")) {
                            hasPhone = "true";
                        } else {
                            hasPhone = "false" ;
                        }
                        if (Boolean.parseBoolean(hasPhone)) {
                            Cursor phones= getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
                            while (phones.moveToNext()) {
                                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            phones.close();
                        }
                    }
                }
                break;
        }*/
    }





    /*百度地图定位*/
    private void initBaiDuSDK(){
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
    private void initMap(){

        mBaiduMap = mMapView.getMap();
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);
        initLocation();
        locationClient.start();
        /**滑屏触发地图状态改变监听器**/
        mBaiduMap.setOnMapStatusChangeListener(this);
        search=GeoCoder.newInstance();
        /**根据经纬度得到屏幕中心点地址**/
        search.setOnGetGeoCodeResultListener(this);
    }



    @OnClick(R.id.rly_addressmanageaddcontacter_addresssearch)
    public void rlyAddressManageAddContacterAddressSearchOnclick(){
        getLaLoFromCity();

    }
    /**得到当前所在城市**/
    private void getCity(){
        addressLocation = etAddressManageAddContacterContent.getText().toString();
        if(addressLocation!=null&&!addressLocation.equals("")){
            int indexProvince=addressLocation.indexOf("省");
            int indexCity=addressLocation.indexOf("市");
            if((indexProvince < 0)||(indexCity < 0)) {

            }else{
                city = addressLocation.substring(indexProvince + 1, indexCity);
            }

        }
    }
    private void getLaLoFromCity(){
        getCity();
        /*location(latitudeLocation, longitudeLocation);*/
        /*search=GeoCoder.newInstance();*/
        if(city != null) {
            search.geocode(new GeoCodeOption().city(city).address(addressLocation));
        }
        /*search.geocode(new GeoCodeOption().city(city).address(addressLocation));*/
      /*  *得到经纬度**/
       /* search.setOnGetGeoCodeResultListener(this);*/
    }

    /**经纬度地址动画显示在屏幕中间  有关mark网站的出处http://blog.csdn.net/callmesen/article/details/40540895**/
    private void location(double latitude,double longitude){
        mBaiduMap.clear();
        LatLng ll = new LatLng(latitude, longitude);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(ll)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    //准备 marker 的图片
        /*BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.search_map);*/
        /*BitmapDescriptor bitmap = null;*/


        TextView textView = new TextView(this);
        Drawable drawable1 = getResources().getDrawable(R.drawable.search_map);
        drawable1.setBounds(0, 0, 20, 25);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textView.setCompoundDrawables(drawable1,null,null,null);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);
//准备 marker option 添加 marker 使用
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(ll);
//获取添加的 marker 这样便于后续的操作
        mBaiduMap.addOverlay(markerOptions);
        /*LatLng ll = new LatLng(latitude, longitude);*/
        /*MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);*/
        /*mBaiduMap.animateMapStatus(msu);*/
        /*mBaiduMap.setBuildingsEnabled(true);*/
        /*mBaiduMap.setMyLocationEnabled(true);*/
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {
    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {
    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        latitude = mapStatus.target.latitude;
        longitude = mapStatus.target.longitude;
        LatLng ptCenter = new LatLng(latitude, longitude);
        search.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult.getLocation() != null) {
            latitudeLocation = geoCodeResult.getLocation().latitude;
            longitudeLocation = geoCodeResult.getLocation().longitude;
            location(latitudeLocation, longitudeLocation);
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
        etAddressManageAddContacterContent.setText(addressLocation);
    }

    /**接收异步返回的定位结果**/
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.e("BaiduLocationApiDem", sb.toString());
            if(isFirst){
                showCurrentPosition(location);
                isFirst = false;
            }
            /*showCurrentPosition(location);*/
        }
    }

    /**配置定位SDK参数**/
    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
//        int span=1000;
//        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);
    }


    /**定位**/
    private void showCurrentPosition(BDLocation location){
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationData locationData=new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
//        MyLocationConfiguration.LocationMode locationMode=MyLocationConfiguration.LocationMode.NORMAL;
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
//        MyLocationConfiguration config = new MyLocationConfiguration(locationMode, true, mCurrentMarker);
//        baiduMap.setMyLocationConfigeration(config);
        mBaiduMap.setMyLocationData(locationData);
        latitudeLocation=location.getLatitude();
        longitudeLocation=location.getLongitude();
        addressLocation=location.getAddrStr();
        location(latitudeLocation, longitudeLocation);
    }





    @Override
    protected void onResume(){
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected  void onPause(){
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mMapView.onDestroy();
        locationClient.unRegisterLocationListener(locationListener);
        if(locationClient!=null){
            locationClient.stop();
        }


    }










    /*百度地图定位*/
/*
    private void initBaiDuSDK(){
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }

    private void initBaiDuMap(){
        mapView = (MapView) this.findViewById(R.id.mv_addressmanageaddcontacter_content);
        baiduMap = mapView.getMap();// 获取地图控件引用
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);

        locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        locationClient.registerLocationListener(myListener); // 注册监听函数
        this.setLocationOption();   //设置定位参数
        locationClient.start(); // 开始定位
        // baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // 设置为一般地图

        // baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE); //设置为卫星地图
        // baiduMap.setTrafficEnabled(true); //开启交通图
    }


    // 三个状态实现地图生命周期管理
    @Override
    protected void onDestroy() {
        //退出时销毁定位
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        // TODO Auto-generated method stub
        super.onDestroy();
        mapView.onDestroy();
        mapView = null;


    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mapView.onPause();
    }






    *//**
     * 设置定位参数
     *//*
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setEnableSimulateGps(true);//设置是否允许模拟GPS true:允许； false:不允许，默认为false
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setIsNeedAltitude(true);//设置是否需要返回海拔高度信息，可以在BDLocation.getAltitude()中得到数据，GPS定位结果中默认返回，默认值Double.MIN_VALUE
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        option.setIsNeedLocationDescribe(true);//设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"， 可以用作地址信息的补充
        option.setIsNeedLocationPoiList(true);//设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setLocationNotify(true);
        option.setOpenAutoNotifyMode();//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        option.setServiceName("浙江省乐清市柳市镇镇政府");
        option.setProdName("浙江省乐清市柳市镇镇政府");
        locationClient.setLocOption(option);
    }*/
/*
    public BDLocationListener myListener = new BDLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation location) {
                    // map view 销毁后不在处理新接收的位置
                    if (location == null || mapView == null)
                        return;

                    MyLocationData locData = new MyLocationData.Builder()
                            .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(100).latitude(location.getLatitude())
                            .longitude(location.getLongitude()).build();
                    mBaiduMap.setMyLocationData(locData);    //设置定位数据


                    if (isFirstLoc) {
                        isFirstLoc = false;


                        LatLng ll = new LatLng(location.getLatitude(),
                                location.getLongitude());
                        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);   //设置地图中心点以及缩放级别
//              MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                        mBaiduMap.animateMapStatus(u);
                    }
                }


            };*/
    /*百度地图定位*/
}
