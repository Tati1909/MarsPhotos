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

//статусы приложения
//Следует учитывать три состояния: загрузка, успех и неудача. Состояние загрузки происходит, пока вы ждете данных.
// Статус успеха - это когда мы успешно извлекаем данные из веб-сервиса.
// Состояние сбоя указывает на любые ошибки сети или подключения.
enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    //Внутренняя MutableLiveData, в котором хранится статус самого последнего запроса
    private val _status = MutableLiveData<MarsApiStatus>()
    // Внешняя неизменяемая LiveData для статуса запроса
    val status: LiveData<MarsApiStatus> = _status

    //наш список с фотками
    private val _photos = MutableLiveData<List<MarsPhoto>>()
    val photos: LiveData<List<MarsPhoto>> = _photos

    /**
    Вызов getMarsPhotos() при инициализации, чтобы мы могли немедленно отобразить фото.
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
            //начальный статус - загрузка, когда сопрограмма работает, а мы ждем данные
            _status.value = MarsApiStatus.LOADING
            try {
                // Сохраняем список полученных фото Марса в новую переменную _photos
                _photos.value = MarsApi.retrofitService.getPhotos()
                _status.value = MarsApiStatus.DONE
                //в случае ошибки запроса на сервер - выводим ошибку на экран
            } catch (e: Exception) {
                //посредине экрана будет поломанное облачко и белый фон
                _status.value = MarsApiStatus.ERROR
                //установим _photos как пустой список. Это очищает RecyclerView.
                _photos.value = listOf()
            }
        }
    }
}
