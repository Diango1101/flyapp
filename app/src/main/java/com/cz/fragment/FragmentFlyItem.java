package com.cz.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cz.MyApplication;
import com.cz.R;
import com.cz.activity.AdminActivity;
import com.cz.activity.FlyAddActivity;
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

public class FragmentFlyItem extends Fragment {
    
    private String loginUsername = null;
    private Activity activity;
    
    private RecyclerView mRecyclerView;
    
    private MyRecycleAdapter<Fly> recycleAdapter;
    private List<Fly> recycleList = new ArrayList<Fly>();
    
    private TextView tvHead;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fly_item, container, false);
        
        this.activity = this.getActivity();
        
        loginUsername = this.getArguments().getString("loginUsername");
        
//        initCtrlView(view);
        initRecycleView(view);
        
        request_db("");
        
        return view;
    }
    
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
            request_db("");
        }
    }
    
//    public void initCtrlView(View v) {
//        ((ImageView) v.findViewById(R.id.idAdd)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, FlyAddActivity.class);
//                startActivityForResult(intent, 1001);
//            }
//        });
//    }
    
    public void initRecycleView(View v) {
        //1.获取控件
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        
        //2.设置布局方式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));  //线性布局
        //mRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));  //网格布局
        mRecyclerView.setHasFixedSize(true);
        
        //3.设置适配器
        mRecyclerView.setAdapter(recycleAdapter = new MyRecycleAdapter<Fly>(activity,
                -1, null,
                -1, null,
                R.layout.item_fragment_fly_item, recycleList) {
            
            @Override
            public void convertHeader(HeaderViewHolder helper, Object obj) {
            }
            
            @Override
            public void convertFooter(FooterViewHolder helper, Object obj) {
            }
            
            @Override
            public void convertItem(ItemViewHolder helper, final Fly item) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(item.getId());
                
                helper.setText(R.id.idFlyId, item.getFlyId());
                helper.setText(R.id.idSrcCity, item.getSrcCity());
                helper.setText(R.id.idDstCity, item.getDstCity());
                helper.setText(R.id.idSrcTime, dateFormat.format(cal.getTime()) + " " + item.getHour() + ":" + item.getMinute());
                helper.setText(R.id.idFlyTime, item.getFlyTime() + "分");
                helper.setText(R.id.idPrice, item.getPrice() + "");
            }
        });
        
        recycleAdapter.setOnClickListener(new MyRecycleAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
            }
        });
        
        recycleAdapter.setOnLongClickListener(new MyRecycleAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
            }
        });
        
    }
    
    public void request_db(String title) {
        recycleList.clear();
        MyApplication.getInstance().getDbHelper().getFlyItem(recycleList, loginUsername);
        recycleAdapter.notifyDataSetChanged();
    }
    
}
