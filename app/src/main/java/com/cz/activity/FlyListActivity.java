package com.cz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cz.MyApplication;
import com.cz.R;
import com.cz.bean.Fly;
import com.cz.util.recycle.FooterViewHolder;
import com.cz.util.recycle.HeaderViewHolder;
import com.cz.util.recycle.ItemViewHolder;
import com.cz.util.recycle.MyRecycleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class FlyListActivity extends AppCompatActivity {
    
    private String loginUsername = null;
    private long srcTime;
    
    private Activity activity;
    
    private RecyclerView mRecyclerView;
    
    private MyRecycleAdapter<Fly> recycleAdapter;
    private List<Fly> recycleList = new ArrayList<Fly>();
    
    private TextView tvDate = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_list);
        
        this.activity = this;
        
        initRecycleView();
        
        loginUsername = this.getIntent().getStringExtra("loginUsername");
        
        tvDate = (TextView) findViewById(R.id.idDate);
        
        int type = this.getIntent().getIntExtra("type", 0);
        srcTime = this.getIntent().getLongExtra("srcTime", 0);
        if (type == 1) {
            String flyId = this.getIntent().getStringExtra("flyId");
            request_db1(flyId, srcTime);
        } else if (type == 2) {
            String srcCity = this.getIntent().getStringExtra("srcCity");
            String dstCity = this.getIntent().getStringExtra("dstCity");
            request_db2(srcCity, dstCity, srcTime);
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(srcTime);
        tvDate.setText(dateFormat.format(cal.getTime()));
    }
    
    public void initRecycleView() {
        //1.获取控件
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        
        //2.设置布局方式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));  //线性布局
        //mRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));  //网格布局
        mRecyclerView.setHasFixedSize(true);
        
        //3.设置适配器
        mRecyclerView.setAdapter(recycleAdapter = new MyRecycleAdapter<Fly>(activity,
                -1, null,
                -1, null,
                R.layout.item_activity_fly_list, recycleList) {
            
            @Override
            public void convertHeader(HeaderViewHolder helper, Object obj) {
            }

            @Override
            public void convertFooter(FooterViewHolder helper, Object obj) {
            }
            
            @Override
            public void convertItem(ItemViewHolder helper, final Fly item) {
                helper.setText(R.id.idFlyId, item.getFlyId());
                helper.setText(R.id.idSrcTime, item.getHour() + ":" + item.getMinute());
                helper.setText(R.id.idFlyTime, item.getFlyTime() + "分");
                helper.setText(R.id.idPrice, item.getPrice() + "");
                
                ((Button) helper.getView(R.id.idBtn)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sql = "insert into tb_item(id, _username, flyId) values(" + srcTime + ", '" + loginUsername + "', '" + item.getFlyId() + "')";
                        MyApplication.getInstance().getDbHelper().update(sql);
                        
                        Toast.makeText(activity, "购票成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        
        recycleAdapter.setOnClickListener(new MyRecycleAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Dish dish = recycleList.get(position);
            }
        });
        
        recycleAdapter.setOnLongClickListener(new MyRecycleAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View view, int Dish) {
            
            }
        });
        
    }
    
    public void request_db1(String flyId, long srcTime) {
        recycleList.clear();
        MyApplication.getInstance().getDbHelper().getFlyById(recycleList, flyId, srcTime);
        recycleAdapter.notifyDataSetChanged();
    }
    
    public void request_db2(String srcCity, String dstCity, long srcTime) {
        recycleList.clear();
        MyApplication.getInstance().getDbHelper().getFlyByCity(recycleList, srcCity, dstCity, srcTime);
        recycleAdapter.notifyDataSetChanged();
    }
    
}
