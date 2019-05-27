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
    var currBook: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val titleView = findViewById<TextView>(R.id.bookNameTitle)
        val goalView = findViewById<TextView>(R.id.currentGoalValue)
        val currChapterView = findViewById<EditText>(R.id.currentChapterValue)
        val saveButton = findViewById<Button>(R.id.buttonSave)

        val bookId = intent.getStringExtra(EXTRA_CURR_BOOK_ID)
        PaperClient.getBook(bookId)
            .subscribe(
                {book ->
                    if (book != null) {
                        currBook = book
                        titleView.text = book.name
                        goalView.text = book.currPlannedChapter.toString()
                        currChapterView.text = Editable.Factory
                            .getInstance()
                            .newEditable(book.currentChapter.toString())

                        saveButton.setOnClickListener {
                            val currChapterView = findViewById<EditText>(R.id.currentChapterValue)
                            val newBook = Book(
                                book.id,
                                book.name,
                                currChapterView.text.toString().toInt(),
                                book.currPlannedChapter
                            )
                            PaperClient.updateBook(newBook)
                        }
                    }
                },
                {error ->
                    println(error)
                }
            )
    }
}
