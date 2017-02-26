package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 * 设置页面
 * Created by zhyan on 2017/2/13.
 */

public class SettingActivity extends Activity {
    @BindView(R.id.rly_setting_topbar_leftmenu)
    RelativeLayout rlyHelpMeSendTopBarLeftMenu;//后退
    @BindView(R.id.rly_setting_idea_submit)//意见反馈
    RelativeLayout rlySettingIdeaSubmit;
    @BindView((R.id.rly_setting_altertel))
    RelativeLayout rlySettingAlterTel;//修改手机号码

    @BindView(R.id.rly_setting_aboutgoto)
    RelativeLayout rlySettingAboutGoto;//关于走兔

    @BindView(R.id.rly_setting_serviceitem)
    RelativeLayout rlySettingServiceItem;//服务条款
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
    }
    /*后退到主界面*/
    @OnClick(R.id.rly_setting_topbar_leftmenu)
    public void rlyHelpMeBuyTopBarLeftMenuOnclick(){
        /*Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();*/
        finish();
    }
    /*后退到主界面*/
    /*意见反馈*/
    @OnClick(R.id.rly_setting_idea_submit)
    public void rlySettingIdeaSubmitOnclick(){
        Intent intent = new Intent(this,IdeaSubmitActivity.class);
        startActivity(intent);
    }
    /*意见反馈*/

    /*修改手机号码*/
    @OnClick(R.id.rly_setting_altertel)
    public void rlySettingAlterTelOnclick(){
        Intent intent = new Intent(this,AlterTelActivity.class);
        startActivity(intent);
    }
    /*修改手机号码*/

    /*关于走兔*/
    @OnClick(R.id.rly_setting_aboutgoto)
    public void rlySettingAboutGotoOnclick(){
        Intent intent = new Intent(this,AboutGoToActivity.class);
        startActivity(intent);
    }
    /*关于走兔*/
    /*服务条款*/
    @OnClick(R.id.rly_setting_serviceitem)
    public void rlySettingServiceItemOnclick(){
        Intent intent = new Intent(this,ServiceItemActivity.class);
        startActivity(intent);
    }
    /*服务条款*/
}
