package tianhao.agoto.Common.Widget.SwipeCardView.SwipePostCard;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Adapter.SwipFlingRecyclerViewAdapter;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.CustomDialog;
import tianhao.agoto.R;

/**
 * Created by Flying SnowBean on 2016/1/26.
 */
public class PostcardAdapter extends SwipePostcard.Adapter {
    private final String TAG = PostcardAdapter.class.getSimpleName();
    private Context mContext;
    private List<Bean> mData;

    public PostcardAdapter(Context context, List<Bean> data) {
        mContext = context;
        mData = data;
    }
    public void addAll(List<Bean> beanList){

        mData.addAll(beanList);

    }
    @Override
    public View createView(ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_shoppinglist_content_piper_card_item_lly, parent, false);
    }

    @Override
    public void bindView(View view, final int position) {
     /*   Bean bean = mData.get(position);*/
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public class ViewHolder {

        @BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
        RecyclerView ervShoppingListContentPiperCardItemGoods ;
        @BindView(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        LinearLayout llyShoppingListContentPiperCardItemParentRV;
        List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
        SwipFlingRecyclerViewAdapter recyclerViewAdapter;
        CustomDialog customDialog;
        View view;
        @OnClick(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        public void testOnclick(){
            System.out.println("this is onclick");
            /*recyclerViewAdapter.addData(new GoodsBean());*/
            customDialog = new CustomDialog(view.getContext()).Build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setCallBackListener(new CustomDialog.DialogCallBackListener() {
                @Override
                public void callBack(String msgName, String msgNum) {

                }
            }).build();


        }
        ViewHolder(View view) {
            ButterKnife.bind(this,view);
            initRecyclerView();
            this.view = view;
     /*       mIvTaeyeon = (ImageView) view.findViewById(R.id.iv_taeyeon);
            mTvDescribe = (TextView) view.findViewById(R.id.tv_describe);
            mButton = (Button) view.findViewById(R.id.btn_event);*/
        }

        private void initRecyclerView(){
            goodsBeanList.add(new GoodsBean());
            recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(mContext,goodsBeanList);
            ervShoppingListContentPiperCardItemGoods.setHasFixedSize(true);
            /*位置不一样会导致刷新不了的bug*/
            ervShoppingListContentPiperCardItemGoods.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
            ervShoppingListContentPiperCardItemGoods.addItemDecoration(new DividerItemDecoration(mContext,
                    DividerItemDecoration.VERTICAL));
        }
    }
}
