package com.lzy.libview.empty

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lzy.libview.R

/**
 * Created by zhaoyang.li5 on 2022/4/23 17:05
 */
class LibViewEmptyView(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context, attrs, defStyleAttr, 0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    init {
        LayoutInflater.from(context).inflate(R.layout.lib_view_empty_view_layout, this, true)
    }

    private val mTextView by lazy {
        findViewById<TextView>(R.id.text_view)
    }

    fun show(text: String, clickListener: OnClickListener) {
        visibility = VISIBLE
        mTextView.text = text
        setOnClickListener(clickListener)
    }

    fun hide() {
        visibility = GONE
    }
}