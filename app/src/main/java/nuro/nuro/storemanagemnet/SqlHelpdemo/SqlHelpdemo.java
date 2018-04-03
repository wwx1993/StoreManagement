package nuro.nuro.storemanagemnet.SqlHelpdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 所有数据库创建
 * Created by Administrator on 2018/3/28.
 */
public class SqlHelpdemo extends SQLiteOpenHelper {
    /*
      * 创建语句
      */
    // 创建用户表  id 用户名 职工号 手机号 密码 确认密码（空缺） 汽修厂名称 汽修厂区域 汽修厂详细地址 地址所属区域 经营范围
    String createUserTable = "create table user_info(_id int auto_increment,username char(20),password char(20),"
            + "usertel char(20),shopname char(40),shoparea char(40),shopaddress char(40),"
            +"repairscope char(20),primary key('_id'));";

    // 定义用户表插入语句
    String insertStr = "insert into user_info(_id,username,password,usertel,shopname,shoparea,shopaddress,repairscope) values(?,?,?,?,?,?,?,?)";

    public SqlHelpdemo(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
// 设置ID
        int _id = 0;
        // 创建用户表，用商品表，顾客表，入库表。出库表
        arg0.execSQL(createUserTable);

        // 插入测试data
        String[] insertValue = {"0","wwx","930820","18855971291","小小汽修厂","浙江省杭州市余杭区","管家塘村大华海派风景","小型汽车"};
        arg0.execSQL(insertStr, insertValue);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
