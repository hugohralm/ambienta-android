package br.com.oversight.ambienta.di

import androidx.lifecycle.ViewModel
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

@Inherited
@kotlin.annotation.Retention
annotation class RequiresViewModel(val value: KClass<out ViewModel>)