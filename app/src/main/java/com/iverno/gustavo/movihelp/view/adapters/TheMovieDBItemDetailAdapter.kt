package com.iverno.gustavo.movihelp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iverno.gustavo.movihelp.R
import com.iverno.gustavo.movihelp.data.TheMovieDBItemListener
import com.iverno.gustavo.movihelp.data.TheMoviedbItem
import com.iverno.gustavo.movihelp.databinding.ListItemTheMovieDetailBinding
import com.squareup.picasso.Picasso

class TheMovieDBItemDetailAdapter (theMovieDBItemListener : TheMovieDBItemListener) : RecyclerView.Adapter<TheMovieDBItemDetailAdapter.ViewHolder>() {
    private lateinit var binding: ListItemTheMovieDetailBinding
    private var mList: List<TheMoviedbItem> = emptyList()
    private var mListener = theMovieDBItemListener
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        binding = ListItemTheMovieDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.bindViewModel(itemsViewModel)
        holder.bindListener(mListener)

    }  // return the number of the items in the list
    
    override fun getItemCount(): Int {
        return if (mList.size > 0) mList.size else 0
    }

    fun setListItem(mList: List<TheMoviedbItem>){
        this.mList = mList
        this.notifyDataSetChanged()
    }

    // Holds the vie czxdfws for adding it to image and text
    class ViewHolder(val binding: ListItemTheMovieDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViewModel(itemViewModel: TheMoviedbItem) {
            binding.mViewModel = itemViewModel
            val picasso = Picasso.get()
            picasso.load(itemViewModel.getBackdropUrlString())
                                .error(R.drawable.ic_error_24)
                                .placeholder(R.drawable.no_image)
                                .into(binding.imageView)

        }
        fun bindListener(theMovieDBItemListener : TheMovieDBItemListener) {
            binding.mListener = theMovieDBItemListener
        }
    }
}