package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import rx.Observer;
import tianhao.agoto.Bean.UserLogin;
import tianhao.agoto.NetWorks.UserSettingNetWorks;
import tianhao.agoto.R;
import tianhao.agoto.Common.Widget.DB.ContactInjfoDao;

/**
 * 登录页面
 * Created by admin on 2017/2/21.
 *
 * 友盟第三方登录
 * http://download.csdn.net/download/donkor_/9700844
 *
 */

public class LoginActivity extends Activity implements Handler.Callback, PlatformActionListener {

    /*注册页面*/
    @BindView(R.id.rly_login_content_reg)
    RelativeLayout rlyLoginContentReg;
    /*注册页面*/
    /*返回页面*/
    @BindView(R.id.rly_login_topbar_leftmenu_back)
    RelativeLayout rlyLoginTopBarLeftMenuBack;
    /*返回页面*/
    /*手机号码*/
    @BindView(R.id.et_login_content_tel)
    EditText etLoginContentTel;
    /*手机号码*/
    /*密码*/
    @BindView(R.id.et_login_content_pass)
    EditText etLoginContentPass;
    /*密码*/
    /*服务条款*/
    @BindView(R.id.tv_login_content_serviceitem)
    TextView tvLoginContentServiceItem;
    /*服务条款*/
    /*登录*/
    /*正常登录*/
    @BindView(R.id.rly_login_content_loginsubmit)
    RelativeLayout rlyLoginContentLoginSubmit;
    /*正常登录*/
    /*第三方登录 qq 微信*/
    @BindView(R.id.rly_login_content_qqlogin)
    RelativeLayout rlyLoginContentQQLogin;
    @BindView(R.id.rly_login_content_wxlogin)
    RelativeLayout rlyLoginContentWXLogin;

    private  final int MSG_USERID_FOUND = 1;
    private  final int MSG_LOGIN = 2;
    private  final int MSG_AUTH_CANCEL = 3;
    private  final int MSG_AUTH_ERROR = 4;
    private  final int MSG_AUTH_COMPLETE = 5;

    /*第三方登录 qq 微信*/

    /*登录*/
    private ContactInjfoDao mDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        //初始化
        ShareSDK.initSDK(this);
        mDao =new ContactInjfoDao(this);
    }

    /*注册页面*/
    @OnClick(R.id.rly_login_content_reg)
    public void rlyLoginContentRegOnclick(){
        Intent intent = new Intent(this,UserRegActivity.class);
        startActivity(intent);
    }
    /*注册页面*/
    /*返回上层*/
    @OnClick(R.id.rly_login_topbar_leftmenu_back)
    public void rlyLoginTopBarLeftMenuBackOnclick(){
        this.finish();
    }
    /*返回上层*/
    /*服务条款*/
    @OnClick(R.id.tv_login_content_serviceitem)
    public void tvLoginContentServiceItemOnclick(){
        Intent intent = new Intent(this,ServiceItemActivity.class);
        startActivity(intent);
    }
    /*服务条款*/
    /*登录提交*/
    @OnClick(R.id.rly_login_content_loginsubmit)
    public void rlyLoginContentLoginSubmitOnclick(){
        loginSubmit();
    }
    /*第三方登录  qq 微信*/
    @OnClick(R.id.rly_login_content_qqlogin)
    public void rlyLoginContentQQLoginOnclick(){
    //执行授权,获取用户信息
            authorize(new QQ(this));
    }
    @OnClick(R.id.rly_login_content_wxlogin)
    public void rlyLoginContentWXLoginOnclick(){
        authorize(new Wechat(this));
    }



    //执行授权,获取用户信息
    private void authorize(Platform plat) {
        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        //true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(false);
        plat.showUser(null);
    }

    //发送登陆信息
    private void login(String plat, String userId, HashMap<String, Object> userInfo) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }
    protected void onDestroy() {
        //释放资源
        ShareSDK.stopSDK(this);
        Platform qq = ShareSDK.getPlatform(this, QQ.NAME);
        Platform wechat = ShareSDK.getPlatform(this, Wechat.NAME);
        Platform weibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        if (qq.isValid()) {
            qq.removeAccount();
        }
        if (wechat.isValid()) {
            wechat.removeAccount();
        }
        if (weibo.isValid()) {
            weibo.removeAccount();
        }
        super.onDestroy();
    }
    /*第三方登录  qq 微信*/

    /*登录提交*/

    private void loginSubmit(){

        String tel = etLoginContentTel.getText().toString();
        String pass = etLoginContentPass.getText().toString();

        UserSettingNetWorks userSettingNetWorks = new UserSettingNetWorks();
        userSettingNetWorks.userLoginToNet(tel, pass, new Observer<UserLogin>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getBaseContext(),"网络君凯旋失败啦！！快检查你的账号和密码吧",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(UserLogin userLogin) {
                String name = "userName";
                String usid = "usid";
                String phone = "phone";
                String loginStatus = "loginStatus";
                /*Toast.makeText(getBaseContext(),""+userLogin.getResult(),Toast.LENGTH_LONG).show();*/
                if(userLogin.getUserName() != null){
                    mDao.deleteDate(name);
                    mDao.deleteDate(phone);
                    mDao.deleteDate(usid);
                    long addNameLong = mDao.addDate(name,name);
                    long addPhoneLong = mDao.addDate(phone,phone);
                    long addUsidLong = mDao.addDate(usid,usid);
                    long addLoginStatusLong = mDao.addDate(loginStatus,"yes");
                    Toast.makeText(getBaseContext(),""+userLogin.getResult(),Toast.LENGTH_LONG).show();
                    finish();
                }else{

                    Toast.makeText(getBaseContext(),""+userLogin.getResult(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND: {
                Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_LOGIN: {
                String text = getString(R.string.logining, msg.obj);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }
    /*第三方平台登陆完成 并记录返回值*/
    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            //登录成功,获取需要的信息
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), res);

            String openid = platform.getDb().getUserId() + "";
            String gender = platform.getDb().getUserGender();
            String head_url = platform.getDb().getUserIcon();
            String nickname = platform.getDb().getUserName();
            mDao.addDate("userName",nickname);
            mDao.addDate("thirdHeadUrl",head_url);
            finish();

        }
    }
    /*第三方平台登陆完成 并记录返回值*/
    @Override
    public void onError(Platform platform, int action, Throwable t) {
        if(action==Platform.ACTION_USER_INFOR){
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR,this);
        }
        t.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }
}
