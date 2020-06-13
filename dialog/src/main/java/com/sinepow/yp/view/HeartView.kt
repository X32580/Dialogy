package com.sinepow.yp.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.sinepow.yp.dialog.R
import kotlin.math.abs


/**
 * 作者 :  叶鹏
 * 时间 :  2020/6/10 10:04
 * 邮箱 :  1632502697@qq.com
 * 简述 :
 * 更新 :
 * 时间 :
 * 版本 : V 1.0
 */
class HeartView : View {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributes: AttributeSet?) : this(context, attributes, 0) {

    }

    constructor(context: Context, attributes: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributes,
        defStyleAttr
    ) {

        initView(context, attributes, defStyleAttr)
    }


    private val heartPath = Path()

    private val heartPaint = Paint()


    private val batteryPaint =  Paint()

    private val batteryTopPaint =Paint()


    var loadingColor  = Color.GRAY
    var batteryColor =Color.BLUE
    var successfulColor =Color.GREEN
    var errorColor = Color.RED
    var textSize = 20f
    private fun initView(context: Context, attributes: AttributeSet?, defStyleAttr: Int) {

        val ty: TypedArray = context.obtainStyledAttributes(attributes, R.styleable.HeartView)

       loadingColor =
            ty.getColor(R.styleable.HeartView_heartLoadColor, Color.GRAY)

        batteryColor =   ty.getColor(R.styleable.HeartView_heartBatteryColor, Color.BLUE)

        successfulColor = ty.getColor(R.styleable.HeartView_heartSuccessfulColor, Color.GREEN)

        errorColor = ty.getColor(R.styleable.HeartView_heartErrorColor, Color.RED)

        heartHeight =
            ty.getDimension(R.styleable.HeartView_heartHeight, 30f)
        heartWidth = ty.getDimension(R.styleable.HeartView_heartWidth, 30f)

        paintSize = ty.getDimension(R.styleable.HeartView_heartPaintSize,6f)

        textSize =ty.getDimension(R.styleable.HeartView_heartTextSize,20f)

        ty.recycle()

        heartPaint.style = Paint.Style.STROKE
        heartPaint.strokeWidth = paintSize
        heartPaint.color = loadingColor
        heartPaint.isAntiAlias = false


        batteryPaint.style = Paint.Style.STROKE
        batteryPaint.strokeWidth = paintSize
        batteryPaint.color = batteryColor
        batteryPaint.isAntiAlias = false

        batteryTopPaint.style = Paint.Style.FILL
        batteryTopPaint.strokeWidth = paintSize
        batteryTopPaint.color = batteryColor
        batteryTopPaint.isAntiAlias = false

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.color = loadingColor
        textPaint.textSize = textSize

        initAnimation()

    }


    /**
     * 电池 心跳浮动  高度
     */
    private var heartHeight = 50f

    /**
     * 电池 浮动得宽度
     */
    private var heartWidth = 30f

    /**
     * 画笔大小
     */
    private var paintSize = 6f
    //心跳移动速度值
    private var heartSeep = 0.0f
    //路径 计算类
    private val pathMeasure = PathMeasure()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        canvas?.let {
            drawPath(it)
        }


    }

    private var startValue = 0.0f
    private var endValue = 0.0f


    /**
     * 遮盖 心跳得 路径
     */
    private val drawPath = Path()
    private val batteryRect  = RectF()
    private val batteryTopRect = RectF()
    //是否初始化了
    private var initIs = false

    private fun drawPath(canvas: Canvas) {


        if (!initIs) {

            initIs = true

            /**
             * 高度得8/1 为右边凸起得 宽度
             */
            batteryRect.set(paintSize,paintSize,measuredWidth-paintSize*2 - height/8,measuredHeight-paintSize)

            batteryTopRect.set(batteryRect.right,measuredHeight/3f,measuredWidth-paintSize,measuredHeight-measuredHeight/3f)

            heartPath.moveTo(batteryRect.left+paintSize/2, height / 2f)
            heartPath.lineTo(width / 2f-heartWidth, height / 2f)
            //三分之一开始 折线
            heartPath.lineTo(width / 2f , height / 2f - heartHeight)
            heartPath.lineTo(width / 2f + heartWidth, height / 2f + heartHeight)
            heartPath.lineTo(width / 2f + heartWidth * 2, height / 2f)

            heartPath.lineTo(batteryRect.right-paintSize/2, height / 2f)

            pathMeasure.setPath(heartPath, false)

            //计算出电池的宽度高度
            batteryWidth = batteryRect.right - batteryRect.left
            batteryHeight = batteryRect.top - batteryRect.bottom


        }



        drawPath.reset()
        pathMeasure.getSegment(startValue, endValue, drawPath, true)

        //绘制电池主体
        canvas.drawRoundRect(batteryRect,paintSize,paintSize,batteryPaint)
        //绘制电池 右部突出部分
        canvas.drawRoundRect(batteryTopRect,paintSize,paintSize,batteryTopPaint)
        //绘制心跳线条
        canvas.drawPath(drawPath, heartPaint)
        //绘制文字
        drawText(canvas)

    }


    /**
     * 绘制文字
     * @param canvas
     */
    //绘制电池的高度与 宽度
    private var batteryWidth = 0f
    private var batteryHeight = 0f
    //内部文字 描绘
    private var textPaint =Paint()
    private var textX = 0f
    private var textY = 0f
    private var fontMetrics: Paint.FontMetrics? = null
    private var stateText = "loading"
    private fun drawText(canvas: Canvas) {
        textX = textPaint.measureText(stateText)
        // 控件的宽高加上本身控件的 距离
        textX = batteryRect.left + (batteryWidth - textX) / 2
        //文字的y轴坐标
        fontMetrics = textPaint.fontMetrics
        textY =
            batteryRect.top - batteryHeight / 2 + (abs(fontMetrics!!.ascent) - fontMetrics!!.descent) / 2
        canvas.drawText(stateText, textX, textY, textPaint)
    }




    /**
     *  心跳线 动画
     */
    private val heartAnimator: ValueAnimator = ObjectAnimator.ofFloat(0f, 1.0f)


    /**
     *  装载 动画
     */
    private fun initAnimation() {
        heartAnimator.duration = 3000

        heartAnimator.addUpdateListener {

            endValue = (it.animatedValue as Float) * pathMeasure.length
            startValue =
                endValue - ((0.5f - abs(it.animatedValue as Float - 0.5f)) * pathMeasure.length)

            heartSeep  = it.animatedValue as Float
            invalidate()
        }

        heartAnimator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                animation?.let {
                    it.start()
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }


        })


    }


    fun onSuccessful(){
        heartPaint.color = successfulColor
        stateText = "正常"
        textPaint.color =successfulColor
    }


    fun onError(){
        heartPaint.color=errorColor
        stateText = "异常"
        textPaint.color =errorColor
    }


    /**
     * 启动动画
     */
    fun startAnimation() {
        heartAnimator.start()

    }

    /**
     * 停止动画 一定要再界面销毁的时候 调用
     */
    fun onPause(){
        heartAnimator.pause()
    }

    fun onEnd(){
        heartAnimator.end()
    }

    fun onResume(){
        heartAnimator.resume()
    }


    /**
     * 测量 view 尺寸
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //获取到 view 的尺寸
        val width = getSize(80, widthMeasureSpec)
        val height = getSize(40, heightMeasureSpec)
        super.onMeasure(width, height)

        //设置测量尺寸
        setMeasuredDimension(width, height)


    }

    /**
     * 测量 控件
     * @param defaultSize
     * @param measureSpec
     * @return
     */
    private fun getSize(defaultSize: Int, measureSpec: Int): Int {
        var mySize = defaultSize
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        when (mode) {
            MeasureSpec.UNSPECIFIED -> {
                //如果没有指定大小，就设置为默认大小
                mySize = defaultSize
            }
            MeasureSpec.AT_MOST -> {
                //如果测量模式自适应
                //我们将大小取最大值,你也可以取其他值
                Math.min(mySize, size)
            }
            MeasureSpec.EXACTLY -> {
                //如果是固定的大小，那就不要去改变它
                mySize = size
            }
        }
        return mySize
    }




}