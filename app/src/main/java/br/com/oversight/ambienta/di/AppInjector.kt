package br.com.oversight.ambienta.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import br.com.oversight.ambienta.AmbientaApplication
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

class AppInjector(application: AmbientaApplication) {


    init {
        component =
        DaggerAppComponent
            .builder()
            .application(application)
            .build()

        component.inject(application)

        application
            .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                    activity?.let {
                        handleActivityCreated(activity)
                    }
                }

                override fun onActivityPaused(activity: Activity?) {}

                override fun onActivityResumed(activity: Activity?) {}

                override fun onActivityStarted(activity: Activity?) {}

                override fun onActivityDestroyed(activity: Activity?) {}

                override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

                override fun onActivityStopped(activity: Activity?) {}
            })
    }

    private fun handleActivityCreated(activity: Activity) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentPreCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            super.onFragmentPreCreated(fm, f, savedInstanceState)
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }

    companion object{
        lateinit var  component: AppComponent
    }
}