trait Interpolator {
  def interpolate(X: Iterable[Double], Y: Iterable[Double]): Double => Double
}
object Lagrange extends Interpolator {
  private def basisPolynomial(X: Iterable[Double], Y: Iterable[Double], i: Int): Double => Double = {

  }

  override def interpolate(X: Iterable[Double], Y: Iterable[Double]): Double => Double = {

  }
}

object Newton extends Interpolator {
  override def interpolate(X: Iterable[Double], Y: Iterable[Double]): Double => Double = ???
}



