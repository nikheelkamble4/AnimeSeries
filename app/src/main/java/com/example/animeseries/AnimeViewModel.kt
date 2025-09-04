package com.example.animeseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeseries.model.TopAnime
import com.example.animeseries.repository.AnimeRepository
import com.example.animeseries.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(private val repository: AnimeRepository): ViewModel() {

    private val _animeResponseLiveData = MutableLiveData<NetworkResult<TopAnime>>()

    val animeResponseLiveData: LiveData<NetworkResult<TopAnime>>
        get() =_animeResponseLiveData

    init {
        getTopAnimes()
    }

    fun getTopAnimes() = viewModelScope.launch {
        animeInternet()
    }

    private suspend fun animeInternet() {
        _animeResponseLiveData.postValue(NetworkResult.Loading())
        val response = repository.getTopAnime()
        _animeResponseLiveData.postValue(handleResponse(response))
    }

    private fun handleResponse(response: Response<TopAnime>): NetworkResult<TopAnime> {
        if (response.isSuccessful) {
            return NetworkResult.Success(response.body())
        }
        return NetworkResult.Error(response.message())
    }
}