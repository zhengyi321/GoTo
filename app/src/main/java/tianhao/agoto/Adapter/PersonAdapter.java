package tianhao.agoto.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.adapter.BaseViewHolder;
import tianhao.agoto.Common.Widget.RecyclerView.EasyRecyclerView.adapter.RecyclerArrayAdapter;

/**
 * Created by zhyan on 2017/3/4.
 */

public class PersonAdapter extends RecyclerArrayAdapter<Person> {
    public PersonAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonViewHolder(parent);
    }
}
