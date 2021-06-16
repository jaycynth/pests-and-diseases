import org.gradle.api.artifacts.dsl.DependencyHandler

object Apps {
    const val buildToolsVersion = "30.0.3"
    const val compileSdk = 30
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val applicationId = "com.julie.adchakathon"
}

private const val navVersion = "2.3.3"
private const val coroutineVersion = "1.4.1"
private const val hiltVersion = "2.28.1-alpha"
private const val lifecycleVersion = "2.2.0"
private const val okhttpVersion = "4.9.0"
private const val retrofitVersion = "2.9.0"
private const val hiltViewModelVersion ="1.0.0-alpha02"


object BuildPlugins {
    const val gradlePlugin = "com.android.tools.build:gradle:4.1.2"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31"
    const val daggerPlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    const val navPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"
}


object AppLibs {
    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.0"
    private const val appcompat = "androidx.appcompat:appcompat:1.3.0"
    private const val materialUI = "com.google.android.material:material:1.3.0"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    private const val androidXlegacy = "androidx.legacy:legacy-support-v4:1.0.0"
    private const val androidXcore = "androidx.core:core-ktx:1.3.2"

    private const val navFrag = "androidx.navigation:navigation-fragment-ktx:$navVersion"
    private const val navUI = "androidx.navigation:navigation-ui-ktx:$navVersion"

    private const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
    private const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"


    private const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
    private const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
    private const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltViewModelVersion"
    private const val hiltCompiler = "androidx.hilt:hilt-compiler:$hiltViewModelVersion"


    private const val viewmodelLifecycle =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    private const val livedataLifecycle =
        "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    private const val extensionLifecycle = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    private const val fragmentLifecycle = "androidx.fragment:fragment-ktx:1.2.4"

    private const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
    private const val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"

    private const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    private const val retrofitGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"

    private const val kotlinDl =  "org.jetbrains.kotlinx:kotlin-deeplearning-api:0.2.0"

    private const val tensorflowlite = "org.tensorflow:tensorflow-lite-support:0.1.0-rc1"
    private const val tensorflowMetadata = "org.tensorflow:tensorflow-lite-metadata:0.1.0-rc1"
    // CameraX core library using camera2 implementation
    private const val camera2 = "androidx.camera:camera-camera2:1.1.0-alpha05"
    private const val cameraLifecycle = "androidx.camera:camera-lifecycle:1.1.0-alpha05"
    private const val cameraView =  "androidx.camera:camera-view:1.0.0-alpha25"
    private const val actvityKtx=  "androidx.activity:activity-ktx:1.2.3"
    private const val easyPermissions =  "pub.devrel:easypermissions:3.0.0"


    val kaptLibraries = arrayListOf<String>().apply {
        add(hiltAndroidCompiler)
        add(hiltCompiler)
    }


    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(appcompat)
        add(materialUI)
        add(constraintLayout)
        add(androidXlegacy)
        add(androidXcore)
        add(navFrag)
        add(navUI)
        add(coroutinesCore)
        add(coroutinesAndroid)
        add(hiltAndroid)
        add(hiltViewModel)
        add(viewmodelLifecycle)
        add(livedataLifecycle)
        add(extensionLifecycle)
        add(fragmentLifecycle)
        add(okhttp)
        add(okhttpInterceptor)
        add(retrofit)
        add(retrofitGson)
        add(kotlinDl)
        add(tensorflowlite)
        add(tensorflowMetadata)
        add(actvityKtx)
        add(camera2)
        add(cameraLifecycle)
        add(cameraView)
        add(easyPermissions)

    }


    private const val junit = "junit:junit:4.13.2"
    private const val extJUnit = "androidx.test.ext:junit:1.1.2"
    private const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}