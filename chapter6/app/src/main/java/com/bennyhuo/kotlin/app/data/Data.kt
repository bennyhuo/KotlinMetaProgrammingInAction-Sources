package com.bennyhuo.kotlin.app.data

import kotlin.concurrent.thread

/**
 * Created by benny.
 */
data class District(var name: String)

data class Location(var lat: Double, var lng: Double)

data class Company(
  var name: String,
  var location: Location,
  var district: District
)

data class Speaker(var name: String, var age: Int, var company: Company)

data class Talk(var name: String, var speaker: Speaker)

fun main() {
  thread {  }
}