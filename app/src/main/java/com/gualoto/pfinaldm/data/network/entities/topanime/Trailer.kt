package com.gualoto.finalp.data.network.entities.topanime

import com.gualoto.finalp.data.network.entities.topanime.ImagesX

data class Trailer(
    val embed_url: String,
    val images: ImagesX,
    val url: String,
    val youtube_id: String
)