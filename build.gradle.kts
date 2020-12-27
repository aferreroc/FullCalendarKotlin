plugins {
    kotlin("js") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.21"
}

group = "com.initiumsys.alyes"
version = "1.0"

val kotlinVersion = "1.4.21"
val kotlinJsVersion = "pre.133-kotlin-$kotlinVersion"
val kotlinReactVersion = "17.0.0-$kotlinJsVersion"
val kotlinSerializationVersion = "1.0.1"
val fullCalendarVersion = "5.5.0"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
}

dependencies {
    testImplementation(kotlin("test-js"))
    implementation("org.jetbrains:kotlin-react:$kotlinReactVersion")
    implementation("org.jetbrains:kotlin-react-dom:$kotlinReactVersion")
    implementation("org.jetbrains:kotlin-styled:5.2.0-$kotlinJsVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")

    implementation("com.ccfraser.muirwik:muirwik-components:0.6.+")

    implementation(devNpm("webpack-bundle-analyzer", "^3.8.0"))

    // el fullcalendar
    implementation(npm("@fullcalendar/react", fullCalendarVersion, generateExternals = false))
    implementation(npm("@fullcalendar/daygrid", fullCalendarVersion, generateExternals = false))
    implementation(npm("@fullcalendar/core", fullCalendarVersion, generateExternals = false))
    implementation(npm("@fullcalendar/interaction", fullCalendarVersion, generateExternals = false))
}

kotlin {
    js(LEGACY) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
}