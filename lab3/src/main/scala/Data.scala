import Data.{X, Y, splines}
import SplineData.fSpline2
import smile.data.DataFrame
import smile.write

import scala.Function.tupled
import scala.language.postfixOps
import breeze.io.CSVWriter

import scala.io.Source
import java.io.File

object Data extends App {
  val Y = Seq(1.0, 3.0, 4.0, 3.0, 2.0, 1.0, -4.0, -5.2, -.666).toArray
  val X = Y.indices.map(_.toDouble).toArray
  val xFull = (BigDecimal(0) until Y.length - 1 by 0.001).map(_.toDouble)

  val fLagrange = new LagrangeInterpolation(X, Y)
  val fNewton = new NewtonInterpolation(X, Y)

  val splines = (2 to 5).map(new SplineInterpolation(X, Y, _))

  val dfData = xFull.map(_.toString) +: {
    splines ++: IndexedSeq(fLagrange, fNewton)
  }.map(f => xFull.map(x => f(x).toString))

  CSVWriter.writeFile(
    new File("C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\data.csv"),
    IndexedSeq("X", "Lagrange", "Newton", "Spline2", "Spline3", "Spline4", "Spline5") +: dfData.transpose,
    quote = Char.MinValue
  )

  CSVWriter.writeFile(
    new File("C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\axes.csv"),
    IndexedSeq("X", "Y") +: IndexedSeq(X.map(_.toString), Y.map(_.toString)).transpose,
    quote = Char.MinValue
  )

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

  CSVWriter.writeFile(
    new File("C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\axes.csv"),
    IndexedSeq("X", "Y") +: IndexedSeq(X.map(_.toString), Y.map(_.toString)).transpose,
    quote = Char.MinValue
  )

  Array(fSpline2, fSpline3, fSpline4, fSpline5)
    .foreach(
      spline => {
        CSVWriter.writeFile(
          new File(s"C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\spline${spline.dim}_data.csv"),
          ("X" +: spline.F.map(_.A.mkString(" "))) // interval => s"${interval._1} : ${interval._2}"
            +: (XFull.map(_.toString).toIndexedSeq +: spline.F.map(f => XFull.map(x => f(x).toString).toIndexedSeq)).toIndexedSeq.transpose, // cringe
          quote = Char.MinValue
        )
      }
    )
}
