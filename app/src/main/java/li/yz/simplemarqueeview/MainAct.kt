package li.yz.simplemarqueeview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_act.*

/**
 * createed by liyuzheng on 2019/7/26 11:26
 */
class MainAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)

        text.setText("Hello World! This is a Simple Marquee View. You can use it instead of textviewmarquee")
        text2.text = "Hello World! This is textview marquee.  You can use SimpleMarqueeView instead of this"
        text2.isSelected = true
    }

    override fun onPause() {
        super.onPause()
        text1.pause()
    }

    override fun onResume() {
        super.onResume()
        text1.resume()
    }
}