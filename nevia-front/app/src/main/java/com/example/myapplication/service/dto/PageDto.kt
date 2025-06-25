package com.example.myapplication.service.dto

data class PageDto<T>(
    val content: List<T>,
    val number: Int,
    val size: Int,
    val totalElement: Int,
    val first: Boolean,
    val last: Boolean,
    val numberOfElements: Int,
    val hasContent: Boolean,
    val totalPages: Int
)
