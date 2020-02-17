package com.sinepow.yp.dialog.loading

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sinepow.yp.dialog.R

/**
 * 作者 :  叶鹏
 * 时间 :  2019/12/11 13:52
 * 邮箱 :  1632502697@qq.com
 * 简述 :  自定义加载提示框 需要手动 销毁界面
 * 更新 :
 * 时间 :
 */
class DialogLoading(context: Context) :Dialog(context, R.style.DialogLoading) {

     var imageView:ImageView
     var textView :TextView
    private  var tip: String ="加载中...."
    private  var imagePath :Int = R.drawable.loading
    constructor(context: Context,tip:String):this(context){
        this.tip =tip
    }
    constructor(context: Context,tip: String,imageID:Int):this(context){
        this.tip =tip
        imagePath = imageID
    }


    init {
        val  view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
         imageView  = view.findViewById(R.id.dialog_loading_image)
          textView = view.findViewById(R.id.dialog_loading_tip)
         textView.setText(tip)
        Glide.with(imageView).load(imagePath).into(imageView)
          setContentView(view)
        setCanceledOnTouchOutside(false)
    }





}