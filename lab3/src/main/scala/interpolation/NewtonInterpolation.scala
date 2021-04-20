package interpolation

import interpolation.NewtonInterpolation.dividedDifference

import scala.Function.tupled

object NewtonInterpolation {
  def finiteDifference(Y: Array[Double]): Array[Double] =
    Y.sliding(2).map(y => y(1) - y(0)).toArray

  def dividedDifference(Y: Array[Double], step: Double): Array[Double] =
    finiteDifference(Y.map(_ / step))
}

class NewtonInterpolation(
    val X:Array[Double],
    val Y:Array[Double],
    val h:Double=1.0
) extends Interpolation {
  val dividedDifferences: Array[Array[Double]] = X
    .indices
    .drop(1)
    .foldLeft(
      Array(Y)
    )((l, i) => l :+ dividedDifference(l.last, i * h))

  def apply(x: Double): Double = dividedDifferences
    .zip(X
      .dropRight(1)
      .reverse
      .map(x - _)
      .tails
      .map(_.product)
      .toSeq
      .reverse
    ).map(tupled(_.head * _))
    .sum
}
