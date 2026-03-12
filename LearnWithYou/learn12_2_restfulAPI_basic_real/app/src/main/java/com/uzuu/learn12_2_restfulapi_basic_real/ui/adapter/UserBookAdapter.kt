package com.uzuu.learn12_2_restfulapi_basic_real.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn12_2_restfulapi_basic_real.databinding.ItemDisplayBinding
import com.uzuu.learn12_2_restfulapi_basic_real.feature.main.UiItem

class UserBookAdapter(
    private val onItemClick: (UiItem) -> Unit,
    private val onMenuClick: (UiItem, View) -> Unit
): ListAdapter<UiItem, UserBookAdapter.VH>(DIFF) {

    class VH(val binding: ItemDisplayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemDisplayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        println("DEBUG itemmmm = $item")

        val clear = holder.binding

        clear.upLeft.text = ""
        clear.upRight.text = ""
        clear.downLeft.text = ""
        clear.downRight.text = ""

        when(item){
            is UiItem.UserItem -> {
                holder.binding.upLeft.text = item.id.toString()
                holder.binding.upRight.text = item.fullName
                holder.binding.downLeft.text = "User"
                holder.binding.downLeft.setTextColor(Color.parseColor("#23FF00"))
                holder.binding.downRight.text = item.name
            }
            is UiItem.BookItem -> {
                holder.binding.upLeft.text = item.id.toString()
                holder.binding.upRight.text = item.name
                holder.binding.downLeft.text = "Book"
                holder.binding.downLeft.setTextColor(Color.parseColor("#FF0000"))
                holder.binding.downRight.text = "SL: ${item.quantity}"
            }
        }
        holder.binding.root.setOnClickListener { onItemClick(item) } // binding.root or itemView
        holder.binding.optionMenu.setOnClickListener { onMenuClick(item, it) } // holder.binding.optionMenu or it
    }

    companion object {
        private val DIFF = object: DiffUtil.ItemCallback<UiItem>() {
            override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                return when {
                    oldItem is UiItem.UserItem && newItem is UiItem.UserItem -> oldItem.id == newItem.id
                    oldItem is UiItem.BookItem && newItem is UiItem.BookItem -> oldItem.id == newItem.id
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}