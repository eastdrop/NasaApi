package com.example.m17_nasa_api.pagedphotoslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.m17_nasa_api.R
import com.example.m17_nasa_api.databinding.FragmentPhotosPagedListBinding
import com.example.m17_nasa_api.models.PhotoResponse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PhotosListFragment : Fragment() {
    private var _binding: FragmentPhotosPagedListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotosListViewModel by viewModels()
    private val pagedAdapter = PhotosListAdapter { photo -> onItemClick(photo) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosPagedListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefresh.setOnRefreshListener {
            pagedAdapter.refresh()
        }

        pagedAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pagedPhotos.onEach {
            pagedAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun onItemClick(item: PhotoResponse) {
        findNavController().navigate(R.id.SecondFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}