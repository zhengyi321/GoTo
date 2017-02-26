package tianhao.agoto.Activity;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import tianhao.agoto.R;

/**
 *
 * 消息中心
 * Created by zhyan on 2017/2/20.
 */

public class MessageCenterActivity extends Activity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagecenter_lly);
        init();
    }


    private void init(){
        ButterKnife.bind(this);

    }
}
