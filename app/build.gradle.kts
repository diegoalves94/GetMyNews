plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "me.study.getmynews"
    compileSdk = 34

    defaultConfig {
        applicationId = "me.study.getmynews"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
        viewBinding = true
        dataBinding = true
        buildConfig = true
        flavorDimensions += "newsSource"
    }
    hilt {
        enableAggregatingTask = true
    }

    productFlavors {
        create("bbc-news") {
            dimension = "newsSource"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"

            buildConfigField("String", "NEWS_API_KEY", "\"7925162a64f9479abb6091e9c6aee4a3\"")
            buildConfigField("String", "NEWS_BASE_URL", "\"https://newsapi.org/v2/\"")
            buildConfigField("String", "NEWS_SOURCE", "\"bbc-news\"")
        }
        create("techcrunch") {
            dimension = "newsSource"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"

            buildConfigField("String", "NEWS_API_KEY", "\"7925162a64f9479abb6091e9c6aee4a3\"")
            buildConfigField("String", "NEWS_BASE_URL", "\"https://newsapi.org/v2/\"")
            buildConfigField("String", "NEWS_SOURCE", "\"techcrunch\"")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Material Design
    implementation("com.google.android.material:material:1.11.0")

    // Splash API
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    //Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi:1.15.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    // Lifecycle
    val lifecycleVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-service:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")

    // Navigation Components
    val navVersion = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.14.1")
    ksp("com.github.bumptech.glide:compiler:4.14.1")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    implementation("androidx.hilt:hilt-navigation:1.2.0")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")
    ksp("com.google.dagger:hilt-compiler:2.50")

    //Hilt - Unit Tests
    testImplementation("com.google.dagger:hilt-android-testing:2.50")
    kspTest("com.google.dagger:hilt-compiler:2.50")

    //Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("io.mockk:mockk-common:1.12.4")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}