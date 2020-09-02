/*
 * This file is created by Mohsen Sedighi with code name MSFox
 *
 * Copyright 8/8/20 12:07 AM belongs to Mohsen Sedighi from project BaManChallenge
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

package org.msfox.batmanmoviesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import org.msfox.batmanmoviesapp.AppCoroutineDispatchers
import org.msfox.batmanmoviesapp.R
import org.msfox.batmanmoviesapp.binding.ActivityDataBindingComponent
import org.msfox.batmanmoviesapp.databinding.ActivityDescriptionBinding
import org.msfox.batmanmoviesapp.vm.DescriptionViewModel
import javax.inject.Inject

class DescriptionActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appCoroutineDispatchers: AppCoroutineDispatchers

    private lateinit var binding: ActivityDescriptionBinding
    private var dataBindingComponent: DataBindingComponent = ActivityDataBindingComponent(this)

    private val viewModel: DescriptionViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        DataBindingUtil.setDefaultComponent(dataBindingComponent)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_description)
        binding.lifecycleOwner = this
        val id = intent.getStringExtra(MainActivity.ID)

        viewModel.getDescription(id!!).observe(this, Observer {item ->
            binding.status = item.status
            binding.item = item.data

            binding.viewModel = viewModel

        })
    }
}