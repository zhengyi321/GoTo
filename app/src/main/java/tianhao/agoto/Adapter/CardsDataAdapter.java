package tianhao.agoto.Adapter;

import android.content.Context;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.R;


/*
*
*
* https://github.com/wenchaojiang/AndroidSwipeableCardStack
*/
public class CardsDataAdapter extends ArrayAdapter<String> {

    private Context context;
    public CardsDataAdapter(Context context) {
        super(context, R.layout.activity_shoppinglist_content_piper_card_item_lly);
        this.context = context;
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        /*TextView v = (TextView)(contentView.findViewById(R.id.content));
        v.setText(getItem(position));*/
        ViewHold viewHold = new ViewHold(contentView);
        return contentView;
    }


    public class ViewHold{
        @BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
        RecyclerView recyclerView ;
        @BindView(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        LinearLayout testly;
        List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
        SwipFlingRecyclerViewAdapter recyclerViewAdapter;
        @OnClick(R.id.lly_shoppinglist_content_piper_card_item_parent_rv)
        public void testOnclick(){
            System.out.println("this is onclick");

            recyclerViewAdapter.addData(new GoodsBean());

        }
        public ViewHold(View view){
            ButterKnife.bind(this,view);
            goodsBeanList.add(new GoodsBean());
            recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(context,goodsBeanList);
            recyclerView.setHasFixedSize(true);
            /*位置不一样会导致刷新不了的bug*/
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL));
        }

    }
}

