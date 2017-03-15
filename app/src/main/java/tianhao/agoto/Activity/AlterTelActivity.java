package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 * 修改手机号码
 * Created by zhyan on 2017/2/16.
 */

public class AlterTelActivity extends Activity{

    /*返回上一页*/
    @BindView(R.id.rly_altertel_topbar_leftmenu)
    RelativeLayout rlyAlterTelTopBarLeftMenu;
    @OnClick(R.id.rly_altertel_topbar_leftmenu)
    public void rlyAlterTelTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回上一页*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_altertel_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
    }

}
