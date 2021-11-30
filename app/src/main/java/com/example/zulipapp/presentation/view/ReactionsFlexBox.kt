package com.example.zulipapp.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.zulipapp.R

class ReactionsFlexBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val horizontalPadding = resources.getDimension(R.dimen.reactions_horizontal_padding).toInt()
    private val verticalPadding = resources.getDimension(R.dimen.reactions_vertical_padding).toInt()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec) - paddingStart - paddingEnd

        var width = 0
        var height = 0
        var highest = 0
        var maxWidth = 0
        when (widthMode) {
            MeasureSpec.UNSPECIFIED -> {
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    measureChild(child)
                    width += child.measuredWidth
                    if (child.measuredHeight > height) {
                        height = child.measuredHeight
                    }
                }
                width += horizontalPadding * (childCount - 1)
            }
            MeasureSpec.AT_MOST -> {
                var currentPadding = 0
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    measureChild(child)
                    val childWidth = child.measuredWidth
                    val childHeight = child.measuredHeight
                    if (width + currentPadding + childWidth <= widthSize) {
                        width += childWidth
                        currentPadding += horizontalPadding
                        if (highest < childHeight) {
                            highest = childHeight
                        }
                    } else {
                        if (maxWidth < width + currentPadding - horizontalPadding) {
                            maxWidth = width + currentPadding - horizontalPadding
                        }
                        width = childWidth
                        currentPadding = horizontalPadding
                        height += highest + verticalPadding
                        highest = childHeight
                    }
                }
                height += highest
                width = maxOf(width + currentPadding - horizontalPadding, maxWidth)
            }
            MeasureSpec.EXACTLY -> {
                var currentPadding = 0
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    measureChild(child)
                    val childWidth = child.measuredWidth
                    val childHeight = child.measuredHeight
                    if (width + currentPadding + childWidth <= widthSize) {
                        width += childWidth
                        currentPadding += horizontalPadding
                        if (highest < childHeight) {
                            highest = childHeight
                        }
                    } else {
                        width = childWidth
                        currentPadding = horizontalPadding
                        height += highest + verticalPadding
                        highest = childHeight
                    }
                }
                height += highest
                width = widthSize
            }
        }
        setMeasuredDimension(width + paddingStart + paddingEnd, height + paddingTop + paddingBottom)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = measuredWidth - paddingEnd

        var currentLeft = paddingStart
        var currentTop = paddingTop

        var highest = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            if (currentLeft + childWidth <= width) {
                child.layout(
                    currentLeft,
                    currentTop,
                    currentLeft + childWidth,
                    currentTop + childHeight
                )
                currentLeft += childWidth + horizontalPadding
                if (highest < currentTop + childHeight) {
                    highest = currentTop + childHeight
                }
            } else {
                currentTop = highest + verticalPadding
                highest += verticalPadding + childHeight
                currentLeft = paddingLeft
                child.layout(
                    currentLeft,
                    currentTop,
                    currentLeft + childWidth,
                    highest
                )
                currentLeft += childWidth + horizontalPadding
            }
        }
    }

    private fun measureChild(child: View) {
        var size = child.layoutParams.width
        var mode = if (size >= 0) { MeasureSpec.EXACTLY } else { MeasureSpec.UNSPECIFIED }
        val childWidthSpec = MeasureSpec.makeMeasureSpec(size, mode)

        size = child.layoutParams.height
        mode = if (size >= 0) { MeasureSpec.EXACTLY } else { MeasureSpec.UNSPECIFIED }
        val childHeightSpec = MeasureSpec.makeMeasureSpec(size, mode)

        child.measure(childWidthSpec, childHeightSpec)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is MarginLayoutParams
    }
}