package tianhao.agoto.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by admin on 2017/3/6.
 */

public class ShoppingListSwipFlingItemFragment extends Fragment {

    @BindView(R.id.erv_shoppinglist_content_piper_card_item_goods)
    RecyclerView ervShoppingListContentPiperCardItemGoods ;

    List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
    SwipFlingRecyclerViewAdapter recyclerViewAdapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_lly,container,false);
        ButterKnife.bind(this,view );
        init(view);
        return view;
    }
    private void init(View view){
        initRecyclerView();

    }
    private void initRecyclerView(){
        goodsBeanList.add(new GoodsBean());
        recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(getActivity(),goodsBeanList);
        ervShoppingListContentPiperCardItemGoods.setHasFixedSize(true);
            /*位置不一样会导致刷新不了的bug*/
        ervShoppingListContentPiperCardItemGoods.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        ervShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);
        ervShoppingListContentPiperCardItemGoods.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
    }





    public int getTheView(){
        return R.layout.activity_shoppinglist_content_piper_card_item_lly;
    }
    public View returnView(){
        if(null != view) {
            return view;
        }else {
            return null;
        }
    }





}
