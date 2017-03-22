package tianhao.agoto.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Bean.ShopAddressListBean;
import tianhao.agoto.Bean.UserAddressListBean;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/3/22.
 */

public class AddressManageAddUserRVAdapter extends RecyclerView.Adapter<AddressManageAddUserRVAdapter.ItemContentViewHolder> {

    private Context context;
    private List<UserAddressListBean> userAddressListBeanList;
    private LayoutInflater inflater;
    public AddressManageAddUserRVAdapter(Context context1, List<UserAddressListBean> userAddressListBeanList1){
        this.context = context1;
        userAddressListBeanList = userAddressListBeanList1;
        inflater = LayoutInflater.from(context1);
    }
    public void setDataList(Collection<UserAddressListBean> userAddressList1){
        if(userAddressListBeanList == null){
            return;
        }
        int count = userAddressListBeanList.size();
        userAddressListBeanList.clear();
        userAddressListBeanList.addAll(userAddressList1);
        notifyDataSetChanged();
    }
    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new ItemContentViewHolder(inflater.inflate(R.layout.activity_addressmanage_content_user_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        if(holder != null){
            holder.pos = position;
        }
    }

    @Override
    public int getItemCount() {
        return userAddressListBeanList.size();
    }

    public class ItemContentViewHolder extends RecyclerView.ViewHolder{
        public int pos = 0;
        @BindView(R.id.lly_addressmanage_content_user_rv_item_total)
        LinearLayout llyAddressManageContentRVItemTotal;
        @BindView(R.id.fly_addressmanage_content_user_rv_item)
        FrameLayout flyAddressManageContentRVItem;
        @BindView(R.id.rly_addressmanage_content_user_rv_item_delete)
        RelativeLayout rlyAddressManageContentUserRVItemDelete;
        @OnClick(R.id.rly_addressmanage_content_user_rv_item_delete)
        public void rlyAddressManageContentUserRVItemDeleteOnclick(){
            /*Toast.makeText(context,"i am:"+pos,Toast.LENGTH_LONG).show();*/
        }
        /*  @OnClick(R.id.fly_addressmanage_content_rv_item)
          public void flyAddressManageContentRVItemOnclick(){
              Toast.makeText(context,""+shopAddressListBeanList.get())
          }*/
        public ItemContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
