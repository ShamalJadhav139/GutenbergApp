package com.etpl.newbase.model

data class BookListResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
) {
    data class Result(
        val authors: List<Author>,
        val bookshelves: List<String>,
        val download_count: Int,
        val id: Int,
        val languages: List<String>,
        val media_type: String,
        val subjects: List<String>,
        val title: String,
        var image_url: String,
        var html_url: String,
        var pdf_url: String,
        var text_url: String
    ) {
        data class Author(
            val birth_year: Int,
            val death_year: Int,
            val name: String
        )
    }
}