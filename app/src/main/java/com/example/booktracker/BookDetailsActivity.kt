package com.example.booktracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.booktracker.data.PaperClient
import com.example.booktracker.model.Book
import kotlinx.android.synthetic.main.activity_book_details.*

class BookDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val bookId = intent.getStringExtra(EXTRA_CURR_BOOK_ID)
        PaperClient.getBook(bookId)
            .subscribe(
                {book ->
                    if (book == null) return@subscribe
                    findViewById<TextView>(R.id.bookNameTitle).apply {
                        text = book.name
                    }
                    findViewById<TextView>(R.id.currentGoalValue).apply {
                        text = book.currPlannedChapter.toString()
                    }

                    findViewById<EditText>(R.id.currentChapterValue).apply {
                        text = Editable.Factory
                            .getInstance()
                            .newEditable(book.currentChapter.toString())
                    }
                    findViewById<Button>(R.id.buttonSave).setOnClickListener {
                        findViewById<EditText>(R.id.currentChapterValue).let {
                            Book(
                                book.id,
                                book.name,
                                it.text.toString().toInt(),
                                book.currPlannedChapter
                            ).let(PaperClient::updateBook)
                        }
                    }
                },
                {error ->
                    println(error)
                }
            )
    }
}
