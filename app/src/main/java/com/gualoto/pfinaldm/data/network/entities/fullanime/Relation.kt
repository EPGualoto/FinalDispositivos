package com.gualoto.pfinaldm.data.network.entities.fullanime

import com.gualoto.pfinaldm.data.network.entities.fullanime.Entry

data class Relation(
    val entry: List<com.gualoto.pfinaldm.data.network.entities.fullanime.Entry>,
    val relation: String
)