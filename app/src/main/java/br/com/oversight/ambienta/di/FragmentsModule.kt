package br.com.oversight.ambienta.di

import br.com.oversight.ambienta.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

}