package com.iverno.gustavo.movihelp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iverno.gustavo.movihelp.R
import com.iverno.gustavo.movihelp.data.TheMoviedbItem
import com.iverno.gustavo.movihelp.databinding.ListItemTheMovieDetailBinding
import com.squareup.picasso.Picasso

class TheMovieDBItemDetailAdapter () : RecyclerView.Adapter<TheMovieDBItemDetailAdapter.ViewHolder>() {
    private lateinit var binding: ListItemTheMovieDetailBinding
    private var mList: List<TheMoviedbItem> = emptyList()
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        binding = ListItemTheMovieDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.bind(itemsViewModel)

    }  // return the number of the items in the list
    
    override fun getItemCount(): Int {
        return if (mList.size > 0) mList.size else 0
    }

    fun setListItem(mList: List<TheMoviedbItem>){
         this.mList = mList
        this.notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder(val binding: ListItemTheMovieDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemViewModel: TheMoviedbItem) {
            binding.mViewModel = itemViewModel
            val picasso = Picasso.get()
            picasso.load(itemViewModel.getBackdropUrlString())
                                .error(R.drawable.error_loading)
                                .placeholder(R.drawable.no_image)
                                .into(binding.imageView)

        }
    }
}