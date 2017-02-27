package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/2/27.
 */

public class BaiduAddressSearchSuggestActivity extends Activity{

    @BindView(R.id.rlv_baiduaddress_suggest_content)
    RecyclerView rlyBaiduAddressSuggestContent;
    @BindView(R.id.rly_baiduaddress_search_suggest_topbar_back)
    RelativeLayout rlyBaiduAddressSearchSuggestTopBarBack;
    @BindView(R.id.et_baiduaddress_search_suggest_topbar_address)
    EditText etBaiduAddressSearchSuggestTopbarAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baiduaddress_search_suggest_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        /*initGlassBg();*/
        /*initTran();*/


    }
}
