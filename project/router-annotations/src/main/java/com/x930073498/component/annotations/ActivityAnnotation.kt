package com.x930073498.component.annotations


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ActivityAnnotation(
    val group: String = "",
    val path: String,
    val autoRegister:Boolean=true,
    val interceptors: Array<String> = [],
)



