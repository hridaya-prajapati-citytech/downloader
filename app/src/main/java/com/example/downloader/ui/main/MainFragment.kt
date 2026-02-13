package com.example.downloader.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.downloader.data.mapper.DeviceListMapper
import com.example.downloader.databinding.FragmentMainBinding
import com.example.downloader.ui.common.BaseFragment
import com.example.downloader.ui.device.DeviceFragment
import kotlinx.coroutines.launch

class MainFragment(private val listener: FragmentListener) : BaseFragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainDeviceListAdapter


    interface FragmentListener {
        fun loadFragmentFromChild(f: Fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)


        ViewCompat.setOnApplyWindowInsetsListener(binding.deviceList) { v, insets ->
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel()
        lifecycleScope.launch {
            viewModel.loadDeviceList()
        }

        setupRecyclerView()
    }

    internal fun setupRecyclerView() {
        adapter =
            MainDeviceListAdapter(object : MainDeviceListAdapter.MainDeviceListAdapterListener {
                override fun itemClicked(codename: String) {
                    val deviceInfoFragment = DeviceFragment.instance(codename)
                    listener.loadFragmentFromChild(deviceInfoFragment)
                }
            })
        binding.deviceList.adapter = adapter

        viewModel.devices.observe { devices ->
            adapter.submitList(
                DeviceListMapper.toUIDeviceList(devices ?: emptyList())
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "MainFragment"
        fun instance(toLoadFragment: FragmentListener): MainFragment {
            return MainFragment(toLoadFragment)
        }
    }
}