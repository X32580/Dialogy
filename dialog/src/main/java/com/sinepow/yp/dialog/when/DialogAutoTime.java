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
import com.zyyoona7.picker.OptionsPickerView;


import java.util.List;

/**
 * 作者 :  叶鹏
 * 时间 :  2019/11/14 19:02
 * 邮箱 :  1632502697@qq.com
 * 简述 :  设置自动锁定时间提示框
 * 更新 :
 * 时间 :
 */
public class DialogAutoTime extends Dialog {
    private Context context;
    private String  title;
    private  String confirm;
    private int index,index2;
    private List<String> listH;
    private List<String> listM;
    private onClickLister lister;

    public DialogAutoTime(@NonNull Context context, String title, String confirm, List<String> listH, List<String> listM, int index, int index2) {
        super(context, R.style.DialogBackground);
        this.confirm =confirm;
        this.context = context;
        this.title = title;
        this.index = index;
        this.listM = listM;
        this.listH = listH;
        this.index2 = index2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_battery_auto_lock_time, null);
        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        TextView titleTv = view.findViewById(R.id.dialog_auto_time_title);
        TextView confirmTv = view.findViewById(R.id.dialog_auto_time_canfrim);
        OptionsPickerView optionsPickerView = view.findViewById(R.id.dialog_auto_time_PV);
        titleTv.setText(title);
        confirmTv.setText(confirm);
        optionsPickerView.setData(listH, listM);
        optionsPickerView.setShowDivider(true);
        optionsPickerView.setDividerColor(Color.parseColor("#f3f3f3"));
        optionsPickerView.setOnOptionsSelectedListener((opt1Pos, opt1Data, opt2Pos, opt2Data, opt3Pos, opt3Data) -> {
            index = opt1Pos;
            index2 = opt2Pos;
        });

        optionsPickerView.setOpt1SelectedPosition(index,true);
        optionsPickerView.setOpt2SelectedPosition(index2,true);



        confirmTv.setOnClickListener( v -> {

            if (index==0&&index2==0){
                optionsPickerView.setOpt1SelectedPosition(0);
                optionsPickerView.setOpt2SelectedPosition(1,true);
                return;
            }
            lister.click(index,index2);
            dismiss();
        });





    }


    public DialogAutoTime setOnClick(onClickLister lister){
        this.lister = lister;
        show();
        return this;
    }

        public interface  onClickLister{
            void click(int opt1, int opt2);

        }













}
