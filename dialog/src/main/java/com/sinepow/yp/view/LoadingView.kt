package com.sinepow.yp.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.sinepow.yp.dialog.R
import kotlin.math.abs

/**
 * 作者 :  叶鹏
 * 时间 :  2020/6/12 9:57
 * 邮箱 :  1632502697@qq.com
 * 简述 :
 * 更新 :
 * 时间 :
 * 版本 : V 1.0
 */
class LoadingView : View {


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

    private val pathMeasure = PathMeasure()
    private val path = Path()
    private val paint = Paint()
    var errorColor  = Color.RED
    var successfulColor = Color.BLUE
    var loadingColor  = Color.GRAY
    var duration = 3000L

    private fun initView(context: Context, attributes: AttributeSet?, defStyleAttr: Int) {

        val ty = context.obtainStyledAttributes(attributes, R.styleable.LoadingView)

        errorColor = ty.getColor(R.styleable.LoadingView_errorColor,Color.RED)
        successfulColor =ty.getColor(R.styleable.LoadingView_successfulColor,Color.BLUE)
        loadingColor =ty.getColor(R.styleable.LoadingView_loadingColor,Color.BLUE)
        duration = ty.getInt(R.styleable.LoadingView_duration,3000).toLong()

        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = ty.getDimension(R.styleable.LoadingView_strokeSize,6f)
        paint.color = loadingColor
        paint.isAntiAlias = true
        ty.recycle()


    }

    //外部加载圈得 路径
    private val loadingPath = Path()
    //成功 得路径  记录成功得path
    private val successPath = Path()
    //成功 得绘制路径 用户 绘制动画
    private val successPathDs = Path()
    //路径追踪
    private val successPathMeasure = PathMeasure()
    //成功步进值
    private var successDs = 0.0f

    private val errorPath = Path()
    private val errorPathDs = Path()


    private val errorPathMeasure1 = PathMeasure()
    private var errorDS1 = 0.0f

    private val errorPath1 = Path()
    private val errorPathDs1 = Path()


    private val errorPathMeasure = PathMeasure()
    private var errorDS = 0.0f

    private var startValue = 0.0f
    private var endValue = 0.0f

    private var isInit = false
    private var state = 0
    private val animator = ObjectAnimator.ofFloat(0f, 1f)
    private val successfulAnimator = ObjectAnimator.ofFloat(0f, 1f)
    private val errorAnimator = ObjectAnimator.ofFloat(0f, 1f)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!isInit) {
            isInit = true

            //绘制外部得圆
            path.addCircle(
                width / 2f,
                height / 2f,
                width / 2f - paint.strokeWidth,
                Path.Direction.CW
            )


            pathMeasure.setPath(path, false)

            animator.duration = duration

            animator.addUpdateListener {

                endValue = (it.animatedValue as Float) * pathMeasure.length
                startValue =
                    endValue - ((0.5f - abs(it.animatedValue as Float - 0.5f)) * pathMeasure.length)

//                // 重置 成功得 路径
//                if (successDs!=0.0f)
//                    successDs =0.0f

                invalidate()

            }

            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {

                    when (state) {
                        0 -> {
                            onLoading()
                        }
                        1 -> {
                            successfulAnimator.start()
                        }
                        2 -> {
                            errorAnimator.start()
                        }
                    }

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }

            })


            //绘制 勾

            successPath.moveTo(width * 0.3f, height * 0.5f)

            successPath.lineTo(width * 0.45f, height * 0.65f)

            successPath.lineTo(width * 0.75f, height * 0.35f)

            successPathMeasure.setPath(successPath, false)

            successfulAnimator.duration = duration

            successfulAnimator.addUpdateListener {

                endValue = (it.animatedValue as Float) * pathMeasure.length
                successDs = (it.animatedValue as Float) * successPathMeasure.length

                invalidate()

            }

            errorPath.moveTo(width * 0.3f, height * 0.3f)
            errorPath.lineTo(width * 0.7f, height * 0.7f)


            errorPath1.moveTo(width * 0.7f, height * 0.3f)
            errorPath1.lineTo(width * 0.3f, height * 0.7f)

            errorPathMeasure.setPath(errorPath, false)
            errorPathMeasure1.setPath(errorPath1, false)

            errorAnimator.duration = duration

            errorAnimator.addUpdateListener {

                endValue = (it.animatedValue as Float) * pathMeasure.length
                errorDS1 = (it.animatedValue as Float) * errorPathMeasure1.length
                errorDS = (it.animatedValue as Float) * errorPathMeasure.length
                invalidate()
            }


        }

        /**
         * 运行的时候 不处理UI更改
         */
        if (animator.isRunning) {
            loadingPath.reset()
            pathMeasure.getSegment(startValue, endValue, loadingPath, true)
            canvas?.let {
                canvas.drawPath(loadingPath, paint)
            }
            return
        }

        when (state) {

            0 -> {
                loadingPath.reset()
                pathMeasure.getSegment(startValue, endValue, loadingPath, true)
                canvas?.let {
                    canvas.drawPath(loadingPath, paint)
                }
            }
            //成功
            1 -> {
                loadingPath.reset()
                pathMeasure.getSegment(0f, endValue, loadingPath, true)
                successPathDs.reset()
                successPathMeasure.getSegment(0f, successDs, successPathDs, true)
                canvas?.let {
                    canvas.drawPath(loadingPath, paint)
                    canvas.drawPath(successPathDs, paint)
                }
            }
            2 -> {

                loadingPath.reset()
                pathMeasure.getSegment(0f, endValue, loadingPath, true)
                errorPathDs.reset()
                errorPathDs1.reset()
                errorPathMeasure.getSegment(0f, errorDS, errorPathDs, true)
                errorPathMeasure1.getSegment(0f, errorDS1, errorPathDs1, true)

                canvas?.let {
                    canvas.drawPath(loadingPath, paint)
                    canvas.drawPath(errorPathDs, paint)
                    canvas.drawPath(errorPathDs1, paint)
                }


            }

        }


    }


    fun onLoading() {
        //防止重复 调用
        if (!animator.isRunning)
            animator.start()
    }


    //重置 状态
    fun onRest() {
        state = 0
    }

    fun noSuccessful() {
        state = 1
        paint.color = Color.BLUE
    }

    fun onError() {
        state = 2
        paint.color = Color.RED
    }


    /**
     * 测量 view 尺寸
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //获取到 view 的尺寸
        val width = getSize(110, widthMeasureSpec)
        val height = getSize(110, heightMeasureSpec)
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