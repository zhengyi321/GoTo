package tianhao.agoto.Common.Widget.WheelView;

/**
 * Created by admin on 16/6/17.
 */

/**
 * Wheel scrolled listener interface.
 */
public interface OnWheelScrollListener {
    /**
     * Callback method to be invoked when scrolling started.
     *
     * @param wheel
     *            the wheel view whose state has changed.
     */
    void onScrollingStarted(WheelView wheel);

    /**
     * Callback method to be invoked when scrolling ended.
     *
     * @param wheel
     *            the wheel view whose state has changed.
     */
    void onScrollingFinished(WheelView wheel);
}