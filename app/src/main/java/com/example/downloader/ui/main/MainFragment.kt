package com.example.downloader.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.downloader.databinding.FragmentMainBinding
import com.example.downloader.ui.common.BaseFragment
import kotlinx.coroutines.launch

class MainFragment : BaseFragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainDeviceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
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
        binding.deviceList.layoutManager = LinearLayoutManager(requireActivity())
        adapter = MainDeviceListAdapter()
        binding.deviceList.adapter = adapter

        viewModel.devices.observe { devices ->
            adapter.submitList(
                devices ?: emptyList()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "MainFragment"
        fun instance() = MainFragment()
    }
}