

package com.gualoto.pfinaldm.data.network.repository

import com.gualoto.pfinaldm.data.network.endpoints.AnimeEndPoints

object AnimeService {

    private val retrofit = RetrofitBase.returnBaseRetrofitAnime()

    val moviesEndPoints: AnimeEndPoints = retrofit.create(AnimeEndPoints::class.java)
}
