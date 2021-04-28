package ru.uiqkos.lab3.interpolation

import ru.uiqkos.lab3.interpolation.NewtonInterpolation.{dividedDifference, dividedDifference2}

import scala.Function.tupled
import scala.language.postfixOps

object NewtonInterpolation {
  def finiteDifference(Y: Array[Double]): Array[Double] =
    Y.sliding(2).map(y => y(1) - y(0)).toArray

  def dividedDifference(X: Array[Double], Y: Array[Double]): Double = {
    if (X.length == 1) Y.head
    else (dividedDifference(X.drop(1), Y.drop(1)) - dividedDifference(X.dropRight(1), Y.dropRight(1))) / (X.last - X.head)
  }

  def dividedDifference_(X: Array[Double], Y: Array[Double]): (Array[Double], Array[Double]) = {
    println(X.length, Y.length)
    if (Y.length == 2 && X.length == 1) return (Array(), Array(Y(1) - Y(0)))
    val (newX, newY) =
      X.zip(Y.sliding(2))
        .map(tupled((x, y) => {
          println(y(1), y(0), x, (y(1) - y(0)) / x)
          (x, (y(1) - y(0)) / x)
        }))
        .toArray
        .unzip
    (newX.sliding(2).map(a => a.head + a.last).toArray, newY)
  }

  def dividedDifference2(distances: Array[Double], Y: Array[Double]): Array[Double] = {
    assert(distances.length == Y.length - 1)
    if (distances.length == 1) return Array(Y.last - Y.head)

    distances.zip(Y.sliding(2))
      .map(tupled(
        (distance, y) => (y(1) - y(0)) / distance
      ))
  }

}

class NewtonInterpolation(
   val X:Array[Double],
   val Y:Array[Double]
 ) extends Interpolation {

  val dividedDifferences: Array[Array[Double]] =
    (1 to X.length)
      .map(i => X.sliding(i).zip(Y.sliding(i)).map(tupled((x, y) => dividedDifference(x, y))).toArray)
      .toArray

  def apply(x: Double): Double = X
    .indices
    .drop(1)
    .map(i => X.take(i).map(xi => x - xi).product * dividedDifferences(i).head)
    .sum + Y(0)
}