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
 * 时间 : 2019/11/10 15:09
 * 作者 :  叶鹏
 * 邮箱 : 1632502697@qq.com
 * 简述 : 选择均衡模式提示框
 **/
public class DialogRadioGroupJunHen extends Dialog {
    private Context context ;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioGroup radioGroup ;
    private String title;
    private String radiobutton1text;
    private String radiobutton2text;
    private String cancelbuttonText;
    private int index;
    private onClickListener listener;
    public DialogRadioGroupJunHen(@NonNull Context context, String title, String cancelbuttonText, String radiobutton1text, String radiobutton2text, int index) {
        super(context, R.style.DialogBackground);
        this.context = context;
        this.title = title;
        this.cancelbuttonText = cancelbuttonText;
        this.radiobutton1text = radiobutton1text;
        this.radiobutton2text = radiobutton2text;
        this.index = index;
    }


    public interface onClickListener {

        void click(View view, int id);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_junhen, null);
        setContentView(view);
        TextView totleTextView  = view.findViewById(R.id.dialog_junhen_title);
        radioGroup = view.findViewById(R.id.dialog_junhen_group);
        radioButton1 = view.findViewById(R.id.dialog_junhen_radio1);
        radioButton2 = view.findViewById(R.id.dialog_junhen_radio2);
        TextView cancel = view.findViewById(R.id.dialog_junhen_cancel);

        totleTextView.setText(title);
        radioButton1.setText(radiobutton1text);
        radioButton2.setText(radiobutton2text);
        cancel.setText(cancelbuttonText);
        radioGroup.setOnCheckedChangeListener((group, checkedId) ->{
            int id ;
            if (checkedId == R.id.dialog_junhen_radio1)
                id= 0;
            else if (checkedId == R.id.dialog_junhen_radio2)
                id = 1;
            else {
                id = 0;
            }
            cancel.setOnClickListener(v -> {
                listener.click(v,id);
                dismiss();
            });
        });
        setCheck(index);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    private void setCheck(int id){
        if (id == 0){
            radioButton1.setChecked(true);
            radioButton2.setChecked(false);
        }else if (id == 1){
            radioButton1.setChecked(false);
            radioButton2.setChecked(true);
        }else {
            radioButton1.setChecked(true);
            radioButton2.setChecked(false);
            Log.e("均衡模式传入id错误","id"+id);
        }

    }

    public DialogRadioGroupJunHen setListener(onClickListener listener ){
        this.listener = listener;
        show();
        return  this;
    }




}
