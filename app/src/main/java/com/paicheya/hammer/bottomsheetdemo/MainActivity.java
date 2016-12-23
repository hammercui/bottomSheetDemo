package com.paicheya.hammer.bottomsheetdemo;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paicheya.hammer.bottomsheetdemo.adapter.BottomListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private MainActivity mainActivity;
    BottomSheetBehavior bottomSheetBehavior;
    BottomSheetDialog   bottomSheetDialog;
    BottomSheetBehavior bottomSheetDialogBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity  = this;

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showBottomSheet();
            }
        });
        findViewById(R.id.button2).setOnClickListener(view->{
                bottomSheetDialog.show();
                bottomSheetDialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        LinearLayout llBottomLayout = (LinearLayout)this.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);  //修改状态
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("测试","newState:"+newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //Log.d("测试","slideOffset:"+slideOffset);
            }
        });

        initBottomDialog();
    }


    /**
     * 点击隐藏或者显示BottomSheet
     */
    private void  showBottomSheet(){
        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    /**
     * 初始化BottomSheetDialog
     */
    private void initBottomDialog(){
        bottomSheetDialog = new BottomSheetDialog(this);

        //初始化contentview
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(this).inflate(R.layout.bottom_dialog,null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());//添加基础的item动画
        recyclerView.setVerticalScrollBarEnabled(true);
        List<String> data = new ArrayList<>();
        for (int i=0;i<30;i++){
            data.add("按钮"+i);
        }

        recyclerView.setAdapter(new BottomListAdapter(this,data,(view,position)->{
            Log.d("测试","点击了position："+position);
            Toast.makeText(this,"点击了position："+position,Toast.LENGTH_SHORT).show();
            bottomSheetDialog.hide();
        }));

        //contentview添加到dialog
        bottomSheetDialog.setContentView(recyclerView);
        //获得dialog的behavior
        bottomSheetDialogBehavior = BottomSheetBehavior.from((View)recyclerView.getParent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
