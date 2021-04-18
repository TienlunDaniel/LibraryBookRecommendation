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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.librarybookrecommendation.R
import com.example.librarybookrecommendation.ui.adapter.BookGridAdapter


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

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
        homeViewModel.bookList.observe(viewLifecycleOwner, Observer {
            bookGridAdapter.setData(it)
        })

        homeViewModel.refreshBooks()

        val intent = Intent(Intent.ACTION_MAIN)
        intent.component = ComponentName(
            "gov.tphcc.library",
            "io.dcloud.PandoraEntry"
        )
        startActivity(intent)

        return root
    }
}