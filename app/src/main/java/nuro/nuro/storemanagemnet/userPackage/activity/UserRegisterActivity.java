package nuro.nuro.storemanagemnet.userPackage.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nuro.nuro.storemanagemnet.R;
import nuro.nuro.storemanagemnet.SqlHelpdemo.SqlHelpdemo;
import nuro.nuro.storemanagemnet.citylinkage.ScrollerCity;
import nuro.nuro.storemanagemnet.dialogDemo.DialogDemo;

/**
 * 用户注册页面
 *
 * @author wwx
 * @time 180328
 */
public class UserRegisterActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText register_name_et;//用户名
    private EditText register_phone_et;//手机号
    private EditText register_passwd_et;//密码
    private EditText register_surepasswd_et;//确认密码
    private EditText repair_shopname_et;//汽修厂名称
    private EditText repair_shoparea_et;//汽修厂区域选择
    private EditText repair_scope_et;//所属区域
    private EditText repair_shopaddress_et;//经营类型
    private Spinner repair_spinner;
    private ArrayAdapter<String> repair_adapter;
    private ArrayAdapter<String> address_adapter;
    private Button submit;
    private ImageView back;
    private final static int REQUEST_CODE = 1;
    private String TAG = "UserRegisterActivity";

    SqlHelpdemo db;
    private SQLiteDatabase sDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initData();
    }

    private void initData() {
        db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        repair_adapter = new ArrayAdapter<String>(UserRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, getDataRepairSource());
        repair_spinner.setAdapter(repair_adapter);
        repair_spinner.setOnItemSelectedListener(this);
//
//        address_adapter = new ArrayAdapter<String>(UserRegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,getDataAddressSource());
//        address_spinner.setAdapter(address_adapter);
//        address_spinner.setOnItemSelectedListener(this);
    }

    private void initView() {
        register_name_et = (EditText) findViewById(R.id.register_name_et);
        register_phone_et = (EditText) findViewById(R.id.register_phone_et);
        register_passwd_et = (EditText) findViewById(R.id.register_passwd_et);
        register_surepasswd_et = (EditText) findViewById(R.id.register_surepasswd_et);
        repair_shopname_et = (EditText) findViewById(R.id.repair_shopname_et);
        repair_shoparea_et = (EditText) findViewById(R.id.repair_shoparea_et);
        repair_shopaddress_et = (EditText) findViewById(R.id.repair_shopaddress_et);
        repair_scope_et = (EditText) findViewById(R.id.repair_scope_et);
        repair_spinner = (Spinner) findViewById(R.id.repair_spinner);
        back = (ImageView) findViewById(R.id.back);
        submit = (Button) findViewById(R.id.submit);

        repair_shoparea_et.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repair_shoparea_et:
                //  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //  imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                //  ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(UserRegisterActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm.isActive()) {
//                    //imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//
//                }
                showCityPickerView();//调用CityPicker选取区域
                break;
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                onSure();
                break;
            default:
                break;
        }

    }

    //提交到数据库中
    private void onSure() {
        db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
        sDatabase = db.getWritableDatabase();
        if (register_name_et.getText().toString().equals("") || register_passwd_et.getText().toString().equals("")
                || register_surepasswd_et.getText().toString().equals("") || register_phone_et.getText().toString().equals("")
                || repair_shopname_et.getText().toString().equals("") || repair_shoparea_et.getText().toString().equals("")
                || repair_shopaddress_et.getText().toString().equals("") || repair_scope_et.getText().toString().equals("")) {
            DialogDemo.builder(UserRegisterActivity.this, "错误信息", "请填写完整信息！");

        } else if (!register_passwd_et.getText().toString().equals(register_surepasswd_et.getText().toString())) {
            DialogDemo.builder(UserRegisterActivity.this, "错误信息", "两次密码输入不一致！");
        } else {
            String register_name = register_name_et.getText().toString();
            String register_passwd = register_passwd_et.getText().toString();
            String register_phone = register_phone_et.getText().toString();
            String repair_shopname = repair_shopname_et.getText().toString();
            String repair_shoparea = repair_shoparea_et.getText().toString();
            String repair_shopaddress = repair_shopaddress_et.getText().toString();
            String repair_scope = repair_scope_et.getText().toString();

            Log.i(TAG, "name:" + register_name + "  passwd:" + register_passwd + " phone:" + register_phone);

            // 查询语句
            String selectStr = "select username from user_info";
            Cursor select_cursor = sDatabase.rawQuery(selectStr, null);
            select_cursor.moveToFirst();
            String stringname = null;
            String stringphone = null;
            do {
                try {
                    stringname = select_cursor.getString(0);
                    stringphone = select_cursor.getString(2);
                } catch (Exception e) {
                    // TODO: handle exception
                    stringname = "";
                    stringphone = "";
                }
                if (stringname.equals(register_name)) {
                    DialogDemo.builder(UserRegisterActivity.this, "错误信息", "用户名已存在，请另设用户名");
                    select_cursor.close();
                    break;

                }
                if (stringphone.equals(register_phone)) {
                    DialogDemo.builder(UserRegisterActivity.this, "错误信息", "手机号已存在，请直接登录");
                    select_cursor.close();
                    break;

                }
            } while (select_cursor.moveToNext());

            // 没有重名或重手机号注册开始
            if (!(stringname.equals(register_name) || stringphone.equals(register_phone))) {
                // 定义ID
                int id = 0;
                String select = "select max(_id) from user_info";
                Cursor seCursor = sDatabase.rawQuery(select, null);
                try {
                    seCursor.moveToFirst();
                    id = Integer.parseInt(seCursor.getString(0));
                    id += 1;
                } catch (Exception e) {
                    // TODO: handle exception
                    id = 0;
                }
                sDatabase.execSQL("insert into user_info values('" + id + "','" + register_name + "','" + register_passwd + "','"
                        + "','" + register_phone + "','" + repair_shopname + "','" + repair_shoparea + "','" + repair_shopaddress + "','"
                        + "','" + repair_scope + "')");
                DialogDemo.builder(UserRegisterActivity.this, "提示", "注册成功，请返回登录界面登录");
                seCursor.close();
            }
        }
    }

    //城市列表选择器
    private void showCityPickerView() {

//此方法为：跳转到另一个activity中进行选择，从另外activity中返回结果，搭配onActivityResult()函数
//        Intent intent = new Intent();
//        intent.setClass(this,CityPickerActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);
        AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
        View view = LayoutInflater.from(UserRegisterActivity.this).inflate(R.layout.addressdialog, null);
        builder.setView(view);
        LinearLayout addressdialog_linearlayout = (LinearLayout) view.findViewById(R.id.addressdialog_linearlayout);
        final ScrollerCity provincePicker = (ScrollerCity) view.findViewById(R.id.province);
        final ScrollerCity cityPicker = (ScrollerCity) view.findViewById(R.id.city);
        final ScrollerCity counyPicker = (ScrollerCity) view.findViewById(R.id.couny);
        final AlertDialog dialog = builder.show();
        addressdialog_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_shoparea_et.setText(provincePicker.getSelectedText() + cityPicker.getSelectedText() + counyPicker.getSelectedText());
                Log.i("kkkk", provincePicker.getSelectedText() + cityPicker.getSelectedText() + counyPicker.getSelectedText());
                dialog.dismiss();
            }
        });
    }


    public List<String> getDataRepairSource() {
        List<String> list = new ArrayList<String>();
        list.add("轿车SUV");
        list.add("客车");
        list.add("重汽轻卡");
        return list;
    }

    //两个不同activity之间进行数据传递
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == CityPickerActivity.RESULT_CODE) {
                Bundle bundle = data.getExtras();
                String str = bundle.getString("back");
                repair_shoparea_et.setText(str);
                Log.i(TAG, "str:" + str);
                Toast.makeText(UserRegisterActivity.this, str, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.repair_spinner:
                String repairitem = repair_spinner.getItemAtPosition(position).toString();
                repair_scope_et.setText(repairitem);
                break;
            default:
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        repair_scope_et.setText("");
    }
}
