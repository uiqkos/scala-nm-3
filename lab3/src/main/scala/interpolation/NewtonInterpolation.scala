package interpolation

import breeze.numerics.pow
import interpolation.NewtonInterpolation.dividedDifference

import scala.Function.tupled

object NewtonInterpolation {
  def finiteDifference(Y: Array[Double]): Array[Double] =
    Y.sliding(2).map(y => y(1) - y(0)).toArray

  def dividedDifference(X: Array[Double], Y: Array[Double]): Double = {
    if (X.length == 1) Y.head
    else (dividedDifference(X.drop(1), Y.drop(1)) - dividedDifference(X.dropRight(1), Y.dropRight(1))) / (X.last - X.head)
  }
}

class NewtonInterpolation(
    val X:Array[Double],
    val Y:Array[Double]
) extends Interpolation {
  def apply(x: Double): Double = X
    .indices
    .drop(1)
    .map(i => X.take(i).map(xi => x - xi).product * dividedDifference(X.take(i + 1), Y.take(i + 1)))
    .sum + Y(0)
}
