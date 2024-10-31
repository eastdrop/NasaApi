package com.example.m17_nasa_api.pagedphotoslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.m17_nasa_api.databinding.LoadStateBinding

class MyLoadStateAdapter : LoadStateAdapter<LoadStateViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) = Unit
}

class LoadStateViewHolder(binding: LoadStateBinding) : RecyclerView.ViewHolder(binding.root)