package com.x930073498.component.auto.plugin.inject

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.x930073498.component.auto.plugin.register.AutoRegisterHelper
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import java.io.File
import java.io.FileNotFoundException
import java.lang.reflect.Type


internal fun asIs(access: Int, flag: Int): Boolean {
    return access and flag == flag
}

/**
 * 缓存扫描到结果的文件
 * @param project
 * @return File
 */
fun getCacheFile(project: Project, fileName: String): File {
    val baseDir = AutoRegisterHelper.getCacheFileDir(project)
    if (AutoRegisterHelper.mkdirs(baseDir)) {
        return File(baseDir, fileName)
    } else {
        throw  FileNotFoundException("Not found  path:$baseDir")
    }
}

/**
 * 读取文件内容并创建Map
 * @param file 缓存文件
 * @param gson Gson
 * @return
 */
fun <T> read(gson: Gson, file: File, type: Type): T? {
    return if (file.exists()) {
        val text = file.readText()
        if (text.isNotEmpty()) {
            runCatching {
                gson.fromJson<T>(text, type)
            }.onFailure {
                it.printStackTrace()
                file.delete()
            }.getOrNull()
        } else {
            null
        }
    } else {
        null
    }
}

inline fun <reified T> read(gson: Gson, file: File): T? {
    return read(gson, file, object : TypeToken<T>() {}.type)
}

fun <T> write(gson: Gson, file: File, data: T) {
    val content = gson.toJson(data)
    file.ensureParentDirsCreated()
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText(content)
}