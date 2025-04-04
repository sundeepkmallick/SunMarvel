package com.sunmarvel.network.ktor

interface Mapper<F, T> {
    fun map(from: F): T
}