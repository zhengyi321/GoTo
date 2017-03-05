package tianhao.agoto.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tianhao.agoto.R;

/**
 * Created by zhyan on 2017/3/4.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemContentViewHolder>{

    private List<String> testList;
    private Activity activity;
    private LayoutInflater inflater;
    public RecyclerViewAdapter(Activity activity, List<String> stringList){
        testList = stringList;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        System.out.println("this is RecyclerViewAdapter");
    }
    public void setDataList(List<String> dataList){
        testList.clear();
        this.testList = dataList;

        notifyDataSetChanged();
        System.out.println("this is setdatalist");
    }

    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("this is onCreateViewHolder");
        return new ItemContentViewHolder(inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        System.out.println("this is onBindViewHolder");
    }



    @Override
    public int getItemCount() {
        System.out.println("this is getItemCount");
        return testList.size();
    }


    public class ItemContentViewHolder extends RecyclerView.ViewHolder{

        public ItemContentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
