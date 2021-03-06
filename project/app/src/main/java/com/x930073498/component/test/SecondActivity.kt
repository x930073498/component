package com.x930073498.component.test

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.keyIterator
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.Success
import com.x930073498.component.annotations.*
import com.x930073498.component.auto.LogUtil
import com.x930073498.component.core.requireLifecycleOwner
import com.x930073498.component.databinding.ActivitySecondBinding
import com.x930073498.component.router.Router
import com.x930073498.component.router.coroutines.*
import com.x930073498.component.router.asActivity
import com.x930073498.component.router.core.hasPropertyAutoInjectByRouter
import com.x930073498.component.router.impl.DirectRouterInterceptor
import com.x930073498.component.router.interceptor.Chain
import com.x930073498.component.router.navigate
import com.x930073498.component.router.request.RouterRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 */
@InterceptorAnnotation("/interceptor/toActivitySecond")
class SecondInterceptor : DirectRouterInterceptor {
    override fun intercept(chain: Chain<RouterRequest>): Chain.ChainResult<RouterRequest> {
        return chain.process(
            chain.request().newBuilder().uri {
                path("/activity/second")
            }.build()
        )
    }

}

@ActivityAnnotation(path = "/activity/second", interceptors = ["/test/interceptors/test1"])
//@FragmentAnnotation(path = "/activity/second", interceptors = ["/test/interceptors/test1"])
//@InterceptorAnnotation(path = "/activity/second")
//@ServiceAnnotation(path = "/activity/second")
//@MethodAnnotation(path = "/activity/second")
class SecondActivity : AppCompatActivity() {
    @ValueAutowiredAnnotation
    var array: SparseArray<Data>? = null
    private val binding by lazy {
        ActivitySecondBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        array?.let { sparseArray ->
            sparseArray.keyIterator().forEach {
                LogUtil.log("enter this line array key=$it")
            }
            LogUtil.log("enter this line array[1]=${sparseArray[1]}")
        }
        lifecycleScope.launch {
            listenOf {
                delay(2000)
                "1"
            }
                .map { it.toInt() }
                .await()
        }

        binding.tvSecond.setOnClickListener {
            Router.from("/activity/navigation")
//                .asActivity(lifecycleScope,navigatorOption = NavigatorOption.ActivityNavigatorOption(launchMode = LaunchMode.SingleTop))
                .asActivity()
                .requestActivity()


        }

//        GlobalScope.launch {
//            Router.from("test3?a=4&b=3").navigate<TestService>()?.test()
//            LogUtil.log( Router.from("test4?a=4&b=3").bundle {
//                put("c","4")
//            }.navigate<Any>())
//
//        }


    }

    fun toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Router.from("/method/toast")
            .navigate {
                uri {
                    appendQueryParameter("info", "{msg:\"$msg\",duration:$duration}")
                }
            }
//            .bindLifecycle(this)
            .listen {
                while (true) {
                    LogUtil.log("enter this line 989777777777")
                    delay(1000)
                }
            }
            .end()
    }
}