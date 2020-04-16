package br.com.oversight.ambienta.di

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment<ViewModelType: ViewModel>: Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected var viewModel: ViewModelType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assignViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        assignViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.let {
            this.bindViewModel(it)
        }
    }

    abstract fun bindViewModel(viewModel: ViewModelType)

    @Suppress("UNCHECKED_CAST")
    private fun assignViewModel() {
        if (this.viewModel == null) {
            val annotation = javaClass.getAnnotation(RequiresViewModel::class.java)
            val viewModelClass = if(annotation == null) null else annotation.value::java.get()
            if (viewModelClass != null) {
                this.viewModel = ViewModelProvider(this, viewModelFactory)
                    .get(viewModelClass) as? ViewModelType
            }
        }
    }
}