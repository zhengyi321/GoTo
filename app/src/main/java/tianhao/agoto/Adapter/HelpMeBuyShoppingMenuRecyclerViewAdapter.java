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
    public List<GoodsBean> goodsBeanList ;

    public HelpMeBuyShoppingMenuRecyclerViewAdapter(Context mcontext, List<GoodsBean> goodsBeanList1){
        context = mcontext;
        /*this.goodsBeanList = goodsBeanList;*/
        this.goodsBeanList= goodsBeanList1;
    }
    public List<GoodsBean> getGoodsBeanList(){
        return this.goodsBeanList;
    }

    public void setGoodsBeanList(List<GoodsBean> goodsBeanList1){

        if((goodsBeanList1 != null)) {

            /*notifyItemRangeChanged(0,goodsBeanList.size());*/
            if(goodsBeanList1.size() == 0){
                int count = goodsBeanList.size();
                this.goodsBeanList.clear();
                notifyItemRangeRemoved(0,count);
                notifyItemRangeRemoved(0,count);


            }else {
                this.goodsBeanList.clear();
                this.goodsBeanList.addAll(goodsBeanList1);
                notifyItemRangeInserted(goodsBeanList1.size(), goodsBeanList1.size());
                notifyItemChanged(goodsBeanList1.size());
            }
            /*notifyItemRangeChanged(0,goodsBeanList1.size());*/
            /*notifyItemRangeChanged(0,goodsBeanList.size());*/

        }
    }

    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemContentViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_helpmebuy_content_shoppingmenu_rv_item, parent, false));

    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        String content = "";
        if(goodsBeanList.size()>0) {
            if(goodsBeanList.get(position)!= null){
                if(!goodsBeanList.get(position).getName().isEmpty()) {
                    content += goodsBeanList.get(position).getName().toString();
                   /* holder.tvHelpMeBuyContentShoppingMenuRVItem.setText("" + goodsBeanList.get(position).getName() + " X " + goodsBeanList.get(position).getNum());*/
                }
                if(!goodsBeanList.get(position).getNum().isEmpty()){
                    content += ("x"+goodsBeanList.get(position).getNum().toString());
                }
                holder.tvHelpMeBuyContentShoppingMenuRVItem.setText(content);
            }

        }
    }

    @Override
    public int getItemCount() {
        return goodsBeanList.size();
        /*return goodsBeanList.size()>0?goodsBeanList.size():1;*/
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
