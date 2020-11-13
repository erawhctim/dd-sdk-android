/*
 * Unless explicitly stated otherwise all files in this repository are licensed under the Apache License Version 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2016-Present Datadog, Inc.
 */

package com.datadog.gradle.config

import com.android.build.gradle.tasks.factory.AndroidUnitTest
import java.io.File
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.kotlinConfig() {

    taskConfig<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }

    val javaAgentJar = File(File(rootDir, "libs"), "dd-java-agent-0.6.7.jar")
    afterEvaluate {
        taskConfig<AndroidUnitTest>() {
            if (environment["DD_INTEGRATION_JUNIT_5_ENABLED"] == "true") {
                println("DD JUNIT 5 ENABLED for :${this@kotlinConfig.name}:${this.name}")

                environment["DD_ENV_TESTS"]?.let { environment("DD_ENV", it) }
                environment("DD_INTEGRATIONS_ENABLED", "false")
                environment("DD_JMX_FETCH_ENABLED", "false")

                println("-javaagent:${javaAgentJar.absolutePath}")
                jvmArgs("-javaagent:${javaAgentJar.absolutePath}")
            } else {
                println("DD JUNIT 5 DISABLED for ${this.name}")
            }
        }
    }
}
