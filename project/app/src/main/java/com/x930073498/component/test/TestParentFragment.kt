package com.x930073498.component.test

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.x930073498.component.annotations.FactoryAnnotation
import com.x930073498.component.annotations.FragmentAnnotation
import com.x930073498.component.annotations.ValueAutowiredAnnotation
import com.x930073498.component.R
import com.x930073498.component.auto.LogUtil
import com.x930073498.component.router.*
import com.x930073498.component.router.action.ContextHolder
import com.x930073498.component.router.asActivity
import com.x930073498.component.router.impl.FragmentActionDelegate


@FragmentAnnotation(path = "/test/parent")
open class TestParentFragment : Fragment(R.layout.fragment_test) {
    @ValueAutowiredAnnotation
    var name = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().findViewById<TextView>(R.id.tv)?.text = name
        requireView().setOnClickListener {
            Router.from("/activity/navigation")
//                .asActivity(lifecycleScope,navigatorOption = NavigatorOption.ActivityNavigatorOption(launchMode = LaunchMode.SingleTop))
                .asActivity()
                .requestActivity()

        }
    }

    @FactoryAnnotation
    class Factory : FragmentActionDelegate.Factory {
        override fun create(
            contextHolder: ContextHolder,
            clazz: Class<*>,
            bundle: Bundle,
        ): TestParentFragment {
            return TestParentFragment().also {
                it.arguments = bundle
            }
        }
    }

    companion object {

        @FactoryAnnotation
        fun create(bundle: Bundle): TestParentFragment {
            return TestParentFragment().also { it.arguments = bundle }
        }
    }
}



