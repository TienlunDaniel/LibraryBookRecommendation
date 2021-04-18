package com.example.librarybookrecommendation.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.librarybookrecommendation.R
import com.example.librarybookrecommendation.Util.*
import com.example.librarybookrecommendation.model.Book
import com.example.librarybookrecommendation.ui.adapter.BookThumbnailAdapter
import kotlinx.android.synthetic.main.fragment_book_detail.view.*

class BookDetailFragment : Fragment() {

    private lateinit var bookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookViewModel =
            ViewModelProviders.of(this).get(BookViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_book_detail, container, false)

        val book : Book = requireArguments().getSerializable(bookKey) as Book

        root.book_info.text = "${book.title}\n${book.author}\n${book.releaseDate}"
        root.book_description.text = book.description

        root.libraryEntry.setOnClickListener {
            if(isPackageInstalled(newTaipeiPackageName, requireActivity().packageManager)){
                launchLibraryApp("TOBE_Removed", requireActivity())
            }else{
                getApp(newTaipeiPackageName, requireActivity())
            }
        }

        //displayImageinit
        Glide.with(requireContext())
            .load(if (book.images.isNotEmpty()) book.images[0] else R.mipmap.placeholder)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.placeholder)
                    .dontAnimate()
            )
            .into(root.displayImage)

        //Recycler view init
        val bookThumbnailRV = root.findViewById<RecyclerView>(R.id.thumbnailImages)
        bookThumbnailRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val bookThumbnailAdapter = BookThumbnailAdapter(context, book.images)
        bookThumbnailRV.adapter = bookThumbnailAdapter
        bookThumbnailAdapter.setClickListener(object : BookThumbnailAdapter.ThumbnailClickListener{
            override fun onItemClick(view: View?, position: Int, imageUrl: String) {
                Glide.with(requireContext())
                    .load(imageUrl)
                    .apply(
                        RequestOptions()
                            .centerCrop()
                            .placeholder(R.mipmap.placeholder)
                            .dontAnimate()
                    )
                    .into(root.displayImage)
            }
        })

        return root
    }
}