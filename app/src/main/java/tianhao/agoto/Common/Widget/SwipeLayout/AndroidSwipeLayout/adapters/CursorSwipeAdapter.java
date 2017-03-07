package tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.AndroidSwipeLayout;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.implments.SwipeItemMangerImpl;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.interfaces.SwipeAdapterInterface;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.interfaces.SwipeItemMangerInterface;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.util.Attributes;

public abstract class CursorSwipeAdapter extends CursorAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {

    private SwipeItemMangerImpl mItemManger = new SwipeItemMangerImpl(this);

    protected CursorSwipeAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    protected CursorSwipeAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        mItemManger.bind(v, position);
        return v;
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(AndroidSwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<AndroidSwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(AndroidSwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }
}
