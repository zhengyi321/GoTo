package tianhao.agoto.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.R;

/**
 * http://www.tuicool.com/articles/qMnAfen
 * Created by zhyan on 2017/3/1.
 */

public class SwipFlingRecyclerViewAdapter extends RecyclerView.Adapter<SwipFlingRecyclerViewAdapter.ItemContentViewHolder>{
    private List<GoodsBean> goodsBeanList;
    private Context context;
    private LayoutInflater inflater;
    private View mHeaderView;
    private OnItemClickListener mListener;
    private int pos;
    public SwipFlingRecyclerViewAdapter(Context context,List<GoodsBean> goodsBeanList){
        this.context = context;
        this.goodsBeanList = goodsBeanList;
        inflater = LayoutInflater.from(context);
    }
    public void setDataList(List<GoodsBean> dataList){
        this.goodsBeanList = dataList;
        this.notifyDataSetChanged();
    }
    interface OnItemClickListener {
        void onItemClick(int position, String data);
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
            if(goodsBean.getPrice() != null){
                holder.tvShoppingListContentPiperCardItemRVItemGoodsPrice.setText(goodsBean.getPrice());
            }

        }
    }

    @Override
    public int getItemCount() {
        return goodsBeanList.size();
    }


    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getHeaderView() {
        return mHeaderView;
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
