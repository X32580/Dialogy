package com.sinepow.yp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;


import com.sinepow.yp.dialog.R;


/**
 * 作者 :  叶鹏
 * 时间 :  2020/5/13 9:50
 * 邮箱 :  1632502697@qq.com
 * 简述 :  自定义view  显示电池剩余电量
 * 更新 :
 * 时间 :
 * 版本 : V 1.0
 */
public class BatteryView extends View {


    //画笔
    private Paint paint;
    private Paint wavePaint;
    private Paint textPaint;

    private int mWaveDx;
    private int dx;


    public BatteryView(Context context) {
        super(context,null);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);

    }


    private void init(Context context, AttributeSet attributeSet) {

        TypedArray ty  = context.obtainStyledAttributes(attributeSet,R.styleable.BatteryView);

        int batteryColor = ty.getColor(R.styleable.BatteryView_batteryColor, Color.BLUE);
        int waveColo = ty.getColor(R.styleable.BatteryView_batteryWaveColor, Color.BLUE);
        int textColor = ty.getColor(R.styleable.BatteryView_prColor, Color.GRAY);
        int pr = ty.getInteger(R.styleable.BatteryView_progress,50);

        ty.recycle();

        paint = new Paint();
        paint.setColor(batteryColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);
        wavePaint = new Paint();
        wavePaint.setColor(waveColo);
        wavePaint.setStyle(Paint.Style.FILL);
        wavePaint.setStrokeWidth(paintWidth);

        textPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(60f);


        mWaveDx = getResources().getDisplayMetrics().widthPixels;

        setProgress(pr);

    }

    private int getSize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式自适应
                //我们将大小取最大值,你也可以取其他值
                Math.min(mySize, size);
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }


    /**
     * 测量 view 尺寸
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //获取到 view 的尺寸
        int width = getSize(100, widthMeasureSpec);
        int height = getSize(200, heightMeasureSpec);


        super.onMeasure(width, height);


        //设置测量尺寸
        setMeasuredDimension(width, height);


    }

    /**
     * 开始绘制
     * 你妈的 坐标是从0开始的
     * left 0 top 0 开始 而不是获取当前视图的 getLeft 等等
     *
     * @param canvas
     */


    //  线条宽度 6
    private int paintWidth = 10;

    //电池的圆角值
    private int batteryRound = 30;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        Path path = new Path();
//
//        path.reset();
//
//        //贝赛尔的起始点moveTo(x,y)
//        path.moveTo(rect.left, getHeight() / 2);
        //quadTo 参数讲解(x1,y1,x2,y2)
        //x1:控制点x坐标
        //y1:在控制点y坐标
        //x2：终点x坐标
        //y2:终点y坐标


        // 第一步路径 从控件最左边 画到 控件中心 控制点在 控件的4/1 中心 x2 第一笔的终点应该减去 画笔的 宽度
//        path.quadTo(getWidth() / 2 - getWidth()/4, getHeight() / 2 - 40, getWidth() / 2-paintWidth, getHeight() / 2);

        //第二步 从控件的中线减去画笔的宽度 得到起点  画到外部电池圈结束 y轴 不变
//        path.quadTo(getWidth() / 2 -paintWidth + getWidth()/4, getHeight() / 2 + 40, rect.right, getHeight() / 2);


//rQuadTo参数讲解（dx1,dy1,dx2,dy2）：
//dx1：控制点相对起点的x位移
//dy1：控制点相对起点的y位移
//dx2：终点相对起点的x位移
//dy2：终点相对起点的y位移
//        path.rQuadTo(getWidth()/4-paintWidth, -30, getWidth() / 2 - paintWidth, 0);
//        path.rQuadTo(getWidth() /4-paintWidth, 30, getWidth() / 2 - paintWidth, 0);


//        canvas.drawPath(path, paint);

        drawBattery(canvas);

        drawWave(canvas);

        drawText(canvas);

    }




    /**
     * 绘制电池顶部
     */
    //电池外圈绘制 rect
    RectF batteryRect ;
    //电池顶部 rect
    RectF batteryTop;
    //
    Path batteryPath ;
    private void drawBattery(Canvas canvas){

        if (batteryRect==null){
            batteryRect = new RectF();
        }
        if (batteryTop ==null){
            batteryTop = new RectF();
        }
        if (batteryPath==null){
            batteryPath = new Path();
        }

        //重置路径
        batteryPath.reset();
        //外部矩形 等于 总宽高减去 画笔大小
        batteryRect.left = 0 + paintWidth;
        batteryRect.right = getMeasuredWidth() - paintWidth;
        batteryRect.bottom = getMeasuredHeight() - paintWidth;

        //顶部电池突出部分 为 宽度的4/1  是顶部突出的总高度
        batteryRect.top = 0 + paintWidth + getMeasuredWidth() / 4;

        //画出 电池的主体圈
        canvas.drawRoundRect(batteryRect, batteryRound, batteryRound, paint);



        batteryTop.left = 0 + paintWidth + getMeasuredWidth() / 4;
        batteryTop.right = getMeasuredWidth() - paintWidth - getMeasuredWidth() / 4;
        batteryTop.top = 0 + paintWidth;
        // 底部需要和下一个电池相交 不能减去 画笔的大小
        batteryTop.bottom = batteryRect.top;
        //绘制电池顶部 顶部圆角 减少一半
        canvas.drawRoundRect(batteryTop, batteryRound/2, batteryRound/2, paint);
        // 此时需要减去 外部描边的 大小
        batteryRect.set(batteryRect.left+paintWidth,batteryRect.top+paintWidth,batteryRect.right-paintWidth,batteryRect.bottom-paintWidth);
        //添加 电池主题 path
        batteryPath.addRoundRect(batteryRect,batteryRound,batteryRound, Path.Direction.CW);

        //计算出电池的宽度高度
        batteryWidth = batteryRect.right - batteryRect.left;
        batteryHeight = batteryRect.top- batteryRect.bottom;


        //裁剪电池圈外的不显示
        canvas.clipPath(batteryPath);




    }



    /**
     *  绘制内部波浪
     * @param canvas
     */

    private Path wavePath ;

    //进度值
    private float progress = 0.5f;

    //绘制电池的高度与 宽度
    private float batteryWidth;

    private float batteryHeight;
    //进度值对应的 y轴值
    private float py ;

    private void drawWave(Canvas canvas){

        if(wavePath==null){
            wavePath =new Path();
        }
        wavePath.reset();
        py = batteryHeight*progress;
        wavePath.moveTo(-mWaveDx + dx, batteryRect.bottom+py);
        for (int i = -mWaveDx; i < getWidth() + mWaveDx; i += mWaveDx) {
            wavePath.rQuadTo(mWaveDx / 4, -30, mWaveDx / 2, 0);
            wavePath.rQuadTo(mWaveDx / 4, 30, mWaveDx / 2, 0);

        }
        //绘制封闭的区域
        wavePath.lineTo(getWidth(), getHeight());
        wavePath.lineTo(0, getHeight());
        wavePath.close();
        canvas.drawPath(wavePath, wavePaint);
    }


    /**
     * 绘制文字
     * @param canvas
     */
    private float textX;
    private float textY;
    private  Paint.FontMetrics fontMetrics;
    private void drawText(Canvas canvas){


       textX =  textPaint.measureText(prText);
       // 控件的宽高加上本身控件的 距离
       textX = batteryRect.left+(batteryWidth-textX)/ 2;
        //文字的y轴坐标
        fontMetrics  = textPaint.getFontMetrics();
       textY = batteryRect.top-batteryHeight/2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        canvas.drawText(prText,textX,textY,textPaint);

    }




    /**
     *  传入 百分比 0-100
     * @param pr
     */
    private String prText="0%" ;
    public void setProgress(int pr){
        if (pr>100)
            pr=100;
        if (pr<0)
            pr = 0;
        progress =pr*0.01f;
        prText =pr+"%";
    }


    /**
     *  创建并启动动画
     */
    private ValueAnimator valueAnimator;

    public void initAnimation() {
         valueAnimator = ValueAnimator.ofInt(0, mWaveDx);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            //水平方向的偏移量
            dx = (int) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.start();
    }


    /**
     *  开启动画
     */
    public void startAnimation(){

        if (valueAnimator.isStarted()){

            return;
        }
        valueAnimator.start();

    }

    /**
     * 暂停动画
     */
    public void pauseAnimator(){

        if (valueAnimator.isRunning()){
            valueAnimator.pause();
        }
    }

    /**
     * 取消动画
     */
    public void cancelAnimator(){

     valueAnimator.cancel();

    }



}
