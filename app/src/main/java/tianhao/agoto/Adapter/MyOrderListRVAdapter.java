package tianhao.agoto.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tianhao.agoto.Bean.MyOrderBean;
import tianhao.agoto.R;
import tianhao.agoto.Utils.TimeUtil;

/**
 * Created by admin on 2017/3/17.
 */

public class MyOrderListRVAdapter extends RecyclerView.Adapter<MyOrderListRVAdapter.MyHoldView> {

    private List<MyOrderBean> myOrderBeanList;
    private Context context;

    public MyOrderListRVAdapter(Context context1, List<MyOrderBean> orderBeanList){
        context = context1;
        myOrderBeanList = orderBeanList;
    }

    public void setMyOrderBeanList(Collection<MyOrderBean> myOrderBeanList1){
        int tempCount = myOrderBeanList.size();
        myOrderBeanList.clear();
        notifyItemRangeChanged(0,tempCount);
        notifyItemRangeRemoved(0,tempCount);
        myOrderBeanList.addAll(myOrderBeanList1);
        notifyItemRangeInserted(0,myOrderBeanList1.size());
        notifyItemRangeChanged(0,myOrderBeanList1.size());
    }


    @Override
    public MyHoldView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHoldView(LayoutInflater.from(context).inflate(R.layout.activity_myorder_content_tab_vp_item_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHoldView holder, int position) {
        initHoldData(holder,position);
    }
    private void initHoldData(MyHoldView holdView,int pos){
        holdView.tvMyOrderContentTabVPItemRVItemBeginAddr.setText(myOrderBeanList.get(pos).getClientaddrAddr1());
        holdView.tvMyOrderContentTabVPItemRVItemendAddr.setText(myOrderBeanList.get(pos).getClientaddrAddr());
        holdView.tvMyOrderContentTabVPItemRVItemOrderNum.setText(myOrderBeanList.get(pos).getOrderNo());

        holdView.tvMyOrderContentTabVPItemRVItemOrderTime.setText(myOrderBeanList.get(pos).getOrderOrdertime());
        holdView.tvMyOrderContentTabVPItemRVItemOrderMoney.setText("￥"+myOrderBeanList.get(pos).getOrderOrderprice());
    }


    @Override
    public int getItemCount() {
        return myOrderBeanList.size();
    }

    public class MyHoldView extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_item_ordernum)
        TextView tvMyOrderContentTabVPItemRVItemOrderNum;
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_item_beginaddr)
        TextView tvMyOrderContentTabVPItemRVItemBeginAddr;
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_item_endaddr)
        TextView tvMyOrderContentTabVPItemRVItemendAddr;
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_item_ordertime)
        TextView tvMyOrderContentTabVPItemRVItemOrderTime;
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_item_ordermoney)
        TextView tvMyOrderContentTabVPItemRVItemOrderMoney;
        public MyHoldView(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
