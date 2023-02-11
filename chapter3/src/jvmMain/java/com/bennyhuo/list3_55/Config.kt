package com.bennyhuo.list3_55

class Config(val map: Map<String, String>) {
 // 混淆之后，仍然相当于 map["name"]
 val name by map
 // 混淆之后，仍然相当于 map["version"]
 val version by map
}
