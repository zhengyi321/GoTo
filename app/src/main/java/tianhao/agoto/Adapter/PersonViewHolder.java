package tianhao.agoto.Adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.adapter.BaseViewHolder;
import tianhao.agoto.R;

/**
 * Created by zhyan on 2017/3/4.
 */

public class PersonViewHolder extends BaseViewHolder<Person> {
    private TextView mTv_name;
    private ImageView mImg_face;
    private TextView mTv_sign;


    public PersonViewHolder(ViewGroup parent) {
        super(parent, R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly);

    }

    @Override
    public void setData(final Person person){
        Log.i("ViewHolder","position"+getDataPosition());
        mTv_name.setText(person.getName());
        mTv_sign.setText(person.getSign());

    }
}
