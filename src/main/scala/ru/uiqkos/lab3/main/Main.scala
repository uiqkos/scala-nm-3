package ru.uiqkos.lab3.main

//import plotly.Plotly._
import ru.uiqkos.lab3.Derivative
import plotly.Plotly._
import plotly.layout._
import plotly.element._
import plotly._
import ru.uiqkos.lab3.interpolation.{Interpolator, NewtonInterpolation, SplineInterpolation}
import smile.plot.swing._
import smile.plot._

import scala.language.postfixOps

object Main extends App {

//  val interpolator = new Interpolator(
//    Array(0.351, 0.867, 3.315, 5.013, 6.432),
//    Array(-0.572, -2.015, -3.342, -5.752, -6.911),
//  )

//  interpolator.writeAll()(Seq())
//  interpolator.writeSplines(2, 3, 4)

//  val t = (2.4 X ^ (3))


//  val X = Array(0.351, 0.867, 3.315, 5.013, 6.432)
//  val Y = Array(-0.572, -2.015, -3.342, -5.752, -6.911)

  val XFull = (BigDecimal(0.0) to 6.5 by 0.001).map(_.toDouble).toArray

  val X = XFull
  val YSin = XFull.map(math.sin)
  val YCos = XFull.map(math.cos)

  val f = new SplineInterpolation(X, YSin, dim = 2)
  val df1 = Derivative.derivative(f.apply, 0.0, 6.5, 0.001)
  val df2 = Derivative.derivative(df1, 0.0, 6.5, 0.001)

  val traces = Seq(
    Scatter(X.toSeq, YSin.toSeq).withName("sin"),
    Scatter(X.toSeq, X.map(f.apply).toSeq).withName("f"),

    Scatter(X.toSeq, YCos.toSeq).withName("cos"),
    Scatter(X.toSeq, X.map(df1).toSeq).withName("f'"),

    Scatter(X.toSeq, YSin.map(-_).toSeq).withName("-sin"),
    Scatter(X.toSeq, X.map(df2).toSeq).withName("f''"),
  )

  Plotly.plot("1.html", traces, Layout().withTitle(""), openInBrowser = true)
}
