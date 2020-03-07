package com.sinepow.yp.dialog.edit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.sinepow.yp.dialog.R;


/**
 * 作者 :  叶鹏
 * 时间 :  2019/11/13 21:26
 * 邮箱 :  1632502697@qq.com
 * 简述 :  修改密码
 * 更新 :
 * 时间 :
 */
public class DialogEditModifyPW extends Dialog {
    private String title;
    private String oldPWtitle;
    private String newPWtitle;
    private String oldHint;
    private String newHint;
    private String canTitle;
    private String canPWHint;
    private String left;
    private String right;
    private Context context;
    private onClickLister lister;

    public DialogEditModifyPW setLister(onClickLister lister){
        this.lister = lister;
        return  this;
    }

    public DialogEditModifyPW(@NonNull Context context, String title, String oldPWtitle, String newPWtitle, String oldHint, String newHint, String canTitle, String canPWHint, String left, String right) {
        super(context, R.style.DialogBackground);
        this.context = context;
        this.title = title;
        this.oldPWtitle = oldPWtitle;
        this.newPWtitle = newPWtitle;
        this.oldHint = oldHint;
        this.newHint = newHint;
        this.canTitle = canTitle;
        this.canPWHint = canPWHint;
        this.left = left;
        this.right = right;

    }

    public interface onClickLister{
       void  click(String oldPW, String newPW);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    private void init(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_modif_pw, null);
        setContentView(view);
        TextView titleTv = view.findViewById(R.id.dialog_revise_title);
        TextView oldTitleTv = view.findViewById(R.id.dialog_revise_oldTitle);
        TextView newTitleTv = view.findViewById(R.id.dialog_revise_newTitle);
        TextView canfirmTv = view.findViewById(R.id.dialog_revise_confirmTitle);
        TextView leftTv = view.findViewById(R.id.dialog_modif_left);
        TextView rightTv = view.findViewById(R.id.dialog_modif_right);
        EditText oldPW = view.findViewById(R.id.dialog_revise_oldTEd);
        EditText newPW = view.findViewById(R.id.dialog_revise_newEd);
        EditText canPW = view.findViewById(R.id.dialog_revise_confirmEd);
        titleTv.setText(title);
        oldTitleTv.setText(oldPWtitle);
        oldPW.setHint(oldHint);
        newTitleTv.setText(newPWtitle);
        canfirmTv.setText(canTitle);
        leftTv.setText(left);
        rightTv.setText(right);
        newPW.setHint(newHint);
        canPW.setHint(canPWHint);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        leftTv.setOnClickListener(v -> {
            dismiss();
        });
        rightTv.setOnClickListener(v -> {
            if (oldPW.getText().toString()!=null&&newPW.getText().toString()!=null&&canPW.getText().toString()!=null){
                if (newPW.getText().toString().equals(canPW.getText().toString())){
                    lister.click(oldPW.getText().toString().trim(),newPW.getText().toString().trim());
                    dismiss();
                }else {
                    Toast.makeText(context,"两次输入密码不一致",Toast.LENGTH_LONG).show();
                }


            }else {
                Toast.makeText(context,"请输入密码",Toast.LENGTH_LONG).show();
            }



        });

    }












}
