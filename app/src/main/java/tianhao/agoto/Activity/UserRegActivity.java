package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import tianhao.agoto.Utils.PhoneFormatCheckUtils;

/**
 *
 * 注册页面
 * Created by admin on 2017/2/21.
 */

public class UserRegActivity extends Activity {
    /*验证码秒数*/
    int second = 0;
    /*验证码秒数*/
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

    /*获取验证码*/
    @BindView(R.id.rly_userreg_content_smsec)
    RelativeLayout rlyUserRegContentSMSec;
    /*获取验证码*/
    /*验证码倒计时秒数*/
    @BindView(R.id.tv_userreg_content_smsec)
    TextView tvUserRegContentSMSec;
    /*验证码倒计时秒数*/

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

    /*点击获取验证码*/
    @OnClick(R.id.rly_userreg_content_smsec)
    public void rlyUserRegContentSMSecOnclick(){
        if(isPhoneNum()){
             /*第一次点击倒计时*/
            if(second == 0){
                second = 60;
                beginTimeing();
            }else{
                Toast.makeText(this,"亲，请耐心等待。。",Toast.LENGTH_LONG).show();
            }
        /*第一次点击倒计时*/
        }else{
            Toast.makeText(this,"亲，请输入正确的手机号码。。",Toast.LENGTH_LONG).show();
        }

    }
    /*点击获取验证码*/
    /*判断是否正确输入手机号码*/
    private boolean isPhoneNum(){
        String tel = etUserRegContentTel.getText().toString();
        PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
        return  phoneFormatCheckUtils.isPhoneLegal(tel);
    }
    /*判断是否正确输入手机号码*/
    /*多线程开始倒计时    http://blog.csdn.net/yhm2046/article/details/8213629/*/
    private void beginTimeing(){
        ThreadShow threadShow = new ThreadShow();
        Thread thread = new Thread(threadShow);
        thread.start();
    }

    // handler类接收数据
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if(second > 0) {
                    tvUserRegContentSMSec.setText(Integer.toString(second));
                    second--;
                }else{
                    tvUserRegContentSMSec.setText("");
                    tvUserRegContentSMSec.setHint("获取验证码");
                }
                System.out.println("receive...."+second);
            }
        };
    };

    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (second > 0) {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    System.out.println("send...");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
            }
        }
    }

    /*多线程开始倒计时*/
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
