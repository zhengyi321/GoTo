package tianhao.agoto.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Adapter.SplashViewPageAdapter;
import tianhao.agoto.Adapter.SwipFlingRecyclerViewAdapter;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Common.Widget.DB.XCCacheManager.xccache.XCCacheManager;
import tianhao.agoto.Common.Widget.ScrollView.SpringScrollView;
import tianhao.agoto.Common.Widget.SlideShowView.LoopViewPager;
import tianhao.agoto.Common.Widget.SlideShowView.listener.OnItemSelectedListener;

import tianhao.agoto.Common.Widget.ViewPager.MyViewPager;
import tianhao.agoto.R;

/**http://www.open-open.com/lib/view/open1474962270182.html
 * Created by zhyan on 2017/2/10.
 * 过渡页
 *
 */

public class SplashActivity extends Activity {

    private List<ImageView> viewList;
    /*第一次过渡页*/
    @BindView(R.id.mvp_splash_content)
    ViewPager vpSplashContent;
    @BindView(R.id.lly_splash_content)
    LinearLayout ll_dots;
    SplashViewPageAdapter adapter;
    /*第一次过渡页*/
    @BindView(R.id.iv_splash_content)
    ImageView ivSplashContent;

    XCCacheManager xcCacheManager;
    private int[] imgSize = {R.drawable.splash1,R.drawable.splash2,R.drawable.splash3};
/*
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
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_lly);
        init();
    }



    private void init(){
        ButterKnife.bind(this);
        isFirstLoad();
        //开启多线程
       /* new Thread(new MyThread()).start();*/
        //进入主页面 关闭过渡页
        /*closeSplash();*/


    }
    private void isFirstLoad(){
        initViewPage();
      /*  // 自动切换页面功能
        new Thread(new Runnable() {

            @Override
            public void run() {

               *//* while (isLoop) {
                    SystemClock.sleep(2000);
                    handler.sendEmptyMessage(1);
                }*//*
            }
        }).start();*/
    }
    private void initViewPage(){
        vpSplashContent.setVisibility(View.VISIBLE);
        ll_dots.setVisibility(View.VISIBLE);
        ivSplashContent.setVisibility(View.INVISIBLE);
        viewList = new ArrayList<ImageView>();
        initVPData();
        adapter = new SplashViewPageAdapter(this,viewList);
        vpSplashContent.setAdapter(adapter);
       /* vpSplashContent.setOnItemSelectedListener(this);*//*


        vpSplashContent.setOnTouchListener(new View.OnTouchListener() {
            float bx,by;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getBaseContext(),"touch",Toast.LENGTH_SHORT).show();
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bx = event.getRawX();
                        by = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float ex = event.getRawX();
                        float ey = event.getRawY();
                        *//*向右边滑动*//*
                        if((ex -bx)>0){
                            if(vpSplashContent.getCurrentItem() > 0) {
                                vpSplashContent.setCurrentItem(vpSplashContent.getCurrentItem() - 1);
                            }
                        }else{
                            *//*向左边滑动*//*
                            if(vpSplashContent.getCurrentItem() < 2){
                                vpSplashContent.setCurrentItem(vpSplashContent.getCurrentItem() + 1);
                            }
                        }
                        break;
                }
                return false;
            }
        });*/
        /*adapter = new SplashViewPageAdapter(this,viewList);
        vpSplashContent.setAdapter(adapter);*/

        /*vpSplashContent.setViewList(viewList);//给viewpager设置view列表
        vpSplashContent.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void selected(int item, View view) {
                setDots(item);
            }
        });*/
    }
    private void initVPData(){

        for(int i=0;i<imgSize.length;i++){
            ImageView imageView = new ImageView(this);
           /* imageView.setImageResource(imgSize[i]);*/
            /*View v = LayoutInflater.from(this).inflate(R.layout.activity_splash_viewpage_item,null,false);*/
          /*  ImageView iv = (ImageView)v.findViewById(R.id.iv_splash_vp_item);
            iv.setImageResource(imgSize[0]);*//*
            if(i == 2){
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                    }
                });
            }*/
            viewList.add(imageView);
            //动态生成小圆点
            ImageView iv_dot = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(8,
                    8);
            lp.setMargins(4,0,4,0);
            iv_dot.setLayoutParams(lp);
            if(i==0){
                iv_dot.setImageResource(R.drawable.point_skyblue);
            }else{
                iv_dot.setImageResource(R.drawable.point_wheat);
            }

            ll_dots.addView(iv_dot);
        }
       /* viewList.get(0).setImageResource(R.drawable.splash1);
        viewList.get(1).setImageResource(R.drawable.splash2);*/
        ll_dots.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
    }



    private void setDots(int selectedPosition) {
        for (int i = 0; i < ll_dots.getChildCount(); i++) {
            if (i == selectedPosition) {
                ((ImageView) (ll_dots.getChildAt(i))).setImageResource(R.drawable.point_skyblue);
            } else {
                ((ImageView) (ll_dots.getChildAt(i))).setImageResource(R.drawable.point_wheat);
            }
        }
    }

/*


//主线程进入主页
    private void startMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    //其他线程负责关闭过渡页
*/
/*    private class MyThread implements Runnable{
        public void run(){
            try {

            }catch (Exception e){

            }
        }
    }*//*


    //关闭过渡页
    private void closeSplash(){
        vpSplashContent.setVisibility(View.INVISIBLE);
        ll_dots.setVisibility(View.INVISIBLE);
        */
/*ivSplashContent.setVisibility(View.VISIBLE);*//*

        Message message = handler.obtainMessage();
        message.what = 0;
        handler.sendMessageDelayed(message,2500);
    }
    protected void onPause(){
        super.onPause();
        finish();
    }
    protected void onDestroy(){
        super.onDestroy();
        if(null != handler) {
            handler = null;
            */
/*DebugLog.d(TAG, "release Handler success");*//*

        }
    }

*/

}
