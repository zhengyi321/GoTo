package tianhao.agoto.Common.DialogPopupWindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tianhao.agoto.Common.Widget.WheelView.AbstractWheelTextAdapter;
import tianhao.agoto.Common.Widget.WheelView.OnWheelChangedListener;
import tianhao.agoto.Common.Widget.WheelView.OnWheelScrollListener;
import tianhao.agoto.Common.Widget.WheelView.WheelView;
import tianhao.agoto.R;

/**
 * Created by admin on 2017/3/10.
 */

public class HourMinTimePopup extends PopupWindow {
    private View mPopView;
    private Activity activity;
    private boolean isInitData = false;
    private String selectDay,selectHour,selectMin;
    private String currentDay;
    private int currentHour,currentMin;
    private String day;
    private int hour,min;
    private List<String> dayList,hourList,minList;
    private OnDateTimePopupListener onDateTimePopupListener;
    /*取消 确定*/
    @BindView(R.id.rly_popup_threewheel_cancel)
    RelativeLayout rlyPopupThreeWheelCancel;
    @OnClick(R.id.rly_popup_threewheel_cancel)
    public void rlyPopupThreeWheelCancelOnclick(){
        dismiss();
    }
    @BindView(R.id.rly_popup_threewheel_query)
    RelativeLayout rlyPopupThreeWheelQuery;

    @OnClick(R.id.rly_popup_threewheel_query)
    public void rlyPopupThreeWheelQueryOnclick(){
        if(onDateTimePopupListener != null){
            onDateTimePopupListener.onClick(selectDay,selectHour,selectMin);
        }
        dismiss();
    }

    /*取消 确定*/

    /*三个仿造ios的滚轮*/
    @BindView(R.id.wv_popup_threewheel_day)
    WheelView wvPopupThreeWheelDay;
    @BindView(R.id.wv_popup_threewheel_hour)
    WheelView wvPopupThreeWheelHour;
    @BindView(R.id.wv_popup_threewheel_min)
    WheelView wvPopupThreeWheelMin;
    /*三个仿造ios的滚轮*/

    /*数据填充器 adapter*/
    private CalendarTextAdapter mDayAdapter;
    private CalendarTextAdapter hourAdapter;
    private CalendarTextAdapter minAdapter;
    /*数据填充器 adapter*/
    /*字体大小*/
    private int maxTextSize = 24;
    private int minTextSize = 14;
    /*字体大小*/

    public HourMinTimePopup(final Activity activity1){
        activity = activity1;
        init();
    }
    private void init(){
        initInflateView();
        ButterKnife.bind(this,mPopView);
        if(!isInitData){
            initData();
        }
        initDay();;
        initHour();
        initMin();
        initAdapter();
        initListener();
    }
    /*界面初始化*/
    private void initInflateView(){
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView= inflater.inflate(R.layout.popupwindow_threewheel_lly, null);
        this.setContentView(mPopView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);


    }
    /*界面初始化*/
    /*初始化选择天 小时 分钟*/
    private void initData(){
        setData(getDay(),getHour(),getMin());
    }
    private void setData(String day,int hour,int min){
        selectDay = day;
        selectHour = ""+hour;
        selectMin = ""+min;
        this.currentDay = day;
        this.currentHour = hour;
        this.currentMin = min;
    }
    private String getDay(){
        return "请选择";
    }
    private int getHour(){
        return 0;
    }
    private int getMin(){
        return 0;
    }

    private int setDay(String day){
        int dayIndex = 0;

        if(!day.equals(getDay())){
            wvPopupThreeWheelHour.setVisibility(View.VISIBLE);
            wvPopupThreeWheelMin.setVisibility(View.VISIBLE);
            this.hour = 0;
            this.min = 0;

        }else{
            wvPopupThreeWheelHour.setVisibility(View.INVISIBLE);
            wvPopupThreeWheelMin.setVisibility(View.INVISIBLE);
        }
        switch (day){
            case "请选择":
                dayIndex = 0;
                break;
            case "今天":
                dayIndex++;
                break;
            case "明天":
                dayIndex++;
                break;
        }
        return dayIndex;
    }

    private int setHour(int hour){
        int hourIndex = 0;
        for(int i=0;i<24;i++){
            if(i == hour){
                return hourIndex;
            }else {
                hourIndex++;
            }
        }
        return hourIndex;
    }

    private int setMin(int min){
        int minIndex = 0;
        for(int i=0;i<60;i++){
            if(min == i){
                return minIndex;
            }else{
                minIndex++;
                continue;
            }
        }
        return minIndex;
    }
    /*初始化选择天 小时 分钟*/

    /*初始化day的选择*/
    private void initDay(){
        dayList = new ArrayList<String>();
        dayList.add("请选择");
        dayList.add("今天");
        dayList.add("明天");
    }
    /*初始化day的选择*/
    /*初始化小时 分钟*/
    private void initHour(){
        hourList = new ArrayList<String>();
        this.selectHour = "00";
        hourList.clear();
        for(int i=10;i<24;i++)
        {
            if(i <10)
            {
                hourList.add("0"+i);
            }else {
                hourList.add("" + i);
            }
        }
        hourList.add("00");
    }
    private void initMin(){
        minList = new ArrayList<String>();
        this.selectMin = "00";
        minList.clear();
        for(int i=0;i<60;i++)
        {
            if(i <10)
            {
                minList.add("0"+i);
            }else {
                minList.add("" + i);
            }
        }
    }
    /*初始化小时 分钟*/
    /*数据填充初始化*/
    private void initAdapter(){

        mDayAdapter = new CalendarTextAdapter(activity,dayList,setDay(currentDay),maxTextSize,minTextSize);
        wvPopupThreeWheelDay.setVisibleItems(6);
        wvPopupThreeWheelDay.setViewAdapter(mDayAdapter);
        wvPopupThreeWheelDay.setCurrentItem(0);
        hourAdapter = new CalendarTextAdapter(activity,hourList,setHour(currentHour),maxTextSize,minTextSize);
        wvPopupThreeWheelHour.setVisibleItems(6);
        wvPopupThreeWheelHour.setViewAdapter(hourAdapter);
        wvPopupThreeWheelHour.setCurrentItem(0);
        minAdapter = new CalendarTextAdapter(activity,minList,setMin(currentMin),maxTextSize,minTextSize);
        wvPopupThreeWheelMin.setVisibleItems(6);
        wvPopupThreeWheelMin.setViewAdapter(minAdapter);
        wvPopupThreeWheelMin.setCurrentItem(0);
    }
    /*数据填充初始化*/
    /*滚轮监听初始化*/
    private void initListener(){
        wvPopupThreeWheelDay.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentDay = mDayAdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextviewSize(currentDay,mDayAdapter);
                selectDay = currentDay;
                setDay(currentDay);
                initHour();
                hourAdapter = new CalendarTextAdapter(activity,hourList,0,maxTextSize,minTextSize);
                wvPopupThreeWheelHour.setVisibleItems(6);
                wvPopupThreeWheelHour.setViewAdapter(hourAdapter);
                wvPopupThreeWheelHour.setCurrentItem(setDay(currentDay));
            }
        });
        wvPopupThreeWheelDay.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                String currentDay = (String)mDayAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentDay,mDayAdapter);
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

            }
        });
        wvPopupThreeWheelHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentHour = hourAdapter.getItemText(wheel.getCurrentItem()).toString();
                if(currentHour != null) {
                    selectHour = currentHour;
                    setTextviewSize(currentHour, hourAdapter);

                    setHour(Integer.valueOf(currentHour));
                }
            }
        });
        wvPopupThreeWheelHour.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                String currentHour = (String)hourAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentHour,hourAdapter);
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

            }
        });



        wvPopupThreeWheelMin.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentMin = minAdapter.getItemText(wheel.getCurrentItem()).toString();
                if(currentMin != null) {
                    selectMin = currentMin;
                    setTextviewSize(currentMin, minAdapter);

                    setHour(Integer.valueOf(currentHour));
                }
            }
        });
        wvPopupThreeWheelMin.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                String currentMin = (String)minAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentMin,minAdapter);
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

            }
        });
    }



    /*滚轮监听初始化*/

    public interface OnDateTimePopupListener{
        public void onClick(String day,String hour,String min);
    }

    public void setOnDateTimePopupListener(OnDateTimePopupListener onDateTimePopupListener){
        this.onDateTimePopupListener = onDateTimePopupListener;
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }









































    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        List<String> dateList;

        protected CalendarTextAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.dialogpopup_wheel_item, NO_RESOURCE, currentItem, maxsize, minsize);
            this.dateList = list;
            setItemTextResource(R.id.tv_dialogpopup_wheel_item);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return dateList.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return dateList.get(index) + "";
        }
    }
}
