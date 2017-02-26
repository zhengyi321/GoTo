package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 * 设置
 * 服务条款
 * Created by zhyan on 2017/2/16.
 */

public class ServiceItemActivity extends Activity{

    @BindView(R.id.rly_serviceitem_topbar_leftmenu)
    RelativeLayout rlyServiceItemTopBarLeftMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_serviceitem_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
    }

    /*返回上页*/
    @OnClick(R.id.rly_serviceitem_topbar_leftmenu)
    public void rlyServiceItemTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回上页*/
}
