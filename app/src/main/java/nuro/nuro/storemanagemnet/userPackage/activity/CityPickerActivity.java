package nuro.nuro.storemanagemnet.userPackage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import nuro.nuro.storemanagemnet.R;
import nuro.nuro.storemanagemnet.citylinkage.CityPickerView;

/**
 * 城市选择器
 * Created by Administrator on 2018/3/28.
 */
public class CityPickerActivity extends Activity{
    private CityPickerView cityPicker;
    private TextView tv;
    public final static int RESULT_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citypicker);

        cityPicker = (CityPickerView) findViewById(R.id.citypicker);
        tv=(TextView)findViewById(R.id.tv_name);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(cityPicker.getCity_string());
                Intent intent=new Intent();
                intent.putExtra("back",cityPicker.getCity_string());
                setResult(RESULT_CODE, intent);
                finish();
            }
        });
    }
}
