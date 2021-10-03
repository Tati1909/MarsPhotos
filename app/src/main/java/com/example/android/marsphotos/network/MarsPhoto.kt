package com.example.android.marsphotos.network

import com.squareup.moshi.Json

//Moshi анализирует данные JSON и преобразует их в объекты Kotlin.
// Для этого у Moshi должен быть data class для хранения проанализированных результатов,
// поэтому нужно создать класс данных MarsPhoto.

//Пример записи ответа JSON:
//[{
//    "id":"424906",
//    "img_src":"http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631300305227E03_DXXX.jpg"
//},
//...]

data class MarsPhoto(
    //Обратите внимание, что каждая из переменных соответствует имени ключа в объекте JSON.
    //В соглашении Kotlin для переменных используются буквы верхнего и нижнего регистра («верблюжий регистр» ),
    //поэтому заменяем img_src на imgSrcUrl с помощью аннотации @Json.
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String
)
