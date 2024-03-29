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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.marsphotos.databinding.FragmentOverviewBinding

/**
 * This fragment shows the the status of the Mars photos web services transaction.
 */
class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModels()

    /**
    Раздуваем макет с помощью binding, устанавливаем ему владельца жизненного цикла самогос себя (OverviewFragment),
    чтобы включить наблюдение за LiveData, и настраиваем RecyclerView с адаптером.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)

        //Разрешает привязке данных наблюдать за LiveData в течение жизненного цикла этого фрагмента
        binding.lifecycleOwner = this@OverviewFragment

        //Предоставляем привязке доступ к OverviewViewModel
        binding.viewModel = viewModel

        //Привязываем адаптер к нашему списку
        binding.photosGridRecyclerView.adapter = PhotoGridAdapter()

        return binding.root
    }
}
