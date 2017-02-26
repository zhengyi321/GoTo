package tianhao.agoto.Common.Widget.ScrollView;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**https://github.com/LinWright/ScrollViewEmbedListView
 * 外部拦截法，解决ScrollView嵌套ListView的滑动冲突
 *
 * 整个解决冲突问题
 * Created by zhyan on 16/6/28.
 */
public class EmbedListViewScrollView extends NestedScrollView{
    public static boolean bTouchEfficent = true;

    public EmbedListViewScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        bTouchEfficent = true;
    }

    public EmbedListViewScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        bTouchEfficent = true;
    }

    public EmbedListViewScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        bTouchEfficent = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return bTouchEfficent ? super.onInterceptTouchEvent(ev)
                : bTouchEfficent;
    }

}
