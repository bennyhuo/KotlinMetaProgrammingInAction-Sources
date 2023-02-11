package com.bennyhuo.android.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

//    e: Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type Array<(out) String!>?
//    File("illegal path").list().forEach {
//      println(it)
//    }

  }
}
