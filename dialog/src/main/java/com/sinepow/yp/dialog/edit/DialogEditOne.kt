package com.sinepow.yp.dialog.edit

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.sinepow.yp.dialog.R
/**
 * 作者 :  叶鹏
 * 时间 :  2019/12/10 16:46
 * 邮箱 :  1632502697@qq.com
 * 简述 :  自定义弹出框 单输入控件
 * 更新 :  加入java接口
 * 时间 :
 */
class DialogEditOne(context: Context) : Dialog(context, R.style.DialogBackground){

    private lateinit var lister:(String) ->Unit
    private lateinit var listerJ:ClickLister
    var isLister = false
    var isListerJ = false
     var titleTextView:TextView
     var editText: EditText
    var confirmTextView: TextView
    var cancelTextView: TextView
    init {

        val  view = LayoutInflater.from(context).inflate(R.layout.dialog_input_one, null)
        titleTextView = view.findViewById(R.id.dialog_input_title)
        editText = view.findViewById(R.id.dialog_input_edit)
        cancelTextView = view.findViewById(R.id.dialog_input_cancel)
        confirmTextView = view.findViewById(R.id.dialog_input_confirm)

        setContentView(view)
        val window = getWindow()
        window!!.attributes.width = (context.resources.displayMetrics.widthPixels*0.8).toInt()

        cancelTextView.setOnClickListener {
            dismiss()
        }

        confirmTextView.setOnClickListener {
            if (isLister){
                if (editText.text != null){
                    lister(editText.text.toString())
                    dismiss()
                }else{
                    Toast.makeText(context,"请输入内容",Toast.LENGTH_SHORT).show()
                }
            }else{
                if (isListerJ){
                    if (editText.text != null){
                        listerJ.click(editText.text.toString())
                        dismiss()
                    }else{
                        Toast.makeText(context,"请输入内容",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    dismiss()
                }

            }


        }

    }


    fun setConfirmText(string: String):DialogEditOne{
        confirmTextView.setText(string)
        return this
    }
    fun setEditHint(string: String):DialogEditOne{
        editText.setHint(string)
        return this
    }

    fun setTitleText(string: String):DialogEditOne{
        titleTextView.setText(string)
        return this
    }

    fun setCancelText(string: String):DialogEditOne{
        cancelTextView.setText(string)
        return this
    }
    /**
     * 设置监听器  需注意设置监听器以后 自动显示 不设置监听器 需要手动显示
     */
    fun setlisetr(l: (String) -> Unit) {
        lister = l
        isLister =true
        show()
    }

    fun setLister(lister: ClickLister){
        listerJ = lister
        isListerJ =true
        show()
    }

    interface ClickLister{
        fun click(input:String)
    }



}