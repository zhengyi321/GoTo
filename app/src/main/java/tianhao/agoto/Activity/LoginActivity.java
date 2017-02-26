package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import tianhao.agoto.Bean.UserLogin;
import tianhao.agoto.NetWorks.UserSettingNetWorks;
import tianhao.agoto.R;
import tianhao.agoto.Common.Widget.DB.ContactInjfoDao;

/**
 * 登录页面
 * Created by admin on 2017/2/21.
 */

public class LoginActivity extends Activity {

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
    @BindView(R.id.rly_login_content_loginsubmit)
    RelativeLayout rlyLoginContentLoginSubmit;
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
                Toast.makeText(getBaseContext(),"网络君被孤立啦！！"+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(UserLogin userLogin) {
                String name = "userName";
                /*Toast.makeText(getBaseContext(),""+userLogin.getResult(),Toast.LENGTH_LONG).show();*/
                if(userLogin.getUserName() != null){
                    mDao.deleteDate(name);
                    long addLong = mDao.addDate(name, userLogin.getUserName());
                    Toast.makeText(getBaseContext(),""+userLogin.getResult(),Toast.LENGTH_LONG).show();
                    finish();
                }else{

                    Toast.makeText(getBaseContext(),""+userLogin.getResult(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
