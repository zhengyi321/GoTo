package tianhao.agoto.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import tianhao.agoto.Activity.ShoppingListActivity;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Bean.SwipFlingBean;
import tianhao.agoto.Common.Widget.MyRecyclerView;
import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.EasyRecyclerView;
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

public class SwipFlingAdapter  extends BaseAdapter  {

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

/*    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msv_shoppinglist_content_piper_card_item_goodstype:
                Toast.makeText(context,"i'm addGoodsOnclick onclick",Toast.LENGTH_SHORT).show();
                break;
        }

    }*/

    public class ViewHolder {

        @BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
        EasyRecyclerView ervShoppingListContentPiperCardItemGoods;
        @BindView(R.id.lly_shoppinglist_content_piper_card_item_goodstype)
        LinearLayout llyShoppingListContentPiperCardItemGoodsType;
     /*   @BindView(R.id.fly_shoppinglist_content_piper_card_item_addgoods)
        FrameLayout rlyShoppingListContentPiperCardItemAddGoods;*/
        /*添加商品*/
        @OnClick(R.id.lly_shoppinglist_content_piper_card_item_goodstype)
        public void llyShoppingListContentPiperCardItemPaperthreeOnclick(){
            Toast.makeText(context,"i'm addGoodsOnclick onclick",Toast.LENGTH_SHORT).show();
        }
        /*添加商品*/
        public ViewHolder(View v){
            ButterKnife.bind(this,v);
            initRecyclerView(v);
        }

        private void initRecyclerView(View v){
            List<GoodsBean> goodsBeanList = new ArrayList<>();
            GoodsBean bean = new GoodsBean();
            bean.setName("zz");
            bean.setNum("2");
            bean.setPrice("11");
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);
            SwipFlingRecyclerViewAdapter recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context,goodsBeanList);
            /*HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(recyclerViewAdapter);*/
            View view = LayoutInflater.from(context).inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_headview_lly,ervShoppingListContentPiperCardItemGoods,true);
           /* headerAndFooterRecyclerViewAdapter.addHeaderView(view);*/
            recyclerViewAdapter.setHeaderView(view);
            recyclerViewAdapter.setDataList(goodsBeanList);
           ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            ervShoppingListContentPiperCardItemGoods.setLayoutManager(linearLayoutManager);

        }
    }


}