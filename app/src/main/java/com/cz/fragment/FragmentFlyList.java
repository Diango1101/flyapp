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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class FragmentFlyList extends Fragment {
    
    private Activity activity;
    
    private RecyclerView mRecyclerView;
    
    private MyRecycleAdapter<Fly> recycleAdapter;
    private List<Fly> recycleList = new ArrayList<Fly>();
    
    private TextView tvHead;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fly_list, container, false);
        
        this.activity = this.getActivity();
        
        //loginUser = (LoginUser) this.getArguments().getSerializable("loginUser");
        
        initCtrlView(view);
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
    
    public void initCtrlView(View v) {
        ((ImageView) v.findViewById(R.id.idAdd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FlyAddActivity.class);
                startActivityForResult(intent, 1001);
            }
        });
    }
    
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
                R.layout.item_fragment_fly_list, recycleList) {
            
            @Override
            public void convertHeader(HeaderViewHolder helper, Object obj) {
            }
            
            @Override
            public void convertFooter(FooterViewHolder helper, Object obj) {
            }
            
            @Override
            public void convertItem(ItemViewHolder helper, final Fly item) {
                helper.setText(R.id.idFlyId, item.getFlyId());
                helper.setText(R.id.idSrcCity, item.getSrcCity());
                helper.setText(R.id.idDstCity, item.getDstCity());
                helper.setText(R.id.idSrcTime, item.getHour() + ":" + item.getMinute());
                helper.setText(R.id.idFlyTime, item.getFlyTime() + "分");
                helper.setText(R.id.idPrice, item.getPrice() + "");
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
            public void onLongClick(View view, int position) {
                showSingleChoiceDialog(recycleList.get(position));
            }
        });
        
    }
    
    int yourChoice;
    
    private void showSingleChoiceDialog(final Fly fly) {
        final String[] items = {"编辑", "删除"};
        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(activity);
        singleChoiceDialog.setTitle("请选择你的操作");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            switch (yourChoice) {
                                case 0:
                                    Intent intent = new Intent(activity, FlyAddActivity.class);
                                    intent.putExtra("bean", fly);
                                    startActivityForResult(intent, 1001);
                                    break;
                                case 1:
                                    String sql = "delete from tb_fly where id = " + fly.getId();
                                    MyApplication.getInstance().getDbHelper().update(sql);
                                    
                                    recycleList.remove(fly);
                                    recycleAdapter.notifyDataSetChanged();
                                    Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                });
        singleChoiceDialog.show();
    }
    
    public void request_db(String title) {
        recycleList.clear();
        MyApplication.getInstance().getDbHelper().getFly(recycleList);
        recycleAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        request_db("");
    }
}
