package tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.implments;

import android.view.View;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.AndroidSwipeLayout;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.SimpleSwipeListener;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.interfaces.SwipeAdapterInterface;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.interfaces.SwipeItemMangerInterface;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.util.Attributes;

/**
 * SwipeItemMangerImpl is a helper class to help all the adapters to maintain open status.
 */
public class SwipeItemMangerImpl implements SwipeItemMangerInterface {

    private Attributes.Mode mode = Attributes.Mode.Single;
    public final int INVALID_POSITION = -1;

    protected int mOpenPosition = INVALID_POSITION;

    protected Set<Integer> mOpenPositions = new HashSet<Integer>();
    protected Set<AndroidSwipeLayout> mShownLayouts = new HashSet<AndroidSwipeLayout>();

    protected SwipeAdapterInterface swipeAdapterInterface;

    public SwipeItemMangerImpl(SwipeAdapterInterface swipeAdapterInterface) {
        if (swipeAdapterInterface == null)
            throw new IllegalArgumentException("SwipeAdapterInterface can not be null");

        this.swipeAdapterInterface = swipeAdapterInterface;
    }

    public Attributes.Mode getMode() {
        return mode;
    }

    public void setMode(Attributes.Mode mode) {
        this.mode = mode;
        mOpenPositions.clear();
        mShownLayouts.clear();
        mOpenPosition = INVALID_POSITION;
    }

    public void bind(View view, int position) {
        int resId = swipeAdapterInterface.getSwipeLayoutResourceId(position);
        AndroidSwipeLayout swipeLayout = (AndroidSwipeLayout) view.findViewById(resId);
        if (swipeLayout == null)
            throw new IllegalStateException("can not find SwipeLayout in target view");

        if (swipeLayout.getTag(resId) == null) {
            OnLayoutListener onLayoutListener = new OnLayoutListener(position);
            SwipeMemory swipeMemory = new SwipeMemory(position);
            swipeLayout.addSwipeListener(swipeMemory);
            swipeLayout.addOnLayoutListener(onLayoutListener);
            swipeLayout.setTag(resId, new ValueBox(position, swipeMemory, onLayoutListener));
            mShownLayouts.add(swipeLayout);
        } else {
            ValueBox valueBox = (ValueBox) swipeLayout.getTag(resId);
            valueBox.swipeMemory.setPosition(position);
            valueBox.onLayoutListener.setPosition(position);
            valueBox.position = position;
        }
    }

    @Override
    public void openItem(int position) {
        if (mode == Attributes.Mode.Multiple) {
            if (!mOpenPositions.contains(position))
                mOpenPositions.add(position);
        } else {
            mOpenPosition = position;
        }
        swipeAdapterInterface.notifyDatasetChanged();
    }

    @Override
    public void closeItem(int position) {
        if (mode == Attributes.Mode.Multiple) {
            mOpenPositions.remove(position);
        } else {
            if (mOpenPosition == position)
                mOpenPosition = INVALID_POSITION;
        }
        swipeAdapterInterface.notifyDatasetChanged();
    }

    @Override
    public void closeAllExcept(AndroidSwipeLayout layout) {
        for (AndroidSwipeLayout s : mShownLayouts) {
            if (s != layout)
                s.close();
        }
    }

    @Override
    public void closeAllItems() {
        if (mode == Attributes.Mode.Multiple) {
            mOpenPositions.clear();
        } else {
            mOpenPosition = INVALID_POSITION;
        }
        for (AndroidSwipeLayout s : mShownLayouts) {
            s.close();
        }
    }

    @Override
    public void removeShownLayouts(AndroidSwipeLayout layout) {
        mShownLayouts.remove(layout);
    }

    @Override
    public List<Integer> getOpenItems() {
        if (mode == Attributes.Mode.Multiple) {
            return new ArrayList<Integer>(mOpenPositions);
        } else {
            return Collections.singletonList(mOpenPosition);
        }
    }

    @Override
    public List<AndroidSwipeLayout> getOpenLayouts() {
        return new ArrayList<AndroidSwipeLayout>(mShownLayouts);
    }

    @Override
    public boolean isOpen(int position) {
        if (mode == Attributes.Mode.Multiple) {
            return mOpenPositions.contains(position);
        } else {
            return mOpenPosition == position;
        }
    }

    class ValueBox {
        OnLayoutListener onLayoutListener;
        SwipeMemory swipeMemory;
        int position;

        ValueBox(int position, SwipeMemory swipeMemory, OnLayoutListener onLayoutListener) {
            this.swipeMemory = swipeMemory;
            this.onLayoutListener = onLayoutListener;
            this.position = position;
        }
    }

    class OnLayoutListener implements AndroidSwipeLayout.OnLayout {

        private int position;

        OnLayoutListener(int position) {
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onLayout(AndroidSwipeLayout v) {
            if (isOpen(position)) {
                v.open(false, false);
            } else {
                v.close(false, false);
            }
        }

    }

    class SwipeMemory extends SimpleSwipeListener {

        private int position;

        SwipeMemory(int position) {
            this.position = position;
        }

        @Override
        public void onClose(AndroidSwipeLayout layout) {
            if (mode == Attributes.Mode.Multiple) {
                mOpenPositions.remove(position);
            } else {
                mOpenPosition = INVALID_POSITION;
            }
        }

        @Override
        public void onStartOpen(AndroidSwipeLayout layout) {
            if (mode == Attributes.Mode.Single) {
                closeAllExcept(layout);
            }
        }

        @Override
        public void onOpen(AndroidSwipeLayout layout) {
            if (mode == Attributes.Mode.Multiple)
                mOpenPositions.add(position);
            else {
                closeAllExcept(layout);
                mOpenPosition = position;
            }
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}
