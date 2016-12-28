package ru.gildor.kotlinandroid

import android.animation.ObjectAnimator
import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

class MainActivityViewModel(@DrawableRes val icon: Int) {
    
    val text = ObservableString("Hello world")
    val items = ObservableField<List<String>>(listOf("1", "2", "3"))
    fun request() = ::Request
    
    fun rotate(view: View) {
        ObjectAnimator.ofFloat(view, "rotation", 0f, 180f, 0f, 360f)
                .setDuration(5000)
                .apply { interpolator = AnticipateOvershootInterpolator() }
                .start()
    }
    
    fun showSnackbar(view: View) {
        val context = view.context
        Snackbar.make(view, context.getString(R.string.please_replace), Snackbar.LENGTH_LONG)
                .setAction(context.getString(R.string.action), {
                    Toast.makeText(context, R.string.toast, Toast.LENGTH_LONG).show()
                })
                .show()
    }
    
    fun args(): Map<String, Any> = mapOf("arg" to "Test this")
    
    fun entry(): Map.Entry<String, Any> = mapOf("arg" to "Test this").entries.first()
}

class Request(val id:Long)

class ObservableString(arg: String) : ObservableField<String>(arg)

@BindingAdapter("args")
fun args(view: View, holder: ArgsHolder?) {
    holder ?: return
    view.setOnClickListener {
        Toast.makeText(view.context, holder.args()["arg"] as String, Toast.LENGTH_SHORT).show()
    }
}

interface ArgsHolder {
    fun args(): Map<String, Any> = emptyMap()
}

@BindingAdapter("entry")
fun entry(view: View, holder: EntryHolder?) {
    holder ?: return
    view.setOnClickListener {
        Toast.makeText(view.context, holder.entry().value as String, Toast.LENGTH_SHORT).show()
    }
}

interface EntryHolder {
    fun entry(): Map.Entry<String, Any>
}

@BindingAdapter("strings")
fun setAdapter(view: AdapterView<*>, data: List<String>?) {
    data?.run {
        view.adapter = ArrayAdapter<String>(
                view.context,
                android.R.layout.simple_list_item_1,
                this
        )
    }
}