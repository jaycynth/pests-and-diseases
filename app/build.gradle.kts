import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}


android {
    compileSdkVersion(Apps.compileSdk)
    buildToolsVersion = Apps.buildToolsVersion

    defaultConfig {
        applicationId = Apps.applicationId
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode = Apps.versionCode
        versionName = Apps.versionName
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildFeatures {
        dataBinding = true
        mlModelBinding = true
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}




dependencies {
    //std lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //app libs
    implementation(AppLibs.appLibraries)
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlinVersion"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0-rc1")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0-rc1")
    // CameraX core library using camera2 implementation
    implementation ("androidx.camera:camera-camera2:1.1.0-alpha05")
    implementation ("androidx.camera:camera-lifecycle:1.1.0-alpha05")
    implementation ("androidx.camera:camera-view:1.0.0-alpha25")
    implementation ("androidx.activity:activity-ktx:1.2.3")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation ("pub.devrel:easypermissions:3.0.0")


    kapt(AppLibs.kaptLibraries)
    //test libs
    testImplementation(AppLibs.testLibraries)
    androidTestImplementation(AppLibs.androidTestLibraries)
}


