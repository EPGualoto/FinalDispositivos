package com.gualoto.pfinaldm.logic.usercase.anime

import android.util.Log
import com.gualoto.pfinaldm.data.core.getFullInfoAnimeLG
import com.gualoto.pfinaldm.data.network.endpoints.AnimeEndPoints
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullNewsAnimeLG
import com.gualoto.pfinaldm.data.network.repository.RetrofitBase
import java.lang.Exception

class GetAnimeNewsUserCase {

    suspend fun getFullAnimeNews(nameAnime:Int): Result<FullNewsAnimeLG> {

        var result:Result<FullNewsAnimeLG>?=null
        var infoAnime: FullNewsAnimeLG = FullNewsAnimeLG()
        try {
            val baseService= RetrofitBase.returnBaseRetrofitAnime()
            val service= baseService.create(AnimeEndPoints::class.java) //creo mi servicio
            val call= service.getAnimeNews(nameAnime) //ahora si podria acceder a travez de servicio a los metodos
            //me revuelve un response de FullInfoAnime


            Log.d("TAG", "antes de call.isSu..: "+call)
            if(call.isSuccessful){
                Log.d("TAG", "call: "+call)
                val a=call.body()!!
               // infoAnime=a.getFullAnimeNews()


                result = Result.success(infoAnime)
                Log.d("TAG", "result: "+result)
            }else{
                Log.d("TAG", "Error en el llamado a la API de Jikan")
                result=Result.failure(Exception("Error en el llamado a la API de Jikan"))
            }
        }catch (ex: Exception){
            Log.e("TAG","catch: " +ex.stackTraceToString())
            result=Result.failure(Exception(ex))
        };
        return result!!
    }



}