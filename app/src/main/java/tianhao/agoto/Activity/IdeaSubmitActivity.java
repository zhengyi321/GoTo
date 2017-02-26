package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 *
 * setting页面中的意见反馈页面
 * Created by zhyan on 2017/2/15.
 */

public class IdeaSubmitActivity extends Activity {

    @BindView(R.id.rly_ideasubmit_topbar_leftmenu)
    RelativeLayout rlyIdeaSubmitTopBarLeftMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideasubmit_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
    }

    /*返回上页*/
    @OnClick(R.id.rly_ideasubmit_topbar_leftmenu)
    public void rlyIdeaSubmitTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回上页*/
}
