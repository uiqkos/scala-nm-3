import breeze.plot._
import breeze.linalg._
import breeze.stats.mean
import Solver.CustomDenseMatrix
import breeze.numerics.pow
import Utils._


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

//  val A = DenseMatrix(
//    (5.4, -2.4, 3.8),
//    (2.5, 6.8, -1.1),
//    (2.7, -0.6, 1.5)
//  )
//


//  val c = A.withColumn(0, DenseVector(2.43, -1.12, 0.43, 0.83))
//  println(c)

//  val cramer = Solver.solve(SolveMethod.Cramer, A, b)


  var a = DenseVector(5.4, -2.4, 3.8)
  var b = DenseVector(2.5, 6.8, -1.1)
  var c = DenseVector(2.7, -0.6, 1.5)

  var f = DenseVector(5.5, 4.3, -3.5)

  c = b + 6.0 * (a - 2.0 * c)
  a = (a - c) * 3.0 + b

  f.update(2, f(1) + 6.0 * (f(0) - 2.0 * f(2)))
  f.update(0, (f(0) - f(2)) * 3.0 + f(1))

  val C = concatRowsToDenseMatrix(a, b, c)

  println(C)
  println(f)

  val cramer = Solver.solveCramer(C, f)
  println(cramer)

  for (power <- 1 to 5) {
    val accuracy = pow(10.0, -power)
    println(s"Accuracy: $accuracy")

    print("Simple iterations: ")
//    val simpleIterations = Solver.solve(SolveMethod.SimpleIteration, A, b, isReduced = true, accuracy = accuracy)
    val simpleIterations = Solver.solve(SolveMethod.SimpleIteration, C, f, isReduced = false, accuracy = accuracy, m = 10)

    print("Seidel iterations: ")
//    val seidel = Solver.solve(SolveMethod.Seidel, A, b, isReduced = true, accuracy = accuracy)
    val seidel = Solver.solve(SolveMethod.Seidel, C, f, isReduced = false, accuracy = accuracy, m = 10)

    println(simpleIterations)
    println(seidel)

    println(s"Simple accuracy: ${C * simpleIterations - f}, ${simpleIterations - cramer}")
    println(s"Seidel accuracy: ${C * seidel - f}, ${seidel - cramer}")

    println()

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
