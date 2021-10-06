package com.example.android.marsphotos.overview

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.R
import com.example.android.marsphotos.network.MarsPhoto

//Мы должны установить в макете grid_view_item для атрибута app название imageUrl в нашем ImageView
//Для этого мы будем использовать адаптеры привязки (аннотированные методы) с нашими аттрибутами(imageUrl).
//В макете будет так: app:imageUrl="@{photo.imgSrcUrl}".
//Используем библиотеку Coil для загрузки изображения по URL-адресу в [ImageView]
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    //Преобразуем строку URL-адреса в Uri объект с помощью toUri() метода.
    // Чтобы использовать схему HTTPS, добавим buildUpon.scheme("https").
    // Вызываем build(), чтобы построить объект.
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        //используем load(){} from Coil , чтобы загрузить изображение из imgUri в imgView
        imgView.load(imgUri) {
            //картинки загрузки в случае сбоя/медленной загрузки или неудачной загрузки
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

//Устанавливаем в макете fragment_overview название listData для атрибута app в нашем списке.
//Обновляем данные, отображаемые в [RecyclerView].
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsPhoto>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

//Устанавливаем в макете fragment_overview название marsApiStatus для атрибута app в нашем ImageView
//адаптер для состояния приложения
@BindingAdapter("marsApiStatus")
fun bindStatus(
    statusImageView: ImageView,
    status: MarsApiStatus?
) {
    //when{} для переключения между различными состояниями
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}