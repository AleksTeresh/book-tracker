package com.example.booktracker.model

data class Book(
    val id: String,
    val name: String,
    val currentChapter: Int,
    val currPlannedChapter: Int
) {}
