interface Ignore
interface Base: Ignore, Parcelable

data class Location0(var lat: Double, var lng: Double): Base
class Location1(var lat: Double, var lng: Double)
data
class Location2(var lat: Double, var lng: Double)

data // comment here!!
class Location2A(var lat: Double, var lng: Double)

final data class Location3(var lat: Double, var lng: Double): Ignore
data private class Location4(var lat: Double, var lng: Double)
@Parcelize data class Location5(var lat: Double, var lng: Double) {
  val lat10E6: Long = lat * 10e6
}
@Parcelize class Location6(var lat: Double, var lng: Double)

data class LocationM(var lat: Double, var lng: Double) {
  val lat10E6: Int = 0
    get() = (lat * 1000_000).toInt()

  val lng10E6: Int
    get() = (lng * 1000_000).toInt()
}

data class LocationN(var lat: Double, var lng: Double) {
  val lat10E6: Int = (lat * 1000_000).toInt()

  val lng10E6: Int = (lng * 1000_000).toInt()
}

data class LocationX(var lat: Double, var lng: Double): Base {
  val lat10E6: Int by lazy {
    (lat * 1000_000).toInt()
  }

  val lng10E6: Int by lazy {
    (lng * 1000_000).toInt()
  }
}

data class LocationY(var lat: Double, var lng: Double): Ignore {
  var alt: Double = 0.0
    get() = field
    set(value) {
      field = value
    }
}