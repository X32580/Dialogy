package com.sinepow.yp.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.sinepow.yp.dialog.R
import kotlin.math.abs

/**
 * 作者 :  叶鹏
 * 时间 :  2020/5/25 11:16
 * 邮箱 :  1632502697@qq.com
 * 简述 :  电池 显示百分比 显示视图
 * 更新 :  转换为Kotlin 版本
 * 时间 :
 * 版本 : V 1.0
 */
class BatteryViews : View {

    constructor(context: Context):this(context,null)

    constructor(context: Context,attributes: AttributeSet?):this(context,attributes,0){

    }

    constructor(context: Context, attributes: AttributeSet?, defStyleAttr:Int) : super(context,attributes,defStyleAttr) {

        initView(context,attributes,defStyleAttr)
    }



    //画笔
    private var paint: Paint? = null
    //电池内部波浪 画笔
    private var wavePaint: Paint? = null

    //内部文字 描绘
    private var textPaint: Paint? = null
    //波浪 递进值


    //  线条宽度 6
    private val paintWidth = 10

    //电池的圆角值
    private val batteryRound = 30

    //波浪 路径
    private var wavePath: Path?= null

    //进度值
    private var progress = 0.5f

    //绘制电池的高度与 宽度
    private var batteryWidth = 0f

    private var batteryHeight = 0f

    //进度值对应的 y轴值
    private var py = 0f



    private var mWaveDx = 0
    private var dx = 0


    /**
     * 初始化  控件
     */
    private fun initView(context: Context,attributes: AttributeSet?,defStyleAttr: Int){

        val ty: TypedArray = context.obtainStyledAttributes(attributes, R.styleable.BatteryView)

        val batteryColor =
            ty.getColor(R.styleable.BatteryView_batteryColor, Color.BLUE)
        val waveColo =
            ty.getColor(R.styleable.BatteryView_batteryWaveColor, Color.BLUE)
        val textColor =
            ty.getColor(R.styleable.BatteryView_prColor, Color.GRAY)

        ty.recycle()

        paint = Paint()
        paint!!.color = batteryColor
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = paintWidth.toFloat()
        wavePaint = Paint()
        wavePaint!!.color = waveColo
        wavePaint!!.style = Paint.Style.FILL
        wavePaint!!.strokeWidth = paintWidth.toFloat()

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint!!.color = textColor
        textPaint!!.textSize = 60f


        mWaveDx = resources.displayMetrics.widthPixels

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


    /**
     * 测量 view 尺寸
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //获取到 view 的尺寸
        val width = getSize(100, widthMeasureSpec)
        val height = getSize(200, heightMeasureSpec)
        super.onMeasure(width, height)


        //设置测量尺寸
        setMeasuredDimension(width, height)
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        drawBattery(canvas!!)

        drawWave(canvas!!)

        drawText(canvas!!)


    }

    /**
     * 绘制电池顶部
     */
    //电池外圈绘制 rect
    var batteryRect: RectF? = null

    //电池顶部 rect
    var batteryTop: RectF? = null

    //
    var batteryPath: Path? = null
    private fun drawBattery(canvas: Canvas) {
        if (batteryRect == null) {
            batteryRect = RectF()
        }
        if (batteryTop == null) {
            batteryTop = RectF()
        }
        if (batteryPath == null) {
            batteryPath = Path()
        }

        //重置路径
        batteryPath!!.reset()
        //外部矩形 等于 总宽高减去 画笔大小
        batteryRect!!.left = 0 + paintWidth.toFloat()
        batteryRect!!.right = measuredWidth - paintWidth.toFloat()
        batteryRect!!.bottom = measuredHeight - paintWidth.toFloat()

        //顶部电池突出部分 为 宽度的4/1  是顶部突出的总高度
        batteryRect!!.top = 0 + paintWidth + (measuredWidth / 4).toFloat()

        //画出 电池的主体圈
        canvas.drawRoundRect(batteryRect!!, batteryRound.toFloat(), batteryRound.toFloat(), paint!!)
        batteryTop!!.left = 0 + paintWidth + (measuredWidth / 4).toFloat()
        batteryTop!!.right = measuredWidth - paintWidth - (measuredWidth / 4).toFloat()
        batteryTop!!.top = 0 + paintWidth.toFloat()
        // 底部需要和下一个电池相交 不能减去 画笔的大小
        batteryTop!!.bottom = batteryRect!!.top
        //绘制电池顶部 顶部圆角 减少一半
        canvas.drawRoundRect(
            batteryTop!!,
            batteryRound / 2.toFloat(),
            batteryRound / 2.toFloat(),
            paint!!
        )
        // 此时需要减去 外部描边的 大小
        batteryRect!![batteryRect!!.left + paintWidth, batteryRect!!.top + paintWidth, batteryRect!!.right - paintWidth] =
            batteryRect!!.bottom - paintWidth
        //添加 电池主题 path
        batteryPath!!.addRoundRect(
            batteryRect,
            batteryRound / 2.toFloat(),
            batteryRound / 2.toFloat(),
            Path.Direction.CW
        )

        //计算出电池的宽度高度
        batteryWidth = batteryRect!!.right - batteryRect!!.left
        batteryHeight = batteryRect!!.top - batteryRect!!.bottom


        //裁剪电池圈外的不显示
        canvas.clipPath(batteryPath!!)
    }


    /**
     * 绘制内部波浪
     * @param canvas
     */


    private fun drawWave(canvas: Canvas) {
        if (wavePath == null) {
            wavePath = Path()
        }
        wavePath!!.reset()
        py = batteryHeight * progress
        wavePath!!.moveTo(-mWaveDx + dx.toFloat(), batteryRect!!.bottom + py)
        var i = -mWaveDx
        while (i < width + mWaveDx) {
            wavePath!!.rQuadTo(mWaveDx / 4.toFloat(), -30f, mWaveDx / 2.toFloat(), 0f)
            wavePath!!.rQuadTo(mWaveDx / 4.toFloat(), 30f, mWaveDx / 2.toFloat(), 0f)
            i += mWaveDx
        }
        //绘制封闭的区域
        wavePath!!.lineTo(width.toFloat(), height.toFloat())
        wavePath!!.lineTo(0f, height.toFloat())
        wavePath!!.close()
        canvas.drawPath(wavePath!!, wavePaint!!)
    }


    /**
     * 绘制文字
     * @param canvas
     */
    private var textX = 0f
    private var textY = 0f
    private var fontMetrics: Paint.FontMetrics? = null
    private fun drawText(canvas: Canvas) {
        textX = textPaint!!.measureText(prText)
        // 控件的宽高加上本身控件的 距离
        textX = batteryRect!!.left + (batteryWidth - textX) / 2
        //文字的y轴坐标
        fontMetrics = textPaint!!.fontMetrics
        textY =
            batteryRect!!.top - batteryHeight / 2 + (abs(fontMetrics!!.ascent) - fontMetrics!!.descent) / 2
        canvas.drawText(prText, textX, textY, textPaint!!)
    }


    /**
     * 创建并启动动画
     */
    private lateinit var valueAnimator: ValueAnimator

    fun initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, mWaveDx)
        valueAnimator.duration = 2000
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener { animation: ValueAnimator ->
            //水平方向的偏移量
            dx = animation.animatedValue as Int
            invalidate()
        }
        valueAnimator.start()
    }


    /**
     * 开启动画
     */
    fun startAnimation() {
        if (valueAnimator!!.isStarted) {
            return
        }
        valueAnimator!!.start()
    }

    /**
     * 暂停动画
     */
    fun pauseAnimator() {
        if (valueAnimator!!.isRunning) {
            valueAnimator!!.pause()
        }
    }

    /**
     * 取消动画
     */
    fun cancelAnimator() {
        valueAnimator!!.cancel()
    }


    /**
     * 传入 百分比 0-100
     * @param pr
     */
    private var prText = "0%"
    fun setProgress(pr: Int) {
        var pr = pr
        if (pr > 100) pr = 100
        if (pr < 0) pr = 0
        progress = pr * 0.01f
        prText = "$pr%"
    }


}