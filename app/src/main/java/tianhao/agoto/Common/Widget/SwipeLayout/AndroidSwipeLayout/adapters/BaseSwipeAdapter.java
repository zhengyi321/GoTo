package tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.AndroidSwipeLayout;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.implments.SwipeItemMangerImpl;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.interfaces.SwipeAdapterInterface;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.interfaces.SwipeItemMangerInterface;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.util.Attributes;

public abstract class BaseSwipeAdapter extends BaseAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {

    protected SwipeItemMangerImpl mItemManger = new SwipeItemMangerImpl(this);

    /**
     * return the {@link } resource id, int the view item.
     * @param position
     * @return
     */
    public abstract int getSwipeLayoutResourceId(int position);

    /**
     * generate a new view item.
     * Never bind SwipeListener or fill values here, every item has a chance to fill value or bind
     * listeners in fillValues.
     * to fill it in {@code fillValues} method.
     * @param position
     * @param parent
     * @return
     */
    public abstract View generateView(int position, ViewGroup parent);

    /**
     * fill values or bind listeners to the view.
     * @param position
     * @param convertView
     */
    public abstract void fillValues(int position, View convertView);

    @Override
    public void notifyDatasetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = generateView(position, parent);
        }
        mItemManger.bind(v, position);
        fillValues(position, v);
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
