package com.sinepow.yp.dialogy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinepow.yp.dialog.edit.DialogEditOne
import com.sinepow.yp.dialog.loading.DialogLoading
import com.sinepow.yp.dialog.select.DialogSelect
import com.sinepow.yp.dialog.warning.DialogWarning
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    }
}
