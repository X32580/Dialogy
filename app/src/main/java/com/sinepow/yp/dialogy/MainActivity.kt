package com.sinepow.yp.dialogy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinepow.yp.dialog.`when`.DialogAutoTime
import com.sinepow.yp.dialog.`when`.DialogBatteryS
import com.sinepow.yp.dialog.edit.DialogEditModifyPW
import com.sinepow.yp.dialog.edit.DialogEditOne
import com.sinepow.yp.dialog.loading.DialogLoading
import com.sinepow.yp.dialog.select.DialogRadioGroupBatteryType
import com.sinepow.yp.dialog.select.DialogRadioGroupJunHen
import com.sinepow.yp.dialog.select.DialogSelect
import com.sinepow.yp.dialog.warning.DialogWarning
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private  var list : MutableList<String> = mutableListOf()
    private var listH: MutableList<String> = mutableListOf() //小时
    private var listM: MutableList<String> =  mutableListOf()//分钟

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (i in 1..26){
            list.add("$i 串")
        }


        for (i in 0..23) {
            if (i < 10) {
                (listH as ArrayList<String>).add("0$i")
                continue
            }
            (listH as ArrayList<String>).add(i.toString() + "")
        }
        for (i in 0..59) {
            if (i < 10) {
                (listM as ArrayList<String>).add("0$i")
                continue
            }

            (listM as ArrayList<String>).add(i.toString() + "")
        }


        select.setOnClickListener {
            DialogSelect(this).setTitleText("请选择").setLister {
                Toast.makeText(this,"确认",Toast.LENGTH_SHORT).show()
            }
        }
        loading.setOnClickListener {
            DialogLoading(this).show()
        }

        warning.setOnClickListener {
            DialogWarning(this).setLister {
                Toast.makeText(this,"知道了",Toast.LENGTH_SHORT).show()
            }
        }

        input_edit.setOnClickListener {
            DialogEditOne(this).setlisetr {
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }

        }

        dialog_eidt_paaword.setOnClickListener {

            DialogEditModifyPW(this,"修改密码","旧密码","新密码","请输入旧密码","请输入新密码","确认密码","请再次输入密码","取消","确认")
                .setLister { oldPW, newPW ->

                }


        }

        battery_type.setOnClickListener {
            DialogRadioGroupJunHen(
                this,
                "电芯类型",
                "确认",
                "三元锂",
                "磷酸铁锂",
                0
            ).setListener{ view, id ->

            }
        }


        junhen.setOnClickListener {
            DialogRadioGroupBatteryType(this,"选择模式","确认","普通模式","氪金模式","土豪模式",0)
                .setListener {

                }
        }

        where_one.setOnClickListener {

            DialogBatteryS(this,"选择电池串数","确认",list,0)
                .setOnclickLisetr {

                }


        }
        where_two.setOnClickListener {

            DialogAutoTime(this,"选择自锁时间","确认",listH,listM,0,0)
                .setOnClick { opt1, opt2 ->

                }


        }



        }


}
