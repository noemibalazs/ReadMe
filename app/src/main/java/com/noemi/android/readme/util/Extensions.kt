package com.noemi.android.readme.util

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.TextView

fun Context.openActivity(dest: Class<*>) {
    startActivity(Intent(this, dest))
}


fun TextView.setSpannableText(text: String) {
    val spannableString = SpannableString(text)
    val index = text.indexOf(":")
    spannableString.setSpan(
        RelativeSizeSpan(1.1f),
        0,
        index + 1,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannableString.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        index + 1,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = spannableString
}