package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.AlertView;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.OnDismissListener;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.OnItemClickListener;
import tianhao.agoto.R;

/**
 * 地址管理
 * Created by zhyan on 2017/2/17.
 */

public class AddressManageActivity extends Activity implements OnItemClickListener, OnDismissListener {

    @BindView(R.id.rly_addressmanage_topbar_leftmenu)
    RelativeLayout rlyAddressManageTopBarLeftMenuBack;

    @BindView(R.id.rly_addressmanage_topbar_addaddress)
    RelativeLayout rlyAddressManageTopBarAddAddress;

    private AlertView mAlertView;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    private InputMethodManager imm;
    private AlertView mAlertViewExt;//窗口拓展例子
    private EditText etName;//拓展View内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addressmanage_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
        initAlterViewDialog();
    }

    private void initAlterViewDialog(){
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mAlertView = new AlertView("标题", "内容", "取消", new String[]{"确定"}, null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);
        //拓展窗口
        mAlertViewExt = new AlertView("提示", "请完善你的个人资料！", "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, this);

    }

    /*返回上一页*/
    @OnClick(R.id.rly_addressmanage_topbar_leftmenu)
    public void rlyAddressManageTopBarLeftMenuBackOnclick(){
        this.finish();
    }
    /*返回上一页*/

    /*添加地址*/
    @OnClick(R.id.rly_addressmanage_topbar_addaddress)
    public void rlyAddressManageTopBarAddAddressOnclick(){
        /*new AlertView("请选择要添加的地址类型", "添加联系人地址","添加商家地址", new String[]{"取消"}, null, this, AlertView.Style.Alert, this).show();*/
        new AlertView("请选择要添加的地址类型", null, "取消", null,
                new String[]{"添加联系人地址", "添加商家地址"},
                this, AlertView.Style.ActionSheet, this).show();
    }

    @Override
    public void onDismiss(Object o) {
        closeKeyboard();
    }
    private void closeKeyboard() {
        //关闭软键盘
/*        imm.hideSoftInputFromWindow(etName.getWindowToken(),0);*/
        //恢复位置
        mAlertViewExt.setMarginBottom(0);
    }
    @Override
    public void onItemClick(Object o, int position) {
        closeKeyboard();

    }


    /*添加地址*/

}
