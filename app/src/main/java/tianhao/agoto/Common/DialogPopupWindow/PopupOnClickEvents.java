package tianhao.agoto.Common.DialogPopupWindow;

import android.app.Activity;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by zhyan on 2017/2/20.
 */

public class PopupOnClickEvents {

    public void PayConfirm(final Activity activity,LinearLayout layout,  List<String> dataList){
        PayConfirmPopup payConfirmPopup = new PayConfirmPopup(activity,dataList);
        payConfirmPopup.showAtLocation(layout, Gravity.RIGHT, 0, 0);
    }
}
