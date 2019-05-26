package com.example.booktracker.data

import com.example.booktracker.model.Book
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.*
import io.reactivex.disposables.Disposable

object PaperClient {
    val paperBook = RxPaperBook.with()

    fun addBook(book: Book) {
        paperBook.read<Array<Book>>("books")
            .flatMapCompletable { books -> paperBook
                .write("books", arrayOf(book, *books)) }
            .subscribe()
    }

    fun getBooks(): Flowable<Array<Book>> {
        return observeBooks()
    }

    fun getBook(id: String): Flowable<Book> {
        return observeBooks()
            .map { it.filter { it.id == id }.first() }
    }

    fun deleteBook(bookId: String) {
        paperBook.read<Array<Book>>("books")
            .flatMapCompletable {
                books -> paperBook
                    .write(
                        "books",
                        books.filter { it.id != bookId }.toTypedArray()
                    )
            }
            .subscribe()
    }

    private fun observeBooks(): Flowable<Array<Book>> {
        return paperBook.observe<Array<Book>>(
            "books",
            (Array<Book>::class.java),
            BackpressureStrategy.BUFFER
        )
            .mergeWith(RxPaperBook.with().read<Array<Book>>("books"))
    }
}
