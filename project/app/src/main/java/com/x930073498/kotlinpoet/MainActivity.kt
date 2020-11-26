package com.x930073498.kotlinpoet

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.x930073498.annotations.ActivityAnnotation
import com.x930073498.annotations.ValueAutowiredAnnotation
import com.x930073498.router.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@ActivityAnnotation(path = "/test/test")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var app:App

    @Inject
    lateinit var foo:ActivityFoo

val viewModel by lazy {
    ViewModelProvider(this)[MainViewModel::class.java]
}
    @ValueAutowiredAnnotation("name")
    var name: String = ""
    val tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app.a()
        foo.test()
        viewModel.test()
        setContentView(R.layout.activity_main)
        val uri = Uri.parse("/test/a?name=24254")
        val router = Router.from(uri)
        GlobalScope.launch(Dispatchers.Main) {
            val fragment = router.navigate<Fragment>()
            if (fragment != null) {
                supportFragmentManager.beginTransaction().add(R.id.container, fragment)
                    .commitAllowingStateLoss()
            }
        }
//

    }


}

class A {
    init {
        println("enter this line 11111")
    }

    companion object {
        init {
            println("enter this line 2222")
        }
    }
}