package com.example.downloader.ui.main

import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.example.downloader.R
import com.example.downloader.databinding.ActivityMainBinding
import com.example.downloader.ui.common.BaseActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }

        if (savedInstanceState === null) {
            loadFragment(
                MainFragment.instance(object : MainFragment.FragmentListener {
                    override fun loadFragmentFromChild(f: Fragment) {
                        loadFragment(f, true)
                    }
                })
            )
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(
                        MainFragment.instance(object : MainFragment.FragmentListener {
                            override fun loadFragmentFromChild(f: Fragment) {
                                loadFragment(f, true)
                            }
                        }),
                    )
                }
            }
            true
        }

        grabPermissions()
    }

    internal fun grabPermissions() {
        var permissions = arrayOf<String>()
        if (!getSystemService(NotificationManager::class.java).areNotificationsEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions = permissions.plus(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            snackbar =
                Snackbar.make(binding.root, "Allow storage permission", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Settings") {
                        val getpermission = Intent()
                        getpermission.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                        startActivity(getpermission)
                        snackbar.dismiss()
                    }
            snackbar.show()
        } else if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                permissions = permissions.plus(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
        if (permissions.isNotEmpty()) {
            requestPermissions(permissions, 101)
            Toast.makeText(this, "Notification and Storage Permission Required", Toast.LENGTH_SHORT)
                .show()
            return
        }

    }

}