package com.x930073498.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.tasks.KOTLIN_KAPT_PLUGIN_ID
import com.x930073498.Libraries
import com.x930073498.Versions
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class StubPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("测试----")

        project.subprojects {

            tasks.withType<KotlinCompile> {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_1_8.toString()
                }
            }

            afterEvaluate {
                if (plugins.hasPlugin(AppPlugin::class.java)){
                    println("enter this line ")
                    plugins.apply("dagger.hilt.android.plugin")
                    dependencies{
                        add("kapt", "com.google.dagger:hilt-android-compiler:+")
                        add("implementation", "com.google.dagger:hilt-android:+")
                        add("kapt", "androidx.hilt:hilt-compiler:+")
                        add("implementation","androidx.hilt:hilt-lifecycle-viewmodel:+")
                    }
                }
            }



            plugins.whenPluginAdded {
//                if (this is AppPlugin) {
//                    this@subprojects.apply {
//                        plugin(KOTLIN_KAPT_PLUGIN_ID)
//                        plugin("dagger.hilt.android.plugin")
//                    }
//                    dependencies {
//                        add("implementation", "com.google.dagger:hilt-android:+")
//                        add("kapt", "com.google.dagger:hilt-android-compiler:+")
//                    }
//                }
                if (this is KotlinAndroidPluginWrapper) {
                    extensions.findByType<AndroidExtensionsExtension>()?.apply {
                        isExperimental = true
                    }
                }
                if (this is AppPlugin || this is LibraryPlugin) {
                    dependencies {
                        add("implementation", Libraries.kotlin)
//
                    }

                    android.apply {
                        compileSdkVersion(Versions.compileSdk)
                        buildToolsVersion(Versions.buildTools)

                        defaultConfig {
                            minSdkVersion(Versions.minSdk)
                            targetSdkVersion(Versions.targetSdk)
                        }

                        lintOptions {
                            isAbortOnError = true
                            textReport = true
                            textOutput("stdout")
                        }

                        compileOptions {
                            sourceCompatibility = JavaVersion.VERSION_1_8
                            targetCompatibility = JavaVersion.VERSION_1_8
                        }

                        sourceSets {
                            getByName("main").java.apply {
                                setSrcDirs(srcDirs + file("src/main/kotlin"))
                            }
                            getByName("test").java.apply {
                                setSrcDirs(srcDirs + file("src/test/kotlin"))
                            }
                        }
                    }
                }
            }
        }
    }
}