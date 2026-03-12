package com.uzuu.learn12_1_resfulapi_basic.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn12_1_resfulapi_basic.databinding.ItemSimpleBinding
import com.uzuu.learn12_1_resfulapi_basic.feature.main.UiItem

class SimpleItemAdapter(
    private val onClick: (UiItem) -> Unit
) : ListAdapter<UiItem, SimpleItemAdapter.VH>(DIFF) {

    //viewholder
    class VH(val binding: ItemSimpleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemSimpleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        when (item) {
            is UiItem.PostItem -> {
                holder.binding.tvLine1.text = item.line1
                holder.binding.tvLine2.text = item.line2
            }
            is UiItem.CommentItem -> {
                holder.binding.tvLine1.text = item.line1
                holder.binding.tvLine2.text = item.line2
            }
        }
        holder.binding.root.setOnClickListener { onClick(item) }
    }

    //quan trọng, sự keest hợp ListAdapter + DiffUtil + sealed UI model, multiple view type
    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<UiItem>() {
            // kiem tra 2 item co cung id?
            override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                return when {
                    oldItem is UiItem.PostItem && newItem is UiItem.PostItem -> oldItem.id == newItem.id
                    oldItem is UiItem.CommentItem && newItem is UiItem.CommentItem -> oldItem.id == newItem.id
                    else -> false
                }
            }

            //kiem tra data same?
            override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}