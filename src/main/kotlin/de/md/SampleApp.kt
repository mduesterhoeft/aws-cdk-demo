package de.md

import apple.security.AppleProvider
import software.amazon.awscdk.App
import software.amazon.awscdk.AppProps
import software.amazon.awscdk.Environment
import software.amazon.awscdk.StackProps

fun main() {
    App()
        .also { SampleStack(it, "sample-stack", StackProps.builder()
            .withEnv(Environment.builder()
                .withRegion("eu-central-1")
                .build())
            .build())  }
        .synth()
}