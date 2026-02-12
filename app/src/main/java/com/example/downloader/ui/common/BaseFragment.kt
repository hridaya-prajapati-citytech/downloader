package com.example.downloader.ui.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

open class BaseFragment : Fragment() {
    fun <T> LiveData<T>.observe(observer: (T?) -> Unit) =
        observe(this@BaseFragment, Observer { observer(it) })

    fun <T> LiveData<T>.observeNonNull(observer: (T) -> Unit) =
        observe { if (it != null) observer(it) }

    inline fun <reified T : BaseViewModel> getViewModel(): T =
        ViewModelProvider(this)[T::class.java]
}