package io.realworld.conduit.shared.domain

interface SlugGenerator {
    fun generate(value: String): String
}
