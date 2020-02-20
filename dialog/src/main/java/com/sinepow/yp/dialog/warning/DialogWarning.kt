package com.sinepow.yp.dialog.warning

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.sinepow.yp.dialog.R

/**
 * 作者 :  叶鹏
 * 时间 :  2020/1/4 10:47
 * 邮箱 :  1632502697@qq.com
 * 简述 :  显示警告 提示
 * 更新 :  加入适配java得接口
 * 时间 :
 */
class DialogWarning(context: Context) :Dialog(context, R.style.DialogBackground){

    private lateinit var liseter: (View) -> Unit
    var confirmTextView: TextView
    var titleTextView: TextView
    var isLister = false
    var isClick = false
    private lateinit var click: Click

    init {
        val view  = LayoutInflater.from(context).inflate(R.layout.dialog_overvoltage_warning,null)

        titleTextView = view.findViewById(R.id.dialog_over_voltage_title)
        confirmTextView = view.findViewById(R.id.dialog_over_confirm)

        confirmTextView.setOnClickListener { v ->
            if (isLister)
                liseter(v)
            if (isClick)
                click.OnClick()
            dismiss()
        }

        setContentView(view)
        val window = window
        window!!.attributes.width = (context.resources.displayMetrics.widthPixels*0.8).toInt()

    }



    fun setTitleText(string: String):DialogWarning{
        titleTextView.text = string
        return this
    }
    fun setConfirmText(string: String):DialogWarning{
        confirmTextView.text = string
        return this
    }

    /**
     * 设置监听器  需注意设置监听器以后 自动显示 不设置监听器 需要手动显示
     */
    fun setLister(l: (View) -> Unit) :DialogWarning {
        liseter = l
        isLister =true
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        show()
        return this
    }

    fun setClick(click: Click)  {
        this.click = click
        isClick =true
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        show()
    }

    interface Click{
        fun OnClick()
    }



}