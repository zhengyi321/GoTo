package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import tianhao.agoto.R;
import tianhao.agoto.Utils.SystemUtils;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipeFlingAdapterView;

/**
 *
 * 购物清单
 * Created by zhyan on 2017/2/17.
 */

public class ShoppingListActivity extends Activity implements SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener, View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
        initCardHeightByDif();
        initCardSwitch();
    }


















    /*卡片效果*/
    @BindView(R.id.rly_shoppinglist_content_paperone)
    RelativeLayout rlyShoppingListContentPaperone;
    @BindView(R.id.rly_shoppinglist_content_papertwo)
    RelativeLayout rlyShoppingListContentPaperTwo;

   private int tempWidth;
    private int cardWidth;
    private int cardHeight;
    double tempHeight;
    @BindView(R.id.sfav_shoppinglist_content)
    SwipeFlingAdapterView sfavShoppingListContent;
    private ShoppingListActivity.InnerAdapter adapter;


    private void initCardHeightByDif(){

        ViewGroup.LayoutParams paramsOne = rlyShoppingListContentPaperone.getLayoutParams();
        ViewGroup.LayoutParams paramsTwo = rlyShoppingListContentPaperTwo.getLayoutParams();
 /*       ViewGroup.LayoutParams paramsOne = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams paramsTwo = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams paramsThree = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
*/
        SystemUtils systemUtils = new SystemUtils(this);
        int width =systemUtils.getWindowWidth();
        int height= systemUtils.getWindowHeight();
        tempHeight = height - (height/2.8);
        tempWidth = width;
        Toast.makeText(this,"tempHeight:"+tempHeight,Toast.LENGTH_LONG).show();
        paramsOne.height = (int) tempHeight;
        paramsTwo.height = (int) tempHeight ;
        paramsOne.width = (int)(width -width/6);
        paramsTwo.width = (int)(width -width/6.5);

        /*paramsThree.height = (int) temp - 14;*/
        rlyShoppingListContentPaperone.setLayoutParams(paramsOne);
        rlyShoppingListContentPaperTwo.setLayoutParams(paramsTwo);
    }
    private void initCardSwitch(){

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        cardHeight = (int) (dm.heightPixels - (338 * density));
        if (sfavShoppingListContent != null) {
            sfavShoppingListContent.setIsNeedSwipe(true);
            sfavShoppingListContent.setFlingListener(this);
            sfavShoppingListContent.setOnItemClickListener(this);

            adapter = new ShoppingListActivity.InnerAdapter();
            sfavShoppingListContent.setAdapter(adapter);
        }
        loadData();
    }
    private void loadData(){
        new AsyncTask<Void, Void, List<ShoppingListActivity.Talent>>() {
            @Override
            protected List<ShoppingListActivity.Talent> doInBackground(Void... params) {
                ArrayList<ShoppingListActivity.Talent> list = new ArrayList<>(10);
                ShoppingListActivity.Talent talent;
                for (int i = 0; i < 10; i++) {
                    talent = new ShoppingListActivity.Talent();
                    list.add(talent);
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<ShoppingListActivity.Talent> list) {
                super.onPostExecute(list);
                adapter.addAll(list);
            }
        }.execute();
    }
    @Override
    public void onClick(View v) {

    }
    @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {

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
        if (itemsInAdapter == 3) {
            loadData();
        }
    }
    @Override
    public void onScroll(float progress, float scrollXProgress) {

    }
    private class InnerAdapter extends BaseAdapter {

        ArrayList<ShoppingListActivity.Talent> objs;

        public InnerAdapter() {
            objs = new ArrayList<>();
        }

        public void addAll(Collection<ShoppingListActivity.Talent> collection) {
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
        public ShoppingListActivity.Talent getItem(int position) {
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
            ShoppingListActivity.ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shoppinglist_content_piper_card_item_lly, parent, false);
                //holder.portraitView.getLayoutParams().width = cardWidth;
            } else {
                holder = (ShoppingListActivity.ViewHolder) convertView.getTag();
            }
            initPiperCardItem(convertView);

            //holder.jobView.setText(talent.jobName);

            return convertView;
        }

    }

    private  class ViewHolder {

    }

    public  class Talent {
    }

    private void initPiperCardItem(View convertView){
                  /*初始化最外层的页面做到适配各种手机*/
        LinearLayout llyShoppingListPaperThree = (LinearLayout) convertView.findViewById(R.id.lly_shoppinglist_paperthree);
/*
        RelativeLayout rlyShoppingListPiperCardFront = (RelativeLayout) convertView.findViewById(R.id.rly_shoppinglist_content_piper_card_split_front);
        RelativeLayout rlyShoppingListPiperCardBehind = (RelativeLayout) convertView.findViewById(R.id.rly_shoppinglist_content_piper_card_split_behind);
*/
        ViewGroup.LayoutParams paramsThree = llyShoppingListPaperThree.getLayoutParams();/*
        ViewGroup.LayoutParams paramsPiperCardFront = rlyShoppingListPiperCardFront.getLayoutParams();
        ViewGroup.LayoutParams paramsPiperCardBehind = rlyShoppingListPiperCardBehind.getLayoutParams();*/
        paramsThree.height = (int) tempHeight   ;
        /*paramsThree.width = tempWidth - 50;*/
        /*
        paramsPiperCardFront.height = (int) (tempHeight/10) ;
        paramsPiperCardBehind.height = (int) (tempHeight - (tempHeight/1.4))  ;*/
        llyShoppingListPaperThree.setLayoutParams(paramsThree);
        llyShoppingListPaperThree.setGravity(View.TEXT_ALIGNMENT_CENTER);
        /*
        rlyShoppingListPiperCardFront.setLayoutParams(paramsPiperCardFront);
        rlyShoppingListPiperCardBehind.setLayoutParams(paramsPiperCardBehind);*/
            /*初始化最外层的页面做到适配各种手机*/
    }
    /*卡片效果*/






}
