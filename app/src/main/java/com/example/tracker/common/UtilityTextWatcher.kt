package com.example.tracker.common

import android.text.Editable
import android.text.TextWatcher

class UtilityTextWatcher(private val onTextChanged: (String) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun afterTextChanged(s: Editable?) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.let {
            onTextChanged(s.toString())
        }
    }
}
