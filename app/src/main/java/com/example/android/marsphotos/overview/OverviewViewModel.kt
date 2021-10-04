/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsphotos.network.MarsApi
import com.example.android.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch


class OverviewViewModel : ViewModel() {

    //Внутренняя MutableLiveData, в котором хранится статус самого последнего запроса
    private val _status = MutableLiveData<String>()

    // Внешняя неизменяемая LiveData для статуса запроса
    val status: LiveData<String> = _status

    //загрузка фото
    private val _photos = MutableLiveData<MarsPhoto>()
    val photos: LiveData<MarsPhoto> = _photos

    /**
    Вызов getMarsPhotos() при инициализации, чтобы мы могли немедленно отобразить статус.
     */
    init {
        getMarsPhotos()
    }

    /**
    Получаем информацию о фотографиях Марса из MarsApi.retrofitService и обновляем ее.
     */
    private fun getMarsPhotos() {
        //запустите сопрограмму, используя viewModelScope.launch
        //launch - запускать
        viewModelScope.launch {
            try {
                // Сохраняем первую полученную фотографию Марса в новую переменную _photos
                _photos.value = MarsApi.retrofitService.getPhotos()[0]
                _status.value =
                    " Отображение URL-адреса первого изображения из списка фотографий : ${_photos.value!!.imgSrcUrl}"
                //в случае ошибки запроса на сервер - выводим ошибку на экран
            } catch (e: Exception) {
                _status.value = "Сбой: ${e.message}"
            }
        }
    }
}
