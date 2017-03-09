package tianhao.agoto.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/2/28.
 */

public class BaiduAddressSearchSuggestRecycleViewAdapter extends RecyclerView.Adapter<BaiduAddressSearchSuggestRecycleViewAdapter.ItemContentViewHolder> {
    private Activity activity;
    private List<PoiInfo> poiInfoList;
    /*private List<PoiAddrInfo> poiInfoList;*/
    private LayoutInflater inflater;
    private int RESULT_OK = 15;
    public BaiduAddressSearchSuggestRecycleViewAdapter(Activity activity, List<PoiInfo> poiInfoList){
        this.activity = activity;
        this.poiInfoList = poiInfoList;
        inflater = LayoutInflater.from(activity);
    }
    /*实时更新数据*/
    public void setDataList(List<PoiInfo> dataList){
        if((dataList != null)&&(dataList.size() > 0)) {
            int count = poiInfoList.size();
            this.poiInfoList.clear();
            notifyItemRangeRemoved(0,count);
            notifyItemRangeChanged(0,count);
            this.poiInfoList.addAll(dataList);
            notifyItemRangeInserted(0,dataList.size());
            notifyItemRangeChanged(0,dataList.size());

        }
    }
    /*实时更新数据*/

    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemContentViewHolder(inflater.inflate(R.layout.activity_baiduaddress_search_suggest_rv_item_lly, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        if((poiInfoList.get(position) != null)&& (poiInfoList.get(position).location != null)) {
            /*holder.tvBaiduAddressSearchSuggestRVItemContentName.setText(suggestionInfoList.get(position).city + suggestionInfoList.get(position).district + suggestionInfoList.get(position).key);*/
            holder.tvBaiduAddressSearchSuggestRVItemContentName.setText(poiInfoList.get(position).city+poiInfoList.get(position).name+poiInfoList.get(position).address );
/*            holder.tvBaiduAddressSearchSuggestRVItemContentName.setText(poiInfoList.get(position).address +" "+ poiInfoList.get(position).city +" "+poiInfoList.get(position).name +" "+ poiInfoList.get(position).phoneNum+" "+poiInfoList.get(position).postCode+" "+poiInfoList.get(position).uid );*/

           /* holder.lat = suggestionInfoList.get(position).pt.latitude;
            holder.lon = suggestionInfoList.get(position).pt.longitude;*/
             holder.lat = poiInfoList.get(position).location.latitude;
            holder.lon = poiInfoList.get(position).location.longitude;
     
        }
    }

    @Override
    public int getItemCount() {
        if(poiInfoList != null) {
            return poiInfoList.size();
        }else{
            return 0;
        }
    }


    public class ItemContentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_baiduaddress_search_suggest_rv_item_contentname)
        TextView tvBaiduAddressSearchSuggestRVItemContentName;
        @BindView(R.id.rly_baiduaddress_search_suggest_rv_item_total)
        RelativeLayout rlyBaiduAddressSearchSuggestRVItemTotal;
        public double lat =0,lon = 0 ;
        @OnClick(R.id.rly_baiduaddress_search_suggest_rv_item_total)
        public void rlyBaiduAddressSearchSuggestRVItemTotalOnclick(){
            Bundle bundle = new Bundle();
            bundle.putString("address",tvBaiduAddressSearchSuggestRVItemContentName.getText().toString());
            bundle.putString("lat","" + lat);
            bundle.putString("lon","" + lon);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }
        public ItemContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
