package com.company.com.company.di

import com.company.service.EventService
import com.company.service.EventServiceImpl
import org.koin.dsl.module.module


val myModule = module {
    single { EventServiceImpl() as EventService }
}
