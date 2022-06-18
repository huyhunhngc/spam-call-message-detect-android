package com.dotsdev.idcaller.di

import com.dotsdev.idcaller.usecase.UserUsecase
import org.koin.dsl.module

val usecaseModule = module {
    factory { UserUsecase(get()) }
}
