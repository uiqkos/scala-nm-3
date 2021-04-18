import SplineInterpolation.Spline
import smile.math.MathEx.c
import smile.math.matrix.{Matrix, matrix}
import smile.math.{VectorLift, inv, matrix2MatrixExpression}

import scala.Function.tupled
import scala.math.pow

object SplineInterpolation {
  def solveInv(A: Matrix, b: VectorLift): Array[Double] = inv(A) * b
  def polynomial(x: Double, dim: Int): Array[Double] = (dim - 1 to 0 by -1).map(pow(x, _)).toArray

  case class Spline(X: Array[Double], Y: Array[Double]) {
    val dim: Int = X.length
    val A: Array[Double] = solveInv(matrix(X.map(polynomial(_, dim))), c(Y))
    def apply(x: Double): Double = polynomial(x, dim).zip(A).map(tupled(_ * _)).sum
  }
}

class SplineInterpolation(val X: Array[Double], val Y: Array[Double], val dim: Int = 2) {
  val intervals: Array[(Double, Double)] = X
    .sliding(dim)
    .map(x => (x.head, x.last))
    .toArray

  val F: Array[Spline] = X
    .sliding(dim)
    .zip(Y.sliding(dim))
    .map(tupled(Spline))
    .toArray

  def apply(x: Double): Double = F
    .zip(intervals)
    .find(tupled(
      (f, interval) => interval._1 <= x && x <= interval._2
    )).get._1(x)
}
