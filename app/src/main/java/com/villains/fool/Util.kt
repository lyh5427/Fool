package com.villains.fool

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.villains.fool.presentation.Const

object Util {
    fun setFullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.setDecorFitsSystemWindows(false)
            val controller = activity.window.insetsController

            if (controller != null) {
                controller.hide(
                    WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            WindowInsetsControllerCompat(activity.window, activity.window.decorView).apply {
                // Hide the status bar
                hide(WindowInsetsCompat.Type.statusBars())
                // Allow showing the status bar with swiping from top to bottom
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
            activity.window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun countryImage(country: String, resources: Resources): Drawable {
        return when (country) {
            Const.KOREA -> resources.getDrawable(R.mipmap.ctr_korea, null)
            Const.EUROPE -> resources.getDrawable(R.mipmap.ctr_europe, null)
            Const.AUSTRALIA -> resources.getDrawable(R.mipmap.ctr_australia, null)
            Const.VIETNAM -> resources.getDrawable(R.mipmap.ctr_vietnam, null)
            Const.USA -> resources.getDrawable(R.mipmap.ctr_usa, null)
            Const.JAPAN -> resources.getDrawable(R.mipmap.ctr_japan, null)
            Const.CHINA -> resources.getDrawable(R.mipmap.ctr_china, null)
            Const.THAILAND -> resources.getDrawable(R.mipmap.ctr_thailand, null)
            Const.TAIWAN -> resources.getDrawable(R.mipmap.ctr_taiwan, null)
            else -> resources.getDrawable(R.mipmap.ctr_usa, null)
        }
    }

    fun countryName(context: Context, country: String): String {
        return when (country) {
            Const.KOREA -> context.getString(R.string.ctr_name_kr)
            Const.EUROPE -> context.getString(R.string.ctr_name_eur)
            Const.AUSTRALIA -> context.getString(R.string.ctr_name_ast)
            Const.VIETNAM -> context.getString(R.string.ctr_name_vietnam)
            Const.USA -> context.getString(R.string.ctr_name_usa)
            Const.JAPAN -> context.getString(R.string.ctr_name_japan)
            Const.CHINA -> context.getString(R.string.ctr_name_ch)
            Const.THAILAND -> context.getString(R.string.ctr_name_thailand)
            Const.TAIWAN -> context.getString(R.string.ctr_name_taiwan)
            else -> context.getString(R.string.ctr_name_kr)
        }
    }

    fun unitString(country: String, resources: Resources): String {
        return when (country) {
            Const.KOREA -> resources.getString(R.string.kore_name_unit_kr)
            Const.EUROPE -> resources.getString(R.string.kore_name_unit_eur)
            Const.AUSTRALIA -> resources.getString(R.string.kore_name_unit_ast)
            Const.VIETNAM -> resources.getString(R.string.kore_name_unit_vietnam)
            Const.USA -> resources.getString(R.string.kore_name_unit_usa)
            Const.JAPAN -> resources.getString(R.string.kore_name_unit_japan)
            Const.CHINA -> resources.getString(R.string.kore_name_unit_ch)
            Const.THAILAND -> resources.getString(R.string.kore_name_unit_thailand)
            Const.TAIWAN -> resources.getString(R.string.kore_name_unit_taiwan)
            Const.SINGAPORE -> resources.getString(R.string.kore_name_unit_singapore)
            else -> resources.getString(R.string.unit_code_usa)
        }
    }

    fun unit(country: String, resources: Resources) : String{
        return when (country) {
            Const.KOREA -> resources.getString(R.string.unit_code_kr)
            Const.EUROPE -> resources.getString(R.string.unit_code_eur)
            Const.AUSTRALIA -> resources.getString(R.string.unit_code_ast)
            Const.VIETNAM -> resources.getString(R.string.unit_code_vietnam)
            Const.USA -> resources.getString(R.string.unit_code_usa)
            Const.JAPAN -> resources.getString(R.string.unit_code_japan)
            Const.CHINA -> resources.getString(R.string.unit_code_ch)
            Const.THAILAND -> resources.getString(R.string.unit_code_thailand)
            Const.TAIWAN -> resources.getString(R.string.unit_code_taiwan)
            Const.SINGAPORE -> resources.getString(R.string.unit_code_singapore)
            else -> resources.getString(R.string.unit_code_usa)
        }
    }

    fun calculate(input: String): String {
        if (input == "") return "0"

        val plusSplit = input.split("+")
        var result = 0


        for (i in plusSplit) {
            val minSplit = i.split("-")
            var calResult = minSplit[0].toInt()

            for (i in 1 until minSplit.size) {
                calResult -= minSplit[i].toInt()
            }

            result += calResult
        }

        return result.toString()
    }
}