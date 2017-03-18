package tianhao.agoto.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Activity.HelpMeBuyActivity;
import tianhao.agoto.Activity.HelpMeSendActivity;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.CompanyCustomTelDialog;
import tianhao.agoto.R;

import tianhao.agoto.Common.Widget.SlideShowView.InitLoop;
import tianhao.agoto.Common.Widget.TextView.MarqueeText;
import tianhao.agoto.Utils.PhoneFormatCheckUtils;

/**
 * Created by admin on 2017/2/22.
 */

public class MainFragment extends Fragment {

    @BindView(R.id.tv_main_ad)
    MarqueeText tvMainAd;
    private CompanyCustomTelDialog companyCustomTelDialog ;
    /*帮我买*/
    @BindView(R.id.rly_main_content_helpmebuy)
    RelativeLayout rlyMainContentHelpMeBuy;
    @OnClick(R.id.rly_main_content_helpmebuy)
    public void helpMeBuyOnClick(){
        Intent intent = new Intent(view.getContext(),HelpMeBuyActivity.class);
        startActivity(intent);
    }
    /*帮我买*/
    /*帮我送*/
    @BindView(R.id.rly_main_content_helpmesend)
    RelativeLayout rlyMainContentHelpMeSend;
    @OnClick(R.id.rly_main_content_helpmesend)
    public void helpMeSendOnClick(){
        Intent intent = new Intent(view.getContext(),HelpMeSendActivity.class);
        startActivity(intent);
    }
    /*帮我送*/
    /*催单*/
    @BindView(R.id.rly_main_content_reminder)
    RelativeLayout rlyMainContentReminder;
    @OnClick(R.id.rly_main_content_reminder)
    public void rlyMainContentReminderOnclick(){
        Toast.makeText(getActivity(),"正在开发中",Toast.LENGTH_SHORT).show();

        companyCustomTelDialog = new CompanyCustomTelDialog(getActivity())
                .Build
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
    }).setCallBackListener(new CompanyCustomTelDialog.DialogCallBackListener() {
                    @Override
                    public void callBack(String tel) {
                        startCallTel(tel);
                    }
                }).build(getActivity());
        showDialog();
    }
    private void startCallTel(String number){
        /*PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
        if((number != null)&&(phoneFormatCheckUtils.IsNumber(number))) {*/
            //用intent启动拨打电话
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            startActivity(intent);
       /* }*/
    }


    /*催单*/

    /*建议 咨询*/
    @BindView(R.id.rly_main_content_suggest)
    RelativeLayout rlyMainContentSuggest;
    @OnClick(R.id.rly_main_content_suggest)
    public void rlyMainContentSuggestOnclick(){
        Toast.makeText(getActivity(),"正在开发中",Toast.LENGTH_SHORT).show();
        companyCustomTelDialog = new CompanyCustomTelDialog(getActivity()).Build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setCallBackListener(new CompanyCustomTelDialog.DialogCallBackListener() {
            @Override
            public void callBack(String tel) {

           startCallTel(tel);
            }
        }).build(getActivity());
        showDialog();
    }


    /*建议 咨询*/


    public void showDialog() {
        if (companyCustomTelDialog != null && !companyCustomTelDialog.isShowing())
            companyCustomTelDialog.show();
    }

    public void dissmissDialog() {
        if (companyCustomTelDialog != null && companyCustomTelDialog.isShowing())
            companyCustomTelDialog.dismiss();
    }
    private View view ;
    InitLoop initLoop;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_content_sv,container,false);

        init(view);
        return view;
    }

    public int getTheView(){
        return R.layout.fragment_main_content_sv;
    }
    public void onResume(){
        super.onResume();
        tvMainAd.startScroll();
        initLoop.setStart();
    }
    public void onStop(){
        super.onStop();
        tvMainAd.stopScroll();
        initLoop.setStop();
        tvMainAd.destroyDrawingCache();
    }
    public void onPause(){
        super.onPause();
        tvMainAd.stopScroll();
        initLoop.setStop();
        tvMainAd.destroyDrawingCache();

    }
    /*各种初始化*/
    private void init(View view1){
        this.view = view1;
        ButterKnife.bind(this,view1);
        cicleWheelInit(view1);
        initTVRunHorse("即日起，走兔跑腿全面改为线上下单！咨询电话0577-62670222");

    }
    private void cicleWheelInit(View view){
        initLoop = new InitLoop(view);
        initLoop.init();
    }

    /*跑马灯的实现初始化*/
    private void initTVRunHorse(String text){
        if(text.isEmpty() || text == null){
            return ;
        }else {
            tvMainAd.setText(text);
        }

        tvMainAd.startScroll();

    }




}
