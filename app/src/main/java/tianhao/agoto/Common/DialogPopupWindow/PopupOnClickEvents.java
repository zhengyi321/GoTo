package tianhao.agoto.Common.DialogPopupWindow;

import android.app.Activity;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.List;

/**
 * Created by zhyan on 2017/2/20.
 */

public class PopupOnClickEvents {
    private Activity activity;
    public PopupOnClickEvents(Activity activity1){
        activity = activity1;
    }
    public void PayConfirm(LinearLayout layout, String goodsName,String price){
        PayConfirmPopup payConfirmPopup = new PayConfirmPopup(activity,goodsName,price);
        setBackgroundAlpha(0.5f);
        payConfirmPopup.setFocusable(true);
        payConfirmPopup.showAtLocation(layout, Gravity.RIGHT, 0, 0);
        payConfirmPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) activity).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) activity).getWindow().setAttributes(lp);
    }
}
