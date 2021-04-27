package ru.uiqkos.lab12

import breeze.linalg.{sum, _}
import ru.uiqkos.lab12.SolveMethod._

import scala.Function.tupled
import scala.math.{abs, pow, sqrt}

object Solver {

  implicit class CustomDenseMatrix(matrix: DenseMatrix[Double]) {
    def rowsIterator: Iterator[DenseVector[Double]] = matrix
      .activeIterator
      .grouped(matrix.cols)
      .map(seq => DenseVector(seq.toArray.map(_._2)))

    def colsIterator: Iterator[DenseVector[Double]] = matrix.t.rowsIterator

    def withColumn(i: Int, column: DenseVector[Double]): DenseMatrix[Double] =
//      scala 2.13
//      matrix.colsIterator
//        .take(i)
//        .concat(List(column))
//        .concat(matrix.colsIterator.drop(i + 1))
//        .map(_.toDenseMatrix.reshape(matrix.rows, 1))
//        .reduce(DenseMatrix.horzcat(_, _))

//      scala 2.12
      List(
        matrix(::, 0 to i),
        column.toDenseMatrix.reshape(matrix.rows, 1),
        matrix(::, 0 until matrix.cols)
      ).reduce(DenseMatrix.horzcat(_, _))

    def norm1: Double = matrix.colsIterator.map(_.map(abs)).map(sum(_)).max
    def norm2: Double = sqrt(matrix.toArray.map(x => pow(abs(x), 2)).sum)
    def normInfinity: Double = matrix.rowsIterator.map(_.map(abs)).map(sum(_)).max /* mat.T.norm1 */
  }

  implicit class CustomDenseVector(vector: DenseVector[Double]) {
    def norm1: Double = sum(vector.map(abs))
    def norm2: Double = sqrt(sum(vector.map(x => pow(abs(x), 2))))
    def normInfinity: Double = max(vector)
  }

  // todo: reduced matrix.norm < 1
  def reduceMatrix(A: DenseMatrix[Double], m: Double = 1.0): DenseMatrix[Double] = {
    A.rowsIterator
      .zipWithIndex
      .map(tupled(
        (row, rowIndex) => row.iterator.map(tupled(
          (columnIndex, element) =>
            if (columnIndex == rowIndex) (m - row(rowIndex)) / m
            else -element / m
        ))
      ))
      .map(vector => DenseMatrix(vector.toArray))
      .reduce((m1, m2) => DenseMatrix.vertcat(m1, m2))
      .reshape(A.rows, A.cols)
  }

  def checkConvergence(C: DenseMatrix[Double]): Double = {
    val norms = List(C.norm1, C.norm2, C.normInfinity)
    val q = norms.min

    if (q >= 1)
      throw new Exception(s"Сходимость не может быть достигнута " +
        s"(коэффициент сходимости: $q)")
    q
  }

  def reducedForMaxim(A: DenseMatrix[Double]): DenseMatrix[Double] = {
    A.rowsIterator
      .zipWithIndex
      .map(tupled(
        (row, rowIndex) => row.iterator.map(tupled(
          (columnIndex, element) =>
            if (columnIndex == rowIndex) 1
            else -element / row(rowIndex)
        ))
      ))
      .map(vector => DenseMatrix(vector.toArray))
      .reduce((m1, m2) => DenseMatrix.vertcat(m1, m2))
      .reshape(A.rows, A.cols)
  }

  def solveSimpleIteration(
    C: DenseMatrix[Double],
    F: DenseVector[Double],
    maxIterations: Int = 100,
    accuracy: Double = 0.001
  ): (DenseVector[Double], Int) = {

    val q = checkConvergence(C)

    var X = DenseVector.zeros[Double](F.size) // x₀
    var XPrev = X
    var i = 0

    // xᵢ = Cxᵢ₋₁+ f
    do {
      XPrev = X
      X = C * X + F
      i += 1
    } while (
      (q / (1 - q)) * (X - XPrev).normInfinity >= accuracy &&
        i < maxIterations
    )
    (X, i)
  }

  def solveSeidel(
   C: DenseMatrix[Double],
   F: DenseVector[Double],
   maxIterations: Int = 100,
   accuracy: Double = 0.001
  ): (DenseVector[Double], Int) = {

    val q = checkConvergence(C)

    var X = DenseVector.zeros[Double](F.size) // X₀
    var XPrev = X
    var i = 0

    // Xᵢ = CXᵢ₋₁+ f
    do {
      XPrev = X.copy
      for (((i, c), f) <- X.activeKeysIterator.zip(C.rowsIterator).zip(F.activeValuesIterator)) {
        X.update(i, sum(c * X) + f)
      }
      i += 1
    } while (
      (q / (1 - q)) * (X - XPrev).normInfinity >= accuracy &&
        i < maxIterations
    )
    (X, i)
  }

  def solveCramer(C: DenseMatrix[Double], F: DenseVector[Double]): DenseVector[Double] = {
    val CDet = det(C)
    DenseVector(
      (0 until C.cols)
        .map(i => C.withColumn(i, F))
        .map(det(_))
        .map(_ / CDet).toArray
    )
  }


  def solve(
   A: DenseMatrix[Double],
   C: DenseVector[Double],
   method: SolveMethod = SolveMethod.Seidel,
   isReduced: Boolean = false,
   maxIterations: Int = 100,
   accuracy: Double = 0.001,
   m: Double = 1.0
  ): DenseVector[Double] = solveDebug(A, C, method, isReduced, maxIterations, accuracy, m)._1

  def solveDebug(
   A: DenseMatrix[Double],
   B: DenseVector[Double],
   method: SolveMethod = SolveMethod.Seidel,
   isReduced: Boolean = false,
   maxIterations: Int = 100,
   accuracy: Double = 0.001,
   m: Double = 1.0
  ): (DenseVector[Double], Int) = {
    val C = if (isReduced) A else reduceMatrix(A, m = m)
    val F = if (isReduced) B else B / m

    method match {
      case SimpleIteration => solveSimpleIteration(C, F, maxIterations, accuracy)
      case Seidel => solveSeidel(C, F, maxIterations, accuracy)
      case Cramer => (solveCramer(C, F), 0)
    }
  }
}