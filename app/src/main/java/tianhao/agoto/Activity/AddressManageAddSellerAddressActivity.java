package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

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

public class AddressManageAddSellerAddressActivity extends Activity  implements BaiduMap.OnMapStatusChangeListener,OnGetGeoCoderResultListener {

    /*百度地图定位*/
    @BindView(R.id.mv_addressmanageaddselleraddressr_content)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();
    private double latitude,latitudeLocation;
    private double longitude,longitudeLocation;
    private String addressLocation = "";
    private Boolean isFirst = true;

    @BindView(R.id.rly_addressmanageaddselleraddress_addresssearch)
    RelativeLayout rlyAddressManageAddSellerAddressAddressSearch;
    /*百度地图定位*/

    /*地名转换经纬度*/
    @BindView(R.id.et_addressmanageaddselleraddress_content)
    EditText etAddressManageAddSellerAddressContent;
    private GeoCoder search=null;
    private String city;
    /*地名转换经纬度*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaiDuSDK();
        setContentView(R.layout.activity_addressmanageaddselleraddress_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        initMap();
    }

    private void initMap(){

        mBaiduMap = mMapView.getMap();
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);
        initLocation();
        locationClient.start();
        /**滑屏触发地图状态改变监听器**/
        mBaiduMap.setOnMapStatusChangeListener(this);
        search= GeoCoder.newInstance();
        /**根据经纬度得到屏幕中心点地址**/
        search.setOnGetGeoCodeResultListener(this);
    }
    /*百度地图定位*/
    private void initBaiDuSDK(){
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
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
            Toast.makeText(AddressManageAddSellerAddressActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
     /*   mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));*/
        addressLocation = result.getAddress();
        etAddressManageAddSellerAddressContent.setText(addressLocation);
    }

    @OnClick(R.id.rly_addressmanageaddselleraddress_addresssearch)
    public void rlyAddressManageAddSellerAddressAddressSearchOnclick(){
        getLaLoFromCity();

    }


    private void getLaLoFromCity(){
        getCity();
        /*location(latitudeLocation, longitudeLocation);*/
        /*search=GeoCoder.newInstance();*/
        search.geocode(new GeoCodeOption().city(city).address(addressLocation));
      /*  *得到经纬度**/
       /* search.setOnGetGeoCodeResultListener(this);*/
    }

    /**得到当前所在城市**/
    private void getCity(){
        addressLocation = etAddressManageAddSellerAddressContent.getText().toString();
        if(addressLocation!=null&&!addressLocation.equals("")){
            int indexProvince=addressLocation.indexOf("省");
            int indexCity=addressLocation.indexOf("市");
            city = addressLocation.substring(indexProvince + 1, indexCity);

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
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.greenpoint);
        /*BitmapDescriptor bitmap = null;*/
//准备 marker option 添加 marker 使用
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(ll);
//获取添加的 marker 这样便于后续的操作

        mBaiduMap.addOverlay(markerOptions);
        /*LatLng ll = new LatLng(latitude, longitude);*/
        /*MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);*/
        /*mBaiduMap.animateMapStatus(msu);*/
        /*mBaiduMap.setBuildingsEnabled(true);*/
        /*mBaiduMap.setMyLocationEnabled(true);*/
    }
}
