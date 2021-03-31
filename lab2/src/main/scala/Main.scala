import breeze.plot._
import breeze.linalg._

object Main extends App {
  var fig = Figure()
  var plt = fig.subplot(0)

  val A = DenseMatrix(
    0.32, -0.13, -0.08, 0.16,
    0.17, -0.22, 0.13, -0.21,
    0.05, -0.08, 0.0, 0.34,
    0.12, 0.11, -0.19, 0.06
  )
  val b = DenseVector(2.42, 1.48, -0.16, 1.64)




  fig.refresh()
}
