package li.yz.simplemarqueeviewlib

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.abs

/**
 * createed by liyuzheng on 2019/7/30 15:06
 */
class SimpleMarqueeView : View {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val dm = context.applicationContext.resources.displayMetrics
        density = dm.density
        scaleDensity = dm.scaledDensity

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.SimpleMarqueeView, defStyleAttr, defStyleAttr
        )
        textSize = a.getDimension(R.styleable.SimpleMarqueeView_textSize, sp2px(12f).toFloat())
        textColor = a.getColor(R.styleable.SimpleMarqueeView_textColor, Color.parseColor("#000000"))
        when (a.getInt(R.styleable.SimpleMarqueeView_textStyle, 1)) {
            1 -> typeFace = Typeface.DEFAULT
            2 -> typeFace = Typeface.DEFAULT_BOLD
            3 -> typeFace = Typeface.defaultFromStyle(Typeface.ITALIC)
            4 -> typeFace = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
        }
        val text = a.getString(R.styleable.SimpleMarqueeView_text) ?: ""
        shadowWidth = a.getDimension(R.styleable.SimpleMarqueeView_shadow_width, dp2px(14f).toFloat())
        margin = a.getDimension(R.styleable.SimpleMarqueeView_margin_txt, dp2px(133f).toFloat())
        speed = a.getInt(R.styleable.SimpleMarqueeView_speed, 12).toLong()
        delay = a.getInt(R.styleable.SimpleMarqueeView_delay, 1500).toLong()
        a.recycle()
        post {
            initShadow()
        }
        setText(text)
    }

    private var density: Float = 2f
    private var scaleDensity: Float = 2f
    //font size
    private var textSize = 33f
    //font color
    private var textColor = Color.parseColor("#000000")
    //style
    private var typeFace = Typeface.DEFAULT

    //文本
    private var mText = ""
    //compute text width if txtWidth>width  user marquee
    private var txtWidth = 0
    //shadow,if background is not color , that is not useful
    private var shadowWidth = 0f
    //the system marquee textview is 12L
    private var speed = 12L
    //animation delay
    private var delay = 1500L
    //between two texts margin
    private var margin = 0f
    //0 text 1 marquee
    private var showMode = 0
    private var anim: ValueAnimator? = null
    private var animValue: Int = 0
    private var scale: Int = 0

    private var leftShadow: LinearGradient? = null
    private var rightShadow: LinearGradient? = null

    private var paddingRect: Rect = Rect()
    private val shadowPaint by lazy {
        Paint()
    }

    // if background is not color, it's not useful
    private fun initShadow() {
        if (background is ColorDrawable) {
            val colorD = ColorDrawable((background as? ColorDrawable)?.color ?: 0)
            colorD.alpha = 255
            val sColorInt = colorD.color
            colorD.alpha = 0
            val eColorInt = colorD.color
            if (shadowWidth > 0) {
                leftShadow = LinearGradient(
                    paddingStart.toFloat(),
                    0f,
                    paddingStart.toFloat() + shadowWidth,
                    0f,
                    sColorInt,
                    eColorInt,
                    Shader.TileMode.CLAMP
                )
                rightShadow = LinearGradient(
                    width - paddingEnd.toFloat() - shadowWidth,
                    0f,
                    width - paddingEnd.toFloat(),
                    0f,
                    eColorInt,
                    sColorInt,
                    Shader.TileMode.CLAMP
                )
            }

        }
    }

    private val textPaint by lazy {
        TextPaint().apply {
            this.color = this@SimpleMarqueeView.textColor
            this.textSize = this@SimpleMarqueeView.textSize
            this.typeface = this@SimpleMarqueeView.typeFace
            this.isAntiAlias = true
        }
    }

    fun setText(text: String) {
        if(text == mText) return
        this.mText = text
        stopAnim()
        if (visibility == VISIBLE) {
            post {
                measureTxt()
                switchShowMode()
                show()
            }
        }
    }

    fun getText() = mText

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == View.VISIBLE) {
            setText(mText)
        } else {
            stopAnim()
        }
    }

    private fun show() {
        if (showMode == 0) {
            invalidate()
        } else {
            invalidate()
            startAnim()
        }
    }

    private fun startAnim() {
        stopAnim()
        anim = ValueAnimator.ofInt(0, (txtWidth + margin).toInt())
        anim?.duration = ((txtWidth + margin) * speed).toLong()
        anim?.interpolator = LinearInterpolator()
        anim?.repeatCount = 0
        anim?.addUpdateListener {
            animValue = it.animatedValue as Int
            invalidate()
        }
        anim?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                animValue = 0
                startAnim()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
        anim?.startDelay = delay
        anim?.start()
    }

    private fun stopAnim() {
        anim?.takeIf {
            it.isRunning
        }?.run {
            cancel()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val x = -animValue.toFloat() + paddingStart
        val y = x + margin + txtWidth
        paddingRect.left = paddingStart
        paddingRect.top = paddingTop
        paddingRect.right = width - paddingEnd
        paddingRect.bottom = height - paddingBottom
        canvas?.clipRect(paddingRect)
        canvas?.drawText(mText, x, textSize + (height - textSize) / 2f - sp2px(1f), textPaint)
        if (showMode == 1) {
            canvas?.drawText(mText, y, textSize + (height - textSize) / 2f - sp2px(1f), textPaint)
            if (abs(x) < txtWidth - paddingStart && anim?.isRunning == true) {
                leftShadow?.run {
                    shadowPaint.shader = this
                    canvas?.drawRect(
                        paddingStart.toFloat(),
                        0f,
                        paddingStart + shadowWidth,
                        height.toFloat(),
                        shadowPaint
                    )
                }
            }

            rightShadow?.run {
                shadowPaint.shader = this
                canvas?.drawRect(
                    width - paddingEnd.toFloat() - shadowWidth,
                    0f,
                    width - paddingEnd.toFloat(),
                    height.toFloat(),
                    shadowPaint
                )
            }
        }
    }


    private fun switchShowMode() {
        showMode = if (txtWidth + paddingStart + paddingEnd > width) {
            //跑马灯模式
            1
        } else {
            //正常显示
            0
        }
    }

    //compute txt width
    private fun measureTxt() {
        txtWidth = textPaint.measureText(mText).toInt()
        scale = txtWidth / (width - paddingStart - paddingEnd) + 1
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnim()
    }

    private fun dp2px(dipValue: Float): Int {
        return (dipValue * density + 0.5f).toInt()
    }

    private fun sp2px(spValue: Float): Int {
        return (spValue * scaleDensity + 0.5f).toInt()
    }

    //support height wrap_content
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            val tMin = dp2px(3f)
            val pTop = if (paddingTop < tMin) tMin else paddingTop
            val pBottom = if (paddingBottom < tMin) tMin else paddingBottom
            setMeasuredDimension(widthSpecSize, (textSize + pTop + pBottom).toInt())
        }
    }

    //if you want  pause anim,use it
    fun pause() {
        anim?.takeIf {
            it.isRunning
        }?.run {
            pause()
        }
    }

    //if you want resume anim,use it
    fun resume() {
        anim?.run {
            resume()
        }
    }
}