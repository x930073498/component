package com.x930073498.component.test

import com.x930073498.component.annotations.InterceptorAnnotation
import com.x930073498.component.annotations.InterceptorScope
import com.x930073498.component.auto.LogUtil
import com.x930073498.component.router.impl.RouterInterceptor
import com.x930073498.component.router.interceptor.Chain
import com.x930073498.component.router.request.RouterRequest
import com.x930073498.component.router.response.RouterResponse

@InterceptorAnnotation(
    "/test/interceptors/test1",
    scope = InterceptorScope.NORMAL
)
class TestInterceptor : RouterInterceptor {
    override suspend fun intercept(chain: Chain<RouterRequest, RouterResponse>): RouterResponse {
        LogUtil.log("enter this line interceptor test")
        return chain.process(chain.request())
    }
}

@InterceptorAnnotation(
    path = "/interceptor/scheme-http",
    scope = InterceptorScope.GLOBAL
)
class HttpSchemeInterceptor : RouterInterceptor {
    override suspend fun intercept(chain: Chain<RouterRequest, RouterResponse>): RouterResponse {
        val request = chain.request()
        val uri = request.uri
        val scheme = uri.scheme
        return if (scheme.isNullOrEmpty()) {
            chain.process(request)
        } else {
            if (scheme == "http" || scheme == "https") {
                chain.process(request.buildUpon()
                    .uri {
                        path("/fragment/web")
                    }
                    .serializer {
                        put("url", uri.toString())
                    }
                    .build())
            } else {
                chain.process(request)
            }
        }
    }

}