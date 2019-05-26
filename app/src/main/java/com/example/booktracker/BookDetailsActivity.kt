package com.example.booktracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.booktracker.data.PaperClient

class BookDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val bookId = intent.getStringExtra(EXTRA_CURR_BOOK_ID)
        PaperClient.getBook(bookId)
            .subscribe({book ->
                println("We're half way thereeeee!!!")
                println(book.name)
            })
    }
}
