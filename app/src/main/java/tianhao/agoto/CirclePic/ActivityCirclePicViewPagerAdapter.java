package tianhao.agoto.CirclePic;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import tianhao.agoto.R;
import tianhao.agoto.Utils.ImageUtils;

/**
 * Created by zhyan on 2017/2/14.
 */

public class ActivityCirclePicViewPagerAdapter extends PagerAdapter {
    private Context context; //上下文对象
    private List<String> datas;// 数据源
    private List<View> views;

    public ActivityCirclePicViewPagerAdapter(Context context, List<View> views, List<String> datas) {
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
    /*毛玻璃化*/
    private Bitmap initGlassBg(){
       /* Drawable drawable = getResources().getDrawable(R.drawable.wheel1);*/
        /*Bitmap bgBitMap = imageUtils.drawableToBitmap(drawable);
        Bitmap bitmap = blurUtil.doBlur(bgBitMap,70,false);*/
        ImageUtils imageUtils = new ImageUtils();
        Bitmap bitmap = imageUtils.decodeSampledBitmapFromResource(context.getResources(),R.drawable.splash,100,100);
        return bitmap;
/*        Drawable drawableBg = new BitmapDrawable(bitmap);
        activityActivityLLy.setBackground(drawableBg);*/
        /*activityActivityLLy.setB*/
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager)container).addView(views.get(position));
        final int index=position;
        View view=views.get(position);
        try {
            String url=datas.get(position);
            /*RoundImageView riv_item_picture= (RoundImageView) view.findViewById(R.id.riv_main_item_picture);*/
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_activity_activity_content_bg);
            /*RoundedImageView roundedImageView = (RoundedImageView) view.findViewById(R.id.riv_activity_item_pic) ;*/
            /*roundedImageView.setImageResource(R.drawable.splash);*/
            imageView.setImageBitmap(initGlassBg());

            /*riv_item_picture.setImageResource(R.drawable.wheel1);*/
            /*ImageLoaderUtil.getImage(context,iv_item_picture,url,R.mipmap.ic_launcher,R.mipmap.ic_launcher);*/
           /* riv_item_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*Toast.makeText(context,"第"+index+"张图片",Toast.LENGTH_SHORT).show();*//*
                }
            });*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return  view;
    }
}