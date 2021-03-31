import SolveMethod._
import breeze.linalg._

import math.{abs, pow, sqrt}
import scala.Function.tupled

object Solver {

  implicit class CustomDenseMatrix(matrix: DenseMatrix[Double]) {
    def rowsIterator: Iterator[DenseVector[Double]] = matrix
      .activeIterator
      .grouped(matrix.cols)
      .map(seq => DenseVector(seq.toArray.map(_._2)))

    def colsIterator: Iterator[DenseVector[Double]] = matrix.t.rowsIterator
    def norm1: Double = matrix.colsIterator.map(_.map(abs)).map(sum(_)).max
    def norm2: Double = sqrt(matrix.toArray.map(x => pow(abs(x), 2)).sum)
    def normInfinity: Double = matrix.rowsIterator.map(_.map(abs)).map(sum(_)).max /* mat.T.norm1 */
  }

  implicit class CustomDenseVector(vector: DenseVector[Double]) {
    def norm1: Double = sum(vector.map(abs))
    def norm2: Double = sqrt(sum(vector.map(x => pow(abs(x), 2))))
    def normInfinity: Double = max(vector)
  }

  def solve(
     A: DenseMatrix[Double],
     b: DenseVector[Double],
     maxIterations: Int = 100,
     accuracy: Double = 0.001
  ): DenseVector[Double] = {

    val C: DenseMatrix[Double] = A
      .rowsIterator
      .zipWithIndex
      .map(tupled(
        (row, rowIndex) => row.iterator.map(tupled(
          (columnIndex, element) =>
            if (columnIndex == rowIndex) 1 - row(rowIndex)
            else -element
        ))
      ))
      .map(vector => DenseMatrix(vector.toArray))
      .reduce((m1, m2) => DenseMatrix.vertcat(m1, m2))
      .reshape(A.rows, A.cols)

    solveFromReduced(C, b, maxIterations, accuracy)
  }

  def solveFromReduced(
      C: DenseMatrix[Double],
      f: DenseVector[Double],
      maxIterations: Int = 100,
      accuracy: Double = 0.001
  ): DenseVector[Double] = {
    val norms = List(C.norm1, C.norm2, C.normInfinity)
    val q = norms.min

    if (norms.max >= 1)
      throw new Exception(s"Сходимость не может быть достигнута " +
        s"(коэффициент сходимости: ${max(norms)})")

    var x = DenseVector.zeros[Double](f.size) // x₀
    var xPrev = x
    var i = 0

    // xᵢ = Cxᵢ₋₁+ f
    do {
      xPrev = x
      x = C * x + f
      i += 1
    } while (
      (q / (1 - q)) * (x - xPrev).norm2 >= accuracy ||
        i < maxIterations
    )
    println(i)
    x
  }

  def solve(method: SolveMethod, C: DenseMatrix[Double],
            f: DenseVector[Double],
            maxIterations: Int = 100,
            accuracy: Double = 0.001): Unit = {
    method match {
      case SimpleIteration => solve(C, f, maxIterations, accuracy)
      case Seidel => //todo method seidel
    }
  }
}