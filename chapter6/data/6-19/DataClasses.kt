data class Location0(var lat: Double, var lng: Double): Base {
  val lat10E6: Int by lazy{ (lat * 1000_000).toInt() }

  val lng10E6: Int by lazy { (lng * 1000_000).toInt() }
}
class Location1(var lat: Double, var lng: Double) {
  val lat10E6: Int by lazy{ (lat * 1000_000).toInt() }

  val lng10E6: Int by lazy { (lng * 1000_000).toInt() }
}
data
class Location2(var lat: Double, var lng: Double) {
  val lat10E6: Int by lazy{ (lat * 1000_000).toInt() }

  val lng10E6: Int by lazy { (lng * 1000_000).toInt() }
}
final data class Location3(var lat: Double, var lng: Double): Ignore {
  val lat10E6: Int = (lat * 1000_000).toInt()

  val lng10E6: Int = (lng * 1000_000).toInt()
}
data private class Location4(var lat: Double, var lng: Double) {
  val lat10E6: Int = (lat * 1000_000).toInt()

  val lng10E6: Int = (lng * 1000_000).toInt()
}
@Parcelize data class Location5(var lat: Double, var lng: Double)
@Parcelize class Location6(var lat: Double, var lng: Double)