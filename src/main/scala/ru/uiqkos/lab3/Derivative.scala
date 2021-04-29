package ru.uiqkos.lab3

import ru.uiqkos.lab3.interpolation.SplineInterpolation

object Derivative {
  def derivative(f: Double => Double, x0: Double, xn: Double, h: Double = 0.1): Double => Double = {
    val dX = (BigDecimal(x0) to xn by h).map(_.toDouble).toArray
    new SplineInterpolation(
      dX, dX
        .map(x => (f(x + h) - f(x - h)) / (2 * h)), 3
    ).apply
  }
}
