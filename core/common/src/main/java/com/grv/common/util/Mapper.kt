package com.grv.common.util

interface Mapper<Input, Output> {
    fun map(
        input: Input,
    ): Output
}