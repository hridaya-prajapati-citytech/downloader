package com.example.downloader.ui.main

import android.os.Bundle
import com.example.downloader.R
import com.example.downloader.databinding.ActivityMainBinding
import com.example.downloader.ui.common.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState === null) {
            loadFragment(MainFragment.instance())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(MainFragment.instance())
                }
            }
            true
        }
    }

}