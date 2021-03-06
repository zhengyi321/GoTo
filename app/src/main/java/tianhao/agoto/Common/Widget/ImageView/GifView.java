package tianhao.agoto.Common.Widget.ImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.widget.ImageView;

import tianhao.agoto.R;
/*
*出处
* http://www.cnblogs.com/net168/p/4204797.html
* widget控件里和attr里面 两个文件
* create by zhyan 20170215
* */
public class GifView extends ImageView {

	private boolean isGifImage;
	private int image;
	private Movie movie;
	private long movieStart = 0;

	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//获取自定义属性isgifimage
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GifView);
		isGifImage = array.getBoolean(R.styleable.GifView_isgifimage, true);
		array.recycle();//获取自定义属性完毕后需要recycle，不然会对下次获取造成影响
		//获取ImageView的默认src属性
		image = attrs.getAttributeResourceValue( "http://schemas.android.com/apk/res/android", "src", 0);

		movie = Movie.decodeStream(getResources().openRawResource(image));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);//执行父类onDraw方法，绘制非gif的资源
		if(isGifImage){//若为gif文件，执行DrawGifImage()，默认执行
			DrawGifImage(canvas);
		}
	}

	private void DrawGifImage(Canvas canvas) {
		//获取系统当前时间
		long nowTime = android.os.SystemClock.currentThreadTimeMillis();
		if(movieStart == 0){
			//若为第一次加载，开始时间置为nowTime
			movieStart = nowTime;
		}
		if(movie != null){//容错处理
			int duration = movie.duration();//获取gif持续时间
			//如果gif持续时间为小于100，可认为非gif资源，跳出处理
			if(duration > 100){
				//获取gif当前帧的显示所在时间点
				int relTime = (int) ((nowTime - movieStart) % duration);
				movie.setTime(relTime);
				//渲染gif图像
				movie.draw(canvas, 0, 0);
				invalidate();
			}
		}
	}
}
