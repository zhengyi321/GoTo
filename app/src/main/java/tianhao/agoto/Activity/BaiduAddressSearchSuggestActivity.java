package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tianhao.agoto.R;

/**
 *
 * http://lbsyun.baidu.com/index.php?title=androidsdk/guide/retrieval
 * Created by admin on 2017/2/27.
 */

public class BaiduAddressSearchSuggestActivity extends Activity implements OnGetSuggestionResultListener {

    @BindView(R.id.rlv_baiduaddress_suggest_content)
    RecyclerView rlyBaiduAddressSuggestContent;
    @BindView(R.id.rly_baiduaddress_search_suggest_topbar_back)
    RelativeLayout rlyBaiduAddressSearchSuggestTopBarBack;
    @BindView(R.id.et_baiduaddress_search_suggest_topbar_address)
    EditText etBaiduAddressSearchSuggestTopbarAddress;
    private SuggestionSearch mSuggestionSearch;
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
    private void initSuggestionSearch(){
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
    }
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            return;
            //未找到相关结果
        }
        //获取在线建议检索结果
        List<SuggestionResult.SuggestionInfo> suggestionInfoList = suggestionResult.getAllSuggestions();
    }
    private void suggestionSearchOnLine(){
        // 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword("百度")
                .city("北京"));
    }

    protected void onDestroy(){
        mSuggestionSearch.destroy();
        super.onDestroy();

    }
}
