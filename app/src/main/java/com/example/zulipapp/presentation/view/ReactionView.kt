package com.example.zulipapp.presentation.view

import android.content.Context
import android.graphics.*
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.zulipapp.R

class ReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var emoji = ""
        set(value) {
            field = "$value "
            requestLayout()
        }
    var counter = ""
        set(value) {
            field = value
            requestLayout()
        }

    private val commonPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val commonBounds = Rect()
    private val commonCoordinate = PointF()

    private val tempFontMetrics = Paint.FontMetrics()

    private var currentPaddingLeft = 0
    private var currentPaddingRight = 0
    private var currentPaddingTop = 0
    private var currentPaddingBottom = 0

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReactionView, defStyleAttr, defStyleRes)

        emoji = typedArray.getString(R.styleable.ReactionView_emoji) ?: "\uD83D\uDC4D "
        counter = typedArray.getString(R.styleable.ReactionView_text) ?: " "

        commonPaint.apply {
            textSize = typedArray.getDimension(R.styleable.ReactionView_textSize, resources.getDimension(R.dimen.chat_message_emoji_text))
            color = typedArray.getColor(
                R.styleable.ReactionView_textColor,
                ResourcesCompat.getColor(resources, R.color.text_gray_light, context.theme)
            )
//            ("make Selector for Color")
        }

        resolvePaddings()

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val commonText = emoji + counter

        commonPaint.getTextBounds(commonText, 0, commonText.length, commonBounds)

        val totalWidth = commonBounds.width() + currentPaddingLeft + currentPaddingRight
        val totalHeight = commonBounds.height() + currentPaddingTop + currentPaddingBottom

        val resultWidth = resolveSize(totalWidth, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight, heightMeasureSpec)

        setMeasuredDimension(resultWidth , resultHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        commonPaint.getFontMetrics(tempFontMetrics)
        commonCoordinate.x = currentPaddingLeft.toFloat()
        commonCoordinate.y = h/2f + commonBounds.height()/2 - tempFontMetrics.descent
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(emoji + counter, commonCoordinate.x, commonCoordinate.y, commonPaint)
    }

    private fun resolvePaddings(){
        val defaultHorizontalPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics)
        val defaultVerticalPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics)
        currentPaddingLeft = maxOf(defaultHorizontalPadding, paddingLeft.toFloat()).toInt()
        currentPaddingRight = maxOf(defaultHorizontalPadding, paddingRight.toFloat()).toInt()
        currentPaddingTop = maxOf(defaultVerticalPadding, paddingTop.toFloat()).toInt()
        currentPaddingBottom = maxOf(defaultVerticalPadding, paddingBottom.toFloat()).toInt()
    }

    fun setTextColor(color: Int){
        commonPaint.color = color
        invalidate()
    }

    // Не понятно для чего переопределять этот метод!
    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + SUPPORTED_DRAWABLE_STATE.size)
        if (isSelected) {
            mergeDrawableStates(drawableState, SUPPORTED_DRAWABLE_STATE)
        }
        return drawableState
    }

    companion object {
        private val SUPPORTED_DRAWABLE_STATE = intArrayOf(android.R.attr.state_selected)
    }

    // Сохранение состояния
    override fun onSaveInstanceState(): Parcelable {
        val state = SavedState(super.onSaveInstanceState()).also {
            it.emoji = emoji
            it.count = counter
            it.state = isSelected
        }
        return state
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if(state is SavedState){
            super.onRestoreInstanceState(state.superState)
            emoji = state.emoji
            counter = state.count
            isSelected = state.state
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    class SavedState : BaseSavedState {

        var emoji = ""
        var count = ""
        var state = false

        // For saving
        constructor(superState: Parcelable?) : super(superState)

        // For restore
        constructor(source: Parcel?) : super(source){
            source?.apply {
                emoji = readString().orEmpty()
                count = readString().orEmpty()
                state = readByte() > 0
            }
        }

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(emoji)
            out?.writeString(count)
            val byte: Byte = if(state) 1 else 0
            out?.writeByte(byte)
        }

        companion object{
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }
}