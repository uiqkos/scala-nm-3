package ru.uiqkos.lab3.interpolation

import breeze.io.CSVWriter
import ru.uiqkos.lab3.interpolation.Interpolator.dataPath

import java.io.File
import scala.language.postfixOps

object Interpolator {
  //  val dataPath: String = getClass.getResource("data").getPath
  val resPath = "C:\\Users\\uiqko\\IdeaProjects\\scala-nm\\src\\main\\res\\lab3"
  val dataPath: String = resPath + "\\data"
  val plotsPath: String = resPath + "\\plots"
}

class Interpolator(val X: Array[Double], val Y: Array[Double]) {
  CSVWriter.writeFile(
    new File(dataPath + "/axes.csv"),
    IndexedSeq("X", "Y") +: IndexedSeq(X.map(_.toString), Y.map(_.toString)).transpose,
    quote = Char.MinValue
  )

  def writeAll(newTon_h: Double = 1.0)(spline_dims: Seq[Int]): (LagrangeInterpolation, NewtonInterpolation, Seq[SplineInterpolation]) = {
    val xFull = (BigDecimal(X(0)) to X.last by 0.001).map(_.toDouble)

    val fLagrange = new LagrangeInterpolation(X, Y)
    val fNewton = new NewtonInterpolation(X, Y)

    val splines = spline_dims.map(new SplineInterpolation(X, Y, _))

    val dfData = xFull.map(_.toString) +: {
      IndexedSeq(fLagrange, fNewton) :++ splines
    }.map(f => xFull.map(x => f(x).toString))

    val columns = IndexedSeq("X", "Lagrange", "Newton") :++ spline_dims.map(dim => s"Spline${dim}")

    CSVWriter.writeFile(
      new File(dataPath + "/data.csv"),
      columns +: dfData.transpose,
      quote = Char.MinValue
    )
    (fLagrange, fNewton, splines)
  }


  def writeSplines(dims: Seq[Int]): Unit = {
    val XFull = BigDecimal(X(0)) to X.last by 0.001 map(_.toDouble)
    dims
      .map(new SplineInterpolation(X, Y, _))
      .foreach(
        spline => {
          CSVWriter.writeFile(
            new File(dataPath + s"/spline${spline.dim}_data.csv"),
            ("X" +: spline.F.map(_.A.mkString(" ")))
              +: (XFull.map(_.toString) +: spline.F.map(f => XFull.map(x => f(x).toString))).toIndexedSeq.transpose, // cringe
            quote = Char.MinValue
          )
        }
      )
  }
}


