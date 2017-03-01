package tianhao.agoto.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.Adapter.SwipFlingAdapter;
import tianhao.agoto.Bean.SwipFlingBean;
import tianhao.agoto.Common.Widget.ScrollView.MyScrollView;
import tianhao.agoto.R;
import tianhao.agoto.Utils.BlurUtil;
import tianhao.agoto.Utils.ImageUtils;
import tianhao.agoto.Utils.SystemUtils;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipeFlingAdapterView;

/**
 *
 * 购物清单
 * Created by zhyan on 2017/2/17.
 */

public class ShoppingListActivity extends Activity implements SwipeFlingAdapterView.onFlingListener,SwipeFlingAdapterView.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
       /* initCardHeightByDif();*/
        initCardSwitch();
    }


    /*卡片效果*/
    @BindView(R.id.rly_shoppinglist_content_paperone)
    RelativeLayout rlyShoppingListContentPaperone;
    @BindView(R.id.rly_shoppinglist_content_papertwo)
    RelativeLayout rlyShoppingListContentPaperTwo;

    @BindView(R.id.sfav_shoppinglist_content)
    SwipeFlingAdapterView sfavShoppingListContent;
    private SwipFlingAdapter adapter;


    private void initCardSwitch(){


        if (sfavShoppingListContent != null) {
            sfavShoppingListContent.setIsNeedSwipe(true);
            sfavShoppingListContent.setFlingListener(this);
           /* sfavShoppingListContent.setOnItemClickListener(this);*/

            adapter = new SwipFlingAdapter(this);
            sfavShoppingListContent.setAdapter(adapter);
        }
        loadData();
    }
    private void loadData(){
        new AsyncTask<Void, Void, List<SwipFlingBean>>() {
            @Override
            protected List<SwipFlingBean> doInBackground(Void... params) {
                ArrayList<SwipFlingBean> list = new ArrayList<SwipFlingBean>();
                SwipFlingBean talent;
                for (int i = 0; i < 15; i++) {
                    talent = new SwipFlingBean();
                    list.add(talent);
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<SwipFlingBean> list) {
                super.onPostExecute(list);
                adapter.addAll(list);

            }
        }.execute();
    }


    @Override
    public void removeFirstObjectInAdapter() {
        adapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {

    }

    @Override
    public void onRightCardExit(Object dataObject) {

    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 2) {
            loadData();
        }
    }
    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msv_shoppinglist_content_piper_card_item_goodstype:
                Toast.makeText(this,"i'm onClick child onclick",Toast.LENGTH_SHORT).show();
                break;
        }
    }

  /*  @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {
        Toast.makeText(this,"i'm item onclick",Toast.LENGTH_SHORT).show();
    }*/



/*卡片数据填充*/
    /*卡片效果*/






}
