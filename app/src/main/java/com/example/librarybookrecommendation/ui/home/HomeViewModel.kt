package com.example.librarybookrecommendation.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.librarybookrecommendation.LibraryBookApplication
import com.example.librarybookrecommendation.R
import com.example.librarybookrecommendation.model.Book
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL


class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val bookList: MutableLiveData<List<Book>> = MutableLiveData()

    fun refreshBooks() {
        val bookDao = LibraryBookApplication.bookDatabase!!.bookDao()

        GlobalScope.launch {
            val books = bookDao.getBooks(500)
            bookList.postValue(books)
        }
    }
}