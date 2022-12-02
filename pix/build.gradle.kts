import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("maven-publish")
}

android {
    compileSdkVersion(33)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(33)
        versionCode = 6
        versionName = "1.5.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
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
    }
}

repositories {
    mavenCentral()
    maven("https://maven.google.com")
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.4.0-alpha02")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.fragment:fragment-ktx:1.3.4")

    // CameraX core library using camera2 implementation
    implementation("androidx.camera:camera-camera2:1.1.0-alpha05")
    // CameraX Lifecycle Library
    implementation("androidx.camera:camera-lifecycle:1.1.0-alpha05")
    // CameraX View class
    implementation("androidx.camera:camera-view:1.0.0-alpha25")
    // If you want to additionally use the CameraX Extensions library
    implementation("androidx.camera:camera-extensions:1.0.0-alpha25")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    // Skip this if you don"t want to use integration libraries or configure Glide.
    kapt("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.github.bumptech.glide:recyclerview-integration:4.12.0") {
        // Excludes the support library because it"s already included by Glide.
        isTransitive = false
    }

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0-native-mt")

    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
}


val githubProperties = Properties()
githubProperties.load(FileInputStream(rootProject.file("github.properties")))

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("gpr") {
                groupId = "com.dondo"
                artifactId = "pix"
                version = "1.6"
                from(components["release"])
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/dondoapp/PixImagePicker")
                credentials {
                    username = githubProperties["gpr.usr"] as String
                    password = githubProperties["gpr.key"] as String
                }
            }
        }
    }
}