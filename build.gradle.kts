buildscript {

    val kotlinVersion by extra("1.4.21")
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }

    dependencies {
        classpath(BuildPlugins.gradlePlugin)
        classpath(BuildPlugins.kotlinPlugin)
        classpath(BuildPlugins.daggerPlugin)
        classpath(BuildPlugins.navPlugin)

    }

}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}



