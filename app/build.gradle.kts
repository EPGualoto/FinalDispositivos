plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    //añadidos
    //Volverlo a pasar a TOM
    id("com.google.devtools.ksp")

    id("androidx.navigation.safeargs.kotlin")

    //FIREBASE
    // id("com.android.application")   --> Ya no necesitamos porque ya esta arriba
    id("com.google.gms.google-services")
}

android {
    namespace = "com.gualoto.pfinaldm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gualoto.pfinaldm"
        minSdk = 30
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
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.car.ui.lib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.bundles.retrofit)
    implementation(libs.coil)
    implementation(libs.lottieLib)

    // RecyclerView
    implementation(libs.swiperefreshlayout)
    implementation(libs.coordinatorlayout)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler.ksp)

    //Biometric
    implementation("androidx.biometric:biometric:1.1.0")

    // SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    //Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Fragment, activity viewmodel
    implementation("androidx.fragment:fragment-ktx:1.8.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")


    //Import FIREBASE BoM    SDK
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-analytics")


    //FIRESTORE
    implementation("com.google.firebase:firebase-firestore")

    //CARD POP-UP
    implementation ("androidx.cardview:cardview:1.0.0")

    //Material
    implementation ("com.google.android.material:material:1.9.0")
}