package com.uzuu.learn19_5_bottomnav_basic.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn19_5_bottomnav_basic.databinding.ItemProductBinding
import com.uzuu.learn19_5_bottomnav_basic.databinding.ItemTimeBinding
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Times
import com.uzuu.learn19_5_bottomnav_basic.feature.main.UiItem
import com.uzuu.learn19_5_bottomnav_basic.ui.adapter.SearchAdapter.VH
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterAdapter(
    val onClick: (Times) -> Unit
): ListAdapter<Times, RegisterAdapter.VH>(DIFF) {

    class VH(val binding: ItemTimeBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Times>() {

            override fun areItemsTheSame(oldItem: Times, newItem: Times): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Times, newItem: Times): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterAdapter.VH {
        val binding = ItemTimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: RegisterAdapter.VH, position: Int) {
        val item = getItem(position)

        holder.binding.txtStartDay.text = format(item.timeStart)
        holder.binding.txtEndDay.text = format(item.timeEnd)

        holder.binding.root.setOnClickListener { onClick(item) }
    }

    private fun format(time: Long): String {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(Date(time))
    }
}