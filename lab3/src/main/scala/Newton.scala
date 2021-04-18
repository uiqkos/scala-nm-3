import Util.CustomDouble

import scala.{+:, :+}
import scala.Function.tupled

object Newton {
  def finiteDifference(Y: Array[Double]): Array[Double] =
    Y.sliding(2).map(y => y(1) - y(0)).toArray

  private def dividedDifference(Y: Array[Double], step: Double): Array[Double] =
    finiteDifference(Y.map(_ / step))

  def method1(
    X: Array[Double],
    Y: Array[Double],
    h: Double = 1.0
  ): Double => Double = {

    def P(x: Double): Double = {
      val n = X.length
      val k0 = (x - X.head) / h

      for {
        k <- (0 until n).map(i => (0 to i).map(k0 - _).product);
        nFact <- (1 to n).map(n => (1 to n).product.toDouble);
        y0 <- X
          .indices
          .drop(1)
          .foldLeft(
            Array(Y)
          )((l, i) => l :+ finiteDifference(l.last)) //
      } yield y0.head * k / nFact
    }.sum + Y.head

     P
    }

  def byDivided(
    X: Array[Double],
    Y: Array[Double],
    h: Double = 1.0
  ): Double => Double = {
    def N(x: Double): Double = {
      X
        .indices
        .drop(1)
        .foldLeft(
          Array(Y)
        )((l, i) => l :+ dividedDifference(l.last, i * h))
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
    N
  }

  def interpolate(
    X: Array[Double],
    Y: Array[Double],
    h: Double = 1.0
  ): Double => Double = {
    byDivided(X, Y, h)
//    method1(X, Y, h)
  }
}