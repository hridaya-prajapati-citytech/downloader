package com.example.downloader.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.downloader.data.ui.UIDevice
import com.example.downloader.databinding.ItemListBinding
import com.google.android.material.listitem.ListItemCardView
import com.google.android.material.listitem.RevealableListItem
import com.google.android.material.listitem.SwipeableListItem

class MainDeviceListAdapter(private val listener: MainDeviceListAdapterListener) :
    RecyclerView.Adapter<MainDeviceListAdapter.ViewHolder>() {
    private val dataSet = ArrayList<UIDevice>()

    interface MainDeviceListAdapterListener {
        fun itemClicked(codename: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], position, itemCount)
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
        private lateinit var currentDevice: UIDevice

        init {
            setupClickListeners()
            setupSwipeCallback()
        }

        private fun setupClickListeners() {
            binding.downloadButton.setOnClickListener {
                currentDevice.let { device ->
                    listener.itemClicked(device.codename)
                }
            }
        }

        private fun setupSwipeCallback() {
            binding.listItemCardView.addSwipeCallback(object : ListItemCardView.SwipeCallback() {
                override fun onSwipe(swipeOffset: Int) {
                    // noop
                }

                override fun <T> onSwipeStateChanged(
                    newState: Int, activeRevealableListItem: T, gravity: Int
                ) where T : android.view.View, T : RevealableListItem {

                    val device = currentDevice

                    when (newState) {
                        SwipeableListItem.STATE_SWIPE_PRIMARY_ACTION -> {
                            // noop
                        }

                        SwipeableListItem.STATE_OPEN -> {
                            device.isRevealed = true
                            device.swipeGravity = gravity
                        }

                        SwipeableListItem.STATE_CLOSED -> {
                            device.isRevealed = false
                            device.swipeGravity = gravity
                        }

                        SwipeableListItem.STATE_DRAGGING -> {
                            // noop
                        }

                        SwipeableListItem.STATE_SETTLING -> {
                            // noop
                        }
                    }

                    if (newState != SwipeableListItem.STATE_DRAGGING && newState != SwipeableListItem.STATE_SETTLING) {
                        device.swipeState = newState
                        device.swipeGravity = gravity
                    }
                }
            })
        }

        fun bind(device: UIDevice, position: Int, itemCount: Int) {
            currentDevice = device

            binding.listItemLayout.updateAppearance(position, itemCount)
            binding.listItemText.text = device.model
            binding.listItemSupportText.text = device.maintainerName

            binding.listItemLayout.setSwipeState(
                device.swipeState, device.swipeGravity, false
            )
        }
    }
}