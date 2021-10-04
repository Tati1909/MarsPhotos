package com.example.android.marsphotos.overview

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.example.android.marsphotos.R

//Мы должны установить в макете для атрибута app:imageUrl значение ImageView.
//Для этого мы будем использовать адаптеры привязки (аннотированные методы).
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