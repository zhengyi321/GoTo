package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.R;
import tianhao.agoto.Utils.SystemUtils;

/**
 * Created by zhyan on 2017/2/19.
 */

public class HelpMeBuyAddShopDetailActivity extends Activity implements BaiduMap.OnMapStatusChangeListener,OnGetGeoCoderResultListener {

    /*viewpage recycleview 历史记录 收藏地址 功能 begin*/
    @BindView(R.id.tv_helpmebuyaddselleraddress_tabbar_history)
    TextView tvHelpMeBuyAddSellerAddressTabBarHistory;
    @BindView(R.id.iv_helpmebuyaddselleraddress_tabbar_history)
    ImageView ivHelpMeBuyAddSellerAddressTabBarHistory;
    @BindView(R.id.tv_helpmebuyaddselleraddress_tabbar_collectaddress)
    TextView tvHelpMeBuyAddSellerAddressTabBarCollectAddress;
    @BindView(R.id.iv_helpmebuyaddselleraddress_tabbar_collectaddress)
    ImageView ivHelpMeBuyAddSellerAddressTabBarCollectAddress;
    @BindView(R.id.iv_helpmebuyaddselleraddress_tab_greenbottom)
    ImageView ivHelpMeBuyAddSellerAddressTabGreenBottom;
    @BindView(R.id.vp_helpmebuyaddselleraddress_content)
    ViewPager vpHelpMeBuyAddSellerAddressContent;
    @BindView(R.id.rly_helpmebuyaddselleraddress_tabbar_history)
    RelativeLayout rlyHelpMeBuyAddSellerAddressTabBarHistory;
    @BindView(R.id.rly_helpmebuyaddselleraddress_tabbar_collectaddress)
    RelativeLayout rlyHelpMeBuyAddSellerAddressTabBarCollectAddress;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private List<View> viewList; // Tab页面列表
    /*viewpage recycleview 历史记录 收藏地址 功能*/

    /*名称  地址*/
    @BindView(R.id.et_helpmebuyaddselleraddress_content_namecall)
    EditText etHelpMeBuyAddSellerAddressContentNameCall;

    /*名称  地址*/
    @BindView(R.id.rly_helpmebuy_addselleraddress_topbar_leftmenu)
    RelativeLayout rlyHelpMeBuyAddSellerAddressTopBarLeftMenu;
    @BindView(R.id.rly_helpmebuy_addselleraddress_topbar_rightmenu)
    RelativeLayout rlyHelpMeBuyAddSellerAddressTopBarRightMenu;

    /*百度地图定位 begin2*/
    @BindView(R.id.mv_helpmebuyaddselleraddress_content)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();
    private double latitude,latitudeLocation;
    private double longitude,longitudeLocation;
    private String addressLocation = "";
    private Boolean isFirst = true;
    private double blat,blon;
    @BindView(R.id.rly_helpmebuyaddselleraddress_addresssearch)
    RelativeLayout rlyHelpMeBuyAddSellerAddressAddressSearch;
    @BindView(R.id.lly_helpmebuyadd_shopdetail_searchaddress)
    LinearLayout llyHelpMeBuyAddShopDetailSearchAddress;

    /*地名转换经纬度*/
    @BindView(R.id.tv_helpmebuyaddselleraddress_content_address)
    TextView tvHelpMeBuyAddSellerAddressContentAddress;
    private GeoCoder search=null;
    private String city;
    /*地名转换经纬度*/
    /*百度地图定位 end2*/
    private final int RESULT_OK = 10;//startactivityforresult
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*initBaiDuSDK();*/
        setContentView(R.layout.activity_helpmebuyadd_shopdetail_lly);
        init();
    }
    /*取消返回*/
    @OnClick(R.id.rly_helpmebuy_addselleraddress_topbar_leftmenu)
    public void rlyHelpMeBuyAddSellerAddressTopBarLeftMenuOnclick(){
        this.finish();
    }
    /*取消返回*/
    /*信息确认返回*/
    @OnClick(R.id.rly_helpmebuy_addselleraddress_topbar_rightmenu)
    public void rlyHelpMeBuyAddSellerAddressTopBarRightMenuOnclick(){
        Bundle bundle = new Bundle();
        bundle.putString("nameCall",etHelpMeBuyAddSellerAddressContentNameCall.getText().toString());
        bundle.putString("address",tvHelpMeBuyAddSellerAddressContentAddress.getText().toString());
        bundle.putString("blat", "" + blat);
        bundle.putString("blon", "" + blon);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
    /*信息确认返回*/
    private void init(){
        ButterKnife.bind(this);
        initSwitchContent();

        initBaiDuMap();
        /*initGlassBg();*/
        /*initTran();*/


    }

    /*viewpage recycleview 历史记录 收藏地址 功能*/
    private void initSwitchContent(){
        InitTabBg(true);
        InitImageView();
        InitViewPager();
    }

    /**
     * 初始化头标
     */
    private void InitTabBg(Boolean isFirst) {
        if(isFirst) {
            tvHelpMeBuyAddSellerAddressTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeBuyAddSellerAddressActivityTabBarGreenBg));
            ivHelpMeBuyAddSellerAddressTabBarHistory.setImageResource(R.drawable.historyrecordselect);
            tvHelpMeBuyAddSellerAddressTabBarCollectAddress.setTextColor(getResources().getColor(R.color.colorHelpMeBuyAddSellerAddressActivityTabBarGrayBg));
            ivHelpMeBuyAddSellerAddressTabBarCollectAddress.setImageResource(R.drawable.collectaddressnormal);
        }else{
            tvHelpMeBuyAddSellerAddressTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeBuyAddSellerAddressActivityTabBarGrayBg));
            ivHelpMeBuyAddSellerAddressTabBarHistory.setImageResource(R.drawable.historyrecordnormal);
            tvHelpMeBuyAddSellerAddressTabBarCollectAddress.setTextColor(getResources().getColor(R.color.colorHelpMeBuyAddSellerAddressActivityTabBarGreenBg));
            ivHelpMeBuyAddSellerAddressTabBarCollectAddress.setImageResource(R.drawable.collectaddressselect);
        }
    }

    /**
     * 初始化动画
     */
    private void InitImageView() {

        SystemUtils systemUtils = new SystemUtils(this);
        int width = systemUtils.getWindowWidth();
        int height = systemUtils.getWindowHeight();
        int marginLeft = (((width/2)/2)/2);
        int ivWidth = (width/2)/2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ivWidth,getResources().getDimensionPixelSize(R.dimen.activity_myorder_tabbar_bottom_green_height));
        params.setMargins(marginLeft,0,0,0);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2  ) / 2;// 计算偏移量  screenW/有几个tab 就除以几
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        ivHelpMeBuyAddSellerAddressTabGreenBottom.setImageMatrix(matrix);
        ivHelpMeBuyAddSellerAddressTabGreenBottom.setLayoutParams(params);
    }
    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        viewList = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        viewList.add(mInflater.inflate(R.layout.activity_helpmebuyadd_shopdetail_content_vp_itemrv_lly, null));
        viewList.add(mInflater.inflate(R.layout.activity_helpmebuyadd_shopdetail_content_vp_itemrv_lly, null));
        vpHelpMeBuyAddSellerAddressContent.setAdapter(new MyPagerAdapter(viewList));
        vpHelpMeBuyAddSellerAddressContent.setCurrentItem(0);
        vpHelpMeBuyAddSellerAddressContent.setOnPageChangeListener(new MyOnPageChangeListener());

        List<String> dataList = new ArrayList<String>();
       /* dataList.add("");
        dataList.add("");
        dataList.add("");*/
        initRecycleView(0,dataList);
    }


    /**
     * ViewPager适配器
     */
    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {

            if (arg1 < 2) {
                ((ViewPager) arg0).addView(mListViews.get(arg1 % 2), 0);
            }

            return mListViews.get(arg1 % 3);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }


    }

    /*RecycleView适配器*/

    private class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ItemContentViewHolder>{

        private List<String> testList;
        private Context context;
        private LayoutInflater inflater;
        public MyRecycleViewAdapter(Context context,List<String> stringList){
            testList = stringList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void setDataList(List<String> dataList){
            this.testList = dataList;
            this.notifyDataSetChanged();
        }


        @Override
        public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyRecycleViewAdapter.ItemContentViewHolder(inflater.inflate(R.layout.activity_helpmebuyadd_shopdetail_content_vp_itemrv_item_lly, parent, false));

        }

        @Override
        public void onBindViewHolder(MyRecycleViewAdapter.ItemContentViewHolder holder, int position) {

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return testList.size();
        }


        public class ItemContentViewHolder extends RecyclerView.ViewHolder{


            public ItemContentViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    /*RecycleView适配器*/

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 ;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;

            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(200);
            ivHelpMeBuyAddSellerAddressTabGreenBottom.startAnimation(animation);

            List<String> dataList = new ArrayList<String>();
          /*  dataList.add("");
            dataList.add("");
            dataList.add("");
            dataList.add("");*/
            initRecycleView(arg0,dataList);
            switch (arg0){
                case 0:
                    InitTabBg(true);
                    break;
                case 1:
                    InitTabBg(false);

                    break;
            }
        }
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }

    }



    private void initRecycleView(int pos,List<String> dataList){
  /*      *//*多线程运行 行不通*//*
        MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(viewList.get(pos).getContext(),dataList);
        rv =(RecyclerView) viewList.get(pos).findViewById(R.id.rv_helpmebuyaddselleraddress_vp_item);
        rv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewList.get(pos).getContext());
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);
*/

        int count = pos + 1;
        if(count <= dataList.size()) {
            RecyclerView rv = null;
        /*多线程运行 行不通*/
            MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(viewList.get(pos).getContext(), dataList);
            rv = (RecyclerView) viewList.get(pos).findViewById(R.id.rv_helpmebuyadd_receiverdetail_vp_item);
            rv.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(viewList.get(pos).getContext());
            //设置为垂直布局，这也是默认的
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            //设置布局管理器
            rv.setLayoutManager(layoutManager);
        }
    }


    @OnClick(R.id.rly_helpmebuyaddselleraddress_tabbar_history)
    public void rlyHelpMeBuyAddSellerAddressTabBarHistoryOnclick(){
        vpHelpMeBuyAddSellerAddressContent.setCurrentItem(0);
    }

    @OnClick(R.id.rly_helpmebuyaddselleraddress_tabbar_collectaddress)
    public void rlyHelpMeBuyAddSellerAddressTabBarCollectAddressOnclick(){
        vpHelpMeBuyAddSellerAddressContent.setCurrentItem(1);

    }
    /*viewpage recycleview 历史记录 收藏地址 功能 end*/

    /*百度地图定位begin*/
    private void initBaiDuSDK(){
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }


    private void initBaiDuMap(){

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
    /**配置定位SDK参数**/
    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
       /* int span=1000;
        option.setScanSpan(span);*/
      /*  option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);*/
       /* option.setOpenGps(true);*/
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        /*option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.setEnableSimulateGps(false);*/

        option.setCoorType("bd09ll");// 设置定位结果类型
        option.setScanSpan(5000);// 设置发起定位请求的间隔时间,ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向
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
        /*etHelpMeBuyAddSellerAddressContentAddress.setText(location.getAddrStr() + location.getBuildingName() +location.getFloor()+location.getStreet()+location.getStreetNumber());*/
        latitudeLocation=location.getLatitude();
        longitudeLocation=location.getLongitude();
        addressLocation=location.getAddrStr();
        MyLocationData locationData=new MyLocationData.Builder()
                /*.accuracy(location.getRadius())*/
                /*.direction(100)*/.latitude(latitudeLocation)
                .longitude(longitudeLocation).build();
        mBaiduMap.setMyLocationData(locationData);
        location(latitudeLocation, longitudeLocation);
    }

    /**经纬度地址动画显示在屏幕中间  有关mark网站的出处http://blog.csdn.net/callmesen/article/details/40540895**/
    private void location(double latitude,double longitude){
        mBaiduMap.clear();


        LatLng ll = new LatLng(latitude, longitude);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(ll)
                /*.zoom(40)*/
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
        //准备 marker   的图片  定位图标
        /*BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.search_map);*/
        TextView textView = new TextView(this);
        Drawable drawable1 = getResources().getDrawable(R.drawable.search_map);
        drawable1.setBounds(0, 0, 20, 25);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textView.setCompoundDrawables(drawable1,null,null,null);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);
        /*BitmapDescriptor bitmap = null;*/
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
            Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
     /*   mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));*/
        LatLng latLng = result.getLocation();
        addressLocation = result.getAddress();
        /*if(isFirst) {*/
        tvHelpMeBuyAddSellerAddressContentAddress.setText(addressLocation+"  "+result.getSematicDescription());
        location(latLng.latitude,latLng.longitude);
        /*getLaLoFromCity();*/
       /* }else{
            *//**//*
        }*/
        /**/
    }
    /*根据经纬度搜索地址*/

    /*获取手指在地图上的经纬度*/
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
    /*获取手指在地图上的经纬度*/

    @OnClick(R.id.lly_helpmebuyadd_shopdetail_searchaddress)
    public void rlyHelpMeBuyAddSellerAddressAddressSearchOnclick(){
        Intent intent = new Intent(this,BaiduAddressSearchSuggestActivity.class);
        startActivityForResult(intent,RESULT_OK);
       /* getLaLoFromCity();*/
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String address=b.getString("address");//str即为回传的值
                String lat = b.getString("lat");
                String lon = b.getString("lon");
                if((lat != null) && (lon != null)) {
                     blat = Double.parseDouble(lat);
                     blon = Double.parseDouble(lon);
                    location(blat, blon);
                    tvHelpMeBuyAddSellerAddressContentAddress.setText(address);
                }


                break;
            default:
                break;
        }
    }

    private void getLaLoFromCity(){
        /*getCity();*/
        addressLocation = tvHelpMeBuyAddSellerAddressContentAddress.getText().toString();
        if(addressLocation!=null&&!addressLocation.equals("")){
            int indexProvince=addressLocation.indexOf("省");
            int indexCity=addressLocation.indexOf("市");
            if(addressLocation.length() > 0) {
                if(indexCity > 0){
                    city = addressLocation.substring(0, indexCity);
                    search.geocode(new GeoCodeOption().city(city).address(addressLocation));
                    return;
                }
                search.geocode(new GeoCodeOption().city("温州市").address(addressLocation));
            }
        }

        /*location(latitudeLocation, longitudeLocation);*/
        /*search=GeoCoder.newInstance();*/
       /* if(city != null) {
            search.geocode(new GeoCodeOption().city(city).address(addressLocation));
        }*/
      /*  *得到经纬度**/
       /* search.setOnGetGeoCodeResultListener(this);*/
    }

    /**得到当前所在城市**/
    private void getCity(){
        addressLocation = tvHelpMeBuyAddSellerAddressContentAddress.getText().toString();
        if(addressLocation!=null&&!addressLocation.equals("")){
            int indexProvince=addressLocation.indexOf("省");
            int indexCity=addressLocation.indexOf("市");
            if(indexCity < 0) {
                city = null;
            }else{
                city = addressLocation.substring(indexProvince + 1, indexCity);
            }
        }
    }







    /*百度地图 end*/

    /*根据经纬度搜索地址*/

    protected void onDestroy(){
        mBaiduMap.clear();
        search.destroy();
        isFirst = true;
        mMapView.onDestroy();
        locationClient.unRegisterLocationListener(locationListener);
        if(locationClient!=null){
            locationClient.stop();
        }
        super.onDestroy();
    }

    protected void onResume(){
        /*init();*/
        super.onResume();

    }
    protected void onPause(){
        super.onPause();
/*        locationClient.unRegisterLocationListener(locationListener);
        mBaiduMap.clear();
        search.destroy();*/
        /*isFirst = true;*/
    }
}