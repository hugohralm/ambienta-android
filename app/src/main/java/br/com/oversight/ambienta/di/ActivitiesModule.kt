package br.com.oversight.ambienta.di

import br.com.oversight.ambienta.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity
}