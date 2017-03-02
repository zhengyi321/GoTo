package tianhao.agoto.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;
import tianhao.agoto.Activity.ShoppingListActivity;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Bean.SwipFlingBean;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.AlertView;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.DialogUtil;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.OnDismissListener;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.OnItemClickListener;
import tianhao.agoto.Common.Widget.LinearLayout.OverScrollView;
import tianhao.agoto.Common.Widget.LinearLayout.ScrollViewLinearLayout;
import tianhao.agoto.Common.Widget.MyRecyclerView;
import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.EasyRecyclerView;
import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.adapter.RecyclerArrayAdapter;
import tianhao.agoto.Common.Widget.ScrollView.EmbedListViewScrollView;
import tianhao.agoto.Common.Widget.ScrollView.InnerScrollView;
import tianhao.agoto.Common.Widget.ScrollView.MultiScroll;
import tianhao.agoto.Common.Widget.ScrollView.MyNestedScrollView;
import tianhao.agoto.Common.Widget.ScrollView.MyScrollView;
import tianhao.agoto.Common.Widget.ScrollView.ScrollViewExtend;
import tianhao.agoto.Common.Widget.ScrollView.ScrollViewForNesting;
import tianhao.agoto.Common.Widget.ScrollView.SpringScrollView;
import tianhao.agoto.Common.Widget.ScrollView.VerticalScrollView;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipeFlingAdapterView;
import tianhao.agoto.R;
import tianhao.agoto.Utils.SystemUtils;

/**
 * Created by admin on 2017/3/1.
 */

public class SwipFlingAdapter  extends BaseAdapter implements  OnDismissListener, OnItemClickListener {

    private ArrayList<SwipFlingBean> objs;
    private Context context;
    private LayoutInflater inflater;

    public SwipFlingAdapter(Context context) {
        objs = new ArrayList<SwipFlingBean>();
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public void addAll(Collection<SwipFlingBean> collection) {
        if (isEmpty()) {
            objs.addAll(collection);
            notifyDataSetChanged();
        } else {
            objs.addAll(collection);
        }
    }

    public void clear() {
        objs.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return objs.isEmpty();
    }

    public void remove(int index) {
        if (index > -1 && index < objs.size()) {
            objs.remove(index);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return objs.size();
    }

    @Override
    public SwipFlingBean getItem(int position) {
        if(objs==null ||objs.size()==0) return null;
        return objs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // TODO: getView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_lly,parent, false);
            holder = new ViewHolder(convertView);
            //holder.portraitView.getLayoutParams().width = cardWidth;
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.jobView.setText(talent.jobName);
        return convertView;
    }

    @Override
    public void onDismiss(Object o) {

    }


    @Override
    public void onItemClick(Object o, int position) {

    }

/*    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msv_shoppinglist_content_piper_card_item_goodstype:
                Toast.makeText(context,"i'm addGoodsOnclick onclick",Toast.LENGTH_SHORT).show();
                break;
        }

    }*/

    public class ViewHolder {
        SwipFlingRecyclerViewAdapter recyclerViewAdapter;
        @BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
        EasyRecyclerView ervShoppingListContentPiperCardItemGoods;
        @BindView(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        LinearLayout llyShoppingListContentPiperCardItemParentRV;
        @OnClick(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        public void llyShoppingListContentPiperCardItemParentRVOnclick(){
            Toast.makeText(context,"dis:x:",Toast.LENGTH_SHORT).show();

        }

        /*添加商品*/
        public ViewHolder(View v){
            ButterKnife.bind(this,v);
            initRecyclerView(v);
        }

        private void initRecyclerView(View v){
            List<GoodsBean> goodsBeanList = new ArrayList<>();
            GoodsBean bean = new GoodsBean();
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);

            recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context,goodsBeanList);
           ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            ervShoppingListContentPiperCardItemGoods.setLayoutManager(linearLayoutManager);
            /*ervShoppingListContentPiperCardItemGoods.setOnTouchListener(new MyTouchListener(recyclerViewAdapter));*/

        }
       /* public class MyTouchListener implements View.OnTouchListener{
            SwipFlingRecyclerViewAdapter adapter;
            public MyTouchListener(SwipFlingRecyclerViewAdapter adapter){
                this.adapter = adapter;
            }
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x1 =0;
                int y1 =0;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = (int) event.getRawX();
                        y1 = (int)event.getRawY();
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setName("11221");//测试
                        goodsBean.setNum("1112");

                        adapter.addData(goodsBean);
                        break;
                    case MotionEvent.ACTION_UP:

                        int x2 = (int) event.getRawX();
                        int y2 = (int) event.getRawY();
                            *//*Toast.makeText(context,"dis:x:"+Math.abs(x1 - x2)+"dis:y:"+Math.abs(y2 - y1),Toast.LENGTH_SHORT).show();*//*
                        if((Math.abs(x2 - x1) < 490)&&(Math.abs(y2-y1)< 580)){
                            *//*Toast.makeText(context,"dis:x:"+Math.abs(x1 - x2)+"dis:y:"+Math.abs(y2 - y1),Toast.LENGTH_SHORT).show();
                            AlterViewDialogForGoods altv = new AlterViewDialogForGoods(adapter);*//*

                        }
                        break;
                }

                return false;
            }*/
        }

        public class AlterViewDialogForGoods{
            AlertDialog alertDialog;
            SwipFlingRecyclerViewAdapter adapter;
            @BindView(R.id.et_dialog_add_goods_goodsname)
            EditText etDialogAddGoodsGoodName;
            @BindView(R.id.et_dialog_add_goods_goodsnum)
            EditText etDialogAddGoodsGoodNum;
            @BindView(R.id.rly_dialog_add_goods_cancel)
            RelativeLayout rlyDialogAddGoodsCancel;
            @BindView(R.id.rly_dialog_add_goods_query)
            RelativeLayout rlyDialogAddGoodsQuery;
           public String name,num;
            @OnClick(R.id.rly_dialog_add_goods_query)
            public void rlyDialogAddGoodsQueryOnclick(){
                name = etDialogAddGoodsGoodName.getText().toString();
                num = etDialogAddGoodsGoodNum.getText().toString();
                GoodsBean bean = new GoodsBean();
                bean.setName(name);
                bean.setNum(num);
                adapter.addData(bean);
                /*alertDialog.dismiss();*/
            }
            @OnClick(R.id.rly_dialog_add_goods_cancel)
            public void rlyDialogAddGoodsCancelOnclick(){
                alertDialog.dismiss();
            }

            public AlterViewDialogForGoods(SwipFlingRecyclerViewAdapter adapter){
                this.adapter = adapter;
                DialogUtil dialogUtil = new DialogUtil(context);
                View v =dialogUtil.createDialogAddGoods(R.layout.dialog_add_goods_lly);
                alertDialog = dialogUtil.getAlertDialog();
                ButterKnife.bind(this,alertDialog);
            }
         /*   GoodsBean goodsBean = new GoodsBean();
            goodsBean.setName("111");
            goodsBean.setNum("111");
            List<GoodsBean> goodsBeanList = new ArrayList<>();
            goodsBeanList.add(goodsBean);
            goodsBeanList.add(goodsBean);
            goodsBeanList.add(goodsBean);
            recyclerViewAdapter.setDataList(goodsBeanList);*/

        }


       /* public class AddGoods{
            AlertDialog alertDialog;
            SwipFlingRecyclerViewAdapter adapter;
            @BindView(R.id.et_dialog_add_goods_goodsname)
            EditText etDialogAddGoodsGoodName;
            @BindView(R.id.et_dialog_add_goods_goodsnum)
            EditText etDialogAddGoodsGoodNum;
            @BindView(R.id.rly_dialog_add_goods_cancel)
            RelativeLayout rlyDialogAddGoodsCancel;
            @BindView(R.id.rly_dialog_add_goods_query)
            RelativeLayout rlyDialogAddGoodsQuery;

            @OnClick(R.id.rly_dialog_add_goods_query)
            public void rlyDialogAddGoodsQueryOnclick(){
                GoodsBean bean = new GoodsBean();
                bean.setName(etDialogAddGoodsGoodName.getText().toString());
                bean.setNum(etDialogAddGoodsGoodNum.getText().toString());
                List<GoodsBean> goodsBeanList = recyclerViewAdapter.getAllData();
                goodsBeanList.add(bean);
                adapter.addData(bean);
                alertDialog.dismiss();
            }
            @OnClick(R.id.rly_dialog_add_goods_cancel)
            public void rlyDialogAddGoodsCancelOnclick(){
                alertDialog.dismiss();
            }
            public AddGoods(View v, AlertDialog alertDialog,SwipFlingRecyclerViewAdapter adapter){
                this.alertDialog = alertDialog;
                this.adapter = adapter;
                ButterKnife.bind(this,v);
            }
        }
    }*/

}