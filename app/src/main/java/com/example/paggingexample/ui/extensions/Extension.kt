package com.example.paggingexample.ui.extensions

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.paggingexample.R

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(urlImage: String) {
    Glide.with(context).load(urlImage).placeholder(R.drawable.loading_animation)
        .transition(DrawableTransitionOptions.withCrossFade()).circleCrop().into(this)
}

fun Fragment.enableToolbarForListeners(toolbar: Toolbar) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).supportActionBar?.title = ""
    setHasOptionsMenu(true)
}


fun Fragment.myOnCreateOptionsMenu(
    menu: Menu,
    isSearchIconVisible: Boolean = true,
    setonMenuItemActionExpand: () -> Unit = {},
    myOnMenuItemActionCollapse: () -> Unit = {},
    myOnQueryTextSubmit: (myQuery: String) -> Unit = {},
    myOnQueryTextChange: (MyNewText: String) -> Unit = {},
    clickOnChange: () -> Unit = {}
) {
    activity?.menuInflater?.inflate(R.menu.menu_search, menu)
    val searchItem = menu.findItem(R.id.search)
    val change = menu.findItem(R.id.change)
    searchItem.isVisible = isSearchIconVisible
    change.setOnMenuItemClickListener {
        clickOnChange()
        false
    }

    searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
            setonMenuItemActionExpand()
            return true
        }

        override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
            myOnMenuItemActionCollapse()
            searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
            return true
        }
    })
    val searchView: SearchView = searchItem.actionView as SearchView
    searchView.queryHint = "Buscar"
    searchView.gravity = View.TEXT_ALIGNMENT_CENTER
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            myOnQueryTextSubmit(query)
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            myOnQueryTextChange(newText)
            return false
        }
    })
}


