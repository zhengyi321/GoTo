package tianhao.agoto.Bean;

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
import tianhao.agoto.R;

/**
 * Created by zhyan on 2017/3/1.
 */

public class GoodsBean {
    private String name ;
    private String num;
/*    private String price;*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

  /*  public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }*/



}
