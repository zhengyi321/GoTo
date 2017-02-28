package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Adapter.BaiduAddressSearchSuggestRecycleViewAdapter;
import tianhao.agoto.Common.Widget.LinearLayoutManager.CustomLinearLayoutManager;
import tianhao.agoto.Common.Widget.LinearLayoutManager.FullyLinearLayoutManager;
import tianhao.agoto.Common.Widget.LinearLayoutManager.MyLinearLayoutManager;
import tianhao.agoto.R;

/**
 *
 * http://lbsyun.baidu.com/index.php?title=androidsdk/guide/retrieval
 * Created by admin on 2017/2/27.
 */

public class BaiduAddressSearchSuggestActivity extends Activity implements/* OnGetSuggestionResultListener,*/TextWatcher,OnGetPoiSearchResultListener {

    @BindView(R.id.rlv_baiduaddress_suggest_content)
    RecyclerView rlvBaiduAddressSuggestContent;
    @BindView(R.id.rly_baiduaddress_search_suggest_topbar_back)
    RelativeLayout rlyBaiduAddressSearchSuggestTopBarBack;
    @BindView(R.id.et_baiduaddress_search_suggest_topbar_address)
    EditText etBaiduAddressSearchSuggestTopbarAddress;
    // 类PoiSearch继承poi检索接口
    private PoiSearch mpoiSearch;
    private SuggestionSearch mSuggestionSearch;
    private SuggestionSearchOption suggestionSearchOption;
    private BaiduAddressSearchSuggestRecycleViewAdapter baiduAddressSearchSuggestRecycleViewAdapter;
    private  List<SuggestionInfo> suggestionInfoList;
    private List<PoiInfo> poiInfoList;
    private String city,keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baiduaddress_search_suggest_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        initSuggestionSearch();
        initRecycleView();
        /*initGlassBg();*/
        /*initTran();*/


    }
    private void initSuggestionSearch(){
        /*在线建议初始化*/
        /*mSuggestionSearch = SuggestionSearch.newInstance();*/
        /*监听在线建议*/
       /* mSuggestionSearch.setOnGetSuggestionResultListener(this);*/
        /*输入初始化*/
        etBaiduAddressSearchSuggestTopbarAddress.addTextChangedListener(this);
        /*建议地址初始化*/
        /*suggestionInfoList = new ArrayList<SuggestionInfo>();*/
        poiInfoList = new ArrayList<PoiInfo>();
        /*搜索参数初始化*/
       /* suggestionSearchOption = new SuggestionSearchOption();*/
        // 实例化PoiSearch
        mpoiSearch = PoiSearch.newInstance();
        // 注册搜索事件监听
        mpoiSearch.setOnGetPoiSearchResultListener(this);
    }

    private void initRecycleView(){

        /*建议地址数据填充*/
        baiduAddressSearchSuggestRecycleViewAdapter = new BaiduAddressSearchSuggestRecycleViewAdapter(this,poiInfoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
       /* layoutManager.setAutoMeasureEnabled(true);*/
        //设置布局管理器
        rlvBaiduAddressSuggestContent.setLayoutManager(layoutManager);
        rlvBaiduAddressSuggestContent.setAdapter(baiduAddressSearchSuggestRecycleViewAdapter);


    }
   /* @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            return;
            //未找到相关结果
        }
        //获取在线建议检索结果
        *//*suggestionInfoList = suggestionResult.getAllSuggestions();*//*
        *//*Toast.makeText(this,"et:"+suggestionResult.getAllSuggestions().get(0).city+suggestionResult.getAllSuggestions().get(0).district,Toast.LENGTH_SHORT).show();*//*

       *//* Toast.makeText(this,"size:"+suggestionResult.getAllSuggestions().size(),Toast.LENGTH_SHORT).show();
        baiduAddressSearchSuggestRecycleViewAdapter.setDataList(suggestionResult.getAllSuggestions());*//*

    }*/
    private void suggestionSearchOnLine(String address){
        int indexProvince=address.indexOf("省");
        int indexCity=address.indexOf("市");
        if((address.length() >= 0)) {

            if((indexCity >= 0)){
            /*搜索全国*/
            /*city = keyword.substring(indexProvince + 1, indexCity);*/
                city = address.substring(0,indexCity);
            /*keyword = address.substring(indexCity+1,address.length()-1);*/
                // 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                /*mSuggestionSearch.requestSuggestion(suggestionSearchOption
                        .keyword(address).city(city));*/
                // 分页编号
                /*mpoiSearch.searchInCity((new PoiCitySearchOption())
                        .city(city)
                        .keyword(address)
                        .pageNum(30));*/
                /*mpoiSearch.searchNearby(new PoiNearbySearchOption().keyword(address).pageNum(30));*/
                mpoiSearch.searchInCity(new PoiCitySearchOption().pageNum(0).pageCapacity(30).city(city).keyword(address).isReturnAddr(true));
                return;
            }
            /*默认搜索温州市*/
            /*keyword = address.substring(indexProvince+1,address.length() - 1);*/
            /*mSuggestionSearch.requestSuggestion(suggestionSearchOption
                    .keyword(address).city("温州市"));*/
            // 分页编号
            /*mpoiSearch.searchNearby(new PoiNearbySearchOption().keyword(address).pageNum(30));*/
            mpoiSearch.searchInCity(new PoiCitySearchOption().pageNum(0).pageCapacity(30).city("温州市").isReturnAddr(true).keyword(address));
                    /*searchInCity((new PoiCitySearchOption())
                    .city("温州市")
                    .keyword(address)
                    .pageNum(30));*/
        }


                /*.city("北京"));*/
    }

    protected void onDestroy(){
        /*mSuggestionSearch.destroy();*/
        mpoiSearch.destroy();;
        super.onDestroy();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    /*监控edittext的输入情况*/
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /*Toast.makeText(this,"et:"+etBaiduAddressSearchSuggestTopbarAddress.getText().toString(),Toast.LENGTH_SHORT).show();*/

        suggestionSearchOnLine(etBaiduAddressSearchSuggestTopbarAddress.getText().toString());
    }
    /*监控edittext的输入情况*/
    @Override
    public void afterTextChanged(Editable s) {

    }


    /*返回*/
    @OnClick(R.id.rly_baiduaddress_search_suggest_topbar_back)
    public void rlyBaiduAddressSearchSuggestTopBarBackOnclick(){
        this.finish();
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if ((result == null) || (result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)) {
            return;
        }
        /*Toast.makeText(this,"size:"+suggestionResult.getAllSuggestions().size(),Toast.LENGTH_SHORT).show();*/
        /*Toast.makeText(this,"size:success"+result.getAllPoi().size(),Toast.LENGTH_SHORT).show();*/
        baiduAddressSearchSuggestRecycleViewAdapter.setDataList(result.getAllPoi());
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
           /* Toast.makeText(this, "成功，查看详情页面", Toast.LENGTH_SHORT)
                    .show();*/

        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
