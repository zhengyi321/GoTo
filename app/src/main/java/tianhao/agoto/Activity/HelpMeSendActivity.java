package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 *
 * 帮我送
 * Created by zhyan on 2017/2/12.
 */

public class HelpMeSendActivity extends Activity {

    @BindView(R.id.rly_helpmesend_topbar_leftmenu)
    RelativeLayout rlyHelpMeSendTopBarLeftMenu;


    @BindView(R.id.lly_helpmesend_receiverdata)
    LinearLayout llyHelpMeSendReceiverData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpmesend_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
    }
    /*后退到主界面*/
    @OnClick(R.id.rly_helpmesend_topbar_leftmenu)
    public void rlyHelpMeBuyTopBarLeftMenuOnclick(){
        /*Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();*/
        finish();
    }
    /*后退到主界面*/
    /*添加联系人*/

    @OnClick(R.id.lly_helpmesend_receiverdata)
    public void llyHelpMeSendReceiverDataOnclick(){
        Intent intent = new Intent(this,HelpMeSendAddContacterActivity.class);
        startActivity(intent);
    }
    /*添加联系人*/
}