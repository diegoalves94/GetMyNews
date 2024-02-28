package me.study.getmynews.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setToolbarTitle(title: String) {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = when (title) {
        "bbc-news" -> "BBC News"
        else -> "TechCrunch"
    }
}