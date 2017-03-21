package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.Point;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
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
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.Common.Widget.XRecycleView.XRecyclerView;
import tianhao.agoto.R;
import tianhao.agoto.Utils.SystemUtils;


/**
 * Created by zhyan on 2017/2/19.
 */

public class HelpMeSendAddContacterActivity extends Activity implements OnGetGeoCoderResultListener,OnGetPoiSearchResultListener {

    /*viewpage recycleview 历史记录 收藏地址 功能 begin*/
    @BindView(R.id.tv_helpmesendadd_contacter_tabbar_nearby)
    TextView tvHelpMeSendAddContacterTabBarNearBy;
    @BindView(R.id.iv_helpmesendadd_contacter_tabbar_nearby)
    ImageView ivHelpMeSendAddContacterTabBarNearBy;
    @BindView(R.id.rly_helpmesendadd_contacter_tabbar_nearby)
    RelativeLayout rlyHelpMeSendAddContacterTabBarNearBy;
    @BindView(R.id.tv_helpmesendadd_contacter_tabbar_history)
    TextView tvHelpMeSendAddContacterTabBarHistory;
    @BindView(R.id.iv_helpmesendadd_contacter_tabbar_history)
    ImageView ivHelpMeSendAddContacterTabBarHistory;
    @BindView(R.id.rly_helpmesendadd_contacter_tabbar_history)
    RelativeLayout rlyHelpMeSendAddContacterTabBarHistory;
    @BindView(R.id.iv_helpmesendadd_contacter_tab_greenbottom)
    ImageView ivHelpMeSendAddContacterTabGreenBottom;
    @BindView(R.id.vp_helpmesendadd_contacter_content)
    ViewPager vpHelpMeSendAddContacterContent;

    private LatLng currentPt = new LatLng(0,0);
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private List<View> viewList; // Tab页面列表
    /*viewpage recycleview 历史记录 收藏地址 功能*/

    /*百度地图定位 begin2*/
    @BindView(R.id.mv_helpmesendadd_contacter_content)
    MapView mMapView;
    @BindView(R.id.iv_helpmesendadd_contacter_centerloc)
    ImageView ivHelpMeSendAddContacterCenterLoc;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();

    private String addressLocation = "";
    private Boolean isFirst = true;

    @BindView(R.id.rly_helpmesendadd_contacter_addresssearch)
    RelativeLayout rlyHelpMeSendAddContacterAddressSearch;
    private double lat = 0,lon = 0;

    @BindView(R.id.et_helpmesendadd_contacter_contentname)
    EditText etHelpMeSendAddContacterContentName;
    @BindView(R.id.et_helpmesendadd_contacter_contenttel)
    EditText etHelpMeSendAddContacterContentTel;
    @BindView(R.id.rly_helpmesendadd_contacter_searchcontacter)
    RelativeLayout rlyHelpMeSendAddContacterSearchContacter;

    /*地名转换经纬度*/
    @BindView(R.id.et_helpmesendadd_contacter_contentaddr)
    EditText etHelpMeSendAddContacterContentAddr;
    private GeoCoder search=null;
    /*关键字poi检索*/
    private PoiSearch poiSearch;
    /*关键字poi检索*/
    private String city;
    /*地名转换经纬度*/
    /*百度地图定位 end2*/
    private  int RESULT_TYPE = 0;
    private final int RESULT_SEARCH = 15;
    private final int RESULT_CONTACTER = 11;
    private LocationMode mCurrentMode;
    private  final int accuracyCircleFillColor = 0xAAFFFF88;
    private  final int accuracyCircleStrokeColor = 0xAA00FF00;
    private InitRecycleView initRecycleView;
    private BaiduMap.OnMapTouchListener mapTouchListener;
    @BindView(R.id.lly_helpmesendadd_contacter_searchaddress)
    LinearLayout llyHelpMeSendAddContacterSearchAddress;

    @BindView(R.id.rly_helpmesendadd_contacter_topbar_leftmenu)
    RelativeLayout rlyHelpMeSendAddContacterTopBarLeftMenu;
    @BindView(R.id.rly_helpmesendadd_contacter_topbar_rightmenu)
    RelativeLayout rlyHelpMeSendAddContacterTopBarRightMenu;

    /*上拉*/
    @BindView(R.id.rly_helpmesendadd_contacter_tabbar_up)
    RelativeLayout rlyHelpMeSendAddContacterTabbarUp;
    @BindView(R.id.lly_helpmesendadd_contacter_content_bottom)
    LinearLayout llyHelpMeSendAddContacterContentBottom;
    @OnClick(R.id.rly_helpmesendadd_contacter_tabbar_up)
    public void rlyHelpMeSendAddContacterTabbarUpOnclick(){
        int tempHeight = 0;
        if(!isUp){
            isUp = true;
            ViewGroup.LayoutParams layoutParams = llyHelpMeSendAddContacterContentBottom.getLayoutParams();
            SystemUtils systemUtils = new SystemUtils(this);
            tempHeight = layoutParams.height;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_enter);
            animation.setDuration(1000);
            layoutParams.height = 0;
            llyHelpMeSendAddContacterContentBottom.setLayoutParams(layoutParams);
            llyHelpMeSendAddContacterContentBottom.startAnimation(animation);
            layoutParams.height += systemUtils.getWindowHeight()/2;
            llyHelpMeSendAddContacterContentBottom.setLayoutParams(layoutParams);
            ivHelpMeSendAddContacterTabbarUpArrow.setBackgroundResource(R.drawable.down_arrow);
            /*rlyHelpMeBuyAddShopDetailContentUp.setAnimation(R.style.PopupAnimation);;*/
        }else{
            ViewGroup.LayoutParams layoutParams = llyHelpMeSendAddContacterContentBottom.getLayoutParams();

            layoutParams.height =tempHeight ;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_exit);
            /*animation.setDuration(1000);*/
            llyHelpMeSendAddContacterContentBottom.startAnimation(animation);
            llyHelpMeSendAddContacterContentBottom.setLayoutParams(layoutParams);
            isUp = false;
            ivHelpMeSendAddContacterTabbarUpArrow.setBackgroundResource(R.drawable.up_arrow);
        }
    }
    @BindView(R.id.iv_helpmesendadd_contacter_tabbar_uparrow)
    ImageView ivHelpMeSendAddContacterTabbarUpArrow;
    private boolean isUp = false;
    /*上拉*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*initBaiDuSDK();*/
        setContentView(R.layout.activity_helpmesendadd_contacter_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        initEditText();
        initSwitchContent();
        initBaiDuMap();
        initResultType();
        /*initGlassBg();*/
        /*initTran();*/
    }
    //软键盘实现搜索和多行
    private void initEditText(){

        etHelpMeSendAddContacterContentAddr.setHorizontallyScrolling(false);
        etHelpMeSendAddContacterContentAddr.setMaxLines(Integer.MAX_VALUE);
    }
    //软键盘实现搜索和多行
    private void initResultType(){
        Bundle bundle = this.getIntent().getExtras();
        String sender = bundle.getString("sender");
        String receiver = bundle.getString("receiver");
        if(sender != null) {
            RESULT_TYPE = Integer.valueOf(sender);
        }
        if(receiver != null) {
            RESULT_TYPE = Integer.valueOf(receiver);
        }

    }
    /*viewpage recycleview 历史记录 收藏地址 功能*/
    private void initSwitchContent(){
      /*  InitTabBg(true);*/
        InitImageView();
        InitViewPager();
    }

    /**
     * 初始化头标
     */
    private void InitTabBg(int ag0) {



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
                ivHelpMeSendAddContacterTabGreenBottom.startAnimation(animation);
                tvHelpMeSendAddContacterTabBarNearBy.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGreenBg));
                ivHelpMeSendAddContacterTabBarNearBy.setImageResource(R.drawable.collectaddressselect);
                tvHelpMeSendAddContacterTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGrayBg));
                ivHelpMeSendAddContacterTabBarHistory.setImageResource(R.drawable.historyrecordnormal);
                beginSearchLalByAddress(etHelpMeSendAddContacterContentAddr.getText().toString());
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
                ivHelpMeSendAddContacterTabGreenBottom.startAnimation(animation);
                tvHelpMeSendAddContacterTabBarNearBy.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGrayBg));
                ivHelpMeSendAddContacterTabBarNearBy.setImageResource(R.drawable.collectaddressnormal);
                tvHelpMeSendAddContacterTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGreenBg));
                ivHelpMeSendAddContacterTabBarHistory.setImageResource(R.drawable.historyrecordselect);
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
        ivHelpMeSendAddContacterTabGreenBottom.setImageMatrix(matrix);
        ivHelpMeSendAddContacterTabGreenBottom.setLayoutParams(params);
    }
    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        viewList = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        viewList.add(mInflater.inflate(R.layout.activity_helpmesendadd_contacter_content_vp_itemrv_lly, null));
        viewList.add(mInflater.inflate(R.layout.activity_helpmesendadd_contacter_content_vp_itemrv_lly, null));
        vpHelpMeSendAddContacterContent.setAdapter(new MyPagerAdapter(viewList));
        vpHelpMeSendAddContacterContent.setCurrentItem(0);
        vpHelpMeSendAddContacterContent.setOnPageChangeListener(new MyOnPageChangeListener());
        initRecycleView = new InitRecycleView(viewList.get(0));
        initRecycleView.initXRV();
    }


    public class  InitRecycleView{
        @BindView(R.id.xrv_helpmesendadd_contacter_vp_item)
        XRecyclerView xrvHelpMeSendAddContacterVPItem;
        public MyRecycleViewAdapter adapter ;

        private List<PoiInfo> poiInfoList = new ArrayList<>();
        public InitRecycleView(View view){
            ButterKnife.bind(this,view);
            initXRV();
        }

        private void initXRV(){
            adapter = new MyRecycleViewAdapter(getBaseContext(),poiInfoList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
            xrvHelpMeSendAddContacterVPItem.setLayoutManager(layoutManager);
            xrvHelpMeSendAddContacterVPItem.setAdapter(adapter);
        }
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

        public void setDataList(List<PoiInfo> dataList){
            this.testList = dataList;
            this.notifyDataSetChanged();
        }


        @Override
        public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemContentViewHolder(inflater.inflate(R.layout.activity_helpmesendadd_contacter_content_vp_itemrv_item_lly, parent, false));

        }

        @Override
        public void onBindViewHolder(ItemContentViewHolder holder, int position) {
            try {
                if (testList.size() != 0) {
                    holder.tvHelpMeSendAddContacterContentVPItemRVItemAddress.setText(testList.get(position).city + testList.get(position).name);/*testList.get(position).address+testList.get(position).describeContents()*/
                }
            }catch (Exception e){

            }
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
            public LatLng lng;
            @BindView(R.id.lly_helpmesendadd_contacter_content_vp_itemrv_item_total)
            LinearLayout llyHelpMeSendAddContacterContentVPItemRVItemTotal;
            @BindView(R.id.tv_helpmesendadd_contacter_content_vp_itemrv_item_address)
            TextView tvHelpMeSendAddContacterContentVPItemRVItemAddress;
            @OnClick(R.id.lly_helpmesendadd_contacter_content_vp_itemrv_item_total)
            public void llyHelpMeSendAddContacterContentVPItemRVItemTotalOnclick(){
                etHelpMeSendAddContacterContentAddr.setText(tvHelpMeSendAddContacterContentVPItemRVItemAddress.getText().toString());
                if(lng != null){
                    lat = lng.latitude;
                    lon = lng.longitude;
                }
            }
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

        int one = offset * 2 ;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

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




    @OnClick(R.id.rly_helpmesendadd_contacter_tabbar_nearby)
    public void setRlyHelpMeSendAddContacterTabBarNearByOnclick(){
        vpHelpMeSendAddContacterContent.setCurrentItem(0);
        currIndex = 1;
        InitTabBg(0);
    }

    @OnClick(R.id.rly_helpmesendadd_contacter_tabbar_history)
    public void setRlyHelpMeSendAddContacterTabBarHistoryOnclick(){
        vpHelpMeSendAddContacterContent.setCurrentItem(1);
        currIndex = 0;
        InitTabBg(1);

    }
    /*viewpage recycleview 历史记录 收藏地址 功能 end*/

    /*百度地图定位begin*/
    private void initBaiDuSDK(){
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }


    private void initBaiDuMap(){
        initPoiSearch();
        mBaiduMap = mMapView.getMap();

        search= GeoCoder.newInstance();
        /**根据经纬度得到屏幕中心点地址**/
        search.setOnGetGeoCodeResultListener(this);
        mBaiduMap.setMyLocationEnabled(true);
        //设置缩放级别，默认级别为12
        initOverlyWithMapView();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);

        initLocation();
        locationClient.start();
    }


    private void initPoiSearch(){
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
    }

    /*输入地址poi附近检索*/
    private void poiBeginSearch(){
        /*Toast.makeText(getBaseContext(),"poiBeginSearch",Toast.LENGTH_SHORT).show();*/
        etHelpMeSendAddContacterContentAddr.setOnEditorActionListener(new MyEditorActionListener());

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
                hideInput(HelpMeSendAddContacterActivity.this);//隐藏软键盘

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
                ivHelpMeSendAddContacterCenterLoc.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Point point = new Point(x,y);
                /*Toast.makeText(getBaseContext(),"x:"+x+"y:"+y,Toast.LENGTH_SHORT).show();*/
                //http://blog.csdn.net/sjf0115/article/details/7306284 获取控件在屏幕上的坐标
                currentPt = mBaiduMap.getProjection().fromScreenLocation(point);
                search.reverseGeoCode(new ReverseGeoCodeOption().location(currentPt));
                lat = currentPt.latitude;
                lon = currentPt.longitude;
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
            if (location == null || mMapView == null) {
                return;
            }
            if(isFirst){
                showCurrentPosition(location);
                isFirst = false;
            }
            /*showCurrentPosition(location);*/
        }
    }

    @OnClick(R.id.rly_helpmesendadd_contacter_searchcontacter)
    public void rlyHelpMeSendAddContacterSearchContacterOnclick(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);//vnd.android.cursor.dir/contact
        startActivityForResult(intent, RESULT_CONTACTER);
    }


    @OnClick(R.id.lly_helpmesendadd_contacter_searchaddress)
    public void llyHelpMeSendAddContacterSearchAddressOnclick(){
        Intent intent = new Intent(this,BaiduAddressSearchSuggestActivity.class);
        startActivityForResult(intent,RESULT_SEARCH);
        /*getLaLoFromCity();*/
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
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
                etHelpMeSendAddContacterContentAddr.setText(address);
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
                etHelpMeSendAddContacterContentName.setText(name);
                if (Boolean.parseBoolean(hasPhone)) {
                    int contactId = c.getInt(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (phoneNumber != null) {
                            etHelpMeSendAddContacterContentTel.setText(phoneNumber);
                        }
                    }
                    phones.close();
                }
            }
        }

    }
    /*返回上级菜单*/
    @OnClick(R.id.rly_helpmesendadd_contacter_topbar_leftmenu)
    public void rlyHelpMeSendAddContacterTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回上级菜单*/
    /*信息确认*/
    @OnClick(R.id.rly_helpmesendadd_contacter_topbar_rightmenu)
    public void rlyHelpMeSendAddContacterTopBarRightMenuOnclick(){
        Bundle bundle = new Bundle();
        bundle.putString("nameCall",etHelpMeSendAddContacterContentName.getText().toString());
        bundle.putString("tel",etHelpMeSendAddContacterContentTel.getText().toString());
        bundle.putString("address",etHelpMeSendAddContacterContentAddr.getText().toString());
        bundle.putString("lat", "" + lat);
        bundle.putString("lon", "" + lon);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_TYPE, intent);
        finish();
    }
    /*信息确认*/
 /*   private void getLaLoFromCity(){
        addressLocation = tvHelpMeSendAddContacterContentAddr.getText().toString();
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
    }

    *//**得到当前所在城市**//*
    private void getCity(){
        addressLocation = tvHelpMeSendAddContacterContentAddr.getText().toString();
        if(addressLocation!=null&&!addressLocation.equals("")){
            int indexProvince=addressLocation.indexOf("省");
            int indexCity=addressLocation.indexOf("市");
            if(indexCity < 0) {
                city = null;
            }else{
                city = addressLocation.substring(indexProvince + 1, indexCity);
            }
        }
    }*/







    /**定位**/
    private void showCurrentPosition(BDLocation location){

        TextView textView = new TextView(this);
        Drawable drawable1 = getResources().getDrawable(R.drawable.arrow);
        drawable1.setBounds(0, 0, 35, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
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
        etHelpMeSendAddContacterContentAddr.setText(addressLocation);
        beginSearchLalByAddress(addressLocation);
    }

    /**经纬度地址动画显示在屏幕中间  有关mark网站的出处http://blog.csdn.net/callmesen/article/details/40540895**/
    private void location(LatLng latLng){

       /*只要调用画面 就能赋值*/
        lat = latLng.latitude;
        lon = latLng.longitude;

        /*无论哪个调用此动画 都将经纬度赋值*/
       /* mBaiduMap.clear();*/
        //定义地图状态
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
    }

     /*根据经纬度搜索地址*/








    @Override
    public void onGetPoiResult(PoiResult result) {
        if(result.getAllPoi() != null) {

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
            location(geoCodeResult.getLocation());
             /*搜索附近地址*/
            poiSearchNearBy(geoCodeResult.getAddress(),geoCodeResult.getLocation());
            /*搜索附近地址*/
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(HelpMeSendAddContacterActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
     /*   mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));*/
        LatLng latLng = result.getLocation();
        addressLocation = result.getAddress();
        etHelpMeSendAddContacterContentAddr.setText(addressLocation+"  "+result.getSematicDescription());
   /*     location(latLng);*/
        poiSearchNearBy(addressLocation,latLng);
    /*    location(latLng);*/
    }
    /*根据经纬度搜索地址*/
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



    /*百度地图 end*/
    protected void onResume(){
        /*init();*/
        super.onResume();
        mMapView.onResume();
    }
    protected void onPause(){
        super.onPause();
        mMapView.onPause();
/*        locationClient.unRegisterLocationListener(locationListener);
        mBaiduMap.clear();
        search.destroy();*/
        /*isFirst = true;*/
    }


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
}
