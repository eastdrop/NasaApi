package com.example.m17_nasa_api.pagedphotoslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m17_nasa_api.databinding.PhotoItemBinding
import com.example.m17_nasa_api.models.PhotoResponse

class PhotosListAdapter(
    private val onClick: (PhotoResponse) -> Unit
) : PagingDataAdapter<PhotoResponse, PhotoViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            earthDate.text = (item?.earth_date ?: "").toString()
            sol.text = (item?.sol ?: "").toString()
            rover.text = """
        Rover Name: ${item?.rover?.name}
        Rover ID: ${item?.rover?.id}
        Landing Date: ${item?.rover?.landing_date}
        Launch Date: ${item?.rover?.launch_date}
        Status: ${item?.rover?.status}
        Cameras: ${item?.rover?.camera?.joinToString { it.full_name }}
    """.trimIndent()
            item?.let {
                Glide.with(photo.context)
                    .load(it.img_src)
                    .into(photo)
            }
        }
    }
}


class DiffUtilCallback : DiffUtil.ItemCallback<PhotoResponse>() {
    override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
        return oldItem == newItem
    }
}

class PhotoViewHolder(val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root)