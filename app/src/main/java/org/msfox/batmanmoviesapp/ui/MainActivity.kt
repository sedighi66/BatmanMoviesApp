package org.msfox.batmanmoviesapp.ui

import android.content.Intent
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
import org.msfox.batmanmoviesapp.adapter.ItemListAdapter
import org.msfox.batmanmoviesapp.binding.ActivityDataBindingComponent
import org.msfox.batmanmoviesapp.databinding.ActivityMainBinding
import org.msfox.batmanmoviesapp.vm.SearchMoviesViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appCoroutineDispatchers: AppCoroutineDispatchers

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemListAdapter
    private var dataBindingComponent: DataBindingComponent = ActivityDataBindingComponent(this)


    private val viewModel: SearchMoviesViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = ItemListAdapter(appCoroutineDispatchers, dataBindingComponent) { item ->
            //onclick implementation
//            val intent = Intent(this, DescriptionActivity::class.java)
//            intent.putExtra(ID, item.id)
//            startActivity(intent)
        }
        binding.itemList.adapter = adapter
        initItemList()

        binding.itemList.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(binding.itemList.layoutManager!!,0){
            override fun onLoadMore() {
                viewModel.loadData()
            }
        })

    }


    private fun initItemList() {
        viewModel.getMovies().observe(this, Observer {resource ->
            adapter.submitList(resource?.data)
            binding.status = resource?.status
        })
    }

    companion object{
        const val ID = "id"
    }
}