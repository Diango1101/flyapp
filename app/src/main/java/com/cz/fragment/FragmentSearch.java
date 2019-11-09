package com.cz.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cz.R;
import com.cz.activity.FlyListActivity;
import com.cz.activity.FlySearchActivity;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class FragmentSearch extends Fragment {
    
    private String loginUsername = null;
    
    private Activity activity;
    
    private EditText flyId;
    
    private TextView atvSrcCity, atvDstCity;
    
    private TextView rb01, rb02;
    private LinearLayout layout1, layout2;
    
    private TextView tvDate1, tvDate2;
    private Calendar ca1 = Calendar.getInstance();
    private Calendar ca2 = Calendar.getInstance();
    
    private Button btnSearch1, btnSearch2;
    
    private int mYear, mMonth, mDay;
    private int dateFlag = 1;
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }
                
            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }
                
            }
            if (dateFlag == 1) {
                tvDate1.setText(days);
                ca1.set(mYear, mMonth, mDay);
            } else if (dateFlag == 2) {
                tvDate2.setText(days);
                ca2.set(mYear, mMonth, mDay);
            }
        }
    };
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        
        this.activity = this.getActivity();
        
        ca1.set(Calendar.HOUR_OF_DAY, 0);
        ca1.set(Calendar.MINUTE, 0);
        ca1.set(Calendar.SECOND, 0);
        ca1.set(Calendar.MILLISECOND, 0);
        
        ca2.set(Calendar.HOUR_OF_DAY, 0);
        ca2.set(Calendar.MINUTE, 0);
        ca2.set(Calendar.SECOND, 0);
        ca2.set(Calendar.MILLISECOND, 0);
        
        loginUsername = this.getArguments().getString("loginUsername");
        
        initView(view);
        
        return view;
    }
    
    public void initView(View v) {
        flyId = (EditText) v.findViewById(R.id.idFlyId);
        tvDate1 = (TextView) v.findViewById(R.id.idSrcDate1);
        tvDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear = ca1.get(Calendar.YEAR);
                mMonth = ca1.get(Calendar.MONTH);
                mDay = ca1.get(Calendar.DAY_OF_MONTH);
                dateFlag = 1;
                
                new DatePickerDialog(activity, onDateSetListener, mYear, mMonth, mDay).show();
            }
        });
        
        btnSearch1 = (Button) v.findViewById(R.id.idBtnSearch1);
        btnSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calCheck(ca1) == false) {
                    Toast.makeText(activity, "请选择明日及以后日期购票", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(activity, FlyListActivity.class);
                intent.putExtra("loginUsername", loginUsername);
                intent.putExtra("type", 1);
                intent.putExtra("flyId", flyId.getEditableText().toString());
                intent.putExtra("srcTime", ca1.getTimeInMillis());
                startActivity(intent);
            }
        });
        
        atvSrcCity = (TextView) v.findViewById(R.id.idSrcCity);
        atvSrcCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FlySearchActivity.class);
                intent.putExtra("city", "srcCity");
                activity.startActivityForResult(intent, 1001);
            }
        });
        atvDstCity = (TextView) v.findViewById(R.id.idDstCity);
        atvDstCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FlySearchActivity.class);
                intent.putExtra("city", "dstCity");
                activity.startActivityForResult(intent, 1002);
            }
        });
        tvDate2 = (TextView) v.findViewById(R.id.idSrcDate2);
        tvDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear = ca2.get(Calendar.YEAR);
                mMonth = ca2.get(Calendar.MONTH);
                mDay = ca2.get(Calendar.DAY_OF_MONTH);
                dateFlag = 2;
                
                new DatePickerDialog(activity, onDateSetListener, mYear, mMonth, mDay).show();
            }
        });
        
        btnSearch2 = (Button) v.findViewById(R.id.idBtnSearch2);
        btnSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calCheck(ca2) == false) {
                    Toast.makeText(activity, "请选择明日及以后日期购票", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(activity, FlyListActivity.class);
                intent.putExtra("loginUsername", loginUsername);
                intent.putExtra("type", 2);
                intent.putExtra("srcCity", atvSrcCity.getText().toString());
                intent.putExtra("dstCity", atvDstCity.getText().toString());
                intent.putExtra("srcTime", ca2.getTimeInMillis());
                startActivity(intent);
            }
        });
        
        rb01 = (TextView) v.findViewById(R.id.id_rb01);
        rb02 = (TextView) v.findViewById(R.id.id_rb02);
        
        rb01.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                switchView(1);
            }
        });
        rb02.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                switchView(2);
            }
        });
        
        layout1 = (LinearLayout) v.findViewById(R.id.id1);
        layout2 = (LinearLayout) v.findViewById(R.id.id2);
        
        switchView(
                1);
        
    }
    
    public boolean calCheck(Calendar calOld) {
        Calendar cal = Calendar.getInstance();
        
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        //cal.add(Calendar.DAY_OF_MONTH, 1);
        
        if (calOld.after(cal)) {
            return true;
        } else {
            return false;
        }
    }
    
    public void switchView(int type) {
        switch (type) {
            case 1: {
                rb02.setBackgroundResource(R.drawable.zx_bottom_line_clear);
                rb01.setBackgroundResource(R.drawable.zx_bottom_line);
                
                layout2.setVisibility(View.INVISIBLE);
                layout1.setVisibility(View.VISIBLE);
            }
            break;
            case 2: {
                rb01.setBackgroundResource(R.drawable.zx_bottom_line_clear);
                rb02.setBackgroundResource(R.drawable.zx_bottom_line);
                
                layout1.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.VISIBLE);
            }
            break;
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    atvSrcCity.setText(data.getStringExtra("city"));
                    break;
                case 1002:
                    atvDstCity.setText(data.getStringExtra("city"));
                    break;
            }
        }
    }
}
