package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;

import butterknife.ButterKnife;
import tianhao.agoto.R;

/**
 *
 * 查看订单
 * Created by zhyan on 2017/2/19.
 */

public class OrderCheckActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaiDuSDK();
        setContentView(R.layout.fragment_orderstatus_content_lly);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
    }
    /*百度地图定位begin*/
    private void initBaiDuSDK(){
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
