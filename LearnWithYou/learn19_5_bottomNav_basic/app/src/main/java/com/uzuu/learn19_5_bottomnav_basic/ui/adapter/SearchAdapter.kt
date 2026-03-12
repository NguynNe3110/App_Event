package com.uzuu.learn19_5_bottomnav_basic.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn19_5_bottomnav_basic.databinding.ItemProductBinding
import com.uzuu.learn19_5_bottomnav_basic.feature.main.UiItem
import kotlin.text.trim

class SearchAdapter(
    private val onClick: (UiItem) -> Unit
    // listadapter phải dùng thư viện mới androidx.recyclerview.widget.ListAdapter<T, VH>
): ListAdapter<UiItem, SearchAdapter.VH>(DIFF) {

    class VH(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root)

    companion object {
        private val DIFF = object: DiffUtil.ItemCallback<UiItem>() {
            override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                return when {
                    oldItem is UiItem && newItem is UiItem -> oldItem.id == newItem.id
                    oldItem is UiItem && newItem is UiItem -> oldItem.id == newItem.id
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)

        holder.binding.productId.text = item.id.toString().trim()
        holder.binding.productName.text = item.name.trim()
        holder.binding.productPrice.text = item.price.toString().trim()

        // nhan item
        holder.binding.root.setOnClickListener { onClick(item) }
    }
}