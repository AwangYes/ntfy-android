package io.heckel.ntfy.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceGroupAdapter
import androidx.recyclerview.widget.RecyclerView
import io.heckel.ntfy.R

/**
 * Draws each visible preference row on an inset iOS-style card. Keeping this in
 * an ItemDecoration avoids replacing AndroidX Preference's internal row layouts.
 */
@SuppressLint("RestrictedApi") // PreferenceGroupAdapter is the only public way to inspect row types.
class PreferenceCardDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val cardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.ios_card)
    }
    private val cornerRadius = context.resources.getDimension(R.dimen.ios_control_radius)
    private val horizontalInset = context.resources.getDimension(R.dimen.ios_preference_inset)
    private val verticalGap = context.resources.getDimension(R.dimen.ios_preference_gap)

    override fun getItemOffsets(
        outRect: android.graphics.Rect,
        view: android.view.View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (!shouldDrawCard(parent, position)) return
        outRect.top = verticalGap.toInt()
        outRect.bottom = verticalGap.toInt()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)
            val position = parent.getChildAdapterPosition(child)
            if (!shouldDrawCard(parent, position)) continue
            val left = horizontalInset
            val right = parent.width - horizontalInset
            val top = child.top + verticalGap
            val bottom = child.bottom - verticalGap
            if (right <= left || bottom <= top) continue
            canvas.drawRoundRect(RectF(left, top, right, bottom), cornerRadius, cornerRadius, cardPaint)
        }
    }

    private fun shouldDrawCard(parent: RecyclerView, position: Int): Boolean {
        if (position == RecyclerView.NO_POSITION) return false
        val adapter = parent.adapter as? PreferenceGroupAdapter ?: return false
        if (position !in 0 until adapter.itemCount) return false
        return adapter.getItem(position) !is PreferenceCategory
    }
}
