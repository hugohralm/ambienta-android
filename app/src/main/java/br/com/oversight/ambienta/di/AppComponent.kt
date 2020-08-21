package br.com.oversight.ambienta.di

import br.com.oversight.ambienta.AmbientaApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivitiesModule::class,
        FragmentsModule::class,
        ViewModelModule::class,
        DialogFragmentModule::class]
)
interface AppComponent : AndroidInjector<AmbientaApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: AmbientaApplication): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: AmbientaApplication)
}