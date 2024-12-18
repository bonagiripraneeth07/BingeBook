plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.bingebook"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bingebook"
        minSdk = 26
        targetSdk = 33
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
}
configurations.all {
    resolutionStrategy {
        force ("com.android.support:support-v4:28.0.0")// Example of forcing a version
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("com.github.amitshekhariitbhu.Fast-Android-Networking:android-networking:1.0.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
       implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
     implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //implementation("com.github.amitshekhariitbhu.Fast-Android-Networking:android-networking:1.0.4")
}