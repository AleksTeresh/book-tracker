package com.example.booktracker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.example.booktracker.data.PaperClient
import com.example.booktracker.model.Book
import com.example.booktracker.ui.CustomListAdapter
import io.paperdb.Paper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

const val EXTRA_CURR_BOOK_ID = "com.example.booktracker.CURR_BOOK_ID"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val allBooksStream = PaperClient.getBooks()


        allBooksStream
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {books -> setBooks(listOf(*books))},
                {error ->
                    println("THIS IS ERRORRR!!!")
                    println(error)}
            )
    }

    fun setBooks(books: List<Book>) {
        val adapter = CustomListAdapter(this, books)
        findViewById<ListView>(R.id.bookList).run {
            this.adapter = adapter
        }
    }

    /** Called when the user taps the Create button */
    fun createBook(view: View) {
        val message = findViewById<EditText>(R.id.editText).let {
            it.text.toString()
        }
        Book(
            UUID.randomUUID().toString(),
            message,
            0,
            0
        ).let(PaperClient::addBook)
    }
}
