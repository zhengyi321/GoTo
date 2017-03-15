package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
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
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Common.Widget.EditText.EditTextWithDel;
import tianhao.agoto.R;
import tianhao.agoto.Utils.SystemUtils;

/**
 * Created by admin on 2017/2/28.
 */

public class HelpMeBuyAddReceiverDetailActivity extends Activity implements BaiduMap.OnMapStatusChangeListener,OnGetGeoCoderResultListener {

    /*viewpage recycleview 历史记录 收藏地址 功能 begin*/
    @BindView(R.id.tv_helpmebuyadd_receiverdetail_tabbar_history)
    TextView tvHelpMeBuyAddReceiverDetailTabBarHistory;
    @BindView(R.id.iv_helpmebuyadd_receiverdetail_tabbar_history)
    ImageView ivHelpMeBuyAddReceiverDetailTabBarHistory;
    @BindView(R.id.tv_helpmebuyadd_receiverdetail_tabbar_collectaddress)
    TextView tvHelpMeBuyAddReceiverDetailTabBarCollectAddress;
    @BindView(R.id.iv_helpmebuyadd_receiverdetail_tabbar_collectaddress)
    ImageView ivHelpMeBuyAddReceiverDetailTabBarCollectAddress;
    @BindView(R.id.iv_helpmebuyadd_receiverdetail_tab_greenbottom)
    ImageView ivHelpMeBuyAddReceiverDetailTabGreenBottom;
    @BindView(R.id.vp_helpmebuyadd_receiverdetail_content)
    ViewPager vpHelpMeBuyAddReceiverDetailContent;
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_tabbar_history)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailTabBarHistory;
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_tabbar_collectaddress)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailTabBarCollectAddress;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private List<View> viewList; // Tab页面列表
    /*viewpage recycleview 历史记录 收藏地址 功能*/

    /*百度地图定位 begin2*/
    @BindView(R.id.mv_helpmebuyadd_receiverdetail_content)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();
    private double latitude,latitudeLocation;
    private double longitude,longitudeLocation;
    private String addressLocation = "";
    private Boolean isFirst = true;

/*
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_addresssearch)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailAddressSearch;
*/


    /*地名转换经纬度*/
    @BindView(R.id.tv_helpmebuyadd_receiverdetail_contentaddress)
    TextView tvHelpMeBuyAddReceiverDetailContentAddress;
    private GeoCoder search=null;


    /*地名转换经纬度*/
    /*百度地图定位 end2*/
    @BindView(R.id.lly_helpmebuyadd_receiverdetail_addresssearch)
    LinearLayout llyHelpMeBuyAddReceiverDetailSearchAddress;
    /*手机通讯录*/
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_addcontacter)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailAddContacter;
    @BindView(R.id.et_helpmebuyadd_receiverdetail_content_name)
    EditText etHelpMeBuyAddReceiverDetailContentName;
    @BindView(R.id.et_helpmebuyadd_receiverdetail_content_tel)
    EditText etHelpMeBuyAddReceiverDetailContentTel;

    /*手机通讯录*/
    /*返回*/
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_topbar_leftmenu)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailTopBarLeftMenu;
    /*返回*/
    /*确认*/
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_topbar_rightmenu)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailTopBarRightMenu;
    /*确认*/
    private final int RESULT_OK = 11;
    private final int RESULT_SEARCH = 15;
    private final int RESULT_CONTACTER = 12;
    private Double rlat,rlon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* initBaiDuSDK();*/
        setContentView(R.layout.activity_helpmebuyadd_receiverdetail_lly);
        init();
    }


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
        initEditAddress();
    }
    /*初始化输入地址框 保证随时找到新地址*/
    private void initEditAddress(){
        /*搜索参数初始化*/
       /* suggestionSearchOption = new SuggestionSearchOption();*/
        // 实例化PoiSearch
  /*      mpoiSearch = PoiSearch.newInstance();
        // 注册搜索事件监听
        mpoiSearch.setOnGetPoiSearchResultListener(this);*/

    }

    /*初始化输入地址框 保证随时找到新地址*/


    /*返回上级菜单*/
    @OnClick(R.id.rly_helpmebuyadd_receiverdetail_topbar_leftmenu)
    public void rlyHelpMeBuyAddReceiverDetailTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回上级菜单*/
    /*信息确认返回*/
    @OnClick(R.id.rly_helpmebuyadd_receiverdetail_topbar_rightmenu)
    public void rlyHelpMeBuyAddReceiverDetailTopBarRightMenuOnclick(){
        Bundle bundle = new Bundle();
        bundle.putString("nameCall",etHelpMeBuyAddReceiverDetailContentName.getText().toString());
        bundle.putString("tel",etHelpMeBuyAddReceiverDetailContentTel.getText().toString());
        bundle.putString("address",tvHelpMeBuyAddReceiverDetailContentAddress.getText().toString());
        bundle.putString("rlat", "" + rlat);
        bundle.putString("rlon", "" + rlon);
        /*Toast.makeText(getBaseContext(),"rlyHelpMeBuyAddReceiverDetailTopBarRightMenuOnclick:"+rlat+" "+rlon+" ",Toast.LENGTH_SHORT).show();*/
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
    /*信息确认返回*/
    /*通讯录  http://www.2cto.com/kf/201203/122494.html
    *http://5200415.blog.51cto.com/3851969/969821
    * */
    @OnClick(R.id.rly_helpmebuyadd_receiverdetail_addcontacter)
    public void rlyHelpMeBuyAddReceiverDetailAddContacterOnclick(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);//vnd.android.cursor.dir/contact
        startActivityForResult(intent, RESULT_CONTACTER);
 /*       getPhoneContracts(this);*/
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
                 etHelpMeBuyAddReceiverDetailContentName.setText(name);
                 if (Boolean.parseBoolean(hasPhone)) {
                     int contactId = c.getInt(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                     Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                     while (phones.moveToNext()) {
                         phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                         if (phoneNumber != null) {
                             etHelpMeBuyAddReceiverDetailContentTel.setText(phoneNumber);
                         }
                     }
                     phones.close();
                 }
             }
         }

     }
    /*通讯录
    * http://www.2cto.com/kf/201203/122494.html
    * http://5200415.blog.51cto.com/3851969/969821
    * */
    @OnClick(R.id.lly_helpmebuyadd_receiverdetail_addresssearch)
    public void llyHelpMeBuyAddReceiverDetailSearchAddressOnclick(){
        Intent intent = new Intent(this,BaiduAddressSearchSuggestActivity.class);
        startActivityForResult(intent,RESULT_SEARCH);
    }


    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        /*Toast.makeText(this,"resultcode"+resultCode,Toast.LENGTH_LONG).show();*/
        switch (reqCode) {
            case (RESULT_CONTACTER) :
                if (resultCode == Activity.RESULT_OK) {
                    getPhoneContracts(data);
                }
                break;
            case RESULT_SEARCH:
                getAddressData(data);
                break;
            default:
                break;
        }
    }
    private void getAddressData(Intent data){
        if(data != null) {
            Bundle b = data.getExtras(); //data为B中回传的Intent
            String address = b.getString("address");//str即为回传的值
            String lat = b.getString("lat");
            String lon = b.getString("lon");
            if ((lat != null) && (lon != null)) {
                rlat = Double.parseDouble(lat);
                rlon = Double.parseDouble(lon);
                location(rlat, rlon);
                tvHelpMeBuyAddReceiverDetailContentAddress.setText(address);
            }
        }
    }

    /**
     * 初始化头标
     */
    private void InitTabBg(Boolean isFirst) {
        if(isFirst) {
            tvHelpMeBuyAddReceiverDetailTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGreenBg));
            ivHelpMeBuyAddReceiverDetailTabBarHistory.setImageResource(R.drawable.historyrecordselect);
            tvHelpMeBuyAddReceiverDetailTabBarCollectAddress.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGrayBg));
            ivHelpMeBuyAddReceiverDetailTabBarCollectAddress.setImageResource(R.drawable.collectaddressnormal);
        }else{
            tvHelpMeBuyAddReceiverDetailTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGrayBg));
            ivHelpMeBuyAddReceiverDetailTabBarHistory.setImageResource(R.drawable.historyrecordnormal);
            tvHelpMeBuyAddReceiverDetailTabBarCollectAddress.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGreenBg));
            ivHelpMeBuyAddReceiverDetailTabBarCollectAddress.setImageResource(R.drawable.collectaddressselect);
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
        ivHelpMeBuyAddReceiverDetailTabGreenBottom.setImageMatrix(matrix);
        ivHelpMeBuyAddReceiverDetailTabGreenBottom.setLayoutParams(params);
    }

    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        viewList = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        viewList.add(mInflater.inflate(R.layout.activity_helpmebuyadd_shopdetail_content_vp_itemrv_lly, null));
        viewList.add(mInflater.inflate(R.layout.activity_helpmebuyadd_shopdetail_content_vp_itemrv_lly, null));
        vpHelpMeBuyAddReceiverDetailContent.setAdapter(new MyPagerAdapter(viewList));
        vpHelpMeBuyAddReceiverDetailContent.setCurrentItem(0);
        vpHelpMeBuyAddReceiverDetailContent.addOnPageChangeListener(new MyOnPageChangeListener());
        List<String> dataList = new ArrayList<String>();
   /*     dataList.add("");
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
        public MyRecycleViewAdapter.ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyRecycleViewAdapter.ItemContentViewHolder(inflater.inflate(R.layout.activity_helpmebuyadd_receiverdetail_content_vp_itemrv_item_lly, parent, false));

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
            ivHelpMeBuyAddReceiverDetailTabGreenBottom.startAnimation(animation);

            List<String> dataList = new ArrayList<String>();
           /* dataList.add("");
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
    @OnClick(R.id.rly_helpmebuyadd_receiverdetail_tabbar_history)
    public void rlyHelpMeSendAddContacterTabBarHistoryOnclick(){
        vpHelpMeBuyAddReceiverDetailContent.setCurrentItem(0);
    }

    @OnClick(R.id.rly_helpmebuyadd_receiverdetail_tabbar_collectaddress)
    public void rlyHelpMeSendAddContacterTabBarCollectAddressOnclick(){
        vpHelpMeBuyAddReceiverDetailContent.setCurrentItem(1);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //旋转，移动，缩放
        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
                //放大地图缩放级别，每次放大一个级别

                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case KeyEvent.KEYCODE_2:
                //每次缩小一个级别
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());

                break;
            case KeyEvent.KEYCODE_3:
                //以一个点为中心旋转
                //获取地图当前的状态
                MapStatus mapStatus = mBaiduMap.getMapStatus();
                float rotate = mapStatus.rotate;
                /*Log.d(TAG,  "rotate:" + rotate);*/

                //旋转范围 0-360
                MapStatus newRotate = new MapStatus.Builder().rotate(rotate+30).build();

                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(newRotate));
                break;
            case KeyEvent.KEYCODE_4:
                //以一条直线为轴，旋转 调整俯仰角 overlook
                //范围 0-45
                float overlook = mBaiduMap.getMapStatus().overlook;
                MapStatus overStatus = new MapStatus.Builder().overlook(overlook-5).build();
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(overStatus));
                break;
            case KeyEvent.KEYCODE_5:
                //移动
                MapStatusUpdate moveStatusUpdate = MapStatusUpdateFactory.newLatLng(new LatLng(40.065796,116.349868));
                //带动画的更新地图状态，还是300毫秒
                mBaiduMap.animateMapStatus(moveStatusUpdate);
                break;
            default:
                break;
        }


        return super.onKeyDown(keyCode, event);
    }


    private void initBaiDuMap(){

        mBaiduMap = mMapView.getMap();
        //设置缩放级别，默认级别为12
        MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(12);;
        mBaiduMap.setMapStatus(mapstatusUpdate);
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
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

        /*int span=1000;
        option.setScanSpan(span);*/
      /*  option.setOpenGps(true);*/
/*        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);*/
       /* option.setIsNeedLocationPoiList(true);
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
        /*只要调用画面 就能赋值*/
        rlat = latitude;
        rlon = longitude;
        /*只要调用画面 就能赋值*/
        mBaiduMap.clear();
        LatLng ll = new LatLng(latitude, longitude);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(ll)
                /*.zoom(40)*/
                .build();
        try {
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            if (mMapStatusUpdate != null) {

                mBaiduMap.animateMapStatus(mMapStatusUpdate);
            }
        }catch (Exception e){
        }
        //准备 marker   的图片  定位图标
        /*BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.search_map);*/
        TextView textView = new TextView(this);
        Drawable drawable1 = getResources().getDrawable(R.drawable.search_map);
        drawable1.setBounds(0, 0, 20, 25);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textView.setCompoundDrawables(drawable1,null,null,null);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);
        /*BitmapDescriptor bitmap = null;*/
//准备 marker option 添加 marker 使用
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(ll).zIndex(12).period(10);
//获取添加的 marker 这样便于后续的操作


            // 生长动画
        /*markerOptions.animateType(MarkerAnimateType.grow);*/
        mBaiduMap.addOverlay(markerOptions);
        markerOptions.animateType(MarkerAnimateType.grow);
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
        tvHelpMeBuyAddReceiverDetailContentAddress.setText(addressLocation+"  "+result.getSematicDescription());
        location(latLng.latitude,latLng.longitude);

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
        /*设置反地理编码位置坐标 http://wiki.lbsyun.baidu.com/cms/androidsdk/doc/v4_2_1/index.html*/
        search.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
    }








    /*百度地图 end*/


    protected void onDestroy(){
        mBaiduMap.clear();
        mBaiduMap.setMyLocationEnabled(false);
        search.destroy();
        isFirst = true;
        mMapView.onDestroy();
        locationClient.unRegisterLocationListener(locationListener);
        if(locationClient!=null){
            locationClient.stop();
        }
        super.onDestroy();

    }
    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMapView.onPause();
    }
}
