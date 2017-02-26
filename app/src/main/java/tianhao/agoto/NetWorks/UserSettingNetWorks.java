package tianhao.agoto.NetWorks;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import tianhao.agoto.Bean.UserLogin;
import tianhao.agoto.Bean.UserReg;
import tianhao.agoto.Utils.ContentUtils;

/**
 * Created by admin on 2017/2/21.
 */

public class UserSettingNetWorks extends BaseNetWork{

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";
        /*用户注册*/
        //GET请求
        @GET("appusers/appreg.do")
        Observable<UserReg> userReg(@Query("userName") String tel, @Query("userPassword") String pass);

        //用户登录
        @GET("appusers/applogin.do")
        Observable<UserLogin> userLogin(@Query("userName") String tel, @Query("userPassword") String pass);

    }

    public  void userRegToNet(String userName, String userPassword, Observer<UserReg> observer){
        setSubscribe(service.userReg(userName, userPassword),observer);
    }
    public  void userLoginToNet(String userName, String userPassword, Observer<UserLogin> observer){
        setSubscribe(service.userLogin(userName, userPassword),observer);
    }

}
