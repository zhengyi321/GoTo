package tianhao.agoto.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import tianhao.agoto.Adapter.AddressManageAddShopRVAdapter;
import tianhao.agoto.Adapter.AddressManageAddUserRVAdapter;
import tianhao.agoto.Bean.ShopAddressListBean;
import tianhao.agoto.Bean.UserAddressListBean;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.AddAddressInAddressManageDialog;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.AlertView;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.OnDismissListener;
import tianhao.agoto.Common.DialogAlterView.LikeIosStyle.OnItemClickListener;
import tianhao.agoto.Common.Widget.DB.XCCacheManager.xccache.XCCacheManager;
import tianhao.agoto.Common.Widget.XRecycleView.XRecyclerView;
import tianhao.agoto.NetWorks.AddressManageNetWorks;
import tianhao.agoto.R;

/**
 * 地址管理
 * Created by zhyan on 2017/2/17.
 */

public class AddressManageActivity extends Activity implements OnItemClickListener, OnDismissListener {


    /*返回上一页*/
    @BindView(R.id.rly_addressmanage_topbar_leftmenu)
    RelativeLayout rlyAddressManageTopBarLeftMenuBack;

    @OnClick(R.id.rly_addressmanage_topbar_leftmenu)
    public void rlyAddressManageTopBarLeftMenuBackOnclick() {
        this.finish();
    }

    /*返回上一页*/
    private AddAddressInAddressManageDialog addAddressInAddressManageDialog;
    /*添加地址*/
    @BindView(R.id.rly_addressmanage_topbar_addaddress)
    RelativeLayout rlyAddressManageTopBarAddAddress;
/*    @OnClick(R.id.rly_addressmanage_topbar_addaddress)
    public void rlyAddressManageTopBarAddAddressOnclick(){
        Intent intent = new Intent(this,AddressManageAddSellerAddressActivity.class);
        startActivity(intent);
    }*/
    @OnClick(R.id.rly_addressmanage_topbar_addaddress)
    public void rlyAddressManageTopBarAddAddressOnclick() {
        /*new AlertView("请选择要添加的地址类型", "添加联系人地址","添加商家地址", new String[]{"取消"}, null, this, AlertView.Style.Alert, this).show();*/
        addAddressInAddressManageDialog = new AddAddressInAddressManageDialog(this).Build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setAddContacterButton("添加联系人", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setAddShopButton("添加商家地址", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setCallBackListener(new AddAddressInAddressManageDialog.DialogCallBackListener() {
            @Override
            public void callBack(String type) {
                gotoNextActivity(type);
            }
        }).build(this);
        showDialog();
    }


    private void gotoNextActivity(String type){
        if(type == null){
            return;
        }
        switch (type){
            case "contacter":
                    Intent intent = new Intent(this,AddressManageAddUserFinalActivity.class);
                    startActivity(intent);
                break;
            case "shop":
                Intent intent1 = new Intent(this,AddressManageAddShopFinalActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }


/*    添加地址*/
    private void showDialog(){
        if((addAddressInAddressManageDialog != null)&&(!addAddressInAddressManageDialog.isShowing())){
            addAddressInAddressManageDialog.show();
        }
    }
    private void dissmissDialog(){
        if((addAddressInAddressManageDialog != null)&&(addAddressInAddressManageDialog.isShowing())) {
            addAddressInAddressManageDialog.dismiss();
        }
    }
    private AlertView mAlertView;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    private InputMethodManager imm;
    private AlertView mAlertViewExt;//窗口拓展例子
    private EditText etName;//拓展View内容
    private  int RESULT_TYPE = 0;
    @BindView(R.id.xrv_addressmanage_useraddrlist)
    XRecyclerView xrvAddressManageUserAddrList;
    @BindView(R.id.xrv_addressmanage_shopaddrlist)
    XRecyclerView xrvAddressManageShopAddrList;
    private AddressManageAddShopRVAdapter shopRVAdapter;
    private AddressManageAddUserRVAdapter userRVAdapter;
    private List<ShopAddressListBean> shopAddressListBeanList;
    private List<UserAddressListBean> userAddressListBeanList;
    /*添加商家地址*/
    @BindView(R.id.rb_addressmanage_addshop)
    RadioButton rbAddressManageAddShop;
    @OnClick(R.id.rb_addressmanage_addshop)
    public void rbAddressManageAddShopOnclick()
    {
        getDetailDataFromNet("shop");
        /*getUserAddressFromNet();*/
    }
    /*添加商家地址*/
    /*添加联系人地址*/
    @BindView(R.id.rb_addressmanage_addcontacter)
    RadioButton rbAddressManageAddContacter;
    @OnClick(R.id.rb_addressmanage_addcontacter)
    public void rbAddressManageAddContacterOnclick()
    {
        getDetailDataFromNet("user");
  /*      getUserAddressFromNet();*/
    }
    /*添加联系人地址*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addressmanage_lly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
        initResultType();
        initXRV();
      /*  getUserAddressFromNet();*/
        getDetailDataFromNet("user");
        /*initAlterViewDialog();*/
    }
    private void initResultType(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String type = xcCacheManager.readCache("addressManageType");
        if(type.equals("send")) {
            Bundle bundle = this.getIntent().getExtras();
            String sender = bundle.getString("sender");
            String receiver = bundle.getString("receiver");
            if (sender != null) {
                RESULT_TYPE = Integer.valueOf(sender);
            }
            if (receiver != null) {
                RESULT_TYPE = Integer.valueOf(receiver);
            }
        }
    }
    private void initXRV(){
        shopAddressListBeanList = new ArrayList<>();
        userAddressListBeanList = new ArrayList<>();
        shopRVAdapter = new AddressManageAddShopRVAdapter(this,shopAddressListBeanList);
        userRVAdapter = new AddressManageAddUserRVAdapter(this,userAddressListBeanList,RESULT_TYPE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        xrvAddressManageShopAddrList.setAdapter(shopRVAdapter);
        xrvAddressManageUserAddrList.setAdapter(userRVAdapter);
        xrvAddressManageShopAddrList.setLayoutManager(layoutManager);
        xrvAddressManageUserAddrList.setLayoutManager(layoutManager1);


    }

    private void getUserAddressFromNet(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String usid = xcCacheManager.readCache("usid");
        if((usid != null)&&(!usid.isEmpty())) {
            AddressManageNetWorks addressManageNetWorks = new AddressManageNetWorks();

            xrvAddressManageUserAddrList.setVisibility(View.VISIBLE);
         /*   xrvAddressManageShopAddrList.setVisibility(View.GONE);*/
            Toast.makeText(getBaseContext(),"this is usid:"+usid,Toast.LENGTH_SHORT).show();
            addressManageNetWorks.getUserAddress(usid, new Observer<List<UserAddressListBean>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<UserAddressListBean> userAddressListBeen) {
                    userRVAdapter.setDataList(userAddressListBeen);
                    /*Toast.makeText(getBaseContext(),"this is userlist:"+userAddressListBeen.size(),Toast.LENGTH_SHORT).show();*/
                }
            }
        );
        }


    }

    private void getDetailDataFromNet(String type){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String usid = xcCacheManager.readCache("usid");
        if((usid != null)&&(!usid.isEmpty())) {
            AddressManageNetWorks addressManageNetWorks = new AddressManageNetWorks();
            System.out.println("usid:"+usid);
            switch (type){
                case "user":
                    xrvAddressManageUserAddrList.setVisibility(View.VISIBLE);
                    xrvAddressManageShopAddrList.setVisibility(View.GONE);
                    addressManageNetWorks.getUserAddress(usid, new Observer<List<UserAddressListBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<UserAddressListBean> userAddressListBeen) {
                            /*Toast.makeText(getBaseContext(),"this is userlist:"+userAddressListBeen.size(),Toast.LENGTH_SHORT).show();*/
                            userRVAdapter.setDataList(userAddressListBeen);

                        }
                    });

                    break;
                case "shop":
                    xrvAddressManageUserAddrList.setVisibility(View.GONE);
                    xrvAddressManageShopAddrList.setVisibility(View.VISIBLE);
                    addressManageNetWorks.getShopAddrList(usid, new Observer<List<ShopAddressListBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<ShopAddressListBean> shopAddressListBeen) {
                            shopRVAdapter.setDataList(shopAddressListBeen);

                        }
                    });
                    break;

            }


        }
    }
/*    private void initAlterViewDialog(){
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mAlertView = new AlertView("标题", "内容", "取消", new String[]{"确定"}, new String[]{"确定"}, null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);
        //拓展窗口
        mAlertViewExt = new AlertView("提示", "请完善你的个人资料！", "取消", null, new String[]{"完成"},new String[]{"完成"}, this, AlertView.Style.Alert, this);

    }*/



    /*添加地址*/
/*    @OnClick(R.id.rly_addressmanage_topbar_addaddress)
    public void rlyAddressManageTopBarAddAddressOnclick(){
        *//*new AlertView("请选择要添加的地址类型", "添加联系人地址","添加商家地址", new String[]{"取消"}, null, this, AlertView.Style.Alert, this).show();*//*
 *//*       new AlertView("请选择要添加的地址类型", null, "取消", null,
                new String[]{"添加联系人地址", "添加商家地址"},
                this, AlertView.Style.ActionSheet, this).show();*//*
    }*/

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


    protected void onResume(){
        super.onResume();
        getDetailDataFromNet("user");
    }
}
