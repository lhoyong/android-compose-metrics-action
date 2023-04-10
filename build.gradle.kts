plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
}

subprojects {
    // ./gradlew assembleDebug -Papp.enableComposeCompilerReports=true
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            val args = mutableListOf("-Xopt-in=kotlin.RequiresOptIn")

            if (project.findProperty("app.enableComposeCompilerReports") == "true") {
                args.addAll(
                    listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                                project.buildDir.absolutePath + "/compose_metrics"
                    )
                )
                args.addAll(
                    listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                                project.buildDir.absolutePath + "/compose_metrics"
                    )
                )
            }

            freeCompilerArgs = args
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
