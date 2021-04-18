import Util.IgnoreIterable

import scala.Function.tupled

object Lagrange {
  private def basisPolynomial(
    X: Array[Double],
    i: Int
  ): Double => Double = {
    def p(x: Double): Double = X
      .ignore(i)
      .map(x_ => (x - x_) / (X(i) - x_))
      .product
    p
  }

  def interpolate(X: Array[Double], Y: Array[Double]): Double => Double = {
    val P = X.indices.map(basisPolynomial(X, _)).toList
    def L(x: Double): Double = Y
      .zipWithIndex
      .map(tupled(
        (y, i) => P(i)(x) * y
      )).sum
    L
  }
}