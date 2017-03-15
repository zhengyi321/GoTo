package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 *
 * 消息中心
 * Created by zhyan on 2017/2/20.
 */

public class MessageCenterActivity extends Activity{

    /*返回*/
    @BindView(R.id.rly_messagecenter_topbar_leftmenu)
    RelativeLayout rlyMessageCenterTopBarLeftMenu;
    @OnClick(R.id.rly_messagecenter_topbar_leftmenu)
    public void rlyMessageCenterTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagecenter_lly);
        init();
    }


    private void init(){
        ButterKnife.bind(this);

    }
}
