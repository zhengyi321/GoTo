package tianhao.agoto.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tianhao.agoto.Activity.MainActivity;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/2/23.
 */

public class BaseRecycleViewAdapter  extends RecyclerView.Adapter<BaseRecycleViewAdapter.ItemContentViewHolder>{

    private List<String> testList;
    private Context context;
    private LayoutInflater inflater;
    public BaseRecycleViewAdapter(Context context,List<String> stringList){
        testList = stringList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<String> dataList){
        this.testList = dataList;
        this.notifyDataSetChanged();
    }


    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemContentViewHolder(inflater.inflate(R.layout.fragment_main_content_sv, parent, false));

    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }


    public class ItemContentViewHolder extends RecyclerView.ViewHolder{


        public ItemContentViewHolder(View itemView) {
            super(itemView);
        }
    }
}