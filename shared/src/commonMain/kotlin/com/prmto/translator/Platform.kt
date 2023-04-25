package com.prmto.translator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform