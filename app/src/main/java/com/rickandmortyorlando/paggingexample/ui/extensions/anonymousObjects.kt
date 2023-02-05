package com.rickandmortyorlando.paggingexample.ui.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun EditText.getTextWatcher(checkMyForm: () -> Unit): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            checkMyForm()
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            checkMyForm()
        }

        override fun afterTextChanged(s: Editable) {
            checkMyForm()
        }
    }
}


fun RecyclerView.myOnScrolled(makeAction: () -> Unit) {
    val layoutManager = LinearLayoutManager(this.context)
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    this.layoutManager = layoutManager
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val pastVisibleItems: Int = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + pastVisibleItems == totalItemCount) {
                    makeAction()
                }
            }
        }
    })
}

