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
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import tianhao.agoto.Bean.BaseBean;
import tianhao.agoto.Common.Widget.DB.XCCacheManager.xccache.XCCacheManager;
import tianhao.agoto.Common.Widget.XRecycleView.XRecyclerView;
import tianhao.agoto.NetWorks.AddressManageNetWorks;
import tianhao.agoto.R;
import tianhao.agoto.Utils.SystemUtils;

/**
 * 添加商家地址
 * Created by admin on 2017/3/22.
 */

public class AddressManageAddShopFinalActivity extends Activity implements OnGetGeoCoderResultListener,OnGetPoiSearchResultListener {

    /*topbar*/
    @OnClick(R.id.rly_helpmebuy_addselleraddress_topbar_leftmenu)
    public void rlyHelpMeBuyAddSellerAddressTopBarLeftMenuOnclick(){
        this.finish();
    }
    /*topbar*/

    /*信息确认返回*/
   /* @OnClick(R.id.rly_helpmebuy_addselleraddress_topbar_rightmenu)
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
    */@OnClick(R.id.rly_helpmebuy_addselleraddress_topbar_rightmenu)
    public void rlyHelpMeBuyAddSellerAddressTopBarRightMenuOnclick(){
        addShopAddressToNet();

    }
  /*信息确认返回*/


    private void addShopAddressToNet(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String usid = xcCacheManager.readCache("usid");
        String name = etHelpMeBuyAddSellerAddressContentNameCall.getText().toString();
        String address = etHelpMeBuyAddSellerAddressContentAddress.getText().toString();
        String finalAddress = name+" "+ address;
        float clientaddr1Lat = (float) blat;
        float clientaddr1Long = (float) blon;
        String clientaddr1Isdefault = "0";

        if((usid != null)&&(finalAddress != null)&&(clientaddr1Isdefault != null)&&(!name.isEmpty())){
            AddressManageNetWorks addressManageNetWorks = new AddressManageNetWorks();
            addressManageNetWorks.addShopAddress(usid,finalAddress,clientaddr1Lat,clientaddr1Long,clientaddr1Isdefault, new Observer<BaseBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseBean baseBean) {
                    Toast.makeText(getBaseContext(),baseBean.getResult(),Toast.LENGTH_LONG).show();
                    finish();
                }
            });

        }else{
            Toast.makeText(getBaseContext(),"请输入正确的名称和地址,不能为空",Toast.LENGTH_LONG).show();
        }

    }






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

    /*商家地址经纬度*/
    private double blat,blon;
    /*商家地址经纬度*/


    /*附近地址 历史记录*/
    @BindView(R.id.vp_helpmebuyaddselleraddress_content)
    ViewPager vpHelpMeBuyAddSellerAddressContent;
    @BindView(R.id.tv_helpmebuyaddselleraddress_tabbar_history)
    TextView tvHelpMeBuyAddSellerAddressTabBarHistory;
    @BindView(R.id.iv_helpmebuyaddselleraddress_tabbar_history)
    ImageView ivHelpMeBuyAddSellerAddressTabBarHistory;
    @BindView(R.id.tv_helpmebuyaddselleraddress_tabbar_nearaddress)
    TextView tvHelpMeBuyAddSellerAddressTabBarNearAddress;
    @BindView(R.id.iv_helpmebuyaddselleraddress_tabbar_nearaddress)
    ImageView ivHelpMeBuyAddSellerAddressTabBarNearAddress;
    /*绿色转移叶卡*/
    @BindView(R.id.iv_helpmebuyaddselleraddress_tab_greenbottom)
    ImageView ivHelpMeBuyAddSellerAddressTabGreenBottom;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private List<View> viewList; // Tab页面列表
    /*绿色转移叶卡*/
    @BindView(R.id.rly_helpmebuyaddselleraddress_tabbar_history)
    RelativeLayout rlyHelpMeBuyAddSellerAddressTabBarHistory;
    @BindView(R.id.rly_helpmebuyaddselleraddress_tabbar_nearaddress)
    RelativeLayout rlyHelpMeBuyAddSellerAddressTabBarNearAddress;
    InitRecycleView initRecycleView;
    /*附近地址 历史记录*/



    /*商家名称*/
    @BindView(R.id.et_helpmebuyaddselleraddress_content_namecall)
    EditText etHelpMeBuyAddSellerAddressContentNameCall;
    /*商家名称*/
    /*商家地址*/
    @BindView(R.id.et_helpmebuyaddselleraddress_content_address)
    EditText etHelpMeBuyAddSellerAddressContentAddress;
    private boolean isAddress = true;
    /*商家地址*/


    /*百度地图*/

        /*地理编码检索*/
      /*关键字poi检索*/
    private PoiSearch poiSearch;
    /*关键字poi检索*/
    private GeoCoder mSearch;//地理编码 根据经纬度查找地址
    /*地理编码检索*/
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
    private  final int accuracyCircleFillColor = 0xAAFFFF88;
    private  final int accuracyCircleStrokeColor = 0xAA00FF00;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private LatLng currentPt = new LatLng(0,0);
    /*百度地图*/
    private final int RESULT_SEARCH = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*initBaiDuSDK();*/
        setContentView(R.layout.activity_helpmebuyadd_shopdetail_lly);
        init();
    }

    /*信息确认返回*/
    private void init(){
        ButterKnife.bind(this);
        initEditText();
        initSwitchContent();
        initBaiDuMap();

    }

    private void initEditText(){

        etHelpMeBuyAddSellerAddressContentAddress.setHorizontallyScrolling(false);
        etHelpMeBuyAddSellerAddressContentAddress.setMaxLines(Integer.MAX_VALUE);
    }
    /*附近地址 历史记录*/
    private void initSwitchContent(){
  /*      InitTabBg(true,true);*/
        InitImageView();
        InitViewPager();

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
    /*附近地址 历史记录*/
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


    /*根据地名开始查找经纬度*/
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

        }


        @Override
        public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemContentViewHolder(inflater.inflate(R.layout.activity_helpmebuyadd_shopdetail_content_vp_itemrv_item_lly, parent, false));

        }

        @Override
        public void onBindViewHolder(MyRecycleViewAdapter.ItemContentViewHolder holder, int position) {
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
     * 初始化ViewPager
     */

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
    /*经纬度转换为地址监听*/
    private void initPoiSearch(){
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);

    }
    /*经纬度转换为地址监听*/

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
    private void showCurrentPosition(BDLocation location1){
        TextView textView = new TextView(this);
        Drawable drawable1 = getResources().getDrawable(R.drawable.arrow);
        drawable1.setBounds(0, 0, 40, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textView.setCompoundDrawables(drawable1,null,null,null);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromView(textView);
        /*定位蓝色点*/
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location1.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location1.getLatitude())
                .longitude(location1.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
        /*定位蓝色点*/
        LatLng latLng = new LatLng(location1.getLatitude(),location1.getLongitude());
        location(latLng);
        poiBeginSearch(latLng);
        if((location1.getAddrStr()!= null)&&(location1.getLocationDescribe() != null)) {
            addressLocation = location1.getAddrStr() + " " + location1.getLocationDescribe();
            etHelpMeBuyAddSellerAddressContentAddress.setText(addressLocation);
            beginSearchLalByAddress(addressLocation);
        }
    }
    /*输入地址poi附近检索*/
    private void poiBeginSearch(LatLng latLng){
        /*Toast.makeText(getBaseContext(),"poiBeginSearch",Toast.LENGTH_SHORT).show();*/
        etHelpMeBuyAddSellerAddressContentNameCall.setOnEditorActionListener(new MyEditorActionListener(latLng,true));
        etHelpMeBuyAddSellerAddressContentAddress.setOnEditorActionListener(new MyEditorActionListener(latLng,false));


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
                hideInput(AddressManageAddShopFinalActivity.this);//隐藏软键盘
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
    /*地图移动到经纬度所表示的地方*/
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
    /*地图移动到经纬度所表示的地方*/
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
        }

    }






    /*搜索 并返回*/
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
/*搜索 并返回*/










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
