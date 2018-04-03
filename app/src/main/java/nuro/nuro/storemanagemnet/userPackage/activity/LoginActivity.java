package nuro.nuro.storemanagemnet.userPackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nuro.nuro.storemanagemnet.R;
import nuro.nuro.storemanagemnet.SqlHelpdemo.SqlHelpdemo;
import nuro.nuro.storemanagemnet.dialogDemo.DialogDemo;

/**
 * 用户登陆页面
 * @time 180328
 * @author wwx
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText name_et;
    private EditText passwd_et;
    private Button login;
    private TextView register;
    SQLiteDatabase sDatabase = null;
    private SqlHelpdemo db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();
        initData();
    }

    private void initData() {
        db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
    }

    private void initView() {
        name_et = (EditText) findViewById(R.id.name_et);
        passwd_et = (EditText) findViewById(R.id.passwd_et);
        login = (Button) findViewById(R.id.login_bt);
        register = (TextView) findViewById(R.id.register_bt);

        name_et.setOnClickListener(this);
        passwd_et.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_bt:
                onLoad();
                break;
            case R.id.register_bt:
                Intent intent = new Intent();
                intent.setClass(this,UserRegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //用户登录
    private void onLoad() {
        // 定义取数据的字符串
        String userName = "";
        String userPw = "";

        String i = name_et.getText().toString();
        //		编写数据库语句
        String select_sql = "select username,password from user_info where username = '" + i + "'";
        //		执行语句
        Cursor cursor = sDatabase.rawQuery(select_sql, null);
        cursor.moveToFirst();

        // 将从数据中取出的用户名和密码赋值给两个字符串变量
        try {
            userName = cursor.getString(0);
            userPw = cursor.getString(1);
        } catch (Exception e) {
            // TODO: handle exception
            userName = "";
            userPw = "";
        }

        //		判断用户名是否为空
        if (name_et.getText().toString().equals("")) {
            DialogDemo.builder(LoginActivity.this, "错误信息", "用户名不能为空！");
        }
//		判断密码是否为空
        else if (passwd_et.getText().toString().equals("")) {
            DialogDemo.builder(LoginActivity.this, "错误信息", "密码不能为空！");
        }
//		判断用户名和密码是否正确
        else if (!(name_et.getText().toString().equals(userName) && passwd_et.getText().toString().equals(userPw))) {
            DialogDemo.builder(LoginActivity.this, "错误信息", "用户名或密码错误，请重新输入");
        }
//		全部正确跳转到操作界面
        else {
            cursor.close();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("username", userName);
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), CityPickerActivity.class);
            startActivity(intent);
        }


    }
}
