package com.example.downloader.ui.main

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.downloader.data.ui.UIDevice
import com.example.downloader.databinding.ItemListBinding
import com.google.android.material.listitem.SwipeableListItem

class MainDeviceListAdapter(private val listener: MainDeviceListAdapterListener) :
    RecyclerView.Adapter<MainDeviceListAdapter.ViewHolder>() {
    private val dataSet = ArrayList<UIDevice>()

    interface MainDeviceListAdapterListener {
        fun itemClicked(codename: String)
    }

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

    fun submitList(newList: List<UIDevice>) {
        dataSet.clear()
        dataSet.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(
        private val binding: ItemListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(device: UIDevice) {
            binding.listItemText.text = device.codename
            binding.listItemSupportText.text = device.maintainerName
            binding.downloadButton.setOnClickListener {
                listener.itemClicked(device.codename)
            }
            binding.listItemLayout.setSwipeState(
                if (device.isRevealed) SwipeableListItem.STATE_OPEN
                else SwipeableListItem.STATE_CLOSED, Gravity.END, false
            )
        }
    }
}