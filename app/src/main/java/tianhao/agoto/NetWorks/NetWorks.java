package tianhao.agoto.NetWorks;


import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tianhao.agoto.Utils.RetrofitUtils;

/**
 *
 * 出处：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0504/4208.html
 * 类名称：NetWorks
 * 创建人：zhyan
 * 创建时间：2016-5-18 14:57:11
 * 类描述：网络请求的操作类
 */
public class NetWorks extends RetrofitUtils {


/*
    protected  final NetService service = getRetrofit().create(NetService.class);*/


/*    private interface NetService {
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";*/
     /*
        //POST请求
        @FormUrlEncoded
        @POST("bjws/app.user/login")
        Observable<Verification> getVerfcationCodePost(@Field("tel") String tel, @Field("password") String pass);

        //POST请求
        @FormUrlEncoded
        @POST("bjws/app.user/login")
        Observable<Verification> getVerfcationCodePostMap(@FieldMap Map<String, String> map);

        //GET请求
        @GET("bjws/app.user/login")
        Observable<Verification> getVerfcationGet(@Query("tel") String tel, @Query("password") String pass);


        //GET请求，设置缓存
   *//*     @Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
        @GET("bjws/app.user/login")
        Observable<Verification> getVerfcationGetCache(@Query("tel") String tel, @Query("password") String pass);
*//*
        @Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
        @GET("bjws/app.user/login")
        Observable<Verification> getVerfcationGetCache(@Query("tel") String tel, @Query("password") String pass);


        @Headers("Cache-Control: public," + CACHE_CONTROL_NETWORK)
        @GET("bjws/app.menu/getMenu")
        Observable<MenuBean> getMainMenu();


        //POST请求
        @GET("data/sk/101110101.html")
        Observable<WeatherBean> getWeatherGet();

    }

    //POST请求
    public  void verfacationCodePost(String tel, String pass,Observer<Verification> observer){
        setSubscribe(service.getVerfcationCodePost(tel, pass),observer);
    }


    //POST请求参数以map传入
    public  void verfacationCodePostMap(Map<String, String> map,Observer<Verification> observer) {
       setSubscribe(service.getVerfcationCodePostMap(map),observer);
    }

    //Get请求设置缓存
    public  void verfacationCodeGetCache(String tel, String pass,Observer<Verification> observer) {
        setSubscribe(service.getVerfcationGetCache(tel, pass),observer);
    }

    //Get请求
    public  void verfacationCodeGet(String tel, String pass,Observer<Verification> observer) {
        setSubscribe(service.getVerfcationGet(tel, pass),observer);
    }

    //Get请求
    public  void verfacationCodeGetsub(String tel, String pass, Observer<Verification> observer) {
        setSubscribe(service.getVerfcationGet(tel, pass),observer);
    }

    //Get请求
    public  void Getcache( Observer<MenuBean> observer) {
        setSubscribe(service.getMainMenu(),observer);
    }

    //Post请求
    public  void weatherPost(Observer<WeatherBean> observable){
        setSubscribe(service.getWeatherGet(),observable);
    }*/
    /**
     * 插入观察者
     * @param observable
     * @param observer
     * @param <T>
     */
/*    public  <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }*/

}
