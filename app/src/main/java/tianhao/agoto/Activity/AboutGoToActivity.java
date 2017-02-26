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
 * 关于走兔
 * Created by zhyan on 2017/2/16.
 */

public class AboutGoToActivity extends Activity{

    @BindView(R.id.rly_aboutgoto_topbar_leftmenu)
    RelativeLayout rlyAboutGoToTopBarLeftMenuBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aboutgoto_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
    }

    /*返回上一页*/
    @OnClick(R.id.rly_aboutgoto_topbar_leftmenu)
    public void rlyAboutGoToTopBarLeftMenuBackOnclick(){
        finish();
    }
    /*返回上一页*/
}
