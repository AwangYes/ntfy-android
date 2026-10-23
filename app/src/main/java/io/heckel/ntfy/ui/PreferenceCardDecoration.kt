package io.heckel.ntfy.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceGroupAdapter
import androidx.recyclerview.widget.RecyclerView
import io.heckel.ntfy.R

/**
 * Renders the visible preference rows as iOS-style inset grouped cards. Rows in
 * one category share a single rounded surface and are separated by hairlines.
 */
@SuppressLint("RestrictedApi") // PreferenceGroupAdapter is the only public way to inspect row types.
class PreferenceCardDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val cardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.ios_card)
    }
    private val dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.ios_separator)
        strokeWidth = context.resources.getDimension(R.dimen.ios_separator_height)
    }
    private val cornerRadius = context.resources.getDimension(R.dimen.ios_control_radius)
    private val horizontalInset = context.resources.getDimension(R.dimen.ios_preference_inset)
    private val groupGap = context.resources.getDimension(R.dimen.ios_preference_group_gap)
    private val dividerInset = context.resources.getDimension(R.dimen.ios_card_padding)

    override fun getItemOffsets(
        outRect: android.graphics.Rect,
        view: android.view.View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (!shouldDrawCard(parent, position)) return
        if (isFirstInGroup(parent, position)) outRect.top = groupGap.toInt()
        if (isLastInGroup(parent, position)) outRect.bottom = groupGap.toInt()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)
            val position = parent.getChildAdapterPosition(child)
            if (!shouldDrawCard(parent, position)) continue

            val first = isFirstInGroup(parent, position)
            val last = isLastInGroup(parent, position)
            val left = horizontalInset
            val right = parent.width - horizontalInset
            val top = child.top.toFloat()
            val bottom = child.bottom.toFloat()
            if (right <= left || bottom <= top) continue

            val radii = floatArrayOf(
                if (first) cornerRadius else 0f, if (first) cornerRadius else 0f,
                if (first) cornerRadius else 0f, if (first) cornerRadius else 0f,
                if (last) cornerRadius else 0f, if (last) cornerRadius else 0f,
                if (last) cornerRadius else 0f, if (last) cornerRadius else 0f
            )
            val path = Path().apply {
                addRoundRect(RectF(left, top, right, bottom), radii, Path.Direction.CW)
            }
            canvas.drawPath(path, cardPaint)

            if (!first) {
                canvas.drawLine(
                    left + dividerInset,
                    top,
                    right - dividerInset,
                    top,
                    dividerPaint
                )
            }
        }
    }

    private fun shouldDrawCard(parent: RecyclerView, position: Int): Boolean {
        val preference = preferenceAt(parent, position) ?: return false
        return preference !is PreferenceCategory
    }

    private fun isFirstInGroup(parent: RecyclerView, position: Int): Boolean {
        if (position == 0) return true
        return preferenceAt(parent, position - 1) is PreferenceCategory
    }

    private fun isLastInGroup(parent: RecyclerView, position: Int): Boolean {
        val adapter = parent.adapter ?: return true
        if (position >= adapter.itemCount - 1) return true
        return preferenceAt(parent, position + 1) is PreferenceCategory
    }

    private fun preferenceAt(parent: RecyclerView, position: Int): androidx.preference.Preference? {
        if (position < 0) return null
        val adapter = parent.adapter as? PreferenceGroupAdapter ?: return null
        if (position >= adapter.itemCount) return null
        return adapter.getItem(position)
    }
}
