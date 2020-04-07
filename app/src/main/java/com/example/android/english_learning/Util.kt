package com.example.android.english_learning

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.android.english_learning.statistics.StatisticsViewModel

fun formatStats(stats: List<StatisticsViewModel.Stats>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title_stats))
        stats.forEach {
            append("<br>")
            append(resources.getString(R.string.mode))
            append("${it.mode}<br>")
            append(resources.getString(R.string.theme))
            append("${it.theme}<br>")
            append(resources.getString(R.string.results))
            append("${it.rightNum}/${it.num}<br>")
            append(resources.getString(R.string.percentage))
            append("${it.percentage}%<br>")
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
