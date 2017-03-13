package tianhao.agoto.NetWorks;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Observer;
import tianhao.agoto.Bean.BaseBean;
import tianhao.agoto.Bean.HelpMeBuyBean;
import tianhao.agoto.Bean.UserLogin;
import tianhao.agoto.Bean.UserReg;

/**
 * Created by admin on 2017/2/21.
 */

public class HelpMeSendBuyNetWorks extends BaseNetWork{

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

        /*订单提交*/
        @GET("orders/appsave.do")
        Observable<HelpMeBuyBean> orderUpdate(@QueryMap Map<String, String> param);
        /*订单提交*/
        /*支付*/
        @GET("addrs/apppaysave.do")
        Observable<BaseBean> orderPay(@Query("paystatusPaystatus") int payStatus,@Query("orderNo") String orderNo);
        /*支付*/

    /*        @GET("orders/appsave.do")
        Observable<HelpMeBuyBean> orderUpdate(@Query("userUsid") String usid, @Query("clientaddrAddr") String address, @Query("clientaddrAddr1") String address1, @Query("orderHeight") String orderHeight, @Query("orderName") String orderName, @Query("orderTimeliness") String orderTimeliness, @Query("orderRemark") String orderRemark, @Query("orderOrderprice") String orderOrderprice, @Query("orderMileage") String orderMileage, @Query("clientaddrArea") String clientaddrArea, @Query("detailsGoodsname") String detailsGoodsname
    */


    }
    /*订单提交*/
    public  void orderUpdate(@QueryMap Map<String, String> param, Observer<HelpMeBuyBean> observer){
        setSubscribe(service.orderUpdate( param),observer);
    }
    /*订单提交*/
    /*订单支付*/
    public void orderPay( int payStatus, String orderNo,Observer<BaseBean> observer){
        setSubscribe(service.orderPay(payStatus,orderNo),observer);
    }
    /*订单支付*/
    /*public  void orderUpdate( String usid, String address, String address1, String orderHeight, String orderName, String orderTimeliness, String orderRemark, String orderOrderprice, String orderMileage, String clientaddrArea, String detailsGoodsname, Observer<HelpMeBuyBean> observer){
        setSubscribe(service.orderUpdate(  usid,  address,  address1,  orderHeight,  orderName,  orderTimeliness,  orderRemark,  orderOrderprice,  orderMileage,  clientaddrArea,  detailsGoodsname),observer);
    }*/


}
