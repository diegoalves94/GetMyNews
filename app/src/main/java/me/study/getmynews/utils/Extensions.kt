package me.study.getmynews.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Fragment.setToolbarTitle(title: String) {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = when (title) {
        "bbc-news" -> "BBC News"
        else -> "TechCrunch"
    }
}

fun convertDateFormat(
    inputDateTime: String?
): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    return try {
        val date: Date = inputFormat.parse(inputDateTime)
        outputFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}