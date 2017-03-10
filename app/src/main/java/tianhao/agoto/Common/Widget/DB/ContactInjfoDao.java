package tianhao.agoto.Common.Widget.DB;

/**
 * Created by admin on 2017/2/23.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 *  ContactInjfoDao  数据库操作类  dao后缀的都是数据库操作类
 *http://www.jianshu.com/p/b5ff80941fab
 *  我们这里的每一个 增删改查 的方法都通过getWritableDatabase()去实例化了一个数据库,这里必须这么做
 *  不客气抽取 成为一个成员变量, 否则报错,若是觉得麻烦可以通过定义方法来置为null和重新赋值
 *
 *  —— 其实dao类在这里做得事情特别简单：
 *  1、定义一个构造方法，利用这个方法去实例化一个  数据库帮助类
 *  2、编写dao类的对应的 增删改查 方法。
 *
 */
public class ContactInjfoDao {

    private MyDBHelper mMyDBHelper;

    /**
     * dao类需要实例化数据库Help类,只有得到帮助类的对象我们才可以实例化 SQLiteDatabase
     * @param context
     */
    public ContactInjfoDao(Context context) {
        mMyDBHelper=new MyDBHelper(context);
    }

    // 将数据库打开帮帮助类实例化，然后利用这个对象
    // 调用谷歌的api去进行增删改查

    // 增加的方法吗，返回的的是一个long值
    public long addDate(String key,String values){
        // 增删改查每一个方法都要得到数据库，然后操作完成后一定要关闭
        // getWritableDatabase(); 执行后数据库文件才会生成
        // 数据库文件利用DDMS可以查看，在 data/data/包名/databases 目录下即可查看
        SQLiteDatabase sqLiteDatabase =  mMyDBHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(key,values);

        // 返回,显示数据添加在第几行
        // 加了现在连续添加了3行数据,突然删掉第三行,然后再添加一条数据返回的是4不是3
        // 因为自增长
        long rowid=sqLiteDatabase.insert("contactinfo",null,contentValues);

        sqLiteDatabase.close();
        return rowid;
    }


    // 删除的方法，返回值是int
    public int deleteDate(String name){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        int deleteResult = sqLiteDatabase.delete("contactinfo", "name=?", new String[]{name});
        sqLiteDatabase.close();
        return deleteResult;
    }

    /**
     * 修改的方法
     * @param key
     * @param value
     * @return
     */
    public int updateData(String key,String value){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(key, value);
        int updateResult = sqLiteDatabase.update("contactinfo", contentValues, "name=?", new String[]{key});
        sqLiteDatabase.close();
        return updateResult;
    }

    /**
     * 查询的方法（查找电话）
     * @param key value colum
     * @return
     */
    public String queryDate(String key/*,String value*//*,String column,String columnValues*/){
        String phone = null;
        try {
            SQLiteDatabase readableDatabase = mMyDBHelper.getReadableDatabase();
            /*ContentValues cvValues = new ContentValues();
            cvValues.put(key,value);*///key为字段名，value为值
            // 查询比较特别,涉及到 cursor
            Cursor cursor = readableDatabase.query("contactinfo", new String[]{key}/*new String[]{"phone"}*/,null/* column+"=?"*//*"name=?"*/, null/*new String[]{columnValues}*/, null, null, null);
            if(cursor.moveToNext()){
                phone=cursor.getString(0);
            }

            cursor.close(); // 记得关闭 corsor
            readableDatabase.close(); // 关闭数据库
        }catch (Exception e){

        }
        return phone;
    }


}