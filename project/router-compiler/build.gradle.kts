import com.x930073498.Libraries

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(Libraries.kotlin)
    kapt(Libraries.google_auto_service)
    implementation(Libraries.google_auto_service)
    implementation(Libraries.kotlin_poet)
    implementation(Libraries.kotlin_reflect)
    implementation(Libraries.fastJson)
    implementation(Libraries.androidx_annotation)
    implementation(Libraries.kotlin_compiler_embeddable)
    implementation(project(":router-annotations"))
}