package com.example.librarybookrecommendation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.librarybookrecommendation.R
import com.example.librarybookrecommendation.model.Book


class BookGridAdapter internal constructor(
    val context: Context?,
    data: List<Book>
) :
    RecyclerView.Adapter<BookGridAdapter.ViewHolder>() {
    private var mData: List<Book> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = mInflater.inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val book = mData[position]
        Glide.with(context!!)
            .load(if (book.images.isNotEmpty()) book.images[0] else R.mipmap.placeholder)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.placeholder)
                    .dontAnimate()
            )
            .into(holder.bookPicture)
        holder.bookInfo.text = book.title
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var bookInfo: TextView = itemView.findViewById(R.id.book_info)
        var bookPicture: ImageView = itemView.findViewById(R.id.book_picture)
        override fun onClick(view: View?) {
            mClickListener?.onItemClick(view, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Book {
        return mData[id]
    }

    fun setData(books: List<Book>) {
        mData = books
        notifyDataSetChanged()
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}