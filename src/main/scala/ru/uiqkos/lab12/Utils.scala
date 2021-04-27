package ru.uiqkos.lab12

import breeze.linalg.{DenseMatrix, DenseVector}

object Utils {
  def time[R](message: String = "")(block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println(s"${message} Elapsed time: ${(t1 - t0) / math.pow(10, 7)}")
    result
  }

  def concatRowsToDenseMatrix(rows: DenseVector[Double]*): DenseMatrix[Double] =
    rows
      .map(row => row.toDenseMatrix.reshape(1, row.size))
      .reduce(DenseMatrix.vertcat(_, _))
}
