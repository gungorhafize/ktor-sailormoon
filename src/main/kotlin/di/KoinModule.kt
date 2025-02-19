package com.hafize.di

import com.hafize.database.DatabaseFactory
import com.hafize.repository.CharacterRepository
import com.hafize.repository.CharacterRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single { DatabaseFactory }
    single<CharacterRepository> { CharacterRepositoryImpl() }
}