package com.uzuu.learn15_roomdb_retrofitapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn15_roomdb_retrofitapi.databinding.ItemUserBinding
import com.uzuu.learn15_roomdb_retrofitapi.domain.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.VH>() {

    private val items = mutableListOf<User>()

    fun submit(list: List<User>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class VH(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val u = items[position]
        holder.binding.tvUser.text = "${u.id} - ${u.displayName}"
    }

    override fun getItemCount(): Int = items.size
}
