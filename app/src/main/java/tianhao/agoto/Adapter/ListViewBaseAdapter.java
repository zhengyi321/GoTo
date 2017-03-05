package tianhao.agoto.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.List;

import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Bean.SwipFlingBean;
import tianhao.agoto.R;

/**
 * Created by zhyan on 2017/3/4.
 */

public class ListViewBaseAdapter extends BaseAdapter{
    List<GoodsBean> goodsBeanList;
    Activity activity ;
    LayoutInflater inflater;
    public ListViewBaseAdapter(Activity activity, List<GoodsBean> goodsBeanList){
        this.activity =activity;
        this.goodsBeanList = goodsBeanList;
        inflater= LayoutInflater.from(activity);
        System.out.println("this is adapter");
    }
    public void setAllData(Collection<GoodsBean> collection){

    /*    if (isEmpty()) {
            goodsBeanList.addAll(collection);

        } else {
            goodsBeanList.addAll(collection);
        }*/
  /*      notifyDataSetChanged();*/

        this.goodsBeanList.clear();
        this.goodsBeanList.addAll(collection);
        this.notifyDataSetInvalidated();
        this.notifyDataSetChanged();/*
        this.notifyDataSetInvalidated();*/
    }
    public void addData(GoodsBean goodsBean){
        /*goodsBeanList.clear();*/
        goodsBeanList.add(goodsBean);
        System.out.println("this is addData");
        this.notifyDataSetChanged();
    }
    public void refresh(GoodsBean goodsBean) {
        goodsBeanList.add(goodsBean);
        this.notifyDataSetChanged();
        System.out.println("this is refresh");
    }
    @Override
    public int getCount() {
        System.out.println("this is getCount"+goodsBeanList.size());
        return goodsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        System.out.println("this is getItem");
        return goodsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("this is getItemId");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        System.out.println("this is getView1");
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly,parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        System.out.println("this is getView2");

        return convertView;
    }
    public class ViewHolder{

        public ViewHolder(View view){

        }
    }
}
