package com.emamagic.domain.repository

import com.emamagic.common_entity.Genre
import com.emamagic.common_jvm.ResultWrapper

interface GenreRepository {

    suspend fun getAllGenre(): ResultWrapper<List<Genre>>

}