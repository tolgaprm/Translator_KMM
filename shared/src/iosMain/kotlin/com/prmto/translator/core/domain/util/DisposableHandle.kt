package com.prmto.translator.core.domain.util

import kotlinx.coroutines.DisposableHandle

fun interface DisposableHandle : DisposableHandle

// fun interface is the same in the below code

/*fun DisposableHandle(block: () -> Unit): DisposableHandle {
    return object : DisposableHandle {
        override fun dispose() {
            block()
        }
    }
}*/