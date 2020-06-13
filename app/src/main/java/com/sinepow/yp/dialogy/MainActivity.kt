package com.sinepow.yp.dialogy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text1.setOnClickListener {
            text1.startAnimation()
        }
        succeed.setOnClickListener {
            loading.noSuccessful()
        }
        error.setOnClickListener {
            loading.onError()
        }

        loading.setOnClickListener {
            loading.onLoading()
        }

    }

}


