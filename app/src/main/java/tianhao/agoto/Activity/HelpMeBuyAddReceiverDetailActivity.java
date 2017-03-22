package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.ContentResolver;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Common.Widget.EditText.EditTextWithDel;
import tianhao.agoto.Common.Widget.XRecycleView.XRecyclerView;
import tianhao.agoto.R;
import tianhao.agoto.Utils.SystemUtils;

/**
 * Created by admin on 2017/2/28.
 */

public class HelpMeBuyAddReceiverDetailActivity extends Activity implements OnGetGeoCoderResultListener,OnGetPoiSearchResultListener {

    /*viewpage recycleview 历史记录 收藏地址 功能 begin*/
    @BindView(R.id.tv_helpmebuyadd_receiverdetail_tabbar_nearby)
    TextView tvHelpMeBuyAddReceiverDetailTabBarNearBy;
    @BindView(R.id.iv_helpmebuyadd_receiverdetail_tabbar_nearby)
    ImageView ivHelpMeBuyAddReceiverDetailTabBarNearBy;
    @BindView(R.id.tv_helpmebuyadd_receiverdetail_tabbar_history)
    TextView tvHelpMeBuyAddReceiverDetailTabBarHistory;
    @BindView(R.id.iv_helpmebuyadd_receiverdetail_tabbar_history)
    ImageView ivHelpMeBuyAddReceiverDetailTabBarHistory;
    @BindView(R.id.iv_helpmebuyadd_receiverdetail_tab_greenbottom)
    ImageView ivHelpMeBuyAddReceiverDetailTabGreenBottom;
    @BindView(R.id.vp_helpmebuyadd_receiverdetail_content)
    ViewPager vpHelpMeBuyAddReceiverDetailContent;
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_tabbar_nearby)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailTabBarNearBy;
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_tabbar_history)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailTabBarHistory;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private List<View> viewList; // Tab页面列表
    /*viewpage recycleview 历史记录 收藏地址 功能*/

    /*百度地图定位 begin2*/
    @BindView(R.id.mv_helpmebuyadd_receiverdetail_content)
    MapView mvHelpMeBuyAddReceiverDetailContent;
    @BindView(R.id.iv_helpmebuyadd_receiverdetail_centerloc)
    ImageView ivHelpMeBuyAddReceiverDetailCenterLoc;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();
    private BaiduMap.OnMapTouchListener mapTouchListener;
    InitRecycleView initRecycleView;
    private String addressLocation = "";
    private Boolean isFirst = true;
    private LocationMode mCurrentMode;
/*
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_addresssearch)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailAddressSearch;
*/

    /*地名转换经纬度*/
    @BindView(R.id.et_helpmebuyadd_receiverdetail_contentaddress)
    EditText etHelpMeBuyAddReceiverDetailContentAddress;
    private GeoCoder search=null;
    /*关键字poi检索*/
    private PoiSearch poiSearch;
    /*关键字poi检索*/
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
    /*上拉*/
    @BindView(R.id.rly_helpmebuyadd_receiverdetail_tabbar_up)
    RelativeLayout rlyHelpMeBuyAddReceiverDetailTabBarUp;
    @BindView(R.id.lly_helpmebuyadd_receiverdetail_tabbar_bottom)
    LinearLayout llyHelpMeBuyAddReceiverDetailTarBarBottom;
    @OnClick(R.id.rly_helpmebuyadd_receiverdetail_tabbar_up)
    public void rlyHelpMeBuyAddReceiverDetailTabBarUpOnclick(){
        int tempHeight = 0;
        if(!isUp){
            isUp = true;
            ViewGroup.LayoutParams layoutParams = llyHelpMeBuyAddReceiverDetailTarBarBottom.getLayoutParams();
            SystemUtils systemUtils = new SystemUtils(this);
            tempHeight = layoutParams.height;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_enter);
            animation.setDuration(1000);
            layoutParams.height = 0;
            llyHelpMeBuyAddReceiverDetailTarBarBottom.setLayoutParams(layoutParams);
            llyHelpMeBuyAddReceiverDetailTarBarBottom.startAnimation(animation);
            layoutParams.height += systemUtils.getWindowHeight()/2;
            llyHelpMeBuyAddReceiverDetailTarBarBottom.setLayoutParams(layoutParams);
            ivHelpMeBuyAddReceiverDetailTabBarUpArrow.setBackgroundResource(R.drawable.down_arrow);
            /*rlyHelpMeBuyAddShopDetailContentUp.setAnimation(R.style.PopupAnimation);;*/
        }else{
            ViewGroup.LayoutParams layoutParams = llyHelpMeBuyAddReceiverDetailTarBarBottom.getLayoutParams();

            layoutParams.height =tempHeight ;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_exit);
            /*animation.setDuration(1000);*/
            llyHelpMeBuyAddReceiverDetailTarBarBottom.startAnimation(animation);
            llyHelpMeBuyAddReceiverDetailTarBarBottom.setLayoutParams(layoutParams);
            isUp = false;
            ivHelpMeBuyAddReceiverDetailTabBarUpArrow.setBackgroundResource(R.drawable.up_arrow);
        }
    }
    @BindView(R.id.iv_helpmebuyadd_receiverdetail_tabbar_uparrow)
    ImageView ivHelpMeBuyAddReceiverDetailTabBarUpArrow;
    private boolean isUp = false;
    /*上拉*/
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
    private LatLng currentPt = new LatLng(0,0);
    private  final int accuracyCircleFillColor = 0xAAFFFF88;
    private  final int accuracyCircleStrokeColor = 0xAA00FF00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* initBaiDuSDK();*/
        setContentView(R.layout.activity_helpmebuyadd_receiverdetail_lly);
        init();
    }


    private void init(){
        ButterKnife.bind(this);
        initEditText();
        initSwitchContent();
        initBaiDuMap();
        /*initGlassBg();*/
        /*initTran();*/
    }
    private void initEditText(){

        etHelpMeBuyAddReceiverDetailContentAddress.setHorizontallyScrolling(false);
        etHelpMeBuyAddReceiverDetailContentAddress.setMaxLines(Integer.MAX_VALUE);
    }

    /*viewpage recycleview 历史记录 收藏地址 功能*/
    private void initSwitchContent(){
        /*InitTabBg(true);*/
        InitImageView();
        InitViewPager();
        /*initEditAddress();*/
    }



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
        bundle.putString("address",etHelpMeBuyAddReceiverDetailContentAddress.getText().toString());
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
                LatLng latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
                location(latLng);
                etHelpMeBuyAddReceiverDetailContentAddress.setText(address);
            }
        }
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
                ivHelpMeBuyAddReceiverDetailTabGreenBottom.startAnimation(animation);
                tvHelpMeBuyAddReceiverDetailTabBarNearBy.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGreenBg));
                ivHelpMeBuyAddReceiverDetailTabBarNearBy.setImageResource(R.drawable.collectaddressselect);
                tvHelpMeBuyAddReceiverDetailTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGrayBg));
                ivHelpMeBuyAddReceiverDetailTabBarHistory.setImageResource(R.drawable.historyrecordnormal);
                beginSearchLalByAddress(etHelpMeBuyAddReceiverDetailContentAddress.getText().toString());
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
                ivHelpMeBuyAddReceiverDetailTabGreenBottom.startAnimation(animation);
                tvHelpMeBuyAddReceiverDetailTabBarNearBy.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGrayBg));
                ivHelpMeBuyAddReceiverDetailTabBarNearBy.setImageResource(R.drawable.collectaddressnormal);
                tvHelpMeBuyAddReceiverDetailTabBarHistory.setTextColor(getResources().getColor(R.color.colorHelpMeSendAddContacterActivityTabBarGreenBg));
                ivHelpMeBuyAddReceiverDetailTabBarHistory.setImageResource(R.drawable.historyrecordselect);
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
        initRecycleView = new InitRecycleView(viewList.get(0));
        initRecycleView.initXRV();
   /*     dataList.add("");
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
        public MyRecycleViewAdapter(Context context,List<PoiInfo> stringList){
            testList = stringList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void setDataList(List<PoiInfo> dataList){
            this.testList = dataList;
            this.notifyDataSetChanged();
        }


        @Override
        public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemContentViewHolder(inflater.inflate(R.layout.activity_helpmebuyadd_receiverdetail_content_vp_itemrv_item_lly, parent, false));

        }

        @Override
        public void onBindViewHolder(ItemContentViewHolder holder, int position) {
            try {
                if (testList.size() != 0) {
                    holder.tvHelpMeBuyAddReceiverDetailContentVPItemRVItemAddr.setText(testList.get(position).city + testList.get(position).name);/*testList.get(position).address+testList.get(position).describeContents()*/
                    holder.lng = testList.get(position).location;
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
            @BindView(R.id.lly_helpmebuyadd_receiverdetail_content_vp_itemrv_item_total)
            LinearLayout llyHelpMeBuyAddReceiverDetailContentVPItemRVItemTotal;
            @OnClick(R.id.lly_helpmebuyadd_receiverdetail_content_vp_itemrv_item_total)
            public void llyHelpMeBuyAddReceiverDetailContentVPItemRVItemTotalOnclick(){
                etHelpMeBuyAddReceiverDetailContentAddress.setText(tvHelpMeBuyAddReceiverDetailContentVPItemRVItemAddr.getText().toString());
                if(lng != null){
                    rlat = lng.latitude;
                    rlon = lng.longitude;
                }

            }
            @BindView(R.id.tv_helpmebuyadd_receiverdetail_content_vp_itemrv_item_addr)
            public TextView tvHelpMeBuyAddReceiverDetailContentVPItemRVItemAddr;

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


    @OnClick(R.id.rly_helpmebuyadd_receiverdetail_tabbar_nearby)
    public void rlyHelpMeSendAddContacterTabBarNearByOnclick(){
        vpHelpMeBuyAddReceiverDetailContent.setCurrentItem(0);
        currIndex = 1;
        InitTabBg(0);
    }

    @OnClick(R.id.rly_helpmebuyadd_receiverdetail_tabbar_history)
    public void rlyHelpMeSendAddContacterTabBarHistoryOnclick(){
        vpHelpMeBuyAddReceiverDetailContent.setCurrentItem(1);
        currIndex = 0;
        InitTabBg(1);
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
    /*输入地址poi附近检索*/
    private void poiBeginSearch( ){
        /*Toast.makeText(getBaseContext(),"poiBeginSearch",Toast.LENGTH_SHORT).show();*/
        etHelpMeBuyAddReceiverDetailContentAddress.setOnEditorActionListener(new MyEditorActionListener());

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
                hideInput(HelpMeBuyAddReceiverDetailActivity.this);//隐藏软键盘

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
    private void initBaiDuMap(){
        initPoiSearch();
        mBaiduMap = mvHelpMeBuyAddReceiverDetailContent.getMap();

        initOverlyWithMapView();
        //设置缩放级别，默认级别为12
        mBaiduMap.setMyLocationEnabled(true);
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        search= GeoCoder.newInstance();
        /**根据经纬度得到屏幕中心点地址**/
        search.setOnGetGeoCodeResultListener(this);
        initLocation();
        locationClient.start();


    }
    /*poi城市内检索*/
    private void initPoiSearch(){
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);

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
                ivHelpMeBuyAddReceiverDetailCenterLoc.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Point point = new Point(x,y);
                /*Toast.makeText(getBaseContext(),"x:"+x+"y:"+y,Toast.LENGTH_SHORT).show();*/
                //http://blog.csdn.net/sjf0115/article/details/7306284 获取控件在屏幕上的坐标
                currentPt = mBaiduMap.getProjection().fromScreenLocation(point);
                search.reverseGeoCode(new ReverseGeoCodeOption().location(currentPt));
                rlat = currentPt.latitude;
                rlon = currentPt.longitude;
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
            if (location == null || mvHelpMeBuyAddReceiverDetailContent == null) {
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
        drawable1.setBounds(0, 0,40, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
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
        poiBeginSearch();
        /*etHelpMeBuyAddSellerAddressContentAddress.setText(location.getAddrStr() + location.getBuildingName() +location.getFloor()+location.getStreet()+location.getStreetNumber());*/
        addressLocation=location.getAddrStr()+" "+location.getLocationDescribe();
        etHelpMeBuyAddReceiverDetailContentAddress.setText(addressLocation);
        beginSearchLalByAddress(addressLocation);
    }


    /**经纬度地址动画显示在屏幕中间  有关mark网站的出处http://blog.csdn.net/callmesen/article/details/40540895**/
    private void location(LatLng latLng){
        /*只要调用画面 就能赋值*/
        rlat = latLng.latitude;
        rlon = latLng.longitude;

        /*无论哪个调用此动画 都将经纬度赋值*/
       /* mBaiduMap.clear();*/
        //定义地图状态
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

    }
    @Override
    public void onGetPoiResult(PoiResult result) {
        if(result.getAllPoi() != null) {

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

    //地名转经纬度结果
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult.getLocation() != null) {
            location(geoCodeResult.getLocation());
            /*搜索附近地址*/
            poiSearchNearBy(geoCodeResult.getAddress(),geoCodeResult.getLocation());
            /*搜索附近地址*/
        }
    }

    //经纬度转地名结果
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
      /*  location(latLng);*/
        etHelpMeBuyAddReceiverDetailContentAddress.setText(addressLocation+"  "+result.getSematicDescription());
        poiSearchNearBy(addressLocation,latLng);

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


    /*poi附近检索*/

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





    /*百度地图 end*/


    protected void onDestroy(){
        mBaiduMap.clear();
        mBaiduMap.setMyLocationEnabled(false);
        search.destroy();
        isFirst = true;
        mvHelpMeBuyAddReceiverDetailContent.onDestroy();
        locationClient.unRegisterLocationListener(locationListener);
        if(locationClient!=null){
            locationClient.stop();
        }
        super.onDestroy();

    }
    @Override
    protected void onResume() {
        mvHelpMeBuyAddReceiverDetailContent.onResume();
        super.onResume();
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mvHelpMeBuyAddReceiverDetailContent.onPause();
    }
}
