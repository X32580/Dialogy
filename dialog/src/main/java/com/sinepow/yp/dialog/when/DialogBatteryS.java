package com.sinepow.yp.dialog.when;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sinepow.yp.dialog.R;
import com.zyyoona7.wheel.WheelView;

import java.util.List;

/**
 * 作者 :  叶鹏
 * 时间 :  2019/11/14 13:35
 * 邮箱 :  1632502697@qq.com
 * 简述 :  选择电池串数
 * 更新 :
 * 时间 :
 */
public class DialogBatteryS extends Dialog {
    private String title;
    private String confirmtitle;
    private List<String> list;
    private Context context;
    private onClickLister lister;
    private int index;

    public DialogBatteryS(@NonNull Context context, String  title, String confirmtitle, List<String> list, int index) {
        super(context, R.style.DialogBackground);
        this.confirmtitle = confirmtitle;
        this.context =context;
        this.title = title;
        this.list = list;
        this.index = index;
    }

    public DialogBatteryS setOnclickLisetr(onClickLister lisetr){
        this.lister = lisetr;
        show();
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public interface onClickLister{
        void click(int index);
    }

    private void init(){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_battery_s, null);
        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        TextView titleTv = view.findViewById(R.id.dialog_battery_s_title);
        TextView canfrimTv = view.findViewById(R.id.dialog_battery_S_confirm);
         WheelView<String> wheelView = findViewById(R.id.dialog_battery_s_WV);
         if (list.size()>0){
            wheelView.setData(list);
            wheelView.setSelectedItemPosition(index);
             wheelView.setDrawSelectedRect(true);
             wheelView.setResetSelectedPosition(true);
             wheelView.setVisibleItems(7);
             wheelView.setShowDivider(true);
             wheelView.setTextSize(18f,true);
             wheelView.setDividerColor(Color.parseColor("#f3f3f3"));

        }
         canfrimTv.setText(confirmtitle);
         titleTv.setText(title);
        wheelView.setOnWheelChangedListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onWheelScroll(int scrollOffsetY) {

            }

            @Override
            public void onWheelItemChanged(int oldPosition, int newPosition) {

            }

            @Override
            public void onWheelSelected(int position) {
                index = position;
            }

            @Override
            public void onWheelScrollStateChanged(int state) {

            }
        });

         canfrimTv.setOnClickListener(v -> {

             lister.click(index);
             dismiss();

         });

    }



}
