package tianhao.agoto.NetWorks;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import tianhao.agoto.Bean.MyOrderBean;
import tianhao.agoto.Bean.UserReg;

/**
 * Created by admin on 2017/2/21.
 */

public class OrderNetWorks extends BaseNetWork{

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";
        /*用户查询订单列表*/
        //GET请求
        @GET("/orders/appfind.do")
        Observable<List<MyOrderBean>> getMyOrderList(@Query("userUsid") String userUsid);
        /*用户查询订单列表*/
  /*      //用户登录
        @GET("users/applogin.do")
        Observable<UserLogin> userLogin(@Query("userName") String tel, @Query("userPassword") String pass);

        *//*用户退出*//*
        @GET("users/appexit.do")
        Observable<BaseBean> userExit();
        *//*用户退出*/
    }

    public  void getMyOrderList(String userUsid, Observer<List<MyOrderBean>> observer){
        setSubscribe(service.getMyOrderList(userUsid),observer);
    }
/*
    public  void userLoginToNet(String userName, String userPassword, Observer<UserLogin> observer){
        setSubscribe(service.userLogin(userName, userPassword),observer);
    }
    public void userExit(Observer<BaseBean> observer){
        setSubscribe(service.userExit(),observer);
    }
*/

}
