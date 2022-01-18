package com.emamagic.common_entity

data class Movie(
    val id: Long? = null,
    val name: String? = null,
    val imageLink: String,
    val time: String? = null,
    val categoryName: String? = null,
    val rank: String? = null,
    val imdbRate: String? = null,
    val published: String? = null,
    val director: String? = null,
    val episode: String? = null,
    val genreName: String? = null
)