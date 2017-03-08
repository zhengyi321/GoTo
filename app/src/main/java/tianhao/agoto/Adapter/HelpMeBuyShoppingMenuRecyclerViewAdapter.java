package tianhao.agoto.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
            Log.i(" goodsBeanList1begin",goodsBeanList1.size()+"");
            /*notifyItemRangeChanged(0,goodsBeanList.size());*/
            if(goodsBeanList1.size() == 0){
                Log.i(" goodsBeansize()==bg",goodsBeanList1.size()+"");
                int count = goodsBeanList.size();
                this.goodsBeanList.clear();
                Log.i(" goodsBeansize()==0b0",goodsBeanList1.size()+"");
                notifyItemRangeRemoved(0,count);
                Log.i(" goodsBeansize()==0b1",goodsBeanList1.size()+"");
                notifyItemRangeChanged(0,count);
                Log.i(" goodsBeansize()==0b2",goodsBeanList1.size()+"");

            }else {
                Log.i(" goodsBeansize()!=0bb0",goodsBeanList1.size()+"");
                int count = goodsBeanList.size();
                this.goodsBeanList.clear();
                notifyItemRangeRemoved(0,count);
                notifyItemRangeChanged(0,count);
                Log.i(" goodsBeansize()!=0bb1",goodsBeanList1.size()+"");
                this.goodsBeanList.addAll(goodsBeanList1);

                Log.i(" goodsBeansize()!=0bb2",goodsBeanList1.size()+"");
                notifyItemRangeInserted(0, goodsBeanList1.size());
                Log.i(" goodsBeansize()!=0bb3",goodsBeanList1.size()+"");
                notifyItemRangeChanged(0,goodsBeanList1.size());
                Log.i(" goodsBeansize()!=0bb4",goodsBeanList1.size()+"");
               /* notifyItemChanged(goodsBeanList1.size());*/
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
        Log.i(" onBindViewHolder0:",position+"");
        String content = "";
        if(goodsBeanList.size()>0) {
            Log.i(" onBindViewHolder1:",position+"");
            if(goodsBeanList.get(position)!= null){
                Log.i(" onBindViewHolder2:",position+"");
                if(!goodsBeanList.get(position).getName().isEmpty()) {
                    Log.i(" onBindViewHolder3:",position+"");
                    content += goodsBeanList.get(position).getName().toString();
                   /* holder.tvHelpMeBuyContentShoppingMenuRVItem.setText("" + goodsBeanList.get(position).getName() + " X " + goodsBeanList.get(position).getNum());*/
                }
                Log.i(" onBindViewHolder4:",position+"");
                if(!goodsBeanList.get(position).getNum().isEmpty()){
                    Log.i(" onBindViewHolder5:",position+"");
                    content += ("x"+goodsBeanList.get(position).getNum().toString());
                }
                holder.tvHelpMeBuyContentShoppingMenuRVItem.setText(content);
                Log.i(" onBindViewHolder6:",position+"");
            }
            Log.i(" onBindViewHolder7:",position+"");
        }
        Log.i(" onBindViewHolder8:",position+"");
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
