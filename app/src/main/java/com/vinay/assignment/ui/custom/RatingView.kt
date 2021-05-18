package com.vinay.assignment.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.vinay.assignment.R

class RatingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    //Define the first brush and draw a circle in the background
    private var mPaintBackCircle: Paint? = null

    //Define the second brush, draw the circle of the foreground color (the circle of the progress bar)
    private var mPaintFrontCircle: Paint? = null

    //Define the brush for drawing text
    private var mPaintText: Paint? = null

    //Define the width of the ring
    private var mStrokeWidth = 6f

    private var mRadius = 20f

    //Define rectangle
    private var mRectF: RectF? = null

    //Start progress
    private var mProgress = 50

    //Maximum progress
    private val mMax = 100

    //Define the width and height of the view
    private var mWidth = 0
    private var mHeight = 0
    private fun initRect() {
        if (mRectF == null) {
            mRectF = RectF()
            val viewSize = (mRadius * 2).toInt()
            val left = (mWidth - viewSize) / 2
            val top = (mHeight - viewSize) / 2
            val right = left + viewSize
            val bottom = top + viewSize
            mRectF!![left.toFloat(), top.toFloat(), right.toFloat()] = bottom.toFloat()
        }
    }

    //Initialize the brush
    fun init(progress: Int) {
        mProgress = progress
        mPaintBackCircle = Paint()
        //Set the color
        mPaintBackCircle!!.color = Color.WHITE
        //Set anti-aliasing
        mPaintBackCircle!!.isAntiAlias = true
        //Set to hollow
        mPaintBackCircle!!.style = Paint.Style.STROKE
        mPaintBackCircle!!.strokeWidth = mStrokeWidth
        mPaintFrontCircle = Paint()
        if(progress>50){
            mPaintFrontCircle!!.color = -0x99386a
        } else{
            mPaintFrontCircle!!.color = ResourcesCompat.getColor(getResources(), R.color.text_color_yellow, null)
        }
        mPaintFrontCircle!!.isAntiAlias = true
        mPaintFrontCircle!!.style = Paint.Style.STROKE
        mPaintFrontCircle!!.strokeWidth = mStrokeWidth
        mPaintText = Paint()
        mPaintText!!.color = Color.WHITE
        mPaintText!!.isAntiAlias = true
        mPaintText!!.textSize = 24f
        //Set the text to be centered
        mPaintText!!.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        initRect()
        //Get the progress percentage
        val angle = mProgress / mMax.toFloat() * 360
        canvas.drawCircle(
            mWidth / 2.toFloat(), mHeight / 2.toFloat(), mRadius,
            mPaintBackCircle!!
        )
        canvas.drawArc(mRectF!!, -90f, angle, false, mPaintFrontCircle!!)
        canvas.drawText(
            "$mProgress%",
            mWidth / 2.toFloat(),
            mHeight / 2.toFloat(),
            mPaintText!!
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        //Measure width and height
        mWidth = getRealSize(widthMeasureSpec)
        mHeight = getRealSize(heightMeasureSpec)

        //Save the measured width and height
        setMeasuredDimension(mWidth, mHeight)
    }

    //Measure the real size of View
    private fun getRealSize(measureSpec: Int): Int {
        var result = -1
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        result = if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            //Calculate by yourself
            (mRadius * 2 + mStrokeWidth).toInt()
        } else {
            size
        }
        return result
    }

    companion object {
        //Default radius
        private const val DEFAULT_RADIUS = 20
        private const val DEFAULT_STROKE_WIDTH = 6
    }

    init {

        //Get custom attributes
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.CirlceView)
            mRadius = array.getDimensionPixelSize(
                R.styleable.CirlceView_radius,
                DEFAULT_RADIUS
            ).toFloat()
            mStrokeWidth = array.getDimensionPixelSize(
                R.styleable.CirlceView_stroke_width,
                DEFAULT_STROKE_WIDTH
            ).toFloat()
            array.recycle()
        }
    }
}