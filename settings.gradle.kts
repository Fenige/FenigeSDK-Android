pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://github.com/Fenige/FenigeSDK-Android")
            credentials {
                username = "Fenige"
                password = "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"
            }}
        mavenCentral()
    }
}

rootProject.name = "FenigeSdk"
include(":app")
include(":fenigepaytool")
