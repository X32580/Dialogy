package com.sinepow.yp.dialog.tip

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.sinepow.yp.dialog.R
import com.sinepow.yp.dialog.warning.DialogWarning

/**
 * 作者 :  叶鹏
 * 时间 :  2020/3/19 14:15
 * 邮箱 :  1632502697@qq.com
 * 简述 :  加载 网页布局  的提示框
 * 更新 :
 * 时间 :
 * 版本 : V 1.0
 */
class DialogWebText(context: Context,url: String) : Dialog(context, R.style.DialogBackground){



    constructor(context: Context, url: String,title : String ) :this(context,url){
        setUrl(url)
        setTitle(title)
    }




    private lateinit var lister :(view : View)-> Unit

    val web : WebView
    val confrim : TextView
    val title : TextView

    var isLister = false
    var isClick = false
    /**
     * Kotlin 的监听器
     */
    private lateinit var click: DialogWarning.Click

    init {

        val view  = LayoutInflater.from(context).inflate(R.layout.dialog_web_text,null)
        web = view.findViewById(R.id.dialog_web)

        web.settings.useWideViewPort=true //将图片调整到适合webview的大小
        web.settings.loadWithOverviewMode =true // 缩放至屏幕的大小
        web.settings.defaultTextEncodingName = "utf-8"

        confrim = view.findViewById(R.id.dialog_web_confirm)
        title = view.findViewById(R.id.dialog_web_title)
        confrim.setOnClickListener {
            if (isLister)
                lister(it)
            if (isClick)
                click.OnClick()
            dismiss()
        }
        web.loadUrl(url)
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

    fun setUrl(url: String): DialogWebText{
        web.loadUrl(url)
        return this
    }

    fun setTitle(string: String):DialogWebText{
        title.text = string
        return this
    }


    /**
     * Java 的监听器
     */
    interface Click{
        fun OnClick()
    }

}