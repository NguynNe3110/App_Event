package com.uzuu.learn19_1_navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uzuu.learn19_1_navigation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    // để nullable vì view của fragment có thể bị destroy
    private var _binding: FragmentHomeBinding? = null
    // sau đó dùng binding như 1 shortcut để dùng như non-null
    // chỉ được dùng binding từ sau onCreateView đến trước onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //inflate giao diện ui
        //FragmentHomeBinding giống ActivityMainBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //trả về view gốc để hệ thống gawsn vào NavHostFragment container
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnGo.setOnClickListener {
            val input = binding.edtUserId.text.toString().trim()
            val userId = input.toIntOrNull() ?: 0

            // SafeArgs: class này được generate từ nav_graph.xml
            val action = HomeFragmentDirections
                .actionHomeFragmentToDetailFragment(userId)

            //navigation đọc action -> tạo fragmentDetail -> chuyển sang fragmentDetail
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
