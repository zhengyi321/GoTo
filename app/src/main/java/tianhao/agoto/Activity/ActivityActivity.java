package tianhao.agoto.Activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.CirclePic.ActivityPictureRotator;
import tianhao.agoto.CirclePic.MainPictureRotator;
import tianhao.agoto.R;
import tianhao.agoto.Utils.BlurUtil;
import tianhao.agoto.Utils.ImageUtils;
import tianhao.agoto.Utils.MemoryUtils;

/**
 * 活动页面
 * Created by zhyan on 2017/2/13.
 */

public class ActivityActivity extends Activity {


    private LayoutInflater inflater;
    @BindView(R.id.activity_activity_lly)
    LinearLayout activityActivityLLy;
/*    @BindView(R.id.iv_activity_activity_content_bg)
    ImageView ivActivityActivityBg;*/
    @BindView(R.id.activity_activity_topbar_lly)
    LinearLayout activityActivityTopBarLLy;
    @BindView(R.id.lly_activity_board_viewpager)
    LinearLayout llyActivityBoardViewPager;
    @BindView(R.id.rly_activity_activity_topbar_leftmenu)
    RelativeLayout rlyActivityActivityTopBarLeftMenu;

    BlurUtil blurUtil = new BlurUtil();
    ImageUtils imageUtils = new ImageUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activity_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        inflater=LayoutInflater.from(this);
        /*initGlassBg();*/
        /*initTran();*/
        cicleWheelInit();

    }


    /* 轮播初始化*/
    private void cicleWheelInit(){
       /* llyActivityBoardViewPager= (LinearLayout) findViewById(R.id.lly_activity_board_viewpager);*/
        List<String> datas = new ArrayList<String>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        llyActivityBoardViewPager.addView(new ActivityPictureRotator(this,inflater,2500).initView(datas));

    }
    /*毛玻璃化*/
    private void initGlassBg(){
       /* Drawable drawable = getResources().getDrawable(R.drawable.wheel1);*/
        /*Bitmap bgBitMap = imageUtils.drawableToBitmap(drawable);
        Bitmap bitmap = blurUtil.doBlur(bgBitMap,70,false);*/
        /*Bitmap bitmap = imageUtils.decodeSampledBitmapFromResource(getResources(),R.drawable.splash,100,100);*/
       /* ivActivityActivityBg.setImageBitmap(bitmap);*/
/*        Drawable drawableBg = new BitmapDrawable(bitmap);
        activityActivityLLy.setBackground(drawableBg);*/
        /*activityActivityLLy.setB*/
    }
    /*毛玻璃化*/
    /*透明*/
    private void initTran(){
        if(Build.VERSION.SDK_INT > 16) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_LAYOUT_FLAGS|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }
    /*透明*/

    /*沉浸式状态栏http://www.jianshu.com/p/be2b7be418d7*/

    /*https://github.com/wuyinlei/ImmersiveStatusBar*/

    private void initTopbarDownWay(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }
    /*https://github.com/wuyinlei/ImmersiveStatusBar*/
/*沉浸式状态栏http://www.jianshu.com/p/be2b7be418d7*/

    @OnClick(R.id.rly_activity_activity_topbar_leftmenu)
    public void activityActivityTopBarLeftMenuBackOnClick(){

        finish();
        onDestory();
    }

    /*为了解决关闭activity后内存容量没有回收导致的内存溢出问题*/
    public   void onDestory(){
        super.onDestroy();
/*        MemoryUtils memoryUtils = new MemoryUtils();
        memoryUtils.cleanMemoryNoText(this);*/
        /*llyActivityBoardViewPager = null;*/
    }
    /*为了解决关闭activity后内存容量没有回收导致的内存溢出问题*/
}
