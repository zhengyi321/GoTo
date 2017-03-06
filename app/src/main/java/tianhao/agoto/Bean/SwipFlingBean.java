package tianhao.agoto.Bean;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import tianhao.agoto.Adapter.PersonAdapter;
import tianhao.agoto.Adapter.SwipFlingRecyclerViewAdapter;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.CustomDialog;
import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.EasyRecyclerView;
import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.adapter.RecyclerArrayAdapter;
import tianhao.agoto.Common.Widget.RecyclerView.LoadMoreRecyclerView.adapter.BookAdapter;
import tianhao.agoto.Common.Widget.RecyclerView.LoadMoreRecyclerView.db.BookDBHelper;
import tianhao.agoto.Common.Widget.RecyclerView.LoadMoreRecyclerView.provider.BookProvider;
import tianhao.agoto.R;
import tianhao.agoto.Utils.TimeUtil;

/**
 * Created by admin on 2017/3/1.
 */

public class SwipFlingBean {

    @BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
    RecyclerView recyclerView ;
   /* @BindView(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
    LinearLayout testly;*/
    private BookAdapter adapter;
        /*List<String> goodsBeanList= new ArrayList<String>();*/

/*    @OnClick(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
    public void testOnclick(){
        System.out.println("this is onclick");

        showDialog();
    }*/

    private void showDialog() {

        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        View v = activity.getLayoutInflater().inflate(R.layout.dialog, null);
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

                activity.getContentResolver().insert(BookProvider.URI_BOOK_ALL, values);
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

                CursorLoader loader = new CursorLoader(activity, BookProvider.URI_BOOK_ALL, null, null, null, null);

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

            Cursor c = activity.getContentResolver().query(BookProvider.URI_BOOK_ALL, null, null, null, null);
            adapter = new BookAdapter(activity, c, 1);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
  /*  public interface DialogCallBackListener{//通过该接口回调Dialog需要传递的值
        public void callBack(String msg);//具体方法
    }
    public DialogCallBackListener mDialogCallBackListener;
    public void setCallBackListener(DialogCallBackListener mDialogCallBackListener){//设置回调
        this.mDialogCallBackListener = mDialogCallBackListener;
    }*/
    TimeUtil timeUtil = new TimeUtil();
    String timeBegin = "";
    CustomDialog mDialog;
    Activity activity;
    List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
    /*SwipFlingRecyclerViewAdapter recyclerViewAdapter ;*/



    /*@BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
    public RecyclerView ervShoppingListContentPiperCardItemGoods;*/
        /*List<GoodsBean> goodsBeanList = new ArrayList<>();*/
      /*  SwipFlingRecyclerViewAdapter recyclerViewAdapter ;*/


    /*  判断是点击事件 不是滑动事件的解决方法http://www.cnblogs.com/wader2011/archive/2011/12/02/2271981.html*/
   /* @OnClick(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
    public void llyShoppingListContentPiperCardItemParentRVOnclick(){*/
     /*
        LinearLayoutManager linearLayoutManager;
        ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ervShoppingListContentPiperCardItemGoods.setLayoutManager(linearLayoutManager);*/
       /* recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context,goodsBeanList);

        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/
        /*goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());*/
       /* ArrayList<String> stringList  = new ArrayList<String>();
        stringList.add("");
        stringList.add("");
        stringList.add("");
        stringList.add("");
        recyclerViewAdapter.addDatas(stringList);*//*
        ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
        ervShoppingListContentPiperCardItemGoods.setLayoutManager(linearLayoutManager);*/
        /*initRecycleView();*/
    /*    recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context,goodsBeanList);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewAdapter.addData(new GoodsBean());
        recyclerViewAdapter.addData(new GoodsBean());
        recyclerViewAdapter.addData(new GoodsBean());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
        ervShoppingListContentPiperCardItemGoods.setLayoutManager(linearLayoutManager);*/
        /*Toast.makeText(context,"dis:OnClick:",Toast.LENGTH_SHORT).show();*/
       /* goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        recyclerViewAdapter.setDataList(goodsBeanList);*//*
        ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
        ervShoppingListContentPiperCardItemGoods.setLayoutManager(linearLayoutManager);*/
        /*clickOnce();*/
/*    }*/
/*
    @OnTouch(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
    public boolean llyShoppingListContentPiperCardItemParentRVOnclick(View v,MotionEvent event){
        *//*Toast.makeText(context,"dis:OnTouch:",Toast.LENGTH_SHORT).show();*//*
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                timeBegin = timeUtil.getCurrentDateTime();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return false;
    }*/

    /* 判断是点击事件 不是滑动事件的解决方法http://www.cnblogs.com/wader2011/archive/2011/12/02/2271981.html
     添加商品*/


    public void init(View v,Activity activity){
        this.activity = activity;
        ButterKnife.bind(this,v);
        initViews();
        initLoader();

        /*goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context,goodsBeanList);*/
       /* initRecycleView();*/
      /*  recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context,goodsBeanList);
        linearLayoutManager = new LinearLayoutManager(context);*/
        /*initRecycleView();*/
       /* this.context = context;
        GoodsBean bean = new GoodsBean();

        recyclerViewAdapter.addData(bean);
        recyclerViewAdapter.addData(bean);
        recyclerViewAdapter.addData(bean);
        recyclerViewAdapter.addData(bean);*/

    }

  /*  初始化列表*/
    public void initRecycleView(){

/*        ervShoppingListContentPiperCardItemGoods.setLayoutManager(new LinearLayoutManager(context));
        ervShoppingListContentPiperCardItemGoods.setAdapter(adapter = new PersonAdapter(context));*/
       /* GoodsBean bean = new GoodsBean();*/
      /*  recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context,goodsBeanList);*/
       /* recyclerViewAdapter.addData(bean);*/

      /*  ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ervShoppingListContentPiperCardItemGoods.setLayoutManager(linearLayoutManager);
        stringList  = new ArrayList<String>();
        stringList.add("");
        stringList.add("");
        stringList.add("");
        stringList.add("");
        recyclerViewAdapter.addDatas(stringList);*/
  /*      recyclerViewAdapter.addData(new GoodsBean());
        recyclerViewAdapter.addData(new GoodsBean());
        recyclerViewAdapter.addData(new GoodsBean());*/
    }
     /*   初始化列表*/
    public void clickOnce(){
        //这个设置为全局
        String currentTime = timeUtil.getCurrentDateTime();;
        long timeGap = timeUtil.getSubTwoTimeBySeconds(currentTime,timeBegin);// 与现在时间相差秒数
        /*Toast.makeText(context,"dis:timeGap:"+timeGap,Toast.LENGTH_SHORT).show();*/
            /*触摸时间小于3秒则判断为点击事件*/
        if(Math.abs(timeGap) <1){
            Toast.makeText(activity,"dis:timeGap:"+timeGap,Toast.LENGTH_SHORT).show();
            GoodsBean bean = new GoodsBean();
            List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);
            goodsBeanList.add(bean);
            /*recyclerViewAdapter.setDataList(goodsBeanList);*/
                /*recyclerViewAdapter.addPos(bean,0);*/
                /*recyclerViewAdapter.addData(bean);*/
            /*
            ((SwipFlingRecyclerViewAdapter)this.ervShoppingListContentPiperCardItemGoods.getAdapter()).addPos(bean,0);
            ((SwipFlingRecyclerViewAdapter)this.ervShoppingListContentPiperCardItemGoods.getAdapter()).addPos(bean,0);*//*
            ((SwipFlingRecyclerViewAdapter)ervShoppingListContentPiperCardItemGoods.getAdapter()).addData(bean);
            ((SwipFlingRecyclerViewAdapter)ervShoppingListContentPiperCardItemGoods.getAdapter()).addData(bean);*/
            /*recyclerViewAdapter.addData(bean);
            recyclerViewAdapter.addData(bean);
            recyclerViewAdapter.addData(bean);
            recyclerViewAdapter.addData(bean);*/
                /*new AlterViewDialogForGoods();*/
        }
    }
   /* public class MyOnclickListener implements View.OnClickListener{

        public MyOnclickListener(){

        }
        @Override
        public void onClick(View v) {
            Toast.makeText(context,"dis:OnTouch:",Toast.LENGTH_SHORT).show();
            GoodsBean bean = new GoodsBean();
            recyclerViewAdapter.addData(bean);
            recyclerViewAdapter.addData(bean);
            recyclerViewAdapter.addData(bean);
        }
    }*/
}
