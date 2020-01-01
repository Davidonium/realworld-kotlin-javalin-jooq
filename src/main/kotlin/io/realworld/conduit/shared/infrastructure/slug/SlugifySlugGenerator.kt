package io.realworld.conduit.shared.infrastructure.slug

import com.github.slugify.Slugify
import io.realworld.conduit.shared.domain.SlugGenerator

class SlugifySlugGenerator : SlugGenerator {
    private val slugify = Slugify()

    override fun generate(value: String): String {
        return slugify.slugify(value)
    }
}
