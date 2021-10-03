package com.example.android.marsphotos.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

//базовый URI для веб-службы
private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

// конструктор Retrofit для построения и создания объекта Retrofit.
//Для модернизации требуется базовый URI для веб-службы и ConverterFactory для создания API.
// ConverterFactory сообщает Retrofit, что делать с данными, которые он получает от веб-службы.
// В этом случае вы хотите, чтобы Retrofit получал ответ JSON от веб-службы и возвращал его как файл String.
// Retrofit имеет объект ScalarsConverter, поддерживающий строки и другие примитивные типы, поэтому
// вы вызываете Builder().addConverterFactory с экземпляром ScalarsConverterFactory.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    //добавляем базовый URI
    .baseUrl(BASE_URL)
    // вызоваем build() для создания объекта Retrofit.
    .build()

//интерфейс MarsApiService определяет, как Retrofit взаимодействует с веб-сервером с помощью HTTP-запросов.
interface MarsApiService {
    //аннотация, чтобы сообщить Retrofit, что это запрос GET, и укажите конечную точку для этого метода веб-службы - /photos.
    @GET("photos")
    //функция для получения строки ответа от веб-службы
    //функцию suspend, чтобы вы могли вызывать этот метод из сопрограммы.
    suspend fun getPhotos(): String
}

//Шаблон синглтона гарантирует, что создается один и только один экземпляр объекта,
// имеющий одну глобальную точку доступа к этому объекту.
// Инициализация объявления объекта является поточно-ориентированной и выполняется при первом доступе.
//Объявление объекта всегда имеет имя после слова object.
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
//* Каждый раз, когда ваше приложение вызывает синглтон MarsApi.retrofitService,
// вызывающий будет обращаться к тому же объекту singleton Retrofit, который имплементирует MarsApiService.