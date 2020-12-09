package com.x930073498.router.impl

import android.os.Bundle
import com.x930073498.router.action.ContextHolder
import com.x930073498.router.action.Target

interface ServiceActionDelegate : ActionDelegate {
    override fun type(): ActionType {
        return ActionType.SERVICE
    }

    suspend fun factory(): Factory

    fun autoInvoke(): Boolean

    interface Factory {
        suspend fun create(contextHolder: ContextHolder, clazz: Class<*>, bundle: Bundle):IService?

    }

}