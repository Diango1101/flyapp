package com.cz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cz.MyApplication;
import com.cz.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlySearchActivity extends Activity {
    
    private Activity activity;
    
    private AutoCompleteTextView atv_content;
    private ImageView ivClear;
    
    private List<String> list = new ArrayList<>();
    
    private String city = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_search);
        
        city = this.getIntent().getStringExtra("city");
        
        this.activity = this;
        
        requestDB();
        initCtrlView();
    }
    
    public void initCtrlView() {
        atv_content = (AutoCompleteTextView) findViewById(R.id.matv_content);
        
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, list);
        atv_content.setAdapter(adapter2);
        
        atv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectCity = (String) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("city", selectCity);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        
        ivClear = (ImageView) findViewById(R.id.idClear);
        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atv_content.setText("");
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    private void requestDB() {
        MyApplication.getInstance().getDbHelper().getCity(list, city);
    }
    
}
