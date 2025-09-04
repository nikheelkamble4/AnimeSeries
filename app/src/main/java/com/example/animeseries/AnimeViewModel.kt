package com.example.animeseries

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeseries.model.AnimeModel
import com.example.animeseries.repository.AnimeRepository
import com.example.animeseries.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(private val repository: AnimeRepository): ViewModel() {

    private val _animeResponseLiveData = MutableLiveData<NetworkResult<List<AnimeModel>>>()
    val animeResponseLiveData: LiveData<NetworkResult<List<AnimeModel>>>
        get() =_animeResponseLiveData

    private val _animeLiveData = MutableLiveData<NetworkResult<AnimeModel>>()

    val animeLiveData: LiveData<NetworkResult<AnimeModel>>
        get() =_animeLiveData

    fun getTopAnimes(context: Context) = viewModelScope.launch {
        _animeResponseLiveData.postValue(NetworkResult.Loading())
        if (internetConnection(context)) {
            val response = repository.getTopAnime()
            if (response.isSuccessful && response.body() != null) {
                val animeEntities: List<AnimeModel> = response.body()?.data?.map { data ->
                    AnimeModel(
                        mal_id = data.mal_id,
                        episodes = data.episodes,
                        genres = data.genres.joinToString(",") { it.name },
                        images = data.images.jpg.image_url,
                        rating = data.rating,
                        synopsis = data.synopsis,
                        title = data.title,
                        image_trailer = data.trailer.images.image_url,
                        trailer = data.trailer.embed_url
                    )
                } ?: emptyList()
                repository.addAnime(animeEntities)
                _animeResponseLiveData.postValue(NetworkResult.Success(animeEntities))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _animeResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
            } else {
                _animeResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
        } else {
            val response = repository.getAnimeOffline()
            _animeResponseLiveData.postValue(NetworkResult.Success(response))
        }
    }

    /*private suspend fun animeInternet() {
        NetworkResult.Loading())
        val response = repository.getTopAnime()
        _animeResponseLiveData.postValue(handleResponse(response))
    }

    private fun handleResponse(response: Response<TopAnime>): NetworkResult<TopAnime> {
        if (response.isSuccessful && response.body() != null) {
            return NetworkResult.Success(response.body())
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            return NetworkResult.Error(errorObj.getString("message"))
        } else {
            return NetworkResult.Error("Something went wrong")
        }
    }*/

    fun getAnimeById(id: Int, context: Context) = viewModelScope.launch {
        _animeLiveData.postValue(NetworkResult.Loading())
        if (internetConnection(context)) {
            val response = repository.getAnimeById(id)
            if (response.isSuccessful && response.body() != null) {
                val animeEntity = AnimeModel(
                    mal_id = response.body()?.data!!.mal_id,
                    episodes = response.body()?.data?.episodes ?: 0,
                    genres = response.body()?.data?.genres!!.joinToString(",") { it.name },
                    images = response.body()?.data?.images!!.jpg.image_url,
                    rating = response.body()?.data?.rating ?: "",
                    synopsis = response.body()?.data?.synopsis ?: "",
                    title = response.body()?.data?.title ?: "",
                    image_trailer = response.body()?.data?.trailer?.images?.image_url,
                    trailer = response.body()?.data?.trailer?.embed_url
                )
                _animeLiveData.postValue(NetworkResult.Success(animeEntity))
            }
        } else {
            val response = repository.getAnimeByIdOffline(id)
            _animeLiveData.postValue(NetworkResult.Success(response))
        }
    }

    fun internetConnection(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }


    /*fun getAnimeById(id: Int) = viewModelScope.launch {
        _animeLiveData.postValue(NetworkResult.Loading())
        Log.d("Nikheel", "getAnimeById: $id")
        val response = repository.getAnimeById(id)
        _animeLiveData.postValue(handleAnimeResponse(response))
    }

    private fun handleAnimeResponse(response: Response<TopAnimes>): NetworkResult<TopAnimes> {
        if (response.isSuccessful && response.body() != null) {
                return NetworkResult.Success(response.body())
            } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            return NetworkResult.Error(errorObj.getString("message"))
        } else {
            return NetworkResult.Error("Something went wrong")
        }
    }*/
}