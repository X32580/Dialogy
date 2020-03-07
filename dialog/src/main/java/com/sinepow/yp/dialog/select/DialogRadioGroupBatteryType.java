package com.sinepow.yp.dialog.select;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sinepow.yp.dialog.R;


/**
 * 时间 : 2019/11/10 12:01
 * 作者 :  叶鹏
 * 邮箱 : 1632502697@qq.com
 * 简述 : 选择电池类型弹出框
 **/
public class DialogRadioGroupBatteryType extends Dialog {

    private Context context;
    private String title;
    private String radiobutton1text;
    private String radiobutton2text;
    private String radiobutton3text;
    private String cancelbuttonText;
    private onClickListener listener;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private int index;

    /**
     * 传入构造参数
     * @param context 上下文
     * @param title 标签
     * @param cancelbuttonText
     * @param radiobutton1text
     * @param radiobutton2text
     * @param radiobutton3text
     * @param index 默认选定得 radioButton
     */
    public DialogRadioGroupBatteryType(@NonNull Context context, String title, String cancelbuttonText, String radiobutton1text, String radiobutton2text, String radiobutton3text, int index) {
        super(context, R.style.DialogBackground);
        this.context = context;
        this.radiobutton1text = radiobutton1text;
        this.radiobutton2text = radiobutton2text;
        this.radiobutton3text = radiobutton3text;
        this.title = title;
        this.cancelbuttonText = cancelbuttonText;
        this.index = index;

    }


    public interface onClickListener {
        void onSelectClick(int id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_battery_type, null);
        setContentView(view);
        TextView titleTextView = view.findViewById(R.id.dialog_radio_title);
        TextView canceTextView = view.findViewById(R.id.dialog_cancel);
        radioGroup = view.findViewById(R.id.dialog_battery_type_radioGroup);
        radioButton1 = view.findViewById(R.id.dialog_battery_type_radiobutton1);
        radioButton2 = view.findViewById(R.id.dialog_battery_type_radiobutton2);
        radioButton3 = view.findViewById(R.id.dialog_battery_type_radiobutton3);
        titleTextView.setText(title);
        canceTextView.setText(cancelbuttonText);
        radioButton1.setText(radiobutton1text);
        radioButton2.setText(radiobutton2text);
        radioButton3.setText(radiobutton3text);
        setCeck(index);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.dialog_battery_type_radiobutton1)
                index = 0;
            if (checkedId ==R.id.dialog_battery_type_radiobutton2)
                index = 1;
            if (checkedId ==R.id.dialog_battery_type_radiobutton3)
                index = 2;
        });
        canceTextView.setOnClickListener(v -> {
            listener.onSelectClick(index);
            dismiss();
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

    }

    public DialogRadioGroupBatteryType setListener(onClickListener listener) {
        this.listener = listener;
        return this;
    }

    private void setCeck(int index) {

        if (index > 2) {
            Log.e("下标越界", "电池类型最多只有三种");
            return;
        }
        if (index == 0) {
            radioButton1.setChecked(true);
            radioButton2.setChecked(false);
            radioButton3.setChecked(false);
        } else if (index == 1) {
            radioButton1.setChecked(false);
            radioButton2.setChecked(true);
            radioButton3.setChecked(false);
        } else if (index == 2) {
            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
            radioButton3.setChecked(true);
        }


    }


}
