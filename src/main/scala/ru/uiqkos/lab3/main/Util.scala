package main

import scala.math.pow

object Util {
  implicit class CustomDouble(x: Double) {
    def ^(power: Double): Double = pow(x, power)

  }

  implicit class IgnoreIterable[T](array: Array[T]) {
    def ignore(i: Int): Iterable[T] =
      List(
        array.take(i),
        array.drop(i + 1)
      ).flatten
  }

}
