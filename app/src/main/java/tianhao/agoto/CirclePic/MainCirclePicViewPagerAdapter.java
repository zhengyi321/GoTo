package tianhao.agoto.CirclePic;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tianhao.agoto.R;
import tianhao.agoto.Common.Widget.ImageView.RoundImageView;

/**
 * https://github.com/sjcao/PictureRotator
 * Created by zhyan on 2017/2/10.
 * 轮播数据填充器
 */

public class MainCirclePicViewPagerAdapter extends PagerAdapter {
    private Context context; //上下文对象
    private List<String> datas;// 数据源
    private List<View> views;

    public MainCirclePicViewPagerAdapter(Context context, List<View> views, List<String> datas) {
        this.context=context;
        this.views=views;
        this.datas=datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager)container).addView(views.get(position));
        final int index=position;
        View view=views.get(position);
        try {
            String url=datas.get(position);
            RoundImageView riv_item_picture= (RoundImageView) view.findViewById(R.id.riv_main_item_picture);
            riv_item_picture.setImageResource(R.drawable.wheel1);
            /*ImageLoaderUtil.getImage(context,iv_item_picture,url,R.mipmap.ic_launcher,R.mipmap.ic_launcher);*/
            riv_item_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Toast.makeText(context,"第"+index+"张图片",Toast.LENGTH_SHORT).show();*/
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return  view;
    }
}
