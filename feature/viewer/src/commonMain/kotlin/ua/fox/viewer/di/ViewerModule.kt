package ua.fox.viewer.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ua.fox.viewer.ui.ViewerViewModel

val viewerModule = module {
    viewModelOf(::ViewerViewModel)
}