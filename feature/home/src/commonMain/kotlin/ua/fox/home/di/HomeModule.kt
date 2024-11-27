package ua.fox.home.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ua.fox.home.ui.HomeViewModel

val homeModule = module {
    viewModelOf(::HomeViewModel)
}