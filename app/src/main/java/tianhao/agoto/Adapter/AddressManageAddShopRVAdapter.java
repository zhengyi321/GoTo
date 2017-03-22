package tianhao.agoto.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Bean.ShopAddressListBean;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/3/22.
 */

public class AddressManageAddShopRVAdapter extends RecyclerView.Adapter<AddressManageAddShopRVAdapter.ItemContentViewHolder>  {

    private List<ShopAddressListBean> shopAddressListBeanList;
    private Context context;
    private LayoutInflater inflater;
    public OnItemClick1Listener onItemClick1Listener ;
    public AddressManageAddShopRVAdapter(Context context1,List<ShopAddressListBean> shopAddressListBeanList1){
        context = context1;
        shopAddressListBeanList = shopAddressListBeanList1;
        inflater = LayoutInflater.from(context);
    }
    public void setDataList(Collection<ShopAddressListBean> shopAddressList){
        if(shopAddressListBeanList == null){
            return;
        }
        int count = shopAddressListBeanList.size();
        shopAddressListBeanList.clear();
        shopAddressListBeanList.addAll(shopAddressList);
        notifyDataSetChanged();
    }

    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemContentViewHolder(inflater.inflate(R.layout.activity_addressmanage_content_shop_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        if(holder != null){
            /*holder.flyAddressManageContentRVItem.setOnClickListener(new MyOnclickListener(context,position));*/
            holder.tvAddressManageContentRVItemName.setText(shopAddressListBeanList.get(position).getClientaddr1Name());
            holder.tvAddressManageContentRVItemAddr.setText(shopAddressListBeanList.get(position).getClientaddr1Addr());
        }
    }
    /*public class MyOnclickListener implements View.OnClickListener{
        int pos;
        Context context;
        public MyOnclickListener(Context context1,int pos1){
            context = context1;
            pos = pos1;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context,"pos:"+pos,Toast.LENGTH_LONG).show();
        }
    }*/
    @Override
    public int getItemCount() {
        return shopAddressListBeanList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ItemContentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.lly_addressmanage_content_shop_rv_item_total)
        LinearLayout llyAddressManageContentRVItemTotal;
        @BindView(R.id.tv_addressmanage_content_shop_rv_item_addr)
        TextView tvAddressManageContentRVItemAddr;
        @BindView(R.id.tv_addressmanage_content_shop_rv_item_name)
        TextView tvAddressManageContentRVItemName;

        @BindView(R.id.fly_addressmanage_content_shop_rv_item)
        FrameLayout flyAddressManageContentRVItem;
        @BindView(R.id.rly_addressmanage_content_shop_rv_item_delete)
        RelativeLayout rlyAddressManageContentRVItemDelete;
      /*  @OnClick(R.id.fly_addressmanage_content_rv_item)
        public void flyAddressManageContentRVItemOnclick(){
            *//*Toast.makeText(context,""+shopAddressListBeanList.get())*//*
        }*/
        public ItemContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClick1Listener{
        public void getItem(ShopAddressListBean shopAddressListBean);
    };

    public void SetOnItemClickListener(OnItemClick1Listener onItemClick1Listener){
        this.onItemClick1Listener = onItemClick1Listener;
    }
}
