package com.example.android.marsphotos.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.MarsPhoto

class PhotoGridAdapter : ListAdapter<MarsPhoto,
        PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {

    //создание вьюшки
    //ViewHolder хранит ссылки на каждый элемент списка
    // (как во фрагментах мы искали заметки по Id)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoGridAdapter.MarsPhotoViewHolder {
        return MarsPhotoViewHolder(
            GridViewItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    //B нашу вьюшку мы кладем значения!
    //Мы вызываем getItem(), чтобы получить объект MarsPhoto, связанный с текущей позицией списка, а
    // затем передать его в метод bind() в MarsPhotoViewHolder.
    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPhotoViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }

    //Мы  сравниваем два фотообъекта Марса внутри этой реализации.
    companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {

        //должен возвращать true, если элементы списка одинаковые(проверяем по id)
        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        //вызывается, только если areItemsTheSame вернул true. Это дополнительная
        //проверка, которая сравнивает переменные класса (элемента списка) между собой по
        //аналогии с equals, чтобы выяснить, изменились ли данные внутри (проверяет само наполнение холдера)
        // Важными данными в MarsPhoto является URL-адрес изображения.
        // Сравниваем URL-адреса oldItem и newItem и возвращаем результат.
        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }
    }


    //Нам нужна переменная для привязки данных MarsPhoto к макету grid_view_item.xml,
    // поэтому передаем ее (binding) в MarsPhotoViewHolder.
    // Базовому классу ViewHolder требуется View в его конструкторе, мы передаем ему binding.root.
    class MarsPhotoViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //привязать/задать значения
        fun bind(marsPhoto: MarsPhoto) {
            binding.photo = marsPhoto
            //executePendingBindings() приводит к немедленному выполнению обновления
            binding.executePendingBindings()
        }
    }
}