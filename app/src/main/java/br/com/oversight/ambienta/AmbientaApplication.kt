package br.com.oversight.ambienta

import android.app.Activity
import androidx.multidex.MultiDexApplication
import br.com.oversight.ambienta.di.AppInjector
import coil.Coil
import coil.ImageLoader
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class AmbientaApplication : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Coil.setDefaultImageLoader(ImageLoader(applicationContext){
            crossfade(true)
        })
        AppInjector(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}