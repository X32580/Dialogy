package com.sinepow.yp.dialog.tip

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.sinepow.yp.dialog.R
import com.sinepow.yp.dialog.warning.DialogWarning

/**
 * 作者 :  叶鹏
 * 时间 :  2020/3/13 16:32
 * 邮箱 :  1632502697@qq.com
 * 简述 :  此弹出框 用于显示 长文本
 * 更新 :
 * 时间 :
 * 版本 : V 1.0
 */
class DialogText(context: Context) : Dialog(context, R.style.DialogBackground) {


    constructor(context: Context,title : String ,string: String) :this(context){
        setText(string)
        setTitle(title)
    }
    constructor(context: Context,string: String) :this(context){
        setText(string)
    }



    private lateinit var lister :(view :View)-> Unit

    val text : TextView
    val confrim : TextView
    val title : TextView

    var isLister = false
    var isClick = false
    private lateinit var click: DialogWarning.Click

    init {

        val view  = LayoutInflater.from(context).inflate(R.layout.dialog_text,null)

        text = view.findViewById(R.id.dialog_text)
        confrim = view.findViewById(R.id.dialog_text_confirm)
        title = view.findViewById(R.id.dialog_text_title)
        confrim.setOnClickListener {
            if (isLister)
                lister(it)
            if (isClick)
                click.OnClick()
            dismiss()
        }

        setContentView(view)
        val window = window
        window!!.attributes.width = (context.resources.displayMetrics.widthPixels*0.8).toInt()
    }



    /**
     * 设置监听器  需注意设置监听器以后 自动显示 不设置监听器 需要手动显示
     */
    fun setLister(l: (View) -> Unit)  {
        lister = l
        isLister =true
        show()
    }

    fun setClick(click: DialogWarning.Click)  {
        this.click = click
        isClick =true
        show()
    }

     fun setText(string: String): DialogText{
        text.text = string
        return this
    }

    fun setTitle(string: String):DialogText{
        title.text = string
        return this
    }






    interface Click{
        fun OnClick()
    }


}