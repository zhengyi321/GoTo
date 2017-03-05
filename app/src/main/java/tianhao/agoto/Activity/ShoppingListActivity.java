package tianhao.agoto.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import butterknife.OnTouch;
import tianhao.agoto.Adapter.SwipFlingAdapter;
import tianhao.agoto.Adapter.SwipFlingRecyclerViewAdapter;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Bean.SwipFlingBean;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.DialogUtil;
import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.EasyRecyclerView;
import tianhao.agoto.Common.Widget.ScrollView.MyScrollView;
import tianhao.agoto.R;
import tianhao.agoto.Utils.BlurUtil;
import tianhao.agoto.Utils.ImageUtils;
import tianhao.agoto.Utils.SystemUtils;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipeFlingAdapterView;
import tianhao.agoto.Utils.TimeUtil;

/**
 *
 * 购物清单
 * Created by zhyan on 2017/2/17.
 */

public class ShoppingListActivity extends Activity implements SwipeFlingAdapterView.onFlingListener{



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

    @BindView(R.id.lly_shoppinglist_total)
    LinearLayout llyShoppingListTotal;
    private SwipFlingAdapter adapter;

    List<SwipFlingBean> list = new ArrayList<SwipFlingBean>();
    private void initCardSwitch(){


        if (sfavShoppingListContent != null) {
            sfavShoppingListContent.setIsNeedSwipe(true);
            sfavShoppingListContent.setFlingListener(this);
           /* sfavShoppingListContent.setOnItemClickListener(this);*/
            adapter = new SwipFlingAdapter(this,list);
            sfavShoppingListContent.setAdapter(adapter);
        }
        loadData();
    }
    private void loadData(){
        new AsyncTask<Void, Void, List<SwipFlingBean>>() {

            @Override
            protected List<SwipFlingBean> doInBackground(Void... params) {

                list.clear();
                SwipFlingBean talent;
                for (int i = 0; i < 6; i++) {
                    talent = new SwipFlingBean();
                    list.add(talent);
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<SwipFlingBean> list) {
                super.onPostExecute(list);
                adapter.addAll(list);
                adapter.remove(0);
            }
        }.execute();
    }


    @Override
    public void removeFirstObjectInAdapter() {
        if(list != null) {
            adapter.remove(0);
        }
    }

    @Override
    public void onLeftCardExit(Object dataObject) {

    }

    @Override
    public void onRightCardExit(Object dataObject) {

    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 3) {
            loadData();
        }
    }
    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }


  /*  @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {
        Toast.makeText(this,"i'm item onclick",Toast.LENGTH_SHORT).show();
    }*/



/*卡片数据填充*/
    /*卡片效果*/




    protected void onResume(){
        super.onResume();
        /*recyclerViewAdapter.setDataList(goodsBeanList);*/
    }

}
