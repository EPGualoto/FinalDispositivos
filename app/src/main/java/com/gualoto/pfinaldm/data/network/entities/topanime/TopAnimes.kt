package com.gualoto.finalp.data.network.entities.topanime

import com.gualoto.finalp.data.network.entities.topanime.Data
import com.gualoto.finalp.data.network.entities.topanime.Pagination

data class TopAnimes(
    val `data`: List<Data> = listOf(),
    val pagination: Pagination? = null
)