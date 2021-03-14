package com.fukuhara.designsystem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.Group

class PageNavigator(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var pageIndicatorText: TextView
    private lateinit var viewGroup: Group

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, DEFAULT_STYLE_ATTR)
    constructor(context: Context) : this(context, null)

    init {
        attrs?.let {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.ds_page_navigator, this, true)

            leftButton = view.findViewById(R.id.ds_pageindicator_left_button)
            rightButton = view.findViewById(R.id.ds_pageindicator_right_button)
            pageIndicatorText = view.findViewById(R.id.ds_pageindicator_page_info_text)
            viewGroup = view.findViewById(R.id.ds_pageindicator_view_group)

            setAttributes(it)
        }
    }

    private fun setAttributes(attrs: AttributeSet) {
        val attributeSet = context.theme.obtainStyledAttributes(attrs, R.styleable.PageNavigator, DEFAULT_STYLE_ATTR, DEFAULT_STYLE_RES)

        attributeSet.getBoolean(R.styleable.PageNavigator_leftButtonEnable, false)
        attributeSet.getBoolean(R.styleable.PageNavigator_rightButtonEnable, false)
        attributeSet.getString(R.styleable.PageNavigator_pageIndicatorText)

        attributeSet.recycle()
    }

    fun onLeftButtonClickListener(onClickListener: OnClickListener) {
        leftButton.setOnClickListener(onClickListener)
    }

    fun onRightButtonClickListener(onClickListener: OnClickListener) {
        rightButton.setOnClickListener(onClickListener)
    }

    fun setLeftButtonVisibility(isEnabled: Boolean) {
        leftButton.isEnabled = isEnabled
    }

    fun setRightButtonVisibility(isEnabled: Boolean) {
        rightButton.isEnabled = isEnabled
    }

    fun setPageIndicatorText(currentPage: String, totalPages: String) {
        pageIndicatorText.text = context.getString(R.string.ds_page_navigator_page_index_text, currentPage, totalPages)
    }

    fun setButtonsClickableState(isButtonClickable: Boolean) {
        leftButton.isClickable = isButtonClickable
        rightButton.isClickable = isButtonClickable
    }

    fun hideComponents() {
        setLeftButtonVisibility(false)
        setRightButtonVisibility(false)
        pageIndicatorText.text = EMPTY_STRING

        handleGroupVisibility(View.GONE)
    }

    fun showComponents() {
        handleGroupVisibility(View.VISIBLE)
    }

    private fun handleGroupVisibility(state: Int) {
        viewGroup.visibility = state
    }

    companion object {
        private const val DEFAULT_STYLE_ATTR = 0
        private const val DEFAULT_STYLE_RES = 0
        private const val EMPTY_STRING = ""
    }
}