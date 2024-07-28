package com.gualoto.finalp.data.network.entities.topanime

import com.gualoto.finalp.data.network.entities.topanime.Items

data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
)