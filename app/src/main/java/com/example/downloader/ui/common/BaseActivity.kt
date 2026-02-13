package com.example.downloader.ui.common

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.downloader.R

abstract class BaseActivity : AppCompatActivity() {
    fun <T> LiveData<T>.observe(observer: (T?) -> Unit) =
        observe(this@BaseActivity, Observer { observer(it) })

    fun <T> LiveData<T>.observeNonNull(observer: (T) -> Unit) =
        observe { if (it != null) observer(it) }

    inline fun <reified T : BaseViewModel> getViewModel(): T =
        ViewModelProvider(this)[T::class.java]

    fun loadFragment(f: Fragment, addBack: Boolean = false) {
        if (addBack) {
            supportFragmentManager.commit {
                replace(R.id.container, f).addToBackStack(f.tag).show(f)
            }
        } else {
            supportFragmentManager.commit {
                replace(R.id.container, f).show(f)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }
}