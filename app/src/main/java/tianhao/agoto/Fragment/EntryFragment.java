package tianhao.agoto.Fragment;

/**
 * 过渡页
 * https://github.com/javajavadog/guideshow
 * Created by admin on 2017/3/13.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tianhao.agoto.Common.Widget.SlideShowView.LoopViewPager;
import tianhao.agoto.R;

public class EntryFragment extends Fragment{



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_splash_viewpage_item, null);
        init(v);
        return v;
    }

    private void init(View v){
        ButterKnife.bind(this,v);
    }
}