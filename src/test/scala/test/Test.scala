package test

import breeze.linalg.DenseVector
import org.scalatest.flatspec.AnyFlatSpec
import ru.uiqkos.lab3.interpolation.{LagrangeInterpolation, SplineInterpolation}
import test.Test.generateRandomArray

import scala.Function.tupled
import scala.util.Random

object Test {
  def generateRandomArray(n: Int): Array[Double] = DenseVector.rand[Double](n).toArray
}

class Test extends AnyFlatSpec {
  "Splines count" should "be equal n - dim + 1" in {
    for (dim <- 2 to 4) assert {
      val n = Random.nextInt(100) + 3
      val splines = new SplineInterpolation(
        generateRandomArray(n),
        generateRandomArray(n),
        dim = dim
      )
      splines.F.length == n - dim + 1
    }
  }

  "Lagrange approximation" should "go through the starting points" in {
    for (i <- 0 to 10) assert {
      val n = Random.nextInt(100) + 3

      val X = generateRandomArray(n)
      val Y = generateRandomArray(n)

      val f = new LagrangeInterpolation(X, Y)

      X.zip(Y).forall(tupled(
        (x, y) => f(x) == y
      ))
    }
  }

  "Spline approximation" should "go through the starting points" in {
    for (i <- 0 to 10) assert {
      val n = Random.nextInt(100) + 3

      val X = (0 until n).map(_.toDouble).toArray
      val Y = generateRandomArray(n)

      val f = new SplineInterpolation(X, Y)

      X.zip(Y).forall(tupled(
        (x, y) => f(x) - y < 1e-13
      ))
    }
  }
}
