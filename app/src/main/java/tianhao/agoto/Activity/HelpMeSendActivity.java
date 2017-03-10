package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import tianhao.agoto.R;

/**
 *
 * 帮我送
 * Created by zhyan on 2017/2/12.
 */

public class HelpMeSendActivity extends Activity {

    @BindView(R.id.rly_helpmesend_topbar_leftmenu)
    RelativeLayout rlyHelpMeSendTopBarLeftMenu;

    /*收件人信息*/
    @BindView(R.id.lly_helpmesend_content_receiverdata)
    LinearLayout llyHelpMeSendReceiverData;
    @BindView(R.id.tv_helpmesend_content_receiveraddr)
    TextView tvHelpMeSendContentReceiverAddr;
    @BindView(R.id.tv_helpmesend_content_receivername)
    TextView tvHelpMeSendContentReceiverName;
    @BindView(R.id.tv_helpmesend_content_receivertel)
    TextView tvHelpMeSendContentReceiverTel;
    /*收件人信息*/
    /*发件人信息*/
    @BindView(R.id.lly_helpmesend_content_senderdata)
    LinearLayout llyHelpMeSendContentSenderData;
    @BindView(R.id.tv_helpmesend_content_senderaddr)
    TextView tvHelpMeSendContentSenderAddr;
    @BindView(R.id.tv_helpmesend_content_sendername)
    TextView tvHelpMeSendContentSenderName;
    @BindView(R.id.tv_helpmesend_content_sendertel)
    TextView tvHelpMeSendContentSenderTel;
    /*发件人信息*/


    /*物品重量*/
    @BindView(R.id.lly_helpmesend_content_weight)
    LinearLayout llyHelpMeSendContentWeight;
    @BindView(R.id.tv_helpmesend_content_goodsweight)
    TextView tvHelpMeSendContentGoodsWeight;

    /*物品重量*/

    /*配送里程*/
    @BindView(R.id.tv_helpmesend_content_senddis)
    TextView tvHelpMeSendContentSendDis;
    /*配送里程*/

    /*物品种类*/
    @BindView(R.id.lly_helpmesend_content_goodstype)
    LinearLayout llyHelpMeSendContentGoodsType;
    @BindView(R.id.tv_helpmesend_content_goodstype)
    TextView tvHelpMeSendContentGoodsType;
    /*物品种类*/

    /*收件时间*/
    @BindView(R.id.lly_helpmesend_content_rectime)
    LinearLayout llyHelpMeSendContentRecTime;
    @BindView(R.id.tv_helpmesend_content_rectime)
    TextView tvHelpMeSendContentRecTime;
    /*收件时间*/


    private final int RESULT_SENDER = 10;
    private final int RESULT_RECEIVER = 11;

    private Double blat,rlat,blon,rlon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpmesend_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
    }
    /*后退到主界面*/
    @OnClick(R.id.rly_helpmesend_topbar_leftmenu)
    public void rlyHelpMeBuyTopBarLeftMenuOnclick(){
        /*Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();*/
        finish();
    }
    /*后退到主界面*/
    /*收件人信息*/
    @OnClick(R.id.lly_helpmesend_content_receiverdata)
    public void llyHelpMeSendReceiverDataOnclick(){
        Bundle bundle = new Bundle();
        bundle.putString("receiver",""+RESULT_RECEIVER);
        Intent intent = new Intent(this,HelpMeSendAddContacterActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,RESULT_RECEIVER);
    }
    /*收件人信息*/

    /*发件人*/
    @OnClick(R.id.lly_helpmesend_content_senderdata)
    public void llyHelpMeSendSenderDataOnclick(){
        Bundle bundle = new Bundle();
        bundle.putString("sender",""+RESULT_SENDER);
        Intent intent = new Intent(this,HelpMeSendAddContacterActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,RESULT_SENDER);
        /*startActivity(intent);*/
    }
    /*发件人*/

    /*百度地图定位结果*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_SENDER:
                getDataFromAddActivity(data,true);
                break;
            case RESULT_RECEIVER:
                getDataFromAddActivity(data,false);


                break;
            default:
                break;
        }
    }
    /*百度地图定位结果*/
    private void getDataFromAddActivity(Intent data,boolean isSender){
        if(data != null){
            Bundle b=data.getExtras(); //data为B中回传的Intent
            String nameCall=b.getString("nameCall");//str即为回传的值
            String address=b.getString("address");//str即为回传的值
            String telphone = b.getString("tel");
            String lat = b.getString("blat");
            String lon = b.getString("blon");

            if(isSender){
                if((lat != null) && (lon != null)) {
                    blat = Double.parseDouble(lat);
                    blon = Double.parseDouble(lon);
                }
                tvHelpMeSendContentSenderName.setText(nameCall);
                tvHelpMeSendContentSenderAddr.setText(address);
                tvHelpMeSendContentSenderTel.setText(telphone);
            }else{
                tvHelpMeSendContentReceiverName.setText(nameCall);
                tvHelpMeSendContentReceiverAddr.setText(address);
                tvHelpMeSendContentReceiverTel.setText(telphone);
                if((lat != null) && (lon != null)) {
                    rlat = Double.parseDouble(lat);
                    rlon = Double.parseDouble(lon);
                }
            }
        }
    }

    protected void onStop(){
        super.onStop();

    }
}