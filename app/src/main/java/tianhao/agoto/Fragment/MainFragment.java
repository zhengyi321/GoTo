package tianhao.agoto.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Activity.HelpMeBuyActivity;
import tianhao.agoto.Activity.HelpMeSendActivity;
import tianhao.agoto.R;

import tianhao.agoto.Common.Widget.SlideShowView.InitLoop;
import tianhao.agoto.Common.Widget.TextView.MarqueeText;

/**
 * Created by admin on 2017/2/22.
 */

public class MainFragment extends Fragment {

    @BindView(R.id.tv_main_ad)
    MarqueeText tvMainAd;
    @BindView(R.id.rly_main_content_helpmesend)
    RelativeLayout rlyMainContentHelpMeSend;
    private View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_content_sv,container,false);
        init(view);
        return view;
    }

    /*各种初始化*/
    private void init(View view){
        this.view = view;
        ButterKnife.bind(this,view);
        cicleWheelInit(view);
        initTVRunHorse("即日起，走兔跑腿全面改为线上下单！咨询电话400-662-0202");

    }
    private void cicleWheelInit(View view){
        InitLoop initLoop = new InitLoop(view);
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

    /*帮我买*/

    @OnClick(R.id.rly_main_content_helpmebuy)
    public void helpMeBuyOnClick(){
        Intent intent = new Intent(view.getContext(),HelpMeBuyActivity.class);
        startActivity(intent);
    }
    /*帮我买*/

    /*帮我送*/

    @OnClick(R.id.rly_main_content_helpmesend)
    public void helpMeSendOnClick(){
        Intent intent = new Intent(view.getContext(),HelpMeSendActivity.class);
        startActivity(intent);
    }
    /*帮我送*/
}
