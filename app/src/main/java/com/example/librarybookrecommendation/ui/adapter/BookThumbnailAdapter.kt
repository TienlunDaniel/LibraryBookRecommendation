package com.example.librarybookrecommendation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.librarybookrecommendation.R


class BookThumbnailAdapter internal constructor(
    val context: Context?,
    images: List<String>
) :
    RecyclerView.Adapter<BookThumbnailAdapter.ViewHolder>() {
    private var mData: List<String> = images
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ThumbnailClickListener? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = mInflater.inflate(R.layout.book_thumbnail, parent, false)
        view.layoutParams = ViewGroup.LayoutParams( parent.measuredHeight, parent.measuredHeight)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        Glide.with(context!!)
            .load(mData[position])
            .apply(
                RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.placeholder)
                    .dontAnimate()
            )
            .into(holder.bookThumbnail)
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var bookThumbnail: ImageView = itemView.findViewById(R.id.book_thumbnail)
        override fun onClick(view: View?) {
            mClickListener?.onItemClick(view, adapterPosition, mData[adapterPosition])
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): String {
        return mData[id]
    }

    fun setData(images: List<String>) {
        mData = images
        notifyDataSetChanged()
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ThumbnailClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ThumbnailClickListener {
        fun onItemClick(view: View?, position: Int, imageUrl : String)
    }
}