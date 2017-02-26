package tianhao.agoto.Common.Widget.Cache.Sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by admin on 16/7/6.
 */
public class ProductDataBaseIniClass {

/*    MyIniClass myIniClass;
    private Activity activity;
    public ProductDataBaseIniClass(Activity activity){
        myIniClass = new MyIniClass(activity,"");
        this.activity = activity;
    }*/
/*

    */
/*
    保存商品
     *//*

    public void goodsSave(Product product){
        Log.d("MyIniClass save",product.getId());
      int num =  myIniClass.ReadInteger(product.getId(),0);
        if(num == 0){
            Log.d("商品不存在",product.getId());
            myIniClass.WriteInteger(product.getId(),1);

        }else{
            Log.d("商品不存在",product.getId());
            num++;
            myIniClass.WriteInteger(product.getId(),num);
        }
        totalNumAdd();
        totalPriceAdd(product.getPrice());
    };
    public void totalNumAdd(){
        int num = myIniClass.ReadInteger("totalNum",0);
        if(num == 0){
            myIniClass.WriteInteger("totalNum",1);
        }else {
            num++;
            myIniClass.WriteInteger("totalNum",num);
        }
    }
    public void totalPriceAdd(Double price){
        String totalPrice = myIniClass.ReadString("totalPrice","");
        if(totalPrice.isEmpty()){
            totalPrice = ""+price;
            myIniClass.WriteString("totalPrice",totalPrice);
        }else{
            Double temp = Double.parseDouble(totalPrice);
            temp += price;
            myIniClass.WriteString("totalPrice",""+temp);
        }
    }
    public  void totalNumSub(){
        int num = myIniClass.ReadInteger("totalNum",0);
        if(num == 0){
        //    myIniClass.WriteInteger("totalNum",1);
        //    myIniClass.WriteString("totalPrice","0");
        }else {
            num--;
            myIniClass.WriteInteger("totalNum",num);
        }
    }
    public void totalPriceSub(Double price,int num){
        String totalPrice = myIniClass.ReadString("totalPrice","");
        if(totalPrice.isEmpty()){
        //    totalPrice = ""+price;
       //     myIniClass.WriteString("totalPrice",totalPrice);
        }else{
            Double temp = Double.parseDouble(totalPrice);
            temp -= price;
            if(num == 0){
                myIniClass.WriteString("totalPrice", "" + 0);
            }else {
                myIniClass.WriteString("totalPrice", "" + temp);
            }
        }
    }
    */
/*
    删减商品数量
     *//*

    public void goodsDelete(Product product){
        int num =  myIniClass.ReadInteger(product.getId(),0);
        if(num == 0){
            Log.d("商品不存在",product.getId());
      //      myIniClass.WriteInteger(product.getId(),1);

        }else{
            Log.d("商品不存在",product.getId());
            num--;
            myIniClass.WriteInteger(product.getId(),num);
            totalNumSub();
            totalPriceSub(product.getPrice(),num);
        }

    };
    */
/*
    获取特定商品数量
     *//*

    public int goodsSelect(Product product){
        int num =  myIniClass.ReadInteger(product.getId(),0);
        Log.d("TAG id",""+product.getId());
        return num;
    };

    public int selectAllNum(){
        int num = myIniClass.ReadInteger("totalNum",0);
        return num;
    }

    public Double selectAllPrice(){
        String totalPrice = myIniClass.ReadString("totalPrice","");
        Double temp = 0.0;
        if(!totalPrice.isEmpty()){
            temp = Double.parseDouble(totalPrice);

        }
        return temp;
    }

    public void resetAll(){
        myIniClass.WriteInteger("totalNum",0);
        myIniClass.WriteString("totalPrice","");
        allDelete();
    }
*/


    /*
    删除所有
     */
/*    public void allDelete(){

        SharedPreferences settings = activity.getSharedPreferences("MyPrefs", activity.MODE_PRIVATE);
        settings.edit().clear().commit();
    };*/


}
