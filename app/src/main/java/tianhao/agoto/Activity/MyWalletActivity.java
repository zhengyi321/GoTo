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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;
import tianhao.agoto.Utils.SystemUtils;
import tianhao.agoto.Common.Widget.SwipeCardView.SwipeFlingAdapterView;
import tianhao.agoto.Common.Widget.WaveView.WaveHelper;
import tianhao.agoto.Common.Widget.WaveView.WaveView;

/**
 *
 * 我的钱包
 * Created by zhyan on 2017/2/15.
 */

public class MyWalletActivity extends Activity implements SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener, View.OnClickListener{

    @BindView(R.id.rly_mywallet_topbar_leftmenu)
    RelativeLayout rlyMyWalletTopBarLeftMenu;
    @BindView(R.id.wv_mywallet_content)
    WaveView wvMyWalletContent;
    private WaveHelper mWaveHelper;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet_lly);



        init();

    }
    private void init(){
        ButterKnife.bind(this);
        wvInit();
        initCardHeightByDif();
        initCardSwitch();

    }
    /*开始波浪动画*/
    private void wvInit(){
        mWaveHelper = new WaveHelper(wvMyWalletContent);
        mWaveHelper.start();
    }
    /*开始波浪动画*/

    @OnClick(R.id.rly_mywallet_topbar_leftmenu)
    public void rlyMyWalletTopBarLeftMenuOnclick(){
        finish();
    }


    /*卡片效果*/
    @BindView(R.id.rly_mywallet_content_paperone)
    RelativeLayout rlyMyWalletContentPaperone;
    @BindView(R.id.rly_mywallet_content_papertwo)
    RelativeLayout rlyMyWalletContentPaperTwo;


    private int cardWidth;
    private int cardHeight;
    double tempHeight;
    @BindView(R.id.sfav_mywallet_content)
    SwipeFlingAdapterView sfavMyWalletContent;
    private InnerAdapter adapter;


    private void initCardHeightByDif(){
        SystemUtils systemUtils = new SystemUtils(this);
        int width =systemUtils.getWindowWidth();
        int height= systemUtils.getWindowHeight();
        ViewGroup.LayoutParams paramsOne = rlyMyWalletContentPaperone.getLayoutParams();
        ViewGroup.LayoutParams paramsTwo = rlyMyWalletContentPaperTwo.getLayoutParams();
 /*       ViewGroup.LayoutParams paramsOne = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams paramsTwo = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams paramsThree = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
*/
        tempHeight = height/1.8;
        paramsOne.height = (int) tempHeight;
        paramsTwo.height = (int) tempHeight - 7;
        /*paramsThree.height = (int) temp - 14;*/
        rlyMyWalletContentPaperone.setLayoutParams(paramsOne);
        rlyMyWalletContentPaperTwo.setLayoutParams(paramsTwo);
    }
    private void initCardSwitch(){

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        cardHeight = (int) (dm.heightPixels - (338 * density));
        if (sfavMyWalletContent != null) {
            sfavMyWalletContent.setIsNeedSwipe(true);
            sfavMyWalletContent.setFlingListener(this);
            sfavMyWalletContent.setOnItemClickListener(this);

            adapter = new InnerAdapter();
            sfavMyWalletContent.setAdapter(adapter);
        }
        loadData();
    }
    private void loadData(){
        new AsyncTask<Void, Void, List<Talent>>() {
            @Override
            protected List<Talent> doInBackground(Void... params) {
                ArrayList<Talent> list = new ArrayList<>(10);
                Talent talent;
                for (int i = 0; i < 10; i++) {
                    talent = new Talent();
                    list.add(talent);
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Talent> list) {
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

        ArrayList<Talent> objs;

        public InnerAdapter() {
            objs = new ArrayList<>();
        }

        public void addAll(Collection<Talent> collection) {
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
        public Talent getItem(int position) {
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mywallet_content_account_piper_card_item, parent, false);
                //holder.portraitView.getLayoutParams().width = cardWidth;
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

           /*初始化最外层的页面做到适配各种手机*/
            LinearLayout llyMyWalletPaperThree = (LinearLayout) convertView.findViewById(R.id.lly_mywallet_paperthree);
            ViewGroup.LayoutParams paramsThree = llyMyWalletPaperThree.getLayoutParams();
            paramsThree.height = (int) tempHeight - 14;
            llyMyWalletPaperThree.setLayoutParams(paramsThree);
            /*初始化最外层的页面做到适配各种手机*/
            //holder.jobView.setText(talent.jobName);

            return convertView;
        }

    }

    private  class ViewHolder {

    }

    public  class Talent {
    }
    /*卡片效果*/
}
