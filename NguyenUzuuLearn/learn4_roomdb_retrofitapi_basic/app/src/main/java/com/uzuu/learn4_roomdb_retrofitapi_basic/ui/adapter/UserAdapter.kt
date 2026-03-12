package com.uzuu.learn4_roomdb_retrofitapi_basic.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn4_roomdb_retrofitapi_basic.databinding.ItemUserBinding

import com.uzuu.learn4_roomdb_retrofitapi_basic.domain.model.User

class UserAdapter : RecyclerView.Adapter<com.uzuu.learn4_roomdb_retrofitapi_basic.ui.adapter.UserAdapter.VH>() {

    // kho data tạm của adapter.
    private val items = mutableListOf<User>()

    fun submit(list: List<User>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    //1 vh đại diện cho 1 dòng item
    //nó giữ binding để k phải findViewbyid.
    //viewholder để tái sử dụng view
    class VH(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    // fun tạo item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        //3tham số inflate
        val binding = ItemUserBinding.inflate(
            //lấy inflater để chuyển(thôi phôngf) xmL thành view
            LayoutInflater.from(parent.context),
            // parent ở đây là recycler (nơi chứa item)
            parent,
            // đừng attach ngay, recycler sẽ attach đúng lúc
            false
        )
        return VH(binding)
    }

    // recycler gọi this fun khi
    // nó có 1 viewHolder (1 item view) và muốn item đó hiển thị data của position
    // position là index trong list (0, 1, 2,...)
    override fun onBindViewHolder(holder: VH, position: Int) {
        val u = items[position]
        holder.binding.tvUser.text = "${u.id} - ${u.displayName}"
    }

    // nếu = 0 thì recycler k vẽ gì cả
    override fun getItemCount(): Int = items.size
}
