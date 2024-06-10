plugins {
    id ("com.android.library")
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("maven-publish")
}

android {
    namespace = "com.sdk.fenigepaytool"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_URL", "\"https://paytool-api-dev.fenige.pl/\"")
            buildConfigField("String", "TRANSACTION_URL", "\"https://paytool-dev.fenige.pl/\"")
        }
        release {
            buildConfigField("String", "API_URL", "\"https://paytool-api-dev.fenige.pl/\"")
            buildConfigField("String", "TRANSACTION_URL", "\"https://paytool-dev.fenige.pl/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.sdk.fenigepaytool"
            artifactId = "fenigeandroidlibrary"
            version = "1.2"

            artifact("$buildDir/outputs/aar/fenigepaytool-release.aar")
        }
    }

    repositories {
        maven {
            name = "GithubRepository"
            url = uri("https://maven.pkg.github.com/Fenige/FenigeSDK-Android")
            credentials {
                username = "Fenige"
                password = "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"
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    testImplementation(libs.koin.test)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    //async
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

    //moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    implementation ("com.intuit.sdp:sdp-android:1.1.0")

  //  implementation("com.sdk.fenigepaytool:fenigeandroidlibrary:1.1")

}