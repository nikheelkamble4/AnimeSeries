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
import okio.IOException
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(private val repository: AnimeRepository): ViewModel() {

    private val _animeListLiveData = MutableLiveData<NetworkResult<List<AnimeModel>>>()
    val animeListLiveData: LiveData<NetworkResult<List<AnimeModel>>>
        get() =_animeListLiveData

    private val _animeDetailsLiveData = MutableLiveData<NetworkResult<AnimeModel>>()
    val animeDetailsLiveData: LiveData<NetworkResult<AnimeModel>>
        get() =_animeDetailsLiveData

    fun getTopAnimes(context: Context) = viewModelScope.launch {
        _animeListLiveData.postValue(NetworkResult.Loading())
        try {
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
                    _animeListLiveData.postValue(NetworkResult.Success(animeEntities))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _animeListLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
                } else {
                    _animeListLiveData.postValue(NetworkResult.Error("Something went wrong"))
                }
            } else {
                val response = repository.getAnimeOffline()
                if (response.isNotEmpty()) {
                    _animeListLiveData.postValue(NetworkResult.Success(response))
                } else {
                    _animeListLiveData.postValue(NetworkResult.Error("No internet connection. Retry after connecting!"))
                }
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> _animeListLiveData.postValue(NetworkResult.Error("Unable to connect"))
                else -> _animeListLiveData.postValue(NetworkResult.Error("No signal"))
            }
        }
    }

    fun getAnimeById(id: Int, context: Context) = viewModelScope.launch {
        _animeDetailsLiveData.postValue(NetworkResult.Loading())
        try {
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
                    _animeDetailsLiveData.postValue(NetworkResult.Success(animeEntity))
                }
            } else {
                val response = repository.getAnimeByIdOffline(id)
                _animeDetailsLiveData.postValue(NetworkResult.Success(response))
            }
        } catch (t: Throwable) {
            when(t) {
                is IOException -> _animeDetailsLiveData.postValue(NetworkResult.Error("Unable to connect"))
                else -> _animeDetailsLiveData.postValue(NetworkResult.Error("No signal"))
            }
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
}