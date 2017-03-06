package tianhao.agoto.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.DialogUtil;
import tianhao.agoto.R;
import tianhao.agoto.Utils.TimeUtil;

/**
 * http://www.tuicool.com/articles/qMnAfen
 * Created by zhyan on 2017/3/1.
 *
 * http://www.jianshu.com/p/c090ec158fc5
 */

public class SwipFlingRecyclerViewAdapter extends RecyclerView.Adapter<SwipFlingRecyclerViewAdapter.ItemContentViewHolder> {
    private List<GoodsBean> goodsBeanList ;
    private Context context;
    private LayoutInflater inflater;

    public SwipFlingRecyclerViewAdapter(Context context,List<GoodsBean> goodsBeanList){
        this.context = context;
        this.goodsBeanList=goodsBeanList ;
        /*inflater = LayoutInflater.from(context);*/
      /*  inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
        System.out.println(" SwipFlingRecyclerViewAdapter");

    }
    public void setDataList(Collection<GoodsBean> dataList){
        this.goodsBeanList.clear();
        this.goodsBeanList.addAll(dataList);
        /*notifyItemInserted(goodsBeanList.size());*/
        /*notifyDataSetChanged();*/
        notifyItemRangeChanged(0,goodsBeanList.size(),goodsBeanList);
        /*notifyItemRangeChanged(goodsBeanList.size()-1,goodsBeanList.size());*/
        System.out.println(" setDataList");
    }
    public void addPos(GoodsBean bean, int position) {
        goodsBeanList.add(position, bean);
        this.notifyItemInserted(position);
        System.out.println(" addPos");
    }
    public void addData(GoodsBean bean){
        this.goodsBeanList.add(bean);
        /*notifyItemInserted(0);*/
  /*      notifyDataSetChanged();*/

        notifyItemRangeChanged(0,1,bean);
        /*notifyDataSetChanged();*/
        /*notifyDataSetChanged();*/
       /* notifyItemRangeChanged(0,1);*/
        /*notifyItemChanged(0);*/
        /*notifyItemInserted(goodsBeanList.size() -1);*/
       /* notifyDataSetChanged();*/
      /*  this.notifyItemRangeChanged(0,1,bean);*/
      /*  notifyItemRangeChanged(goodsBeanList.size() -1,1);*/
      /*  notifyDataSetChanged();*/
       /* notifyItemInserted(1);*/
        System.out.println("addData ");
    }
    public List<GoodsBean> getAllData(){
        System.out.println(" getAllData");
        return goodsBeanList;
    }


    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder parent:"+parent +" viewtype:  "+viewType);
        if(viewType==-1){
            View v=inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly,parent,false);
            return new ItemContentViewHolder(v);
        }

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly,parent,false);
        v.setFocusable(true);
        parent.setFocusable(true);
        ItemContentViewHolder itemContentViewHolder=new ItemContentViewHolder(v);
        return itemContentViewHolder;
        /*return new ItemContentViewHolder(inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly, parent, false));*/
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        System.out.println(" onBindViewHolder");
       /* if(goodsBeanList.get(position) != null){
            GoodsBean goodsBean = goodsBeanList.get(position);
            if(goodsBean.getName() != null){
                holder.tvShoppingListContentPiperCardItemRVItemGoodsName.setText(goodsBean.getName());
            }
            if(goodsBean.getNum() != null){
                holder.tvShoppingListContentPiperCardItemRVItemGoodsNum.setText(goodsBean.getNum());
            }

            *//*if(goodsBean.getPrice() != null){
                holder.tvShoppingListContentPiperCardItemRVItemGoodsPrice.setText(goodsBean.getPrice());
            }*//*
            *//*this.notifyItemChanged(position);*//*
        }*/
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position,List<Object> payloads){
        System.out.println(" onBindViewHolder22");
      /*  if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }

        if(payloads == null){
            onBindViewHolder(holder,position);
        }*//*else{
            if(position == 0){
                return;
            }
            int adjPosition = position - getItemCount();
            int adapterCount;
            if(SwipFlingRecyclerViewAdapter.this != null){
                adapterCount = this.getItemCount();
                if(adjPosition < getItemCount()){
                    this.onBindViewHolder(holder,adjPosition,payloads);
                }
            }
        }*/
    }
/*    @Override
    public int getItemViewType(int position) {
        if(goodsBeanList.size()<=0){
            return -1;
        }
        return super.getItemViewType(position);
    }*/

    @Override
    public int getItemCount() {
        if(goodsBeanList != null){
            System.out.println("getItemCount "+goodsBeanList.size());
            return goodsBeanList.size()>0?goodsBeanList.size():1;
        }else{
            return 1;
        }
        /*return goodsBeanList.size()>0?goodsBeanList.size():1;*/
        /*return goodsBeanList.size();*/
    }



    public void setHeaderItem(GoodsBean bean) {
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
