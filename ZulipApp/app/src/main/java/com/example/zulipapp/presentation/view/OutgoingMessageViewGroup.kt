package com.example.zulipapp.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.example.zulipapp.R
import com.example.zulipapp.presentation.chat.adapter.ReactionItem
import com.example.zulipapp.presentation.util.EmojiCodeMapper

class OutgoingMessageViewGroup  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    private var onPlusClickListener: OnClickListener? = null
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val messageView: TextView
    private val reactionsView: ReactionsFlexBox

    private val marginReactionsTop = 30

    private val plusView = ImageView(context)

    init {
        inflate(context, R.layout.message_outgoing_view_group_layout, this)

        messageView = getChildAt(MESSAGE_INDEX) as TextView
        reactionsView = getChildAt(REACTIONS_INDEX) as ReactionsFlexBox

        plusView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.button_plus_reaction, context.theme))
    }

    fun setOnPlusClickListener(listener: OnClickListener){
        onPlusClickListener = listener
        plusView.setOnClickListener(onPlusClickListener)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        require(childCount == 2) {"Child count should be 2, but was $childCount"}

        measureChildWithMargins(messageView, widthMeasureSpec, 0, heightMeasureSpec, 0)
        measureChildWithMargins(reactionsView, widthMeasureSpec, 0, heightMeasureSpec, 0)

        val totalWidth = maxOf(messageView.measuredWidth, reactionsView.measuredWidth)
        val totalHeight = if(reactionsView.measuredHeight > 0)
            messageView.measuredHeight + marginReactionsTop + reactionsView.measuredHeight
        else messageView.measuredHeight

        val resultWidth = resolveSize(totalWidth, widthMeasureSpec)
        val resultHeight = resolveSize(totalHeight, heightMeasureSpec)

        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        messageView.layout(
            right - paddingRight - messageView.measuredWidth,
            paddingTop,
            right - paddingRight,
            paddingTop + messageView.measuredHeight)
        reactionsView.layout(
            right - paddingRight - reactionsView.measuredWidth,
            paddingTop + messageView.measuredHeight + marginReactionsTop,
            right - paddingRight,
            paddingTop + messageView.measuredHeight + marginReactionsTop + reactionsView.measuredHeight)
    }

    fun setMessageText(message: String){
        messageView.text =
        HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY).dropLast(2)
        requestLayout()
    }

    fun setReactions(reactionItems: List<ReactionItem>, onReactionClickListener: (reaction: ReactionItem) -> Unit){
        reactionsView.removeAllViews()
        reactionItems.forEach { reaction ->
            val reactionView = ReactionView(context)
            reactionView.setOnClickListener{
                onReactionClickListener(reaction)
            }
            reactionView.isSelected = reaction.isSelected
            reactionView.background = ResourcesCompat.getDrawable(resources, R.drawable.reaction_view_bg, context.theme)
            reactionView.emoji = EmojiCodeMapper.codeToEmoji(reaction.emojiCode)
            reactionView.counter = reaction.userIds.size.toString()
            reactionsView.addView(reactionView)
        }
        if(reactionsView.childCount > 0) reactionsView.addView(plusView)
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

    companion object{
        const val MESSAGE_INDEX = 0
        const val REACTIONS_INDEX = 1
    }
}