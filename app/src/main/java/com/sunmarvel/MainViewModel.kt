package com.sunmarvel

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunmarvel.domain.MarvelKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val marvelKeyUseCase: MarvelKeyUseCase
) : ViewModel() {

    private val _privateApiKey = MutableStateFlow<String?>(null)
    val privateApiKey: StateFlow<String?> = _privateApiKey

    private fun setPrivateApiKey(key: String) = viewModelScope.launch {
        marvelKeyUseCase.invokeSaveMarvelPrivateKey(key)
    }

    private fun setPublicApiKey(marvelPublicKey: String) = viewModelScope.launch {
        marvelKeyUseCase.invokeSaveMarvelPublicKey(marvelPublicKey)
    }

    init {
        //Assume Private Key comes from backend server after user authorise himself instead of taking from BuildConfig
        setPrivateApiKey(BuildConfig.MARVEL_PRIVATE_KEY)

        //Public API Key can be kept in build.gradle file
        setPublicApiKey(BuildConfig.MARVEL_PUBLIC_KEY)
    }

    fun getPrivateApiKey() = viewModelScope.launch {
        val result = marvelKeyUseCase.invokeGetMarvelPrivateKey()
        println("Result from marvelKeyUseCase: $result")
        _privateApiKey.value = result
    }
}