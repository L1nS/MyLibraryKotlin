package com.mylibrary.widget.text

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.AppCompatEditText
import com.mylibrary.R

class ClearEditText : AppCompatEditText, OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private var mClearDrawable: Drawable? = null

    /**
     * 控件是否有焦点
     */
    private var hasFocus = false


    constructor(context: Context?) : super(context!!)

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )


    private fun init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
//            mClearDrawable = getResources().getDrawable(R.drawable.icon_edit_clear)
            mClearDrawable = resources.getDrawable(R.drawable.icon_edit_clear, null)
        }
        mClearDrawable!!.setBounds(
            0, 0, (mClearDrawable!!.intrinsicWidth * 1.1).toInt(),
            (mClearDrawable!!.intrinsicHeight * 1.1).toInt()
        )
        //默认设置隐藏图标
        setClearIconVisible(false)
        //设置焦点改变的监听
        onFocusChangeListener = this
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mClearDrawable != null && event.action == MotionEvent.ACTION_UP) {
            val x = event.x.toInt()
            //判断触摸点是否在水平范围内
            val isInnerWidth = x > width - totalPaddingRight &&
                    x < width - paddingRight
            //获取删除图标的边界，返回一个Rect对象
            val rect = mClearDrawable!!.bounds
            //获取删除图标的高度
            val height = rect.height()
            val y = event.y.toInt()
            //计算图标底部到控件底部的距离
            val distance: Int = (getHeight() - height) / 2
            //判断触摸点是否在竖直范围内(可能会有点误差)
            //触摸点的纵坐标在distance到（distance+图标自身的高度）之内，则视为点中删除图标
            val isInnerHeight = y > distance && y < distance + height
            if (isInnerHeight && isInnerWidth) {
                this.setText("")
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(
            compoundDrawables[0], compoundDrawables[1],
            right, compoundDrawables[3]
        )
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFocus = hasFocus
        if (hasFocus) {
            if (text != null) {
                setClearIconVisible(text!!.isNotEmpty())
            } else {
                setClearIconVisible(false)
            }
        } else {
            setClearIconVisible(false)
        }
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (hasFocus) {
            setClearIconVisible(text.isNotEmpty())
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {}

    /**
     * 设置晃动动画
     */
    fun setShakeAnimation() {
        this.startAnimation(shakeAnimation(5))
    }

    companion object {
        /**
         * 晃动动画
         *
         * @param counts 1秒钟晃动多少下
         * @return
         */
        fun shakeAnimation(counts: Int): Animation {
            val translateAnimation: Animation = TranslateAnimation(0F, 10F, 0F, 0F)
            translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
            translateAnimation.duration = 1000
            return translateAnimation
        }
    }

    init {
        init()
    }
}