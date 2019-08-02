package li.yz.simplemarqueeview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_act.*

/**
 * desc: todo Overview
 * createed by liyuzheng on 2019/7/26 11:26
 */
class MainAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)

        text.setText("Hello World! This is a Simple Marquee View. You can use it")

    }
}