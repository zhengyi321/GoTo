package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/3/19.
 */

public class ShouFeiBiaoZhunActivity extends Activity{
    @BindView(R.id.wv_shoufeibiaozhun_content)
    WebView wvShouFeiBiaoZhunContent;

    /*返回*/
    @BindView(R.id.rly_shoufeibiaozhun_topbar_leftmenu)
    RelativeLayout rlyShouFeiBiaoZhunTopBarLeftMenu;
    @OnClick(R.id.rly_shoufeibiaozhun_topbar_leftmenu)
    public void rlyShouFeiBiaoZhunTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回*/


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoufeibiaozhun_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
        initWebView();
    }

    private void initWebView(){
        wvShouFeiBiaoZhunContent.loadUrl("file:///android_asset/index.html");
    }

}
