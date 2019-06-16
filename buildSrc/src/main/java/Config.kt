object Versions {
    // android
    val androidGradleVersion = "3.4.1"
    val kotlin_stdlibVersion = "1.3.31"

    val androidXVersion = "1.0.2"
    val materialComponentVersion = "1.1.0-alpha06"
    val constraintLayoutVersion = "1.1.3"
    val lifecycleVersion = "2.0.0"
    val kotlinCoroutinesVersion = "1.2.1"
    val supportVersion = "1.0.0"

    // 3rd Party Libraries
    val glideVersion = "4.9.0"
    val retrofitVersion = "2.5.0"
    val okhttpVersion = "3.14.2"
    val moshiVersion = "1.8.0"
    val koinVersion = "2.0.0"
    val easyImageVersion = "3.0.1"
    val compressorVersion = "2.1.0"
    val circleViewVersion = "3.0.0"

    //Testing
    //Unit Testing
    val robolectricVersion = "3.8"
    val junitVersion = "4.12"
    val mockitoVersion = "2.1.0"
    val kluentVersion = "1.49"

    //Acceptance Testing
    val testVersion = "1.2.0"
    val testJunitVersion = "1.1.1"
    val espressoVersion = "3.1.1"

}


object BuildPlugins {
    val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradleVersion}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_stdlibVersion}"
}

object Android {
    val compileSdkVersion = 28
    val applicationId = "com.ferdyrodriguez.loquiero"
    val minSdkVersion = 21
    val targetSdkVersion = 28
    val versionCode = 1
    val versionName = "1.0.0"
}


object Modules {
    val data = ":data"
    val domain = ":domain"
}

object Libs {
    val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_stdlibVersion}"
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinesVersion}"
    val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutinesVersion}"

    val appcompat = "androidx.appcompat:appcompat:${Versions.androidXVersion}"
    val corektx = "androidx.core:core-ktx:${Versions.androidXVersion}"
    val materialComponents = "com.google.android.material:material:${Versions.materialComponentVersion}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.supportVersion}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    val cardview = "androidx.cardview:cardview:${Versions.supportVersion}"
    val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"
    val androidAnnotations = "androidx.annotation:annotation:${Versions.androidXVersion}"

    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"
    val moshi = "com.squareup.moshi:moshi:${Versions.moshiVersion}"

    val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
    val easyImage = "com.github.jkwiecien:EasyImage:${Versions.easyImageVersion}"
    val compressor = "id.zelory:compressor:${Versions.compressorVersion}"
    val circleImage = "de.hdodenhof:circleimageview:${Versions.circleViewVersion}"

    val koinCore = "org.koin:koin-core:${Versions.koinVersion}"
    val koinAndroid = "org.koin:koin-android:${Versions.koinVersion}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koinVersion}"
}

object TestLibs {
    val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin_stdlibVersion}"
    val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoVersion}"
    val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
    val kluent = "org.amshove.kluent:kluent-android:${Versions.kluentVersion}"
    val junit = "junit:junit:${Versions.junitVersion}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    val koinTest = "org.koin:koin-test:${Versions.koinVersion}"
    val runner = "androidx.test:runner:${Versions.testVersion}"
    val testCore = "androidx.test:core:${Versions.testVersion}"
    val testCoreKtx = "androidx.test:core-ktx:${Versions.testVersion}"
    val testExt = "androidx.test.ext:junit:${Versions.testJunitVersion}"
    val testRules = "androidx.test:rules:${Versions.testVersion}"
}