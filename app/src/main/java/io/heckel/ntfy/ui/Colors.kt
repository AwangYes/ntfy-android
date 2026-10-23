package io.heckel.ntfy.ui

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors
import io.heckel.ntfy.R
import io.heckel.ntfy.util.isDarkThemeOn

class Colors {
    companion object {
        fun primary(context: Context): Int {
            return MaterialColors.getColor(context, R.attr.colorPrimary, Color.BLUE)
        }

        fun onPrimary(context: Context): Int {
            return MaterialColors.getColor(context, R.attr.colorOnPrimary, Color.WHITE)
        }

        fun notificationIcon(context: Context): Int {
            return MaterialColors.getColor(context, R.attr.colorPrimary, Color.BLUE)
        }

        fun linkColor(context: Context): Int {
            return MaterialColors.getColor(context, R.attr.colorPrimary, Color.BLUE)
        }

        fun itemSelectedBackground(context: Context): Int {
            return ContextCompat.getColor(context, R.color.ios_card_pressed)
        }

        fun cardBackgroundColor(context: Context): Int {
            return ContextCompat.getColor(context, R.color.ios_card)
        }

        fun cardSelectedBackgroundColor(context: Context): Int {
            return ContextCompat.getColor(context, R.color.ios_card_pressed)
        }

        fun statusBarNormal(context: Context, dynamicColors: Boolean, darkMode: Boolean): Int {
            return ContextCompat.getColor(context, R.color.ios_background)
        }

        fun shouldUseLightStatusBar(dynamicColors: Boolean, darkMode: Boolean): Boolean {
            return !darkMode
        }

        fun shouldUseLightNavigationBar(dynamicColors: Boolean, darkMode: Boolean): Boolean {
            return !darkMode
        }

        fun toolbarTextColor(context: Context, dynamicColors: Boolean, darkMode: Boolean): Int {
            return ContextCompat.getColor(context, R.color.md_theme_onBackground)
        }

        fun dangerText(context: Context): Int {
            return MaterialColors.getColor(context, R.attr.colorError, Color.RED)
        }

        fun swipeToRefreshColor(context: Context): Int {
            return MaterialColors.getColor(context, R.attr.colorPrimary, Color.BLUE)
        }
    }
}
