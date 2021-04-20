import LagrangeInterpolation.basisPolynomial
import Util.IgnoreIterable
import scala.Function.tupled

object LagrangeInterpolation {
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
}

class LagrangeInterpolation(X: Array[Double], Y: Array[Double]) extends Interpolation {
  val P: Seq[Double => Double] = X.indices.map(basisPolynomial(X, _))

  def apply(x: Double): Double = Y
    .zipWithIndex
    .map(tupled(
      (y, i) => P(i)(x) * y
    )).sum
}
