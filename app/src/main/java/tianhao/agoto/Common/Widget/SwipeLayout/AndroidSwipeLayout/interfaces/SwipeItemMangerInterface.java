package tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.interfaces;


import java.util.List;

import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.AndroidSwipeLayout;
import tianhao.agoto.Common.Widget.SwipeLayout.AndroidSwipeLayout.util.Attributes;

public interface SwipeItemMangerInterface {

    void openItem(int position);

    void closeItem(int position);

    void closeAllExcept(AndroidSwipeLayout layout);
    
    void closeAllItems();

    List<Integer> getOpenItems();

    List<AndroidSwipeLayout> getOpenLayouts();

    void removeShownLayouts(AndroidSwipeLayout layout);

    boolean isOpen(int position);

    Attributes.Mode getMode();

    void setMode(Attributes.Mode mode);
}
