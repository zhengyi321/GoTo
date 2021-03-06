package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
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
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Common.Widget.XRecycleView.XRecyclerView;
import tianhao.agoto.R;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import tianhao.agoto.Utils.SystemUtils;
import android.widget.TextView.OnEditorActionListener;
/**
 * http://lbsyun.baidu.com/index.php?title=androidsdk/guide/retrieval
 * Created by zhyan on 2017/2/19.
 */

public class HelpMeBuyAddShopDetailActivity extends Activity implements OnGetGeoCoderResultListener,OnGetPoiSearchResultListener {

    /*viewpage recycleview 历史记录 收藏地址 功能 begin*/
    @BindView(R.id.tv_helpmebuyaddselleraddress_tabbar_history)
    TextView tvHelpMeBuyAddSellerAddressTabBarHistory;
    @BindView(R.id.iv_helpmebuyaddselleraddress_tabbar_history)
    ImageView ivHelpMeBuyAddSellerAddressTabBarHistory;
    @BindView(R.id.tv_helpmebuyaddselleraddress_tabbar_nearaddress)
    TextView tvHelpMeBuyAddSellerAddressTabBarNearAddress;
    @BindView(R.id.iv_helpmebuyaddselleraddress_tabbar_nearaddress)
    ImageView ivHelpMeBuyAddSellerAddressTabBarNearAddress;
    @BindView(R.id.iv_helpmebuyaddselleraddress_tab_greenbottom)
    ImageView ivHelpMeBuyAddSellerAddressTabGreenBottom;
    @BindView(R.id.vp_helpmebuyaddselleraddress_content)
    ViewPager vpHelpMeBuyAddSellerAddressContent;
    @BindView(R.id.rly_helpmebuyaddselleraddress_tabbar_history)
    RelativeLayout rlyHelpMeBuyAddSellerAddressTabBarHistory;
    @BindView(R.id.rly_helpmebuyaddselleraddress_tabbar_nearaddress)
    RelativeLayout rlyHelpMeBuyAddSellerAddressTabBarNearAddress;
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
    MapView mvHelpMeBuyAddSellerAddressContent;
    @BindView(R.id.iv_helpmebuyadd_shopdetail_content_centerloc)
    ImageView ivHelpMeBuyAddShopdetailContentCenterLoc;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();
    private BaiduMap.OnMapTouchListener mapTouchListener;
    private String addressLocation = "";
    private Boolean isFirst = true;
    private double blat,blon;
    @BindView(R.id.rly_helpmebuyaddselleraddress_addresssearch)
    RelativeLayout rlyHelpMeBuyAddSellerAddressAddressSearch;
    @BindView(R.id.lly_helpmebuyadd_shopdetail_searchaddress)
    LinearLayout llyHelpMeBuyAddShopDetailSearchAddress;

    /*地名转换经纬度*/
    @BindView(R.id.et_helpmebuyaddselleraddress_content_address)
    EditText etHelpMeBuyAddSellerAddressContentAddress;
    private boolean isAddress = true;

    /*地名转换经纬度*/
    /*上拉*/
    @BindView(R.id.rly_helpmebuyadd_shopdetail_content_up)
    RelativeLayout rlyHelpMeBuyAddShopDetailContentUp;
    @BindView(R.id.lly_helpmebuyadd_shopdetail_bottom)
    LinearLayout llyHelpMeBuyAddShopDetailBottom;
    @OnClick(R.id.rly_helpmebuyadd_shopdetail_content_up)
    public void rlyHelpMeBuyAddShopDetailContentUpOnclick(){
        int tempHeight = 0;
        if(!isUp){
            isUp = true;
            ViewGroup.LayoutParams layoutParams = llyHelpMeBuyAddShopDetailBottom.getLayoutParams();
            SystemUtils systemUtils = new SystemUtils(this);
            tempHeight = layoutParams.height;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_enter);
            animation.setDuration(1000);
            layoutParams.height = 0;
            llyHelpMeBuyAddShopDetailBottom.setLayoutParams(layoutParams);
            llyHelpMeBuyAddShopDetailBottom.startAnimation(animation);
            layoutParams.height += systemUtils.getWindowHeight()/2;
            llyHelpMeBuyAddShopDetailBottom.setLayoutParams(layoutParams);
            ivHelpMeBuyAddShopDetailContentUpArrow.setBackgroundResource(R.drawable.down_arrow);
            /*rlyHelpMeBuyAddShopDetailContentUp.setAnimation(R.style.PopupAnimation);;*/
        }else{
            ViewGroup.LayoutParams layoutParams = llyHelpMeBuyAddShopDetailBottom.getLayoutParams();

             layoutParams.height =tempHeight ;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_exit);
            /*animation.setDuration(1000);*/
            llyHelpMeBuyAddShopDetailBottom.startAnimation(animation);
            llyHelpMeBuyAddShopDetailBottom.setLayoutParams(layoutParams);
            isUp = false;
            ivHelpMeBuyAddShopDetailContentUpArrow.setBackgroundResource(R.drawable.up_arrow);
        }
    }
    @BindView(R.id.iv_helpmebuyadd_shopdetail_content_uparrow)
    ImageView ivHelpMeBuyAddShopDetailContentUpArrow;
    private boolean isUp = false;
    /*上拉*/

    /*关键字poi检索*/
    private PoiSearch poiSearch;
    /*关键字poi检索*/
    private GeoCoder mSearch;//地理编码
    /*百度地图定位 end2*/
    private final int RESULT_OK = 10;//startactivityforresult
    private final int RESULT_SEARCH = 15;
    private  final int accuracyCircleFillColor = 0xAAFFFF88;
    private  final int accuracyCircleStrokeColor = 0xAA00FF00;
    private LocationMode mCurrentMode;
    private LatLng currentPt = new LatLng(0,0);
    private Marker mMarkerA;
    InitRecycleView initRecycleView;
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
        bundle.putString("address",etHelpMeBuyAddSellerAddressContentAddress.getText().toString());
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
        initEditText();
        initSwitchContent();
        /*Thread2 thread2 = new Thread2();
        thread2.start();*/
        initBaiDuMap();
        /*initGlassBg();*/
        /*initTran();*/
    }

    private void initEditText(){

        etHelpMeBuyAddSellerAddressContentAddress.setHorizontallyScrolling(false);
        etHelpMeBuyAddSellerAddressContentAddress.setMaxLines(Integer.MAX_VALUE);
    }


    /*viewpage recycleview 历史记录 收藏地址 功能*/
    private void initSwitchContent(){
  /*      InitTabBg(true,true);*/
        InitImageView();
        InitViewPager();

    }



    /**
     * 初始化头标
     */
    private void InitTabBg(int ag0) {

            /*Toast.makeText(this,"arg:"+ag0,Toast.LENGTH_LONG).show();*/

            int one = offset * 2;// 页卡1 -> 页卡2 偏移量
            int two = one * 2;// 页卡1 -> 页卡3 偏移量
            Animation animation = null;
        switch (ag0) {
           /* if (isFirst) {*/
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                currIndex = 0;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(200);
                ivHelpMeBuyAddSellerAddressTabGreenBottom.startAnimation(animation);
                tvHelpMeBuyAddSellerAddressTabBarNearAddress.setTextColor(getResources().getColor(R.color.colorHelpMeBuyAddSellerAddressActivityTabBarGreenBg));
                ivHelpMeBuyAddSellerAddressTabBarNearAddress.setImageResource(R.drawable.collectaddressselect);
                ivHelpMeBuyAddSellerAddressTabBarHistory.setImageResource(R.drawable.historyrecordnormal);
                tvHelpMeBuyAddSellerAddressTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeBuyAddSellerAddressActivityTabBarGrayBg));
                beginSearchLalByAddress(etHelpMeBuyAddSellerAddressContentAddress.getText().toString());
                break;
         /*   } else {*/
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                currIndex = 1;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(200);
                ivHelpMeBuyAddSellerAddressTabGreenBottom.startAnimation(animation);
                tvHelpMeBuyAddSellerAddressTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeBuyAddSellerAddressActivityTabBarGreenBg));
                ivHelpMeBuyAddSellerAddressTabBarHistory.setImageResource(R.drawable.historyrecordselect);
                tvHelpMeBuyAddSellerAddressTabBarNearAddress.setTextColor(getResources().getColor(R.color.colorHelpMeBuyAddSellerAddressActivityTabBarGrayBg));
                ivHelpMeBuyAddSellerAddressTabBarNearAddress.setImageResource(R.drawable.collectaddressnormal);
                break;
          /*  }*/
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
        vpHelpMeBuyAddSellerAddressContent.addOnPageChangeListener(new MyOnPageChangeListener());
        initRecycleView = new InitRecycleView(viewList.get(0));
        initRecycleView.initXRV();
       /* dataList.add("");
        dataList.add("");
        dataList.add("");*/

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

    public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ItemContentViewHolder>{

        private List<PoiInfo> testList;
        private Context context;
        private LayoutInflater inflater;
        public MyRecycleViewAdapter(Context context1,List<PoiInfo> stringList){
            testList = stringList;
            this.context = context1;
            inflater = LayoutInflater.from(context1);
        }

        public void setDataList(Collection<PoiInfo> dataList){
            int count = testList.size();
            testList.clear();
            if(dataList != null) {
                testList.addAll(dataList);
            }
            notifyDataSetChanged();//数据变动太快用notifyDataSetChanged();
       /*     notifyItemRangeRemoved(0,count);
            notifyItemRangeChanged(0,count);

            if(dataList != null) {
                testList.addAll(dataList);
                notifyItemRangeInserted(0,testList.size());
                notifyItemRangeChanged(0,testList.size());

            }*/
        }


        @Override
        public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemContentViewHolder(inflater.inflate(R.layout.activity_helpmebuyadd_shopdetail_content_vp_itemrv_item_lly, parent, false));

        }

        @Override
        public void onBindViewHolder(ItemContentViewHolder holder, int position) {
            try {
                if (testList.size() != 0) {
                    holder.tvHelpMeBuyAddShopDetailContentVPItemRVItemAddr.setText(testList.get(position).city + testList.get(position).name);/*testList.get(position).address+testList.get(position).describeContents()*/
                    holder.lng = testList.get(position).location;
                }
            }catch (Exception e){

            }
        }



        @Override
        public int getItemCount() {
            return testList.size();
        }


        public class ItemContentViewHolder extends RecyclerView.ViewHolder{
            public LatLng lng;
            @BindView(R.id.rly_helpmebuyadd_shopdetail_content_vp_itemrv_item_addr)
            RelativeLayout rlyHelpMeBuyAddShopDetailContentVpItemRVItemAddr;
            @OnClick(R.id.rly_helpmebuyadd_shopdetail_content_vp_itemrv_item_addr)
            public void rlyHelpMeBuyAddShopDetailContentVpItemRVItemAddrOnclick(){
                etHelpMeBuyAddSellerAddressContentAddress.setText(tvHelpMeBuyAddShopDetailContentVPItemRVItemAddr.getText().toString());
                if(lng != null){
                    blat = lng.latitude;
                    blon = lng.longitude;
                }
            }
            @BindView(R.id.tv_helpmebuyadd_shopdetail_content_vp_itemrv_item_addr)
            public TextView tvHelpMeBuyAddShopDetailContentVPItemRVItemAddr;

            public ItemContentViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }

    /*RecycleView适配器*/

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {



        public void onPageSelected(int arg0) {

            switch (arg0){
                case 0:
                    InitTabBg(arg0);
                    break;
                case 1:
                    InitTabBg(arg0);

                    break;
            }
        }
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }

    }


    public class  InitRecycleView{
        @BindView(R.id.xrv_helpmebuyaddshopdetail_vp_item)
        XRecyclerView xrvHelpMeBuyAddShopDetailVPItem;
        public MyRecycleViewAdapter adapter ;

        private List<PoiInfo> poiInfoList = new ArrayList<>();
        public InitRecycleView(View view){
            ButterKnife.bind(this,view);
            initXRV();
        }

        private void initXRV(){
            adapter = new MyRecycleViewAdapter(getBaseContext(),poiInfoList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
            xrvHelpMeBuyAddShopDetailVPItem.setLayoutManager(layoutManager);
            xrvHelpMeBuyAddShopDetailVPItem.setAdapter(adapter);
        }
    }


    @OnClick(R.id.rly_helpmebuyaddselleraddress_tabbar_nearaddress)
    public void rlyHelpMeBuyAddSellerAddressTabBarNearAddressOnclick(){
        vpHelpMeBuyAddSellerAddressContent.setCurrentItem(0);
        currIndex = 1;
        InitTabBg(0);
    }
    @OnClick(R.id.rly_helpmebuyaddselleraddress_tabbar_history)
    public void rlyHelpMeBuyAddSellerAddressTabBarHistoryOnclick(){
        vpHelpMeBuyAddSellerAddressContent.setCurrentItem(1);
        currIndex = 0;
        InitTabBg(1);
    }

    /*viewpage recycleview 历史记录 收藏地址 功能 end*/

   /* *//*百度地图定位begin*//*
    private void initBaiDuSDK(){
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }*/


    private void initBaiDuMap(){
        /*监听输入框的变化*/
        /*监听输入框的变化*/
        initPoiSearch();
        mBaiduMap = mvHelpMeBuyAddSellerAddressContent.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        initOverlyWithMapView();

       /*地理编码初始化*/
        mSearch = GeoCoder.newInstance();
        /*地理编码初始化*/
        /*设置编码监听者*/
        mSearch.setOnGetGeoCodeResultListener(this);
        /*设置编码监听者*/
        initLocation();
        locationClient.start();
    }
    /*poi城市内检索*/
    private void initPoiSearch(){
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);

    }

    /*输入地址poi附近检索*/
    private void poiBeginSearch(LatLng latLng){
        /*Toast.makeText(getBaseContext(),"poiBeginSearch",Toast.LENGTH_SHORT).show();*/
        etHelpMeBuyAddSellerAddressContentNameCall.setOnEditorActionListener(new MyEditorActionListener(latLng,true));
        etHelpMeBuyAddSellerAddressContentAddress.setOnEditorActionListener(new MyEditorActionListener(latLng,false));


    }

    /**配置定位参数**/
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
            if (location == null || mvHelpMeBuyAddSellerAddressContent == null) {
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
        mCurrentMode = LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
        /*定位蓝色点*/
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        location(latLng);
        poiBeginSearch(latLng);
        /*etHelpMeBuyAddSellerAddressContentAddress.setText(location.getAddrStr() + location.getBuildingName() +location.getFloor()+location.getStreet()+location.getStreetNumber());*/
        addressLocation=location.getAddrStr()+" "+location.getLocationDescribe();
        etHelpMeBuyAddSellerAddressContentAddress.setText(addressLocation);
        beginSearchLalByAddress(addressLocation);
        /*initOverly(latLng);*/


     /*   *//*坐标定位*//*
        location(location.getLatitude(), location.getLongitude());
        *//*坐标定位*//*
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        poiBeginSearch(latLng);*/
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
                 ivHelpMeBuyAddShopdetailContentCenterLoc.getLocationOnScreen(location);
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
    /*地图移动坐标不动*/
    /*软键盘监听*/
    public class MyEditorActionListener implements OnEditorActionListener{
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
                    hideInput(HelpMeBuyAddShopDetailActivity.this);//隐藏软键盘

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


    /*poi附近检索*/



    /*poi城市检索*/
    private void poiSearchInCity(String address){
        if((address == null)||(address.isEmpty())){
            return;
        }
        try {
            isAddress = true;
            int blankIndex = address.indexOf(" ");
            address = address.substring(0, blankIndex);
        /*Toast.makeText(this,"this is adapter"+address,Toast.LENGTH_LONG).show();*/
            int index = address.indexOf("市");
            if (index > 0) {
                String city = address.substring(0, index);
                address = address.substring(index, address.length());
                poiSearch.searchInCity(new PoiCitySearchOption().city(city).pageCapacity(30).pageNum(0).keyword(address));
                        /*mSearch.geocode(new GeoCodeOption().city(city).address(address));*///根据地名查找经纬度
            } else {
                poiSearch.searchInCity(new PoiCitySearchOption().city("温州市").pageCapacity(30).pageNum(0).keyword(address));
                        /*mSearch.geocode(new GeoCodeOption().city("温州市").address(address));*/
            }
        }catch (Exception e){

        }
    }

    /*poi城市检索*/

    /**根据经纬度找地图地址并动画显示在屏幕中间  有关mark网站的出处http://blog.csdn.net/callmesen/article/details/40540895**/
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
    /*根据经纬度添加图标*/
    private void initOverly(LatLng ll){

        TextView textView = new TextView(this);
        Drawable drawable1 = getResources().getDrawable(R.drawable.search_map);
        drawable1.setBounds(0, 0, 38, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textView.setCompoundDrawables(drawable1,null,null,null);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromView(textView);
        mBaiduMap.clear();
        MarkerOptions ooA = new MarkerOptions().position(ll).icon(mCurrentMarker)
               .draggable(true);
        // 掉下动画
        ooA.animateType(MarkerAnimateType.drop);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
    }
    /*根据经纬度添加图标*/

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
                            etHelpMeBuyAddSellerAddressContentAddress.setText(poiInfoList.get(i).address);
                            mBaiduMap.showInfoWindow(mInfoWindow);
                        }
                    }
                    return false;
                }
            });
            /*mBaiduMap.addOverlays(markerOptionsList);*/
        }

    }

    /*监听地址输入框*/

    /*监听地址输入框*/

    /*根据地名开始查找经纬度*/
    private void beginSearchLalByAddress(String address){
       /* String address = etHelpMeBuyAddSellerAddressContentAddress.getText().toString();*/
        int index = address.indexOf("市");
        try {
            if (index > 0) {
                String city = address.substring(0, index);
                address = address.substring(index, address.length());
                mSearch.geocode(new GeoCodeOption().city(city).address(address));
            } else {
                mSearch.geocode(new GeoCodeOption().city("温州市").address(address));
            }
        }catch (Exception e){

        }
    }

    /*根据地名开始查找经纬度*/



    /*搜索附近的关键词*/
    @Override
    public void onGetPoiResult(PoiResult result) {
        if ((result == null) || (result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)) {
            return;
        }
        if(result.getAllPoi() != null) {
            if(!isAddress) {
            /*找到关键词所标注的地方*/
                getPoisFromKeyWordSearch(result.getAllPoi());
            /*找到关键词所标注的地方*/
            }
            isAddress = true;
/*
            Toast.makeText(this,"this is onGetPoiResult"+result.getAllAddr().get(0).address,Toast.LENGTH_LONG).show();*/
               initRecycleView.adapter.setDataList(result.getAllPoi());


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
            /*直接定位到具体地址*/
            location( geoCodeResult.getLocation());
            /*直接定位到具体地址*/
            /*搜索附近地址*/
            poiSearchNearBy(geoCodeResult.getAddress(),geoCodeResult.getLocation());
            /*搜索附近地址*/
            /*Toast.makeText(getBaseContext(),"onGetGeoCodeResult",Toast.LENGTH_SHORT).show();*/
        }
    }
    /*根据经纬度获取具体地址*/
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }

        LatLng latLng = result.getLocation();
        addressLocation = result.getAddress()+ "  " + result.getSematicDescription();
        etHelpMeBuyAddSellerAddressContentAddress.setText(addressLocation );
     /*   location(latLng);*/
        poiSearchNearBy(addressLocation,latLng);
    }
       /*根据经纬度获取具体地址*/
    /*根据经纬度搜索地址*/




    /*手指在地图上的动作来改变地名*/
    /*获取手指在地图上的经纬度*/

    @OnClick(R.id.rly_helpmebuyaddselleraddress_addresssearch)
    public void rlyHelpMeBuyAddSellerAddressAddressSearchOnclick(){
        Intent intent = new Intent(this,BaiduAddressSearchSuggestActivity.class);
        startActivityForResult(intent,RESULT_SEARCH);
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
                    etHelpMeBuyAddSellerAddressContentAddress.setText(address);
                }


                break;
            default:
                break;
        }
    }





    /*百度地图 end*/

    /*根据经纬度搜索地址*/

    protected void onDestroy(){
        mBaiduMap.clear();
        mSearch.destroy();
        isFirst = true;
        mvHelpMeBuyAddSellerAddressContent.onDestroy();
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
        mvHelpMeBuyAddSellerAddressContent.onResume();
    }
    protected void onPause(){
        super.onPause();
        mvHelpMeBuyAddSellerAddressContent.onPause();

    }
}