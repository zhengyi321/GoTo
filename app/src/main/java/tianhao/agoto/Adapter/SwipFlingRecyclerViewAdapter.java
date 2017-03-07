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
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.AndroidSwipeLayout;
import tianhao.agoto.R;
import tianhao.agoto.Utils.TimeUtil;

/**
 * http://www.tuicool.com/articles/qMnAfen
 * Created by zhyan on 2017/3/1.
 *
 * http://www.jianshu.com/p/c090ec158fc5
 */

public class SwipFlingRecyclerViewAdapter extends RecyclerView.Adapter<SwipFlingRecyclerViewAdapter.ItemContentViewHolder> {
    private List<GoodsBean> goodsBeanList /*= new ArrayList<GoodsBean>()*/;
    private Context context;
    private LayoutInflater inflater;

    public SwipFlingRecyclerViewAdapter(Context mContext,List<GoodsBean> goodsBeanList){
        this.goodsBeanList = new ArrayList<GoodsBean>();
        this.context = mContext;
        this.goodsBeanList.addAll(goodsBeanList);
        inflater = LayoutInflater.from(context);
        System.out.println(" SwipFlingRecyclerViewAdapter");

    }
    public void setDataList(Collection<GoodsBean> dataList){
        this.goodsBeanList.clear();
        this.goodsBeanList.addAll(dataList);
        this.notifyItemRangeChanged(0,goodsBeanList.size());
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
        this.notifyItemRangeChanged(0,1,bean);

        System.out.println("addData ");
    }
    public List<GoodsBean> getAllData(){
        System.out.println(" getAllData");
        return this.goodsBeanList;
    }


    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder parent:"+parent +" viewtype:  "+viewType);
        /*if(viewType==-1){
            View v=inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly,parent,false);
            return new ItemContentViewHolder(v);
        }
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly,parent,false);
        ItemContentViewHolder itemContentViewHolder=new ItemContentViewHolder(v);
        return itemContentViewHolder;*/
        return new ItemContentViewHolder(inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        System.out.println(" onBindViewHolder size:"+goodsBeanList.size());
        if(goodsBeanList.size() == 0){
            return;
        }
        if(goodsBeanList.get(position) != null){
            GoodsBean goodsBean = goodsBeanList.get(position);
            if(goodsBeanList.get(position).getName() != null) {
                holder.tvShoppingListContentPiperCardItemRVItemGoodsName.setText(goodsBeanList.get(position).getName());
            }
            if(goodsBeanList.get(position).getNum() != null){
                holder.tvShoppingListContentPiperCardItemRVItemGoodsNum.setText(goodsBeanList.get(position).getNum());
            }
            int i = position % 2;
            if(i == 0){
                holder.llyShoppingListContentPiperCardItemRVItemTotal.setBackgroundColor(context.getResources().getColor(R.color.gray));
            }else{
                holder.llyShoppingListContentPiperCardItemRVItemTotal.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            holder.rlyShoppingListContentPiperCardItemRVItemDelete.setOnClickListener(new MyOnclickListener(position));
        }



     /*   holder.tvShoppingListContentPiperCardItemRVItemGoodsNum.setText(goodsBean.getNum());*/

    }
    public class MyOnclickListener implements View.OnClickListener {
        int pos;
        public MyOnclickListener(int pos){
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rly_shoppinglist_content_piper_card_item_rv_item_delete:
                    if(goodsBeanList.size() > 0){
                        goodsBeanList.remove(pos);
                        /*notifyItemRemoved(pos);*/
                        notifyItemRangeRemoved(pos,1);
                        notifyItemRangeChanged(pos,1);
                    }

                    break;
            }
        }
    }
    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position,List<Object> payloads){
        System.out.println(" onBindViewHolder22");
        onBindViewHolder(holder,position);
       /* if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }

        if(payloads == null){
            onBindViewHolder(holder,position);
        }else{
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
    @Override
    public int getItemViewType(int position) {
        if(goodsBeanList.size()<=0){
            return -1;
        }
        return super.getItemViewType(position);
    }

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



    public class ItemContentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_shoppinglist_content_piper_card_item_rv_item_goodsname)
        TextView tvShoppingListContentPiperCardItemRVItemGoodsName;
        @BindView(R.id.tv_shoppinglist_content_piper_card_item_rv_item_goodsnum)
        TextView tvShoppingListContentPiperCardItemRVItemGoodsNum;
        @BindView(R.id.tv_shoppinglist_content_piper_card_item_rv_item_goodsprice)
        TextView tvShoppingListContentPiperCardItemRVItemGoodsPrice;
        @BindView(R.id.lly_shoppinglist_content_piper_card_item_rv_item_total)
        LinearLayout llyShoppingListContentPiperCardItemRVItemTotal;/*
        @BindView(R.id.as_shoppinglist_content_piper_card_item_rv_item_cehua)
        AndroidSwipeLayout asShoppingListContentPiperCardItemRVItem;*/
        @BindView(R.id.rly_shoppinglist_content_piper_card_item_rv_item_delete)
        RelativeLayout rlyShoppingListContentPiperCardItemRVItemDelete;
        public ItemContentViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
           /* System.out.print("out"+AndroidSwipeLayout.ShowMode.PullOut+",right:"+AndroidSwipeLayout.DragEdge.Right);*/
        }
    }



}
