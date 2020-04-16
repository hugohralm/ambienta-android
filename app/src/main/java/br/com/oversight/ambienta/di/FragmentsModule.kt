package br.com.oversight.ambienta.di

import br.com.oversight.ambienta.ui.home.HomeFragment
import br.com.oversight.ambienta.ui.novaDenuncia.NovaDenunciaFragment
import br.com.oversight.ambienta.ui.novaDenuncia.NovaDenunciaViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeNovaDenunciaFragment(): NovaDenunciaFragment

}