package tianhao.agoto.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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


    @BindView(R.id.sp_shoppinglist_content)
    SwipePostcard spShoppingContent;

    @BindView(R.id.rly_shoppinglist_topbar_leftmenu)
    RelativeLayout rlyShoppingListTopBarLeftMenu;
    @BindView(R.id.rly_shoppinglist_topbar_rightmenu)
    RelativeLayout rlyShoppingListTopBarRightMenu;
    PostcardAdapter adapter;
    private final int RESULT_FOODSMENU = 12;//菜单
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
    /*初始化卡片*/
        /*卡片效果*/
    private void initCard(){

        List<Bean> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Bean bean = new Bean();
            data.add(bean);
        }

        adapter = new PostcardAdapter(this, data);
        if (spShoppingContent != null) {


            /*卡片数据填充*/
            spShoppingContent.setAdapter(adapter);
            spShoppingContent.setMaxPostcardNum(3);
//            postcard.setOffsetY(67);
//            postcard.setMinDistance(200);
            spShoppingContent.setOnPostcardRunOutListener(new SwipePostcard.OnPostcardRunOutListener() {
                @Override
                public void onPostcardRunOut() {
                    Toast.makeText(getBaseContext(), "Run out!", Toast.LENGTH_SHORT).show();
                }
            });

            spShoppingContent.setOnPostcardDismissListener(new SwipePostcard.OnPostcardDismissListener() {
                @Override
                public void onPostcardDismiss(int direction) {
                    if (direction == SwipePostcard.DIRECTION_LEFT) {
                        /*Toast.makeText(getBaseContext(), "Left", Toast.LENGTH_SHORT).show();*/
                    } else if (direction == SwipePostcard.DIRECTION_RIGHT) {
                        /*Toast.makeText(getBaseContext(), "right", Toast.LENGTH_SHORT).show();*/
                    }
                }
            });
          /*  Toast.makeText(getBaseContext(), postcard.getMaxPostcardNum() + " ", Toast.LENGTH_SHORT).show();*/
        }

    }


        /*卡片效果*/
    /*初始化卡片*/

    /*返回*/
    @OnClick(R.id.rly_shoppinglist_topbar_leftmenu)
    public void rlyShoppingListTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回*/
    /*确认订单*/
    @OnClick(R.id.rly_shoppinglist_topbar_rightmenu)
    public void rlyShoppingListTopBarRightMenuOnclick(){
        ArrayList<GoodsBean> goodsBeanList =(ArrayList<GoodsBean>) adapter.getGoodsBeanList();
        System.out.println("this is rlyShoppingListTopBarRightMenuOnclick:"+goodsBeanList.size()+"goodsbean"+goodsBeanList.get(0).getName());
        Bundle b = new Bundle();
        b.putParcelableArrayList("foodsList", goodsBeanList);
        Intent intent = new Intent();
        intent.putExtras(b);
        setResult(RESULT_FOODSMENU,intent);
        finish();
    }

    /*确认订单*/




    protected void onResume(){
        super.onResume();
        /*recyclerViewAdapter.setDataList(goodsBeanList);*/
    }

}
