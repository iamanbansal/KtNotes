package com.ktnotes.di

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools



inline fun <reified T> koin(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T {
    val koin = KoinPlatformTools.defaultContext().get()
    return koin.get(parameters = parameters, qualifier = qualifier)
}