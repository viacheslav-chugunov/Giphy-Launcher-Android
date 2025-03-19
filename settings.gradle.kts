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

rootProject.name = "GiphyLauncher"
include(":app")
include(":core")
include(":feature:storage")
include(":feature:network")
include(":feature:screen:gifs-list")
include(":feature:screen:gif-details")
include(":feature:screen:search-gifs")
