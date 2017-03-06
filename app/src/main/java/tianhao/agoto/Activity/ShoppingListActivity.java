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
import android.util.Log;
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
import tianhao.agoto.Adapter.CardsDataAdapter;
import tianhao.agoto.Adapter.SwipFlingAdapter;
import tianhao.agoto.Adapter.SwipFlingRecyclerViewAdapter;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.Bean.SwipFlingBean;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.DialogUtil;
import tianhao.agoto.Common.Widget.RecyclerView.CardStack.cardstack.CardStack;
import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.EasyRecyclerView;
import tianhao.agoto.Common.Widget.ScrollView.MyScrollView;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipePostCard.Bean;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipePostCard.PostcardAdapter;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipePostCard.SwipePostcard;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipecardRecycleRView.ItemRemovedListener;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipecardRecycleRView.MyAdapter;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipecardRecycleRView.SwipeCardLayoutManager;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipecardRecycleRView.SwipeCardRecyclerView;
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

public class ShoppingListActivity extends Activity {


/*    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;*/

    private SwipeCardRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
        initCard();
  /*      newCard();*/
       /* initCardHeightByDif();*/
        /*initCardSwitch();*/
    }

    private void initCard(){

        SwipePostcard postcard = (SwipePostcard) findViewById(R.id.sp_test);
        List<Bean> data = new ArrayList<>();

        int[] resIds = new int[]{R.drawable.splash1, R.drawable.splash2};
        for (int i = 0; i < 2; i++) {
            Bean bean = new Bean(resIds[i], "世界上最好的金泰妍->" + i);
            data.add(bean);
        }

        PostcardAdapter adapter = new PostcardAdapter(this, data);
        if (postcard != null) {
            postcard.setAdapter(adapter);
            postcard.setMaxPostcardNum(3);
//            postcard.setOffsetY(67);
//            postcard.setMinDistance(200);
            postcard.setOnPostcardRunOutListener(new SwipePostcard.OnPostcardRunOutListener() {
                @Override
                public void onPostcardRunOut() {
                    Toast.makeText(getBaseContext(), "Run out!", Toast.LENGTH_SHORT).show();
                }
            });

            postcard.setOnPostcardDismissListener(new SwipePostcard.OnPostcardDismissListener() {
                @Override
                public void onPostcardDismiss(int direction) {
                    if (direction == SwipePostcard.DIRECTION_LEFT) {
                        Toast.makeText(getBaseContext(), "Left", Toast.LENGTH_SHORT).show();
                    } else if (direction == SwipePostcard.DIRECTION_RIGHT) {
                        Toast.makeText(getBaseContext(), "right", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Toast.makeText(getBaseContext(), postcard.getMaxPostcardNum() + " ", Toast.LENGTH_SHORT).show();
        }

    }

/*
    private void initCard(){
        mRecyclerView = (SwipeCardRecyclerView) findViewById(R.id.scrv_shoppinglist_content);
        mRecyclerView.setLayoutManager(new SwipeCardLayoutManager());
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        mAdapter = new MyAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRemovedListener(new ItemRemovedListener() {
            @Override
            public void onRightRemoved() {
                Toast.makeText(getBaseContext(), list.get(list.size() - 1) + " was right removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftRemoved() {
                Toast.makeText(getBaseContext(), list.get(list.size() - 1) + " was left removed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    /*
    private void newCard(){
        mCardStack = (CardStack) findViewById(R.id.cs_shoppinglist_content);
        mCardStack.setContentResource(R.layout.activity_shoppinglist_content_piper_card_item_lly);
//        mCardStack.setStackMargin(20);

        mCardAdapter = new CardsDataAdapter(getApplicationContext());
        mCardAdapter.add("test1");
        mCardStack.setEnableLoop(!mCardStack.isEnableLoop());


        mCardStack.setAdapter(mCardAdapter);
        mCardStack.reset(true);
        if (mCardStack.getAdapter() != null) {
            Log.i("MyActivity", "Card Stack size: " + mCardStack.getAdapter().getCount());
        }
        mCardStack.setListener(new myListener());
    }
    public class myListener implements CardStack.CardEventListener {

        @Override
        public boolean swipeEnd(int section, float distance) {
            return false;
        }

        @Override
        public boolean swipeStart(int section, float distance) {
            return false;
        }

        @Override
        public boolean swipeContinue(int section, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void discarded(int mIndex, int direction) {

        }

        @Override
        public void topCardTapped() {

        }
    }*/
    /*卡片效果*/
  /*  @BindView(R.id.rly_shoppinglist_content_paperone)
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
           *//* sfavShoppingListContent.setOnItemClickListener(this);*//*
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
*/

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
