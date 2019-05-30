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


    override fun getItem(position: Int): Any {
        return dataSource[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.listview_row, parent, false)
        val book = getItem(position) as Book

        rowView.findViewById<TextView>(R.id.nameTextView).let {
            it.text = book.name
        }
        rowView.findViewById<TextView>(R.id.chapterTextView).let {
            it.text = book.currentChapter.toString()
        }
        rowView.findViewById<ConstraintLayout>(R.id.bookRow).let {
            it.setOnClickListener {
                val intent = Intent(context, BookDetailsActivity::class.java).apply {
                    putExtra(EXTRA_CURR_BOOK_ID, book.id)
                }
                context.startActivity(intent)

            }
        }
        rowView.findViewById<ImageButton>(R.id.deleteButton).let {
            it.setOnClickListener {
                PaperClient.deleteBook(book.id)
            }
        }

        return rowView
    }
}