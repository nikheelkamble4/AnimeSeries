package com.example.animeseries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeseries.model.Data
import com.example.animeseries.model.TopAnime
import com.example.animeseries.model.TopAnimes
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

    private val _animeLiveData = MutableLiveData<NetworkResult<TopAnimes>>()

    val animeLiveData: LiveData<NetworkResult<TopAnimes>>
        get() =_animeLiveData

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

    /*fun getAnimeById(id: Int) = viewModelScope.launch {
        _animeLiveData.postValue(NetworkResult.Loading())
        val response = repository.getAnimeById(id)
        Log.d("Nikheel", "getAnimeById: ${response.body()}")
    }*/

    /*fun getAnimeById(id: Int) = viewModelScope.launch {
        _animeLiveData.postValue(NetworkResult.Loading())
        val response = repository.getAnimeById(id)
        Log.d("Nikheel", "getAnimeById: ${response.body()}")
        _animeLiveData.postValue(NetworkResult.Success(response.body()))
    }*/

    /*private fun handleAnimeResponse(response: Response<TopAnime>): NetworkResult<TopAnime> {
        if (response.isSuccessful) {
            return NetworkResult.Success(response.body())
        }
        return NetworkResult.Error(response.message())
    }*/

    fun getAnimeById(id: Int) = viewModelScope.launch {
        _animeLiveData.postValue(NetworkResult.Loading())
        Log.d("Nikheel", "getAnimeById: $id")
        val response = repository.getAnimeById(id)
        _animeLiveData.postValue(handleAnimeResponse(response))
    }

    private fun handleAnimeResponse(response: Response<TopAnimes>): NetworkResult<TopAnimes> {
        if (response.isSuccessful) {
            Log.d("Nikheel", "handleAnimeResponse: ${response.body()}")
            if (response.body() != null) return NetworkResult.Success(response.body())
            else Log.d("Nikheel", "handleAnimeResponse: ${response.body()}")
        }
        return NetworkResult.Error(response.message())
    }
}