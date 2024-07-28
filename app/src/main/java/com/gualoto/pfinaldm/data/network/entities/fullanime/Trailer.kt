package com.gualoto.pfinaldm.data.network.entities.fullanime

import com.gualoto.pfinaldm.data.network.entities.fullanime.ImagesX

data class Trailer(
    val embed_url: String,
    val images: com.gualoto.pfinaldm.data.network.entities.fullanime.ImagesX,
    val url: String,
    val youtube_id: String
)