package com.example.downloader.ui.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.downloader.R
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


    class MarginItemDecoration(context: Context) : ItemDecoration() {
        private val itemMargin: Int =
            context.resources.getDimensionPixelSize(R.dimen.list_item_margin)

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position != state.itemCount - 1) {
                outRect.bottom = itemMargin
            }
        }
    }


    interface FragmentListener {
        fun loadFragmentFromChild(f: Fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.deviceList.addItemDecoration(MarginItemDecoration(requireContext()))

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