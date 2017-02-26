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
import tianhao.agoto.Bean.UserReg;
import tianhao.agoto.NetWorks.UserSettingNetWorks;
import tianhao.agoto.R;

/**
 *
 * 注册页面
 * Created by admin on 2017/2/21.
 */

public class UserRegActivity extends Activity {

    /*手机号码*/
    @BindView(R.id.et_userreg_content_tel)
    EditText etUserRegContentTel;
    /*手机号码*/
    /*验证码*/
    @BindView(R.id.et_userreg_content_pass)
    EditText etUserRegContentPass;
    /*验证码*/
    /*注册提交*/
    @BindView(R.id.rly_userreg_content_regsubmit)
    RelativeLayout rlyUserRegContentRegSubmit;
    /*注册提交*/

    /*查看服务协议条款*/
    @BindView(R.id.tv_userreg_content_serviceitem)
    TextView tvUserRegContentServiceItem;

    /*查看服务协议条款*/

    /*回退上一页*/
    @BindView(R.id.rly_userreg_topbar_leftmenu_back)
    RelativeLayout rlyUserRegTopBarLeftMenuBack;
    /*回退上一页*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_userreg_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
    }
    /*注册提交点击事件*/
    @OnClick(R.id.rly_userreg_content_regsubmit)
    public void rlyUserRegContentRegSubmitOnclick(){
        regSubmit();
    }
    /*注册提交点击事件*/


    /*查看服务协议*/
    @OnClick(R.id.tv_userreg_content_serviceitem)
    public void tvUserRegContentServiceItemOnclick(){
        Intent intent = new Intent(this,ServiceItemActivity.class);
        startActivity(intent);
    }
    /*查看服务协议*/
    @OnClick(R.id.rly_userreg_topbar_leftmenu_back)
    public void rlyUserRegTopBarLeftMenuBackOnclick(){
        this.finish();
    }

    /*注册信息*/
    private void regSubmit(){
        String tel = etUserRegContentTel.getText().toString();
        String pass = etUserRegContentPass.getText().toString();
        UserSettingNetWorks userSettingNetWorks = new UserSettingNetWorks();
        userSettingNetWorks.userRegToNet(tel,pass,new Observer<UserReg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getBaseContext(),"网络君被孤立啦！！"+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(UserReg userReg) {

                Toast.makeText(getBaseContext(),""+userReg.getResult(),Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    /*注册信息*/
}
