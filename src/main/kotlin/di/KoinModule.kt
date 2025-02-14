package com.hafize.di

import com.hafize.repository.CharacterRepository
import com.hafize.repository.CharacterRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    factory<CharacterRepository> { CharacterRepositoryImpl() }
}