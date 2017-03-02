package tianhao.agoto.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.R;

/**
 * http://www.tuicool.com/articles/qMnAfen
 * Created by zhyan on 2017/3/1.
 */

public class SwipFlingRecyclerViewAdapter extends RecyclerView.Adapter<SwipFlingRecyclerViewAdapter.ItemContentViewHolder> {
    private List<GoodsBean> goodsBeanList;
    private Context context;
    private LayoutInflater inflater;
    public SwipFlingRecyclerViewAdapter(Context context,List<GoodsBean> goodsBeanList){
        this.context = context;
        this.goodsBeanList = goodsBeanList;
        inflater = LayoutInflater.from(context);
    }
    public void setDataList(List<GoodsBean> dataList){
        this.goodsBeanList = dataList;
        this.notifyDataSetChanged();
    }

    public void addData(GoodsBean bean){
        this.goodsBeanList.add(bean);
        this.notifyDataSetChanged();
    }
    public List<GoodsBean> getAllData(){
        return goodsBeanList;
    }


    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemContentViewHolder(inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {

        if(goodsBeanList.get(position) != null){
            GoodsBean goodsBean = goodsBeanList.get(position);
            if(goodsBean.getName() != null){
                holder.tvShoppingListContentPiperCardItemRVItemGoodsName.setText(goodsBean.getName());
            }
            if(goodsBean.getNum() != null){
                holder.tvShoppingListContentPiperCardItemRVItemGoodsNum.setText(goodsBean.getNum());
            }
            /*if(goodsBean.getPrice() != null){
                holder.tvShoppingListContentPiperCardItemRVItemGoodsPrice.setText(goodsBean.getPrice());
            }*/
            /*this.notifyItemChanged(position);*/
        }
    }

    @Override
    public int getItemCount() {
        return goodsBeanList.size();
    }



    public void setHeaderView(GoodsBean bean) {
       /* goodsBeanList.add(bean);
        int size = goodsBeanList.size();
        if(goodsBeanList != null){
            for (int i = 0; i < size; i++) {
                GoodsBean bean1 = goodsBeanList.get(i);
                goodsBeanList.remove(i);
                goodsBeanList.add(bean1);

            }
        }*/
        List<GoodsBean> goodsBeanList1 = new ArrayList<>();
        goodsBeanList1.add(bean);
        for(int i = 0;i<goodsBeanList.size(); i++){
            goodsBeanList1.add(goodsBeanList.get(i));
        }
        goodsBeanList = goodsBeanList1;
        this.notifyDataSetChanged();
     /*   LayoutInflater.from(context).inflate(headerView,parent);
        if(parent != null) {
            if (parent.getChildCount() > 0) {
                Toast.makeText(context, "i'm setHeadView", Toast.LENGTH_SHORT).show();
           *//* LayoutInflater.from(context).inflate(headerView, parent);*//*
                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.addView(parent.getChildAt(i));
                    parent.removeView(parent.getChildAt(i));
                }
            }
            Toast.makeText(context, "i'm setHeadView", Toast.LENGTH_SHORT).show();
            this.notifyDataSetChanged();
        }*/
       /* Toast.makeText(getContext(),"i'm setHeadView",Toast.LENGTH_SHORT).show();
        LayoutInflater.from(getContext()).inflate(headView, mEmptyView);*/

      /*
        notifyItemInserted(0);*/
    }

    public class ItemContentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_shoppinglist_content_piper_card_item_rv_item_goodsname)
        TextView tvShoppingListContentPiperCardItemRVItemGoodsName;
        @BindView(R.id.tv_shoppinglist_content_piper_card_item_rv_item_goodsnum)
        TextView tvShoppingListContentPiperCardItemRVItemGoodsNum;
        @BindView(R.id.tv_shoppinglist_content_piper_card_item_rv_item_goodsprice)
        TextView tvShoppingListContentPiperCardItemRVItemGoodsPrice;
        public ItemContentViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
