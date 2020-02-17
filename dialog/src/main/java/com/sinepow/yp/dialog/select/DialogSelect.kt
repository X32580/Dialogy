package com.sinepow.yp.dialog.select

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.sinepow.yp.dialog.R

/**
 * 作者 :  叶鹏
 * 时间 :  2019/12/10 9:55
 * 邮箱 :  1632502697@qq.com
 * 简述 :  自定义选择弹出框   没有测试 碎片中调用
 * 更新 :
 * 时间 :
 */
class DialogSelect(context: Context) : Dialog(context, R.style.DialogBackground) {

    private lateinit var liseter: (View) -> Unit
    private  var v: View
      var confirmTextView: TextView
      var cancelTextView: TextView
      var titleTextView: TextView
      var contentTextView: TextView
      var isLister = false

    init {
        v = LayoutInflater.from(context).inflate(R.layout.dialog_select, null)

        confirmTextView = v.findViewById(R.id.dialog_select_confirm)
        cancelTextView = v.findViewById(R.id.dialog_select_cancel)
        titleTextView = v.findViewById(R.id.dialog_select_title)
        contentTextView = v.findViewById(R.id.dialog_select_content)

        cancelTextView.setOnClickListener {
            dismiss()
        }

        confirmTextView.setOnClickListener { v ->
            if (isLister)
            liseter(v)
            dismiss()
        }


        setContentView(v)
        val window = window
        window!!.attributes.width = (context.resources.displayMetrics.widthPixels*0.8).toInt()
    }

    fun setConfirmText(string: String):DialogSelect{
        confirmTextView.text = string
        return this
    }
    fun setContentText(string: String):DialogSelect{
        contentTextView.text = string
        return this
    }
    fun setTitleText(string: String):DialogSelect{
        titleTextView.text = string
        return this
    }

    fun setCancelText(string: String):DialogSelect{
        cancelTextView.text = string
        return this
    }



    /**
     * 设置监听器  需注意设置监听器以后 自动显示 不设置监听器 需要手动显示
     */
    fun setLister(l: (View) -> Unit) :DialogSelect {
        liseter = l
        isLister =true
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        show()
        return this
    }



}