package io.heckel.ntfy.ui

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import io.heckel.ntfy.R
import io.heckel.ntfy.util.isDarkThemeOn

/**
 * Shared setup for the iOS-style navigation bar. Top-level screens use a large
 * title under the compact toolbar; pushed screens use a centered inline title.
 */
object IosUi {
    fun setupAppBar(
        activity: AppCompatActivity,
        appBarLayout: AppBarLayout,
        compact: Boolean = false
    ): MaterialToolbar {
        val toolbar = appBarLayout.findViewById<MaterialToolbar>(R.id.toolbar)
        val darkMode = isDarkThemeOn(activity)
        val textColor = Colors.toolbarTextColor(activity, false, darkMode)

        appBarLayout.setBackgroundColor(Colors.statusBarNormal(activity, false, darkMode))
        toolbar.setTitleTextColor(textColor)
        toolbar.setNavigationIconTint(textColor)
        toolbar.overflowIcon?.setTint(textColor)

        activity.setSupportActionBar(toolbar)
        setCompact(activity, compact)

        WindowInsetsControllerCompat(activity.window, activity.window.decorView).apply {
            isAppearanceLightStatusBars = Colors.shouldUseLightStatusBar(false, darkMode)
            isAppearanceLightNavigationBars = Colors.shouldUseLightNavigationBar(false, darkMode)
        }
        return toolbar
    }

    fun setTitle(activity: AppCompatActivity, title: CharSequence?) {
        activity.title = title
        activity.supportActionBar?.title = title
        activity.findViewById<TextView>(R.id.app_bar_large_title)?.text = title
    }

    fun setCompact(activity: AppCompatActivity, compact: Boolean) {
        activity.findViewById<View>(R.id.app_bar_large_title)?.visibility =
            if (compact) View.GONE else View.VISIBLE
        activity.supportActionBar?.setDisplayShowTitleEnabled(compact)
    }

    fun setLargeTitleVisible(activity: AppCompatActivity, visible: Boolean) {
        activity.findViewById<View>(R.id.app_bar_large_title)?.visibility =
            if (visible) View.VISIBLE else View.GONE
    }
}
