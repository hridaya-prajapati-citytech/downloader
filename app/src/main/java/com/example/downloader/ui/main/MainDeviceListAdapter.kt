package com.example.downloader.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.downloader.data.local.LocalDevice
import com.example.downloader.databinding.ItemListBinding

class MainDeviceListAdapter : RecyclerView.Adapter<MainDeviceListAdapter.ViewHolder>() {
    private val dataSet = ArrayList<LocalDevice>()


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {
        holder.bind(dataSet[position])
    }

    fun submitList(newList: List<LocalDevice>) {
        dataSet.clear()
        dataSet.addAll(newList)
        Log.d("TAG", "submitList: $newList")
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size

    class ViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(device: LocalDevice) {
            binding.listItemText.text = device.codename
            binding.listItemSupportText.text = device.maintainerName
        }
    }
}