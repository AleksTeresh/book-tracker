package com.example.booktracker.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.booktracker.BookDetailsActivity
import com.example.booktracker.EXTRA_CURR_BOOK_ID
import com.example.booktracker.R
import com.example.booktracker.data.PaperClient
import com.example.booktracker.model.Book


class CustomListAdapter(
    private val context: Context,
    private val dataSource: List<Book>
) : BaseAdapter() {
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.listview_row, parent, false)

        val nameTextField = rowView.findViewById<TextView>(R.id.nameTextView)
        val chapterNumberField = rowView.findViewById<TextView>(R.id.chapterTextView)
        val actualRow = rowView.findViewById<ConstraintLayout>(R.id.bookRow)
        val deleteButton = rowView.findViewById<ImageButton>(R.id.deleteButton)

        var book = getItem(position) as Book

        actualRow.setOnClickListener {
            val intent = Intent(context, BookDetailsActivity::class.java).apply {
                putExtra(EXTRA_CURR_BOOK_ID, book.id)
            }
            context.startActivity(intent)

        }
        deleteButton.setOnClickListener {
            PaperClient.deleteBook(book.id)
        }

        nameTextField.text = book.name
        chapterNumberField.text = book.currentChapter.toString()

        return rowView
    }
}