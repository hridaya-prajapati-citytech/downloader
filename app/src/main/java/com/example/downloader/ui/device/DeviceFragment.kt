package com.example.downloader.ui.device

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import com.example.downloader.DownloaderApplication
import com.example.downloader.R
import com.example.downloader.databinding.FragmentDeviceBinding
import com.example.downloader.ui.common.BaseFragment
import com.ketch.Ketch
import kotlinx.coroutines.launch

class DeviceFragment(private val codename: String) : BaseFragment() {
    private var _binding: FragmentDeviceBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DeviceViewModel
    private lateinit var ketch: Ketch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDeviceBinding.inflate(inflater, container, false)

        val view = binding.root
        ketch = (requireContext().applicationContext as DownloaderApplication).ketch

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

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel()
        lifecycleScope.launch { viewModel.loadDeviceInfo(codename) }
        setupDeviceInfo()
    }

    internal fun setupDeviceInfo() {
        viewModel.loading.observeNonNull { isLoading ->
            binding.loadingIndicator.isVisible = isLoading
            binding.deviceInfoLayout.isVisible = !isLoading
        }

        viewModel.deviceInfo.observeNonNull { localDeviceInfo ->
            run {
                binding.loadingIndicator.isVisible = false
                binding.deviceModel.text =
                    getString(R.string.model_placeholder, localDeviceInfo.model)
                binding.deviceVendor.text =
                    getString(R.string.vendor_placeholder, localDeviceInfo.vendor)
                binding.deviceMaintainer.text = getString(
                    R.string.maintainer_placeholder,
                    localDeviceInfo.maintainer.firstOrNull()?.maintainerName
                )
                binding.deviceStatus.text =
                    getString(R.string.status_placeholder, localDeviceInfo.isActive.toString())
                binding.lastUpdated.text =
                    getString(R.string.last_updated_placeholder, localDeviceInfo.lastUpdated)
                binding.releaseStatus.text =
                    getString(R.string.release_status_placeholder, localDeviceInfo.release)
                binding.deviceArchive.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).setData(localDeviceInfo.archive.toUri()))
                }
                binding.deviceDownload.setOnClickListener {
                    ketch.download(
                        url = localDeviceInfo.downloadLink,
                        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path,
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "DeviceFragment"
        fun instance(codename: String): DeviceFragment {
            return DeviceFragment(codename)
        }
    }


}