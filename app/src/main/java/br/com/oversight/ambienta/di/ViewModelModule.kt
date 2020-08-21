package br.com.oversight.ambienta.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.oversight.ambienta.ui.MainViewModel
import br.com.oversight.ambienta.ui.detalheDenuncia.DetalheDenunciaViewModel
import br.com.oversight.ambienta.ui.home.HomeViewModel
import br.com.oversight.ambienta.ui.novaDenuncia.NovaDenunciaViewModel
import com.umobi.mercadoon.util.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NovaDenunciaViewModel::class)
    abstract fun bindNovaDenunciaViewModel(viewModel: NovaDenunciaViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetalheDenunciaViewModel::class)
    abstract fun bindDetalheDenunciaViewModel(viewModel: DetalheDenunciaViewModel): ViewModel
}