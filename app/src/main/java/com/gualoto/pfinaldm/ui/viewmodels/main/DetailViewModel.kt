package com.gualoto.pfinaldm.ui.viewmodels.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.logic.usercase.anime.GetAnimeTopUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel: ViewModel(){

    val anime=MutableLiveData<FullInfoAnimeLG>()
    val error= MutableLiveData<String>()
    fun loadInfoAnime(animeID:Int){
        Log.d("UCE",animeID.toString())

        viewModelScope.launch(Dispatchers.Main) {

            val resp= withContext(Dispatchers.IO){
                GetAnimeTopUserCase().getFullAnimeInfo(animeID)
            }

            resp.onSuccess {
                anime.postValue(it)
            }
            resp.onFailure {
                error.postValue(it.message.toString())
            }


        }
    }
}