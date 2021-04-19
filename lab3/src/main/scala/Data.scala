import SplineData.fSpline2
import smile.data.DataFrame
import smile.stat.Hypothesis.F
import smile.write
import smile.write.table

import scala.+:
import scala.Function.tupled
import scala.language.postfixOps

object Data extends App {
  val y = Seq(1.0, 3.0, 4.0, 3.0, 2.0, 1.0, -4.0, -5.2, -.666)
//  val y = Seq(1.0, 3.0, 4.0, 3.0, 2.0, 1.0, -4.0, -5.2, -.666)
  val x = y.indices.map(_.toDouble)
  val xFull = BigDecimal(0) until y.length - 1 by 0.001 map(_.toDouble)

  val fLagrange = Lagrange.interpolate(x.toArray, y.toArray)
  val fNewton = Newton.byDivided(x.toArray, y.toArray)

  val dfData = xFull.toArray +: Array(fLagrange, fNewton)
    .map(xFull.map(_).toArray)

  val df = DataFrame.of(dfData.transpose, "X", "Lagrange", "Newton", "spline2", "spline3", "spline4")

  write.csv(df, "C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\data.csv", delimiter = ';')
}

object SplineData extends App {
  val Y = Array(1.0, 3.0, 4.0, 3.0, 6.9, 1.0, -4.0, -5.2, -.666, -1.0)
  val X = Y.indices.map(_.toDouble).toArray
  val XFull = BigDecimal(0) until Y.length - 1 by 0.001 map(_.toDouble) toArray

  val fSpline2 = new SplineInterpolation(X, Y, 2)
  val spline2Data = XFull +: fSpline2.F.map(f => XFull.map(x => f(x)))

  val fSpline3 = new SplineInterpolation(X, Y, 3)
  val spline3Data = XFull +: fSpline3.F.map(f => XFull.map(x => f(x)))

  val fSpline4 = new SplineInterpolation(X, Y, 4)
  val spline4Data = XFull +: fSpline4.F.map(f => XFull.map(x => f(x)))

  val fSpline5 = new SplineInterpolation(X, Y, 5)
  val spline5Data = XFull +: fSpline5.F.map(f => XFull.map(x => f(x)))

  write.table(Array(X, Y), "C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\axes.txt")
  Array(fSpline2, fSpline3, fSpline4, fSpline5).map(_.F.map(_.A).toArray)
    .zipWithIndex
    .foreach(tupled(
      (d, i) => write.table(
        d, f"C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\spline${i + 2}_labels.txt"
      )
  ))

  write.table(spline2Data, "C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\spline2_data.txt")
  write.table(spline3Data, "C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\spline3_data.txt")
  write.table(spline4Data, "C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\spline4_data.txt")
  write.table(spline5Data, "C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\spline5_data.txt")

}
