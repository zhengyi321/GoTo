package tianhao.agoto.Bean;

/**
 * Created by admin on 2017/3/12.
 */

public class OrderDetail {
    /*用户id*/
    private String userUsid = "";
    /*用户id*/
    /*寄件信息:姓名＋详细地址＋联系方式*/
    private String clientaddrAddr= "";
    /*寄件信息:姓名＋详细地址＋联系方式*/
    /*收件信息:姓名＋详细地址＋联系方式*/
    private String clientaddrAddr1= "";
    /*收件信息:姓名＋详细地址＋联系方式*/
    /*重量*/
    private String orderHeight= "";
    /*重量*/
    /*物品种类*/
    private String orderName= "";
    /*物品种类*/
    /*收件时间*/
    private String orderTimeliness= "";
    /*收件时间*/
    /*备注*/
    private String orderRemark= "";
    /*备注*/
    /*价格*/
    private String orderOrderprice= "";
    /*价格*/
    /*里程*/


    private String orderMileage= "";
    /*里程*/
    /*区域(镇名)*/
    private String clientaddrArea= "";
    /*区域(镇名)*/
    /*清单*/
    private String detailsGoodsname= "";
    /*清单*/

    public String getUserUsid() {
        return userUsid;
    }

    public void setUserUsid(String userUsid) {
        this.userUsid = userUsid;
    }

    public String getClientaddrAddr() {
        return clientaddrAddr;
    }

    public void setClientaddrAddr(String clientaddrAddr) {
        this.clientaddrAddr = clientaddrAddr;
    }

    public String getClientaddrAddr1() {
        return clientaddrAddr1;
    }

    public void setClientaddrAddr1(String clientaddrAddr1) {
        this.clientaddrAddr1 = clientaddrAddr1;
    }

    public String getOrderHeight() {
        return orderHeight;
    }

    public void setOrderHeight(String orderHeight) {
        this.orderHeight = orderHeight;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderTimeliness() {
        return orderTimeliness;
    }

    public void setOrderTimeliness(String orderTimeliness) {
        this.orderTimeliness = orderTimeliness;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderOrderprice() {
        return orderOrderprice;
    }

    public void setOrderOrderprice(String orderOrderprice) {
        this.orderOrderprice = orderOrderprice;
    }

    public String getOrderMileage() {
        return orderMileage;
    }

    public void setOrderMileage(String orderMileage) {
        this.orderMileage = orderMileage;
    }

    public String getClientaddrArea() {
        return clientaddrArea;
    }

    public void setClientaddrArea(String clientaddrArea) {
        this.clientaddrArea = clientaddrArea;
    }

    public String getDetailsGoodsname() {
        return detailsGoodsname;
    }

    public void setDetailsGoodsname(String detailsGoodsname) {
        this.detailsGoodsname = detailsGoodsname;
    }
}
