package tianhao.agoto.Common.DialogAlterView.LikeIosStyle;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Adapter.SwipFlingRecyclerViewAdapter;
import tianhao.agoto.Bean.GoodsBean;
import tianhao.agoto.R;

/**
 *
 * http://www.cnblogs.com/shen-hua/p/5709663.html
 * Created by admin on 2017/3/2.
 *
 *
 * http://blog.csdn.net/qwm8777411/article/details/45420451
 */

public class DialogUtil {
    Context context;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    public DialogUtil(Context context){
        this.context = context;
         /*/ 创建对话框构建器*/
         builder = new AlertDialog.Builder(context);
    }
    public AlertDialog getAlertDialog(){
        return alertDialog;
    }

    /*添加商品*/
    public View createDialogAddGoods(int layout ){

        // 获取布局
        View view2 = View.inflate(context, layout, null);
        // 设置参数
        builder.setView(view2);
        // 设置参数
       /* builder.setTitle("Login").setIcon(R.drawable.ic_launcher)
                .setView(view2);*/
        // 创建对话框
        /*final AlertDialog*/
        alertDialog = builder.create();
        alertDialog.show();
        return view2;
   /*     ((GoodsBean)widget).addGoods(view2,alertDialog);*/
    }


    /*添加商品*/
}
