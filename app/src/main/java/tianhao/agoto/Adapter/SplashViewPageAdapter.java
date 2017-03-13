package tianhao.agoto.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mob.tools.gui.ViewPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tianhao.agoto.Activity.MainActivity;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/3/13.
 */

public class SplashViewPageAdapter extends PagerAdapter {

    private List<ImageView> dataList ;
    private Activity activity;
    public SplashViewPageAdapter(Activity activity1,List<ImageView> dataList1){
        activity = activity1;
        dataList = dataList1;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        /*Widget widget;


        View v = LayoutInflater.from(activity).inflate(R.layout.activity_splash_viewpage_item,null);
        widget = new Widget(v);

        switch (position){
            case 0:
                widget.ivSplashVpItem.setImageResource(R.drawable.splash1);
                break;
            case 1:
                widget.ivSplashVpItem.setImageResource(R.drawable.splash2);
                break;
            case 2:
                widget.ivSplashVpItem.setImageResource(R.drawable.splash3);
                break;
        }
        if(position == 2){
            widget.ivSplashVpItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                }
            });
        }*/
        ImageView v= dataList.get(position);
        parent.addView(v,0);
            return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((dataList.get(position)));
    }
    public class Widget{
        @BindView(R.id.iv_splash_vp_item)
        ImageView ivSplashVpItem;
        public Widget(View view){
            ButterKnife.bind(this,view);
        }
    }

}
