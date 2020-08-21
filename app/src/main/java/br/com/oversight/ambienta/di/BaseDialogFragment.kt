package br.com.oversight.ambienta.di

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseDialogFragment<ViewModelType: ViewModel>: DialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var viewModel: ViewModelType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assignViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel?.let {
            this.bindViewModel(it)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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