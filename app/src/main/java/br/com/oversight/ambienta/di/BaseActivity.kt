package br.com.oversight.ambienta.di

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity<ViewModelType: ViewModel>: AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var viewModel: ViewModelType? = null

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assignViewModel()
    }

    override fun onResume() {
        super.onResume()
        assignViewModel()
    }

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