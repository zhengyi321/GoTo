package tianhao.agoto.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import net.tsz.afinal.FinalBitmap;

import java.net.URL;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import tianhao.agoto.Common.Widget.DB.XCCacheManager.xccache.XCCacheManager;
import tianhao.agoto.Common.Widget.ImageView.CircleImageView;
import tianhao.agoto.Fragment.MainFragment;
import tianhao.agoto.R;
import tianhao.agoto.Common.Widget.DB.ContactInjfoDao;
import tianhao.agoto.Utils.ImageUtils;

/*

    主页
Create by zhyan*/
public class MainActivity extends AppCompatActivity {

    private LayoutInflater inflater;


    @BindView(R.id.rly_main_leftmenu_setting)
    RelativeLayout rlyMainLeftMenuSetting;
    @BindView(R.id.rly_main_leftmenu_activity)
    RelativeLayout rlyMainLeftMenuActivity;
    @BindView(R.id.rly_main_leftmenu_myorder)
    RelativeLayout rlyMainLeftMenuMyOrder;
    @BindView(R.id.rly_main_leftmenu_mywallet)
    RelativeLayout rlyMainLeftMenuMyWallet;
    @BindView(R.id.rly_main_leftmenu_addressmanage)
    RelativeLayout rlyMainLeftMenuAddressManage;
    @BindView(R.id.rly_main_leftmenu_message)
    RelativeLayout rlyMainLeftMenuMessage;
    @BindView(R.id.rly_main_topbar_leftmenu)
    RelativeLayout rlyLeftMenu;
    @BindView(R.id.lly_main_leftmenu_login)
    LinearLayout llyMainLeftMenuLogin;
    @BindView(R.id.dly_main_activity)
    DrawerLayout dlyMainActivity;
   /* @BindView(R.id.civ_main_leftmenu_headimg)
    ImageView civMainLeftMenuHeadImg;*/
    @BindView(R.id.civ_main_leftmenu_headimg)
    CircleImageView civMainLeftMenuHeadImg;


    @BindView(R.id.tv_main_leftmenu_name)
    TextView tvMainLeftMenuName;
    private XCCacheManager xcCacheManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lly);
        init();

    }
    /*各种初始化*/
    private void init(){
        ButterKnife.bind(this);
        xcCacheManager = XCCacheManager.getInstance(this);
        initFragment();

    }


/*    private void initRecycleView(RecyclerView rv){
        List<String> dataList = new ArrayList<String>();
        dataList.add("");
        *//*多线程运行 行不通*//*
        MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(getBaseContext(),dataList);
        rv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);
        rv.addOnScrollListener(new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                *//*loadMoreData();*//*
            }
        });
    }*/



    /*初始化fragment*/
    private void initFragment(){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        // 动态增加Fragment
        MainFragment mainFragment = new MainFragment();
        transaction.add(R.id.main_content_lly, mainFragment, "content");
        transaction.commit();
    }

    /*初始化fragment*/



    /*侧边栏*/

    @OnClick(R.id.rly_main_topbar_leftmenu)
    public void leftMenuOnClick(){
        dlyMainActivity.openDrawer(Gravity.LEFT);
        /*Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();*/

    }
    /*侧边栏*/




/*侧滑菜单设置*/

    @OnClick(R.id.rly_main_leftmenu_setting)
    public void rlyMainLeftMenuSettingOnClick(){
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

/*侧滑菜单设置*/


/*活动页面*/

    @OnClick(R.id.rly_main_leftmenu_activity)
    public void rlyMainLeftMenuActivityOnClick(){
        Intent intent = new Intent(this,ActivityActivity.class);
        startActivity(intent);
    }

/*活动页面*/



/*我的订单*/

    @OnClick(R.id.rly_main_leftmenu_myorder)
    public void rlyMainLeftMenuMyOrderOnclick(){
        Intent intent = new Intent(this,MyOrderActivity.class);
        startActivity(intent);
    }

/*我的订单*/



/*我的钱包*/

    @OnClick(R.id.rly_main_leftmenu_mywallet)
    public void rlyMainLeftMenuMyWalletOnclick(){
        Intent intent = new Intent(this,MyWalletActivity.class);
        startActivity(intent);
    }
/*
我的钱包*/


/*地址管理*/

    @OnClick(R.id.rly_main_leftmenu_addressmanage)
    public void rlyMainLeftMenuAddressManageOnclick(){
        Intent intent = new Intent(this,AddressManageActivity.class);
        startActivity(intent);
    }

/*地址管理*/


/*消息中心*/

    @OnClick(R.id.rly_main_leftmenu_message)
    public void rlyMainLeftMenuMessageOnclick(){
        Intent intent = new Intent(this,MessageCenterActivity.class);
        startActivity(intent);
    }

/*消息中心*/



/*登录*/

    @OnClick(R.id.lly_main_leftmenu_login)
    public void llyMainLeftMenuLoginOnclick(){
        String isLogin = xcCacheManager.readCache("loginStatus");
        if((isLogin != null)&&(isLogin.equals("yes"))){

        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

/*登录*/


    protected void onStop(){
        super.onStop();
     /*   MemoryUtils memoryUtils = new MemoryUtils();
        memoryUtils.cleanMemoryNoText(this);*/
    }


    protected void onResume(){
        super.onResume();

        initAfterLogin();
 /*       MemoryUtils memoryUtils = new MemoryUtils();
        memoryUtils.cleanMemoryNoText(this);*/
    }

    /*登陆以后初始化名字*/
    private void initAfterLogin(){
        String userName = xcCacheManager.readCache("userName");
        /*Toast.makeText(this,"userName:"+userName,Toast.LENGTH_LONG).show();*/
        if(userName != null){
            tvMainLeftMenuName.setText(userName);
            String headImgUrl = xcCacheManager.readCache("headUrl");
            if((headImgUrl != null) &&(!headImgUrl.isEmpty())){
                Toast.makeText(this,"headimgurl:"+headImgUrl,Toast.LENGTH_LONG).show();
                FinalBitmap finalBitMap=null;
                finalBitMap=FinalBitmap.create(MainActivity.this);
                finalBitMap.display(civMainLeftMenuHeadImg, headImgUrl);
                /*ImageUtils imageUtils = new ImageUtils();
                Bitmap bitmap = imageUtils.getbitmap(headImgUrl);
                civMainLeftMenuHeadImg.setImageBitmap(bitmap);*/

            }
            /*Toast.makeText(this,"onResume"+userLogin.getUserName(),Toast.LENGTH_LONG).show();*/
        }
    }
    /*登陆以后初始化名字*/
}


