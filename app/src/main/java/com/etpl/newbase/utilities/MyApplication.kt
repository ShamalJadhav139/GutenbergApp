package com.etpl.newbase.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle

class MyApplication :Application(),Application.ActivityLifecycleCallbacks{

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppDetails.activity = activity;
        AppDetails.context = this
    }

    override fun onActivityPaused(activity: Activity?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResumed(activity: Activity?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStarted(activity: Activity?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityDestroyed(activity: Activity?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStopped(activity: Activity?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}