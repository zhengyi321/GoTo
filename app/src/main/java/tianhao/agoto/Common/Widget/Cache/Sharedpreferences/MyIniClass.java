package tianhao.agoto.Common.Widget.Cache.Sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by admin on 16/6/14.
 */
public class MyIniClass {
    public  final String MyPREFERENCES = "MyPrefs" ;

    private SharedPreferences sharedpreferences = null;
    private  Context context;
    public MyIniClass(Context FContext, String IniFileName,Boolean isRead){
        super();
        this.context = FContext;
        if(isRead) {
            if (IniFileName.isEmpty()) {
                sharedpreferences = FContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            } else {
                sharedpreferences = FContext.getSharedPreferences(IniFileName, Context.MODE_PRIVATE);
            }
        }else{
            if (IniFileName.isEmpty()) {
                sharedpreferences = FContext.getSharedPreferences(MyPREFERENCES, Context.MODE_WORLD_READABLE);
            } else {
                sharedpreferences = FContext.getSharedPreferences(IniFileName, Context.MODE_WORLD_READABLE);
            }
        }
    }

    public String ReadString(String KeyStr, String DefualtStr){

        if (sharedpreferences != null)
        {
            return sharedpreferences.getString(KeyStr, DefualtStr);
        }
        else{
            return "";
        }
    }

    public int ReadInteger(String KeyStr, int DefualtValue){

        if (sharedpreferences != null)
        {
            return sharedpreferences.getInt(KeyStr, DefualtValue);
        }
        else{
            return 0;
        }
    }

    public Long ReadLong(String KeyStr, Long DefualtValue){

        if (sharedpreferences != null)
        {
            return sharedpreferences.getLong(KeyStr, DefualtValue);
        }
        else{
            return (long)0;
        }
    }

    public boolean Readbool(String KeyStr, boolean DefualtValue){

        if (sharedpreferences != null)
        {
            return sharedpreferences.getBoolean(KeyStr, DefualtValue);
        }
        else{
            return false;
        }
    }



    public void WriteString(String KeyStr, String DefualtStr){

        if (sharedpreferences != null)
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(KeyStr, DefualtStr);
            editor.commit();
        }
    }

    public void WriteInteger(String KeyStr, int DefualtStr){

        if (sharedpreferences != null)
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(KeyStr, DefualtStr);
            editor.commit();
        }
    }

    public void WriteBoolean(String KeyStr, boolean DefualtStr){

        if (sharedpreferences != null)
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(KeyStr, DefualtStr);
            editor.commit();
        }
    }

    /**
     * 清空数据
     */
    public  void reset(final Context ctx)
    {

        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.clear();
        edit.commit();
    }

    public  void remove(String... keys)
    {

        if (keys != null)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (String key : keys)
            {
                editor.remove(key);
            }
            editor.commit();
        }
    }

    public Object getObjectInfo(String name) {
        try {
            SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String personBase64 = mSharedPreferences.getString(name, "");
            byte[] base64Bytes = Base64.decodeBase64(personBase64.getBytes());
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object object = (Object) ois.readObject();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



    public void saveObject( String name,Object object) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);

            String personBase64 = new String(Base64.encodeBase64(baos.toByteArray()));
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(name, personBase64);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     删除所有
      */
    public void allDelete(){

        SharedPreferences settings = context.getSharedPreferences("MyPrefs", context.MODE_PRIVATE);
        settings.edit().clear().commit();
    };
}
