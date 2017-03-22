package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tianhao.agoto.Adapter.SplashViewPageAdapter;
import tianhao.agoto.Common.Widget.DB.XCCacheManager.xccache.XCCacheManager;
import tianhao.agoto.R;

/**http://www.open-open.com/lib/view/open1474962270182.html
 * Created by zhyan on 2017/2/10.
 * 过渡页
 *
 */

public class SplashActivity extends Activity {

    private List<View> viewList ;
    /*第一次过渡页*/
    @BindView(R.id.mvp_splash_content)
    ViewPager vpSplashContent;
    @BindView(R.id.lly_splash_content)
    LinearLayout lly_dots;
    //用于引用布局好的三个itemView布局
    SplashViewPageAdapter adapter;
    XCCacheManager xcCacheManager;
    /*第一次过渡页*/
    private int[] imgSize = {R.drawable.splash1,R.drawable.splash2,R.drawable.splash3};
    //Handler处理事物
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    startMainActivity();
                    finish();
                default:
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_lly);
        ButterKnife.bind(this);
        init();
    }



    private void init(){
        xcCacheManager = XCCacheManager.getInstance(this);

        isFirstLoad();
    }
    private void isFirstLoad(){

        if(xcCacheManager.readCache("isNewApp")== null) {
            initViewPage();
        }else{
            closeSplash();
        }


    }

    private void initViewPage(){
        vpSplashContent.setVisibility(View.VISIBLE);
        lly_dots.setVisibility(View.VISIBLE);
        viewList= new ArrayList<View>();
        for(int i = 0;i<imgSize.length;i++) {

     /*       ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imgSize[i]);*/
            if(i == 0) {
                View view = getLayoutInflater().inflate(R.layout.activity_splash_item_img1, null, false);
                viewList.add(view);
            }else if(i == 1){
                View view = getLayoutInflater().inflate(R.layout.activity_splash_item_img2, null, false);
                viewList.add(view);
            }else if(i == 2){
                View view = getLayoutInflater().inflate(R.layout.activity_splash_item_img3, null, false);
                viewList.add(view);
            }
            ImageView iv_dot = new ImageView(this);
     /*       viewList.add(iv_dot);*/
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(8,
                    8);
            lp.setMargins(4, 0, 4, 0);
            iv_dot.setLayoutParams(lp);
            if (i == 0) {
                iv_dot.setImageResource(R.drawable.point_skyblue);
            } else {
                iv_dot.setImageResource(R.drawable.point_wheat);
            }
            lly_dots.addView(iv_dot);

        }
        lly_dots.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        adapter = new SplashViewPageAdapter(this,viewList);
        vpSplashContent.setAdapter(adapter);
        vpSplashContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setDots(position);
                if(position == 2){
                    viewList.get(position).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            xcCacheManager.writeCache("isNewApp","yes");
                            startMainActivity();
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setDots(int selectedPosition) {
        for (int i = 0; i < lly_dots.getChildCount(); i++) {
            if (i == selectedPosition) {
                ((ImageView) (lly_dots.getChildAt(i))).setImageResource(R.drawable.point_skyblue);
            } else {
                ((ImageView) (lly_dots.getChildAt(i))).setImageResource(R.drawable.point_wheat);
            }
        }
    }

    //关闭过渡页
    private void closeSplash(){

        vpSplashContent.setVisibility(View.INVISIBLE);
        lly_dots.setVisibility(View.INVISIBLE);
        Message message = handler.obtainMessage();
        message.what = 0;
        handler.sendMessageDelayed(message,2500);
    }



    //主线程进入主页
    private void startMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    protected void onPause(){
        super.onPause();
        finish();
    }
    protected void onDestroy(){
        super.onDestroy();
        if(null != handler) {
            handler = null;


        }
    }

}
