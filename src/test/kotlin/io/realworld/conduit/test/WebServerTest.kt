package io.realworld.conduit.test

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(WebServerExtension::class)
@Tag("integration-test")
annotation class WebServerTest
