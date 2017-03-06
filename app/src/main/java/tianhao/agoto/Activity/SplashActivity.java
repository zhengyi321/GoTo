package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Adapter.SwipFlingRecyclerViewAdapter;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.R;

/**http://www.open-open.com/lib/view/open1474962270182.html
 * Created by zhyan on 2017/2/10.
 * 过渡页
 *
 */

public class SplashActivity extends Activity{

    //Handler处理事物
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    startMainActivity();
                    finish();
                default:
                    ;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist_content_piper_card_item_lly);
   /*     test();*/
        init();
    }
    @BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
    RecyclerView recyclerView ;
    @BindView(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
    LinearLayout testly;
    List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
    SwipFlingRecyclerViewAdapter recyclerViewAdapter;
    @OnClick(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
    public void testOnclick() {
        System.out.println("this is onclick");

        recyclerViewAdapter.addData(new GoodsBean());
    }
    private void test(){



        ButterKnife.bind(this);
        goodsBeanList.add(new GoodsBean());
        recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(this,goodsBeanList);
        recyclerView.setHasFixedSize(true);
            /*位置不一样会导致刷新不了的bug*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        /*RecyclerView recyclerView = (RecyclerView) findViewById(R.id.erv_shoppinglist_content_piper_card_item_goods);
        LinearLayout button = (LinearLayout) findViewById(R.id.lly_shoppinglist_content_piper_card_item_parent_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
         List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>() ;
        goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        goodsBeanList.add(new GoodsBean());
        final SwipFlingRecyclerViewAdapter swipFlingRecyclerViewAdapter = new SwipFlingRecyclerViewAdapter(this,goodsBeanList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(swipFlingRecyclerViewAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipFlingRecyclerViewAdapter.addData(new GoodsBean());
            }
        });*/
    }

    private void init(){
        //开启多线程
       /* new Thread(new MyThread()).start();*/
        //进入主页面 关闭过渡页
        closeSplash();


    }


//主线程进入主页
    private void startMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    //其他线程负责关闭过渡页
/*    private class MyThread implements Runnable{
        public void run(){
            try {

            }catch (Exception e){

            }
        }
    }*/

    //关闭过渡页
    private void closeSplash(){
        Message message = handler.obtainMessage();
        message.what = 0;
        handler.sendMessageDelayed(message,2500);
    }
    protected void onPause(){
        super.onPause();
        finish();
    }
    protected void onDestroy(){
        super.onDestroy();
        if(null != handler) {
            handler = null;
            /*DebugLog.d(TAG, "release Handler success");*/
        }
    }
}
