package tianhao.agoto.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/3/7.
 */

public class HelpMeBuyShoppingMenuRecyclerViewAdapter extends RecyclerView.Adapter<HelpMeBuyShoppingMenuRecyclerViewAdapter.ItemContentViewHolder>{

    public Context context;
    public List<GoodsBean> goodsBeanList/* = new ArrayList<GoodsBean>()*/;

    public HelpMeBuyShoppingMenuRecyclerViewAdapter(Context mcontext, List<GoodsBean> goodsBeanList){
        context = mcontext;
        this.goodsBeanList = new ArrayList<GoodsBean>();
        this.goodsBeanList.addAll(goodsBeanList);
    }
    public List<GoodsBean> getGoodsBeanList(){
        return this.goodsBeanList;
    }

    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemContentViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_helpmebuy_content_shoppingmenu_rv_item, parent, false));

    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        if(goodsBeanList.size()>0) {
            if(goodsBeanList.get(position)!= null){
                if(!goodsBeanList.get(position).getName().isEmpty()) {
                    holder.tvHelpMeBuyContentShoppingMenuRVItem.setText("" + goodsBeanList.get(position).getName() + " X " + goodsBeanList.get(position).getNum());
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return goodsBeanList.size()>0?goodsBeanList.size():1;
    }



    public class ItemContentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_helpmebuy_content_shoppingmenu_rv_item)
        TextView tvHelpMeBuyContentShoppingMenuRVItem;

        public ItemContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
