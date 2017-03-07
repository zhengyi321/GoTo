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
import tianhao.agoto.Common.Widget.ScrollView.SpringScrollView;
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
