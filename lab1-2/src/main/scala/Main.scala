import breeze.plot._
import breeze.linalg._
import breeze.stats.mean
import Solver.CustomDenseMatrix
import breeze.numerics.pow
object Main extends App {
//  var fig = Figure()
//  var plt = fig.subplot(0)

//  val A = DenseMatrix(
//    0.32, -0.13, -0.08, 0.16,
//    0.17, -0.22, 0.13, -0.21,
//    0.05, -0.08, 0.0, 0.34,
//    0.12, 0.11, -0.19, 0.06
//  )
//  val b = DenseVector(2.42, 1.48, -0.16, 1.64)

//  val A = DenseMatrix(
//    (0.06,  0.17, 0.34, 0.16),
//    (0.32,  0.23, .0,   -0.35),
//    (0.16, -0.08, .0,   -0.12),
//    (0.09, 0.21, -0.13, .0)
//  )
//
//  val b = DenseVector(2.43, -1.12, 0.43, 0.83)

//  A.rowsIterator.map(_ * DenseVector.zeros[Double](4) + 1.0).foreach(println)

//  print("Simple iterations: ")
//  val simple = Solver.solve(SolveMethod.SimpleIteration, A, b, isReduced = true)
//  println(simple)
//  print("Seidel iterations: ")
//  val seidel = Solver.solve(SolveMethod.Seidel, A, b, isReduced = true)

//  println(s"Simple accuracy: ${simple - (A * simple + b)}")
//  println(s"Seidel accuracy: ${seidel - (A * seidel + b)}")

  val A = DenseMatrix(
    (5.4, -2.4, 3.8),
    (2.5, 6.8, -1.1),
    (2.7, -0.6, 1.5)
  )

  val b = DenseVector(5.5, 4.3, -3.5)

//  val c = A.withColumn(0, DenseVector(2.43, -1.12, 0.43, 0.83))
//  println(c)

//  val cramer = Solver.solve(SolveMethod.Cramer, A, b)

  for (power <- 1 to 5) {
    val accuracy = pow(10.0, -power)
    println(s"Accuracy: ${accuracy}")
    print(f"Simple iteration iterations: ")
    val simpleIterations = Solver.solve(SolveMethod.SimpleIteration, A, b, isReduced = true, accuracy = accuracy)
    print(f"Seidel iterations: ")
    val seidel = Solver.solve(SolveMethod.Seidel, A, b, isReduced = true, accuracy = accuracy)

    println(simpleIterations)
    println(seidel)
  }


//  println(cramer)
//  println(simpleIterations)
//  println(seidel)

//  println(s"Simple accuracy = ${mean(cramer - simpleIterations)}")
//  println(s"Seidel accuracy = ${mean(cramer - seidel)}")

  // 1. b + 6.0*(a_ - 2.0*c) = c
  // 2. (a_ - c)*3.0 + b = a
// List(a, b, c).map(x => x.toDenseMatrix.reshape(1, 3)).reduce(DenseMatrix.vertcat(_, _))
//  fig.refresh()
}
