package com.example.librarybookrecommendation.ui.home

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.librarybookrecommendation.R
import com.example.librarybookrecommendation.Util.bookKey
import com.example.librarybookrecommendation.Util.getApp
import com.example.librarybookrecommendation.Util.isPackageInstalled
import com.example.librarybookrecommendation.Util.newTaipeiPackageName
import com.example.librarybookrecommendation.model.Book
import com.example.librarybookrecommendation.ui.adapter.BookGridAdapter


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var bookList = listOf<Book>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val bookRefreshLayout = root.findViewById<SwipeRefreshLayout>(R.id.bookRefreshLayout)
        bookRefreshLayout.setOnRefreshListener {
            homeViewModel.refreshBooks{
                bookRefreshLayout.isRefreshing = false
            }
        }

        val bookRecyclerView = root.findViewById<RecyclerView>(R.id.book_grid)
        bookRecyclerView.layoutManager = GridLayoutManager(context, 4)
        val bookGridAdapter = BookGridAdapter(context, listOf())
        bookRecyclerView.adapter = bookGridAdapter
        bookGridAdapter.setClickListener(object : BookGridAdapter.ItemClickListener{
            override fun onItemClick(view: View?, position: Int, book: Book) {
                findNavController().navigate(R.id.action_nav_home_to_bookDetailFragment,
                    Bundle().apply { putSerializable(bookKey, book) })
            }
        })

        homeViewModel.bookList.observe(viewLifecycleOwner, Observer {
            bookList = it
            bookGridAdapter.setData(it)
        })

        if(bookList.isEmpty()){
            homeViewModel.refreshBooks()
        }

        return root
    }
}