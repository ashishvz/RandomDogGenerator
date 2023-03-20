package com.ashishvz.dogimage.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ashishvz.dogimage.R
import com.ashishvz.dogimage.data.local.models.CachedDogData
import com.ashishvz.dogimage.databinding.ImageCardBinding
import com.bumptech.glide.Glide

class DogImageAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<DogImageAdapter.ViewHolder>() {

    private lateinit var binding: ImageCardBinding
    private val cachedDogDataList = mutableListOf<CachedDogData>()

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(cachedDogData: CachedDogData) {
            binding.apply {
                Glide.with(context).load(cachedDogData.imgUrl).into(dogImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.image_card,
            parent,
            false
        )
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return cachedDogDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(cachedDogDataList[position])
        holder.setIsRecyclable(false)
    }

    fun setData(cachedList: MutableList<CachedDogData>) {
        cachedDogDataList.clear()
        cachedDogDataList.addAll(cachedList)
        notifyDataSetChanged()
    }
}