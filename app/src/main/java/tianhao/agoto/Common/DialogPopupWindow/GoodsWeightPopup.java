package tianhao.agoto.Common.DialogPopupWindow;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.ShouDongShuRuDialog;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/3/11.
 */

public class GoodsWeightPopup extends PopupWindow {

    private Activity activity;
    private View mPopView;
    public OnSelectGoodsItemValueListener onSelectGoodsItemValueListener;
    public String selectItem;
    private ShouDongShuRuDialog shouDongShuRuDialog;
    /*10kg*/
    @BindView(R.id.cb_popupwindow_goodsweight_10kg)
    CheckBox cbPopupWindowGoodsWeight10kg;
    @OnClick(R.id.cb_popupwindow_goodsweight_10kg)
    public void cbPopupWindowGoodsWeight10kgOnclick(){
        selectItem = getSelectedItemValue("10");
    }
    /*10kg*/
    /*11kg*/
    @BindView(R.id.cb_popupwindow_goodsweight_11kg)
    CheckBox cbPopupWindowGoodsWeight11kg;
    @OnClick(R.id.cb_popupwindow_goodsweight_11kg)
    public void cbPopupWindowGoodsWeight11kgOnclick(){
        selectItem = getSelectedItemValue("11");
    }
    /*11kg*/
    /*12kg*/
    @BindView(R.id.cb_popupwindow_goodsweight_12kg)
    CheckBox cbPopupWindowGoodsWeight12kg;
    @OnClick(R.id.cb_popupwindow_goodsweight_12kg)
    public void cbPopupWindowGoodsWeight12kgOnclick(){
        selectItem = getSelectedItemValue("12");
    }
    /*12kg*/
    /*13kg*/
    @BindView(R.id.cb_popupwindow_goodsweight_13kg)
    CheckBox cbPopupWindowGoodsWeight13kg;
    @OnClick(R.id.cb_popupwindow_goodsweight_13kg)
    public void cbPopupWindowGoodsWeight13kgOnclick(){
        selectItem = getSelectedItemValue("13");
    }
    /*13kg*/
    /*14kg*/
    @BindView(R.id.cb_popupwindow_goodsweight_14kg)
    CheckBox cbPopupWindowGoodsWeight14kg;
    @OnClick(R.id.cb_popupwindow_goodsweight_14kg)
    public void cbPopupWindowGoodsWeight14kgOnclick(){
        selectItem = getSelectedItemValue("14");
    }
    /*14kg*/
    /*15kg*/
    @BindView(R.id.cb_popupwindow_goodsweight_15kg)
    CheckBox cbPopupWindowGoodsWeight15kg;
    @OnClick(R.id.cb_popupwindow_goodsweight_15kg)
    public void cbPopupWindowGoodsWeight15kgOnclick(){
        selectItem = getSelectedItemValue("15");
    }
    /*15kg*/
    /*16kg*/
    @BindView(R.id.cb_popupwindow_goodsweight_16kg)
    CheckBox cbPopupWindowGoodsWeight16kg;
    @OnClick(R.id.cb_popupwindow_goodsweight_16kg)
    public void cbPopupWindowGoodsWeight16kgOnclick(){
        selectItem = getSelectedItemValue("16");
    }
    /*16kg*/
  
  
    /*手动输入*/
    @BindView(R.id.cb_popupwindow_goodsweight_manualInput)
    CheckBox cbPopupWindowGoodsWeightManualInput;
    @OnClick(R.id.cb_popupwindow_goodsweight_manualInput)
    public void cbPopupWindowGoodsWeightManualInputOnclick(){
            dismiss();
            shouDongShuRuDialog = new ShouDongShuRuDialog(activity).Build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dissmissDialog();
                }
            }).setPositiveButton("确认",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dissmissDialog();
                }
            }).setCallBackListener(new ShouDongShuRuDialog.DialogCallBackListener() {
                @Override
                public void callBack(String msgName) {

                }
            }).build(activity);
        showDialog(mPopView);
    }
    public void showDialog(View view) {
        if (shouDongShuRuDialog != null && !shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.show();
    }

    public void dissmissDialog() {
        if (shouDongShuRuDialog != null && shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.dismiss();
    }
    /*手动输入*/












    /*确定 取消*/
    @BindView(R.id.rly_popupwindow_goodsweight_query)
    RelativeLayout rlyPopupWindowGoodsWeightQuery;
    @OnClick(R.id.rly_popupwindow_goodsweight_query)
    public void rlyPopupWindowGoodsWeightQueryOnclick(){
        if(onSelectGoodsItemValueListener != null){
            if(selectItem != null) {
                onSelectGoodsItemValueListener.OnClick(selectItem);
            }else{
                onSelectGoodsItemValueListener.OnClick("");
            }
        }
        dismiss();
    }

    @BindView(R.id.rly_popupwindow_goodsweight_cancel)
    RelativeLayout rlyPopupWindowGoodsWeightCancel;
    @OnClick(R.id.rly_popupwindow_goodsweight_cancel)
    public void rlyPopupWindowGoodsWeightCancelOnclick(){
           dismiss();
     }
    /*确定 取消*/



    public GoodsWeightPopup(Activity activity1){
        activity = activity1;
        init();
    }
    private void init(){
        initInflateView();
        ButterKnife.bind(this,mPopView);


    }

    private void initInflateView(){
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView= inflater.inflate(R.layout.popupwindow_goodsweightl_lly, null);
        this.setContentView(mPopView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
    }






    private String getSelectedItemValue(String selectItem){

        switch (selectItem){
            case "10":
                cbPopupWindowGoodsWeight10kg.setChecked(true);
                cbPopupWindowGoodsWeight10kg.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsWeight11kg.setChecked(false);
                cbPopupWindowGoodsWeight11kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight12kg.setChecked(false);
                cbPopupWindowGoodsWeight12kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight13kg.setChecked(false);
                cbPopupWindowGoodsWeight13kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight14kg.setChecked(false);
                cbPopupWindowGoodsWeight14kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight15kg.setChecked(false);
                cbPopupWindowGoodsWeight15kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight16kg.setChecked(false);
                cbPopupWindowGoodsWeight16kg.setTextColor(activity.getResources().getColor(R.color.black));
                
                selectItem = "10kg";
                break;
            case "11":
                cbPopupWindowGoodsWeight10kg.setChecked(false);
                cbPopupWindowGoodsWeight10kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight11kg.setChecked(true);
                cbPopupWindowGoodsWeight11kg.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsWeight12kg.setChecked(false);
                cbPopupWindowGoodsWeight12kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight13kg.setChecked(false);
                cbPopupWindowGoodsWeight13kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight14kg.setChecked(false);
                cbPopupWindowGoodsWeight14kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight15kg.setChecked(false);
                cbPopupWindowGoodsWeight15kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight16kg.setChecked(false);
                cbPopupWindowGoodsWeight16kg.setTextColor(activity.getResources().getColor(R.color.black));
                
                selectItem = "11kg";
                break;
            case "12":
                cbPopupWindowGoodsWeight10kg.setChecked(false);
                cbPopupWindowGoodsWeight10kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight11kg.setChecked(false);
                cbPopupWindowGoodsWeight11kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight12kg.setChecked(true);
                cbPopupWindowGoodsWeight12kg.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsWeight13kg.setChecked(false);
                cbPopupWindowGoodsWeight13kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight14kg.setChecked(false);
                cbPopupWindowGoodsWeight14kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight15kg.setChecked(false);
                cbPopupWindowGoodsWeight15kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight16kg.setChecked(false);
                cbPopupWindowGoodsWeight16kg.setTextColor(activity.getResources().getColor(R.color.black));
                
                selectItem = "12kg";
                break;
            case "13":
                cbPopupWindowGoodsWeight10kg.setChecked(false);
                cbPopupWindowGoodsWeight10kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight11kg.setChecked(false);
                cbPopupWindowGoodsWeight11kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight12kg.setChecked(false);
                cbPopupWindowGoodsWeight12kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight13kg.setChecked(true);
                cbPopupWindowGoodsWeight13kg.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsWeight14kg.setChecked(false);
                cbPopupWindowGoodsWeight14kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight15kg.setChecked(false);
                cbPopupWindowGoodsWeight15kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight16kg.setChecked(false);
                cbPopupWindowGoodsWeight16kg.setTextColor(activity.getResources().getColor(R.color.black));
               
                selectItem = "13kg";
                break;

            case "14":
                cbPopupWindowGoodsWeight10kg.setChecked(false);
                cbPopupWindowGoodsWeight10kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight11kg.setChecked(false);
                cbPopupWindowGoodsWeight11kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight12kg.setChecked(false);
                cbPopupWindowGoodsWeight12kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight13kg.setChecked(false);
                cbPopupWindowGoodsWeight13kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight14kg.setChecked(true);
                cbPopupWindowGoodsWeight14kg.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsWeight15kg.setChecked(false);
                cbPopupWindowGoodsWeight15kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight16kg.setChecked(false);
                cbPopupWindowGoodsWeight16kg.setTextColor(activity.getResources().getColor(R.color.black));
                
                selectItem = "14kg";
                break;
            case "15":
                cbPopupWindowGoodsWeight10kg.setChecked(false);
                cbPopupWindowGoodsWeight10kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight11kg.setChecked(false);
                cbPopupWindowGoodsWeight11kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight12kg.setChecked(false);
                cbPopupWindowGoodsWeight12kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight13kg.setChecked(false);
                cbPopupWindowGoodsWeight13kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight14kg.setChecked(false);
                cbPopupWindowGoodsWeight14kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight15kg.setChecked(true);
                cbPopupWindowGoodsWeight15kg.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsWeight16kg.setChecked(false);
                cbPopupWindowGoodsWeight16kg.setTextColor(activity.getResources().getColor(R.color.black));

                selectItem = "15kg";
                break;
            case "16":
                cbPopupWindowGoodsWeight10kg.setChecked(false);
                cbPopupWindowGoodsWeight10kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight11kg.setChecked(false);
                cbPopupWindowGoodsWeight11kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight12kg.setChecked(false);
                cbPopupWindowGoodsWeight12kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight13kg.setChecked(false);
                cbPopupWindowGoodsWeight13kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight14kg.setChecked(false);
                cbPopupWindowGoodsWeight14kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight15kg.setChecked(false);
                cbPopupWindowGoodsWeight15kg.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsWeight16kg.setChecked(true);
                cbPopupWindowGoodsWeight16kg.setTextColor(activity.getResources().getColor(R.color.red));

                selectItem = "16kg";
                break;

        }

        return selectItem;

    }









    public interface OnSelectGoodsItemValueListener{
        public void OnClick(String value);
    }
    public void setOnSelectGoodsItemValueListener(OnSelectGoodsItemValueListener onSelectGoodsItemValueListener1){
        this.onSelectGoodsItemValueListener = onSelectGoodsItemValueListener1;
    }
}
