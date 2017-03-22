package tianhao.agoto.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Bean.SwipFlingBean;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.DialogUtil;
import tianhao.agoto.Common.Widget.RecyclerView.LoadMoreRecyclerView.adapter.BookAdapter;
import tianhao.agoto.Common.Widget.RecyclerView.LoadMoreRecyclerView.db.BookDBHelper;
import tianhao.agoto.Common.Widget.RecyclerView.LoadMoreRecyclerView.provider.BookProvider;
import tianhao.agoto.R;

/**
 *
 *
 * http://blog.csdn.net/qwm8777411/article/details/45420451
 * Created by admin on 2017/3/1.
 *
 * http://www.tuicool.com/articles/iYJZFbR
 */

public class SwipFlingAdapter  extends BaseAdapter {

    private List<SwipFlingBean> objs;
    private Activity activity;
    private LayoutInflater inflater;
    private AlertDialog alertDialog;

    public SwipFlingAdapter(Activity activity,List<SwipFlingBean> objs) {
        this.objs = objs;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        System.out.println("SwipFlingAdapter");

    }

    public void addAll(Collection<SwipFlingBean> collection) {

        System.out.println("addAll");
        if (isEmpty()) {
            objs.addAll(collection);
            notifyDataSetChanged();
        } else {
            objs.addAll(collection);
        }

    /*    objs.addAll(collection);
        notifyDataSetChanged();*/
    }

    public void clear() {
        System.out.println("clear");
        objs.clear();
        notifyDataSetChanged();
    }

    public void setObjs(Collection<SwipFlingBean> collection) {
        System.out.println("setObjs");
        objs.clear();
        objs = (ArrayList<SwipFlingBean>) collection;
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        System.out.println("isEmpty");
        return objs.isEmpty();
    }

    public void remove(int index) {
        System.out.println("remove");
        if (index > -1 && index < objs.size()) {
            objs.remove(index);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        System.out.println("getCount");
        return objs.size()>0?objs.size():1;
        /*return objs.size();*/
    }

    @Override
    public SwipFlingBean getItem(int position) {
        System.out.println("getItem");
        if(objs==null ||objs.size()==0) return null;
        return objs.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("getItemId");
        return position;
    }

    // TODO: getView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("this is getView:pos"+ position+"convert:"+convertView+"parent:"+parent);
        ViewHolder holder;
        if (convertView == null) {
            System.out.println("this is getView:111111111,pos:"+position);
            /*parent.getChildCount();*/
            /*convertView.setFocusable(true);*/
            System.out.println("this is getView: parent.getChildCount():"+ parent.getChildCount()+position);
            convertView =LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shoppinglist_content_piper_card_item_lly, parent, false);
            holder = new ViewHolder( convertView,activity);
            convertView.setTag(holder);

        } else {
           /* if(parent.getChildAt(0) != null){
                parent.removeViews(0,1);
            }*/
            /*convertView.setFocusable(true);*/
            System.out.println("this is getView: parent.getChildCount2():"+ parent.getChildCount()+position);
            System.out.println("this is getView:222222222222,pos:"+position);
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }



    /*添加商品dialog*/
    public class AlterViewDialogForGoods{
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
            /*GoodsBean bean = new GoodsBean();
            bean.setName(name);
            bean.setNum(num);
            goodsBeanList.add(bean);*/
         /*   recyclerViewAdapter.setDataList(goodsBeanList);*/
            alertDialog.dismiss();
        }
        @OnClick(R.id.rly_dialog_add_goods_cancel)
        public void rlyDialogAddGoodsCancelOnclick(){
            alertDialog.dismiss();
        }
        public AlterViewDialogForGoods(){
            DialogUtil dialogUtil = new DialogUtil(activity);
            View v =dialogUtil.createDialogAddGoods(R.layout.dialog_add_goods_lly);
            alertDialog = dialogUtil.getAlertDialog();
            ButterKnife.bind(this,alertDialog);

        }

    }
     /*添加商品dialog*/

    public class ViewHolder {

        @BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
        RecyclerView recyclerView ;
       /* @BindView(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        LinearLayout testly;*/
        private BookAdapter adapter;
        /*List<String> goodsBeanList= new ArrayList<String>();*/
        List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
        SwipFlingRecyclerViewAdapter recyclerViewAdapter /*= new SwipFlingRecyclerViewAdapter(activity,goodsBeanList)*/;
        Context context;
/*        @OnClick(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        public void testOnclick(){
            System.out.println("this is onclick");

            recyclerViewAdapter.addData(new GoodsBean());

          *//*  notifyDataSetChanged();*//*
           *//* recyclerViewAdapter.setDataList(goodsBeanList);*//*
            *//*goodsBeanList.add(new GoodsBean());
            goodsBeanList.add(new GoodsBean());
            goodsBeanList.add(new GoodsBean());
            goodsBeanList.add(new GoodsBean());
            recyclerViewAdapter.setDataList(goodsBeanList);*//**//*
            recyclerViewAdapter.notifyItemChanged(goodsBeanList.size()-1,goodsBeanList.size());*//*
          *//*  showDialog();*//*
        }*/
     /*   @OnTouch(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        public boolean toucheTest(View v ,MotionEvent evt ){
            System.out.println("this is toucheTest");
            return  false;
        }*/
        private void showDialog() {

            final AlertDialog dialog = new AlertDialog.Builder(context).create();
            /*View v = activity.getLayoutInflater().inflate(R.layout.dialog, null);*/
            View v = LayoutInflater.from(context).inflate(R.layout.dialog, null);
            dialog.setView(v);
            dialog.setCancelable(false);
            dialog.show();

            final EditText name = (EditText) v.findViewById(R.id.edit_name);
            final EditText price = (EditText) v.findViewById(R.id.edit_price);

            Button cancel = (Button) v.findViewById(R.id.btn_cancel);
            Button save = (Button) v.findViewById(R.id.btn_save);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String mName = name.getText().toString();
                    String mPrice = price.getText().toString();

                    ContentValues values = new ContentValues();
                    values.put(BookDBHelper.NAME, mName);
                    values.put(BookDBHelper.PRICE, Integer.valueOf(mPrice));

                    context.getContentResolver().insert(BookProvider.URI_BOOK_ALL, values);
                    dialog.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }


        private void initLoader() {

           activity.getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {

                    CursorLoader loader = new CursorLoader(context, BookProvider.URI_BOOK_ALL, null, null, null, null);

                    return loader;
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    adapter.swapCursor(data);
                    System.out.println("onLoadFinished");
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    adapter.swapCursor(null);
                }
            });

        }

        private void initViews() {


            if (adapter == null) {

                Cursor c = context.getContentResolver().query(BookProvider.URI_BOOK_ALL, null, null, null, null);
                adapter = new BookAdapter(context, c, 1);
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            /*recyclerView.setHasFixedSize(true);*/
            recyclerView.setAdapter(adapter);

        }



        public ViewHolder( View convertView,Context context1){
            /*goodsBeanList = new ArrayList<GoodsBean>();*/
            this.context = context1;

            ButterKnife.bind(this,convertView);
 /*           goodsBeanList.add(new GoodsBean());
            recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context1,goodsBeanList);
            recyclerView.setHasFixedSize(true);
            *//*位置不一样会导致刷新不了的bug*//*
            recyclerView.setLayoutManager(new LinearLayoutManager(context1,LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(context1,
                    DividerItemDecoration.VERTICAL));*/

         /*   goodsBeanList= new ArrayList<String>();*/
         /*   recyclerViewAdapter  = new SwipFlingRecyclerViewAdapter(activity,goodsBeanList);
*/
           /* initViews();
            initLoader();
*/
           /* testrv.setLayoutManager(linearLayoutManager);*/
/*
            recyclerViewAdapter.addPos(new GoodsBean(),0);*/
        }

    }

/*

    public class ViewHolder {
        TimeUtil timeUtil = new TimeUtil();
        String timeBegin = "";
        CustomDialog mDialog;
     */
/*   private  List<GoodsBean> goodsBeanList = new ArrayList<>();*//*

        */
/*SwipFlingRecyclerViewAdapter recyclerViewAdapter;*//*

        */
/*List<GoodsBean> goodsBeanList = new ArrayList<>();*//*

      */
/*  SwipFlingRecyclerViewAdapter recyclerViewAdapter ;*//*

        @BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
        EasyRecyclerView ervShoppingListContentPiperCardItemGoods;
        @BindView(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        LinearLayout llyShoppingListContentPiperCardItemParentRV;

        */
/*  判断是点击事件 不是滑动事件的解决方法http://www.cnblogs.com/wader2011/archive/2011/12/02/2271981.html*//*

     */
/*   @OnClick(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        public void llyShoppingListContentPiperCardItemParentRVOnclick(){
            *//*
*/
/*Toast.makeText(context,"dis:OnClick:",Toast.LENGTH_SHORT).show();*//*
*/
/*
            clickOnce();
        }*//*


    */
/*
    @OnTouch(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        public boolean llyShoppingListContentPiperCardItemParentRVOnclick(View v,MotionEvent event){

Toast.makeText(context,"dis:OnTouch:",Toast.LENGTH_SHORT).show();
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    timeBegin = timeUtil.getCurrentDateTime();
                    break;
                case MotionEvent.ACTION_UP:

                    break;
            }
            return false;
        }


*/
/* 判断是点击事件 不是滑动事件的解决方法http://www.cnblogs.com/wader2011/archive/2011/12/02/2271981.html
         添加商品*//*

        public ViewHolder(View v){
            ButterKnife.bind(this,v);
        }

        */
/*初始化列表*//*

      */
/*  public void initRecycleView(){
            GoodsBean bean = new GoodsBean();
            goodsBeanList.add(bean);
          *//*
*/
/*  goodsBeanList.add(bean);
            goodsBeanList.add(bean);*//*
*/
/*
            recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context,goodsBeanList);
            ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            ervShoppingListContentPiperCardItemGoods.setLayoutManager(linearLayoutManager);

        }*//*

        */
/*初始化列表*//*

        public void clickOnce(SwipFlingRecyclerViewAdapter adapter){
            //这个设置为全局
            String currentTime = timeUtil.getCurrentDateTime();;
            long timeGap = timeUtil.getSubTwoTimeBySeconds(currentTime,timeBegin);// 与现在时间相差秒数
            Toast.makeText(context,"dis:timeGap:"+timeGap,Toast.LENGTH_SHORT).show();
            */
/*触摸时间小于3秒则判断为点击事件*//*

            if(Math.abs(timeGap) <1){

                GoodsBean bean = new GoodsBean();

                */
/*recyclerViewAdapter.addPos(bean,0);*//*

                */
/*recyclerViewAdapter.addData(bean);*//*

                */
/*recyclerViewAdapter.addData(bean);*//*

                */
/*new AlterViewDialogForGoods();*//*

            }

        }

    }
*/




 /*添加商品dialog*/




/*    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msv_shoppinglist_content_piper_card_item_goodstype:
                Toast.makeText(context,"i'm addGoodsOnclick onclick",Toast.LENGTH_SHORT).show();
                break;
        }

    }*/





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
        }*/