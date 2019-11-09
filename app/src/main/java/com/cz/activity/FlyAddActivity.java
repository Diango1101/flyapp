package com.cz.activity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cz.MyApplication;
import com.cz.R;
import com.cz.bean.Fly;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FlyAddActivity extends Activity {
    
    private Activity activity;
    
    private EditText etFlyId, etSrcCity, etDstCity, etFlyTime, etPrice;
    private TextView etSrcTime;
    private Button btnAdd;
    
    private Fly fly = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_add);
        
        fly = (Fly) this.getIntent().getSerializableExtra("bean");
        
        this.activity = this;
        
        initCtrlView();
    }
    
    public void initCtrlView() {
        etFlyId = (EditText) findViewById(R.id.idFlyId);
        etSrcCity = (EditText) findViewById(R.id.idSrcCity);
        etDstCity = (EditText) findViewById(R.id.idDstCity);
        etSrcTime = (TextView) findViewById(R.id.idSrcTime);
        
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        etSrcTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(
                        FlyAddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                etSrcTime.setText(hourOfDay + ":" + minute);
                            }
                        },
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        true
                ).show();
            }
        });
        etFlyTime = (EditText) findViewById(R.id.idFlyTime);
        etPrice = (EditText) findViewById(R.id.idPrice);
        
        btnAdd = (Button) findViewById(R.id.idBtnAdd);
        if (fly != null) {
            btnAdd.setText("修改");
            
            etFlyId.setText(fly.getFlyId());
            etSrcCity.setText(fly.getSrcCity());
            etDstCity.setText(fly.getDstCity());
            etSrcTime.setText(fly.getHour() + ":" + fly.getMinute());
            etFlyTime.setText(fly.getFlyTime() + "");
            etPrice.setText(fly.getPrice() + "");
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srcTime = etSrcTime.getText().toString();
                String hour = srcTime.split(":")[0];
                String minute = srcTime.split(":")[1];
                String sql = "";
                if (fly != null) {
                    sql = "update tb_fly set srcCity = '" + etSrcCity.getEditableText().toString()
                            + "', flyId = '" + etFlyId.getEditableText().toString()
                            + "', dstCity = '" + etDstCity.getEditableText().toString()
                            + "', srcHour = " + hour
                            + ", srcMinute =" + minute
                            + ", flyTime = " + etFlyTime.getEditableText().toString()
                            + ", price = " + etPrice.getEditableText().toString()
                            + " where id = " + fly.getId() + "";
                    Toast.makeText(FlyAddActivity.this, "信息添加修改", Toast.LENGTH_SHORT).show();
                } else {
                    String id = Calendar.getInstance().getTimeInMillis() + "";
                    
                    sql = "insert into tb_fly(id, flyId, srcCity, dstCity, srcHour, srcMinute, flyTime, price) values("
                            + id
                            + ", '" + etFlyId.getEditableText().toString()
                            + "', '" + etSrcCity.getEditableText().toString()
                            + "', '" + etDstCity.getEditableText().toString()
                            + "', " + hour
                            + ", " + minute
                            + ", " + etFlyTime.getEditableText().toString()
                            + ", " + etPrice.getEditableText().toString()
                            + ");";
                    Toast.makeText(FlyAddActivity.this, "信息添加成功", Toast.LENGTH_SHORT).show();
                }
                MyApplication.getInstance().getDbHelper().update(sql);
                
                finish();
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
}
