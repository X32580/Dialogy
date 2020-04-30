package com.sinepow.yp.dialog.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sinepow.yp.dialog.R;

/**
 * 作者 :  叶鹏
 * 时间 :  2020/3/23 8:29
 * 邮箱 :  1632502697@qq.com
 * 简述 :  web dialog 用于显示 用户协议等web 界面
 * 更新 :
 * 时间 :
 * 版本 : V 1.0
 */
public class DialogProgressBar extends Dialog {


    private ProgressBar progressBar;
    private TextView dialog_progressBar_text;


    public DialogProgressBar(Context context){
        super(context, R.style.DialogBackgroundTransparent);
        init(context);
    }


    private void  init(Context context){


        View v = LayoutInflater.from(context).inflate(R.layout.dialog_download,null);
        progressBar = v.findViewById(R.id.dialog_progressBar);
        dialog_progressBar_text = v.findViewById(R.id.dialog_progressBar_text);

        setContentView(v);

        Window window = getWindow();
        window.getAttributes().width =(int) (context.getResources().getDisplayMetrics().widthPixels*0.8);

        setCancelable(false);

        setCanceledOnTouchOutside(false);

    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public TextView getDialog_progressBar_text() {
        return dialog_progressBar_text;
    }
}

