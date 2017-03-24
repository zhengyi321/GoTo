package tianhao.agoto.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import butterknife.OnTouch;
import rx.Observer;
import tianhao.agoto.Bean.BaseBean;
import tianhao.agoto.Bean.ShopAddressListBean;
import tianhao.agoto.Common.Widget.DB.XCCacheManager.xccache.XCCacheManager;
import tianhao.agoto.NetWorks.AddressManageNetWorks;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/3/22.
 */

public class AddressManageAddShopRVAdapter extends RecyclerView.Adapter<AddressManageAddShopRVAdapter.ItemContentViewHolder>  {

    XCCacheManager xcCacheManager ;
    private List<ShopAddressListBean> shopAddressListBeanList;
    private Activity activity;
    private LayoutInflater inflater;
    public OnItemClick1Listener onItemClick1Listener ;
    public AddressManageAddShopRVAdapter(Activity activity1, List<ShopAddressListBean> shopAddressListBeanList1){
        xcCacheManager = XCCacheManager.getInstance(activity);
        activity = activity1;
        shopAddressListBeanList = shopAddressListBeanList1;
        inflater = LayoutInflater.from(activity1);
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
            holder.pos = position;
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
        private final int RESULT_OK = 10;
        private void backResultForHelpActivity(){

            Bundle bundle = new Bundle();
            bundle.putString("nameCall",shopAddressListBeanList.get(pos).getClientaddr1Name());
            bundle.putString("address",shopAddressListBeanList.get(pos).getClientaddr1Addr());
            bundle.putString("clientaddr1Things1",shopAddressListBeanList.get(pos).getClientaddr1Things1());
            bundle.putString("blat", "" + shopAddressListBeanList.get(pos).getClientaddr1Lat());
            bundle.putString("blon", "" + shopAddressListBeanList.get(pos).getClientaddr1Long());
            Intent intent = new Intent();
            intent.putExtras(bundle);
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }

        float xBegin = 0;
        float yBegin = 0;
        float xEnd = 0;
        float yEnd = 0;
        @OnTouch(R.id.sly_addressmanage_content_shop_rv_item)
        public boolean llyAddressManageContentRVItemTotalOnTouch(View view, MotionEvent event){

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    xBegin = event.getRawX();
                    yBegin = event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    xEnd = event.getRawX();
                    yEnd = event.getRawY();
                    int absXBegin = (int) Math.abs(xBegin);
                    int absYBegin = (int) Math.abs(yBegin);
                    int absXEnd = (int) Math.abs(xEnd);
                    int absYEnd = (int) Math.abs(yEnd);
                    int disX = (absXEnd - absXBegin);
                    int disY = (absYEnd - absYBegin);

                    if((disX == 0)&&(disY == 0)){
                        String type = xcCacheManager.readCache("addressManageType");
                        Toast.makeText(activity,"this is type:"+type,Toast.LENGTH_SHORT).show();
                        if((type != null)&&(!type.equals("main"))) {
                            if(type.equals("shop")) {
                                backResultForHelpActivity();
                            }
                        }
                        return true;
                    }

                    break;

            }
            return false;
        }
        public int pos = 0;
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
        @OnClick(R.id.rly_addressmanage_content_shop_rv_item_delete)
        public void rlyAddressManageContentRVItemDeleteOnclick(){

            final String usid = xcCacheManager.readCache("usid");
            String clientaddr1Things1 = shopAddressListBeanList.get(pos).getClientaddr1Things1();
            if((usid != null)&&(!usid.isEmpty())&&(clientaddr1Things1 != null)) {
               /* Toast.makeText(context,"usid:"+usid+" clientaddrthing:"+clientaddr1Things1,Toast.LENGTH_LONG).show();
                System.out.println("usid:"+usid);
                System.out.println("\n clientaddrthing:"+clientaddr1Things1);*/
                final AddressManageNetWorks addressManageNetWorks = new AddressManageNetWorks();
                addressManageNetWorks.deleteShopAddress(usid, clientaddr1Things1, new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        Toast.makeText(activity,baseBean.getResult(),Toast.LENGTH_LONG).show();
                        addressManageNetWorks.getShopAddrList(usid, new Observer<List<ShopAddressListBean>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<ShopAddressListBean> shopAddressListBeen) {
                                setDataList(shopAddressListBeen);
                            }
                        });
                    }
                });
            }
        }
/*        @OnClick(R.id.fly_addressmanage_content_rv_item)
        public void flyAddressManageContentRVItemOnclick(){
            Toast.makeText(context,""+shopAddressListBeanList.get())
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
