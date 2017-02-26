package tianhao.agoto.Activity;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;
import tianhao.agoto.Utils.SystemUtils;

/**
 *我的订单
 * https://yq.aliyun.com/articles/36007
* Created by zhyan on 2017/2/14.
*/

public class MyOrderActivity extends Activity {
    // ViewPager是google SDk中自带的一个附加包的一个类，可以用来实现屏幕间的切换。
    // android-support-v4.jar
    // 页卡头标
    @BindView(R.id.tv_myorder_allorder)
    TextView tvMyOrderAllOrder;
    @BindView(R.id.tv_myorder_waitforsay)
    TextView tvMyOrderWaitForSay;
    @BindView(R.id.rly_myorder_allorder)
    RelativeLayout rlyMyOrderAllOrder;
    @BindView(R.id.rly_myorder_waitforsay)
    RelativeLayout rlyMyOrderWaitForSay;
    @BindView(R.id.vp_myorder)
    ViewPager vpMyOrder;// 页卡头标 页卡内容
    @BindView(R.id.iv_myorder_tab_greenbottom)
    ImageView ivMyOrderTabGreenBottom;// 动画图片
    @BindView(R.id.rly_myorder_topbar_leftmenu)
    RelativeLayout rlyMyOrderTopBarLeftMenu;
    @BindView(R.id.rly_myorder_topbar_rightmenu)
    RelativeLayout rlyMyOrderTopBarRightMenu;


    private List<View> listViews; // Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_lly);
        init();
    }


    private void init(){
        ButterKnife.bind(this);
        initSwitchContent();
    }

    private void initSwitchContent(){
        InitTextView();
        InitImageView();
        InitViewPager();
    }
    /**
     * 初始化头标
     */
    private void InitTextView() {
        tvMyOrderAllOrder.setTextColor(getResources().getColor(R.color.colorActivityMyOrderTabSwitchGreenBg));
    }

    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        listViews.add(mInflater.inflate(R.layout.activity_myorder_content_tab_vp_item_lv_item, null));
        listViews.add(mInflater.inflate(R.layout.activity_myorder_content_tab_vp_item_lv, null));
        vpMyOrder.setAdapter(new MyPagerAdapter(listViews));
        vpMyOrder.setCurrentItem(0);
        vpMyOrder.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化动画
     */
    private void InitImageView() {
/*        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.icon_tab_winter_a)
                .getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置*/
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
        offset = (screenW / 2 - bmpW) / 2;// 计算偏移量  screenW/有几个tab 就除以几
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        ivMyOrderTabGreenBottom.setImageMatrix(matrix);
        ivMyOrderTabGreenBottom.setLayoutParams(params);
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
            // 测试页卡1内的按钮事件
            /*if (arg1 == 0) {
                Button btn = (Button) arg0.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        new AlertDialog.Builder(MyOrderActivity.this)
                                .setTitle("说明")
                                .setMessage("单个页卡内按钮事件测试")
                                .setNegativeButton("确定",
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                            }
                                        }).show();
                    }
                });
            }*/

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

    @OnClick(R.id.rly_myorder_topbar_leftmenu)
    public void rlyMyOrderTopBarLeftMenuOnclick(){
        finish();
    }

    @OnClick(R.id.rly_myorder_allorder)
    public void rlyMyOrderAllOrderOnclick(){
        vpMyOrder.setCurrentItem(0);
    }

    @OnClick(R.id.rly_myorder_waitforsay)
    public void setRlyMyOrderWaitForSayOnclick(){
        vpMyOrder.setCurrentItem(1);
    }

    @OnClick(R.id.rly_myorder_topbar_rightmenu)
    public void rlyMyOrderTopBarRightMenuOnclick(){
        Intent intent = new Intent(this,OrderCheckActivity.class);
        startActivity(intent);
    }
    /**
     * 头标点击监听
     */


    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
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
            ivMyOrderTabGreenBottom.startAnimation(animation);
            switch (arg0){
                case 0:
                    tvMyOrderAllOrder.setTextColor(getResources().getColor(R.color.colorActivityMyOrderTabSwitchGreenBg));
                    tvMyOrderWaitForSay.setTextColor(getResources().getColor(R.color.colorActivityMyOrderTabSwitchUnSelectedWordGrayBg));
                    break;
                case 1:
                    tvMyOrderAllOrder.setTextColor(getResources().getColor(R.color.colorActivityMyOrderTabSwitchUnSelectedWordGrayBg));
                    tvMyOrderWaitForSay.setTextColor(getResources().getColor(R.color.colorActivityMyOrderTabSwitchGreenBg));

                    break;

            }
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    }
}