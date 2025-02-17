plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.weatherclothes.artist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.weatherclothes.artist"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storePassword = "weather_clothes_291024"
            keyPassword = "weather_clothes_291024"
            keyAlias = "weather_clothes_291024_key"
            storeFile = file("weather_clothes_291024.jks")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }

    tasks.register("buildReleaseAab") {
        dependsOn("bundleRelease")
        doLast {
            val aabPath = "../app/build/outputs/bundle/release/${project.name}${project.version}-release.aab"
            val aabFile = file(aabPath)
            if (aabFile.exists()) {
                println("${aabFile.absolutePath} exist")
                copy {
                    from(aabFile.absolutePath)
                    into("../")
                }
                val resultFile = file("../${project.name}${project.version}-release.aab")
                println(resultFile.exists())
            } else {
                println("${aabFile.absolutePath} NOT exist")
            }
        }
    }

    tasks.register("buildReleaseApk") {
        dependsOn("assembleRelease")
        doLast {val aabPath = "../app/build/outputs/apk/release/${project.name}${project.version}-release.apk"
            val aabFile = file(aabPath)
            if (aabFile.exists()) {
                println("${aabFile.absolutePath} exist")
                copy {
                    from(aabFile.absolutePath)
                    into("../")
                }
                val resultFile = file("../${project.name}${project.version}-release.apk")
                println(resultFile.exists())
            } else {
                println("${aabFile.absolutePath} NOT exist")
            }
        }
    }
}

dependencies {

    // base:
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // com.google.android.gms:
    implementation(libs.services.location)

    // dagger:
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    // Moshi:
    implementation(libs.moshi)
    implementation(libs.moshi.converter)
    ksp(libs.moshi.kotlin.codegen)

    // navigation:
    implementation(libs.bundles.navigation)

    // Retrofit:
    implementation(libs.retrofit)

    // tests:
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // viewPager2:
    implementation (libs.androidx.viewpager2)

    // room:
    implementation(libs.room)
    ksp(libs.room.compiler)
}