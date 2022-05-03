package com.iverno.gustavo.movihelp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iverno.gustavo.movihelp.R
import com.iverno.gustavo.movihelp.databinding.FragmentHomeBinding
import com.iverno.gustavo.movihelp.databinding.ListItemTheMovieDetailBinding

class TheMovieDBItemDetailAdapter (private val mList: List<TheMovieDBItemDetailViewModel>) : RecyclerView.Adapter<TheMovieDBItemDetailAdapter.ViewHolder>() {
    private lateinit var binding: ListItemTheMovieDetailBinding
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        binding = ListItemTheMovieDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(mList[position])
    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(binding: ListItemTheMovieDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TheMovieDBItemDetailViewModel) {
            with(binding) {
                            binding.mViewModel = item
                      }
        }
    }
}