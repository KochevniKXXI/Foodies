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
        mavenCentral()
    }
}

rootProject.name = "Foodies"
include(":app")
include(":core:network")
include(":core:data")
include(":core:model")
include(":feature:catalog")
include(":feature:product")
include(":feature:cart")
include(":core:designsystem")
include(":core:runtime")
