package com.ummug.mobilebank.ui.History

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ummug.mobilebank.R
import com.ummug.mobilebank.databinding.FragmentHistoryBottomSheedBinding
import com.ummug.mobilebank.domain.adapters.IstoryAdapter
import com.ummug.mobilebank.domain.entity.History.Data
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryBottomSheedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryBottomSheedFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: IstoryAdapter
    private val viewModel:HistoryViewModel by viewModels()
    private lateinit var dataList:ArrayList<Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var binding=FragmentHistoryBottomSheedBinding.inflate(inflater,container,false)

        binding.batafsil.setOnClickListener {
            findNavController().navigate(R.id.action_historyBottomSheedFragment_to_historyFragment)
        }
        viewModel.listHistory()


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.openSuccesFlow.collect { data ->
                    dataList= ArrayList(data.data)

                    adapter= IstoryAdapter(dataList) { _, _ -> }
                    binding.rvHistorySheed.adapter=adapter
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openErrorFlow.collect{
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.openNetworkFlow.collect{
                    Toast.makeText(requireContext(), "internet yoq", Toast.LENGTH_SHORT).show()
                }
            }}
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryBottomSheedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryBottomSheedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}