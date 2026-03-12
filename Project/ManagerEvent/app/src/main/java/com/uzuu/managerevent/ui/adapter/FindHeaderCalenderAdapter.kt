//package com.uzuu.managerevent.ui.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.uzuu.managerevent.databinding.ItemDateInCalenderBinding
//import com.uzuu.managerevent.domain.model.Times
//
//class FindHeaderCalenderAdapter(
//    val onClick: (Times) -> Unit
//): ListAdapter<Times, FindHeaderCalenderAdapter.VH>(DIFF) {
//
//    class VH(val binding: ItemDateInCalenderBinding) : RecyclerView.ViewHolder(binding.root)
//
//    companion object {
//        private val DIFF = object : DiffUtil.ItemCallback<Times>() {
//
//            override fun areItemsTheSame(oldItem: Times, newItem: Times): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: Times, newItem: Times): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): FindHeaderCalenderAdapter.VH {
//        val binding = ItemDateInCalenderBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return VH(binding)
//    }
//
//    override fun onBindViewHolder(holder: FindHeaderCalenderAdapter.VH, position: Int) {
//        val item = getItem(position)
//
//
//        holder.binding.root.setOnClickListener { onClick(item) }
//    }
//}
