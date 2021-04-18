import Newton.finiteDifference

import scala.:+
import smile.plot._
import smile.plot.swing.line

import scala.Function.tupled

object Main2 {
//  val y = Array(1.0, 3.0, 4.0, 3.0, 2.0, 1.0, -4.0, -5.2, -.666)
//  val x = y.indices.map(_.toDouble).toArray
//  val x2 = (BigDecimal(0) until y.length - 1 by 0.001).map(_.toDouble).toArray
//  println(y.length, x2.last)
//  val f4 = SplineInterpolator.interpolate(x, y, 4)
//  val y4 = x2.map(f4).toArray
//
//  x
//    .indices
//    .dropRight(1)
//    .foldLeft(
//      Array(y)
//    )((l, i) => l :+ finiteDifference(l.last)).foreach({x =>
//    x.foreach(print(_, " "))
//    println()
//  })



//  val f2 = Lagrange.interpolate(x, y)
//  val x2 = (BigDecimal(0) until y.length by 0.001).map(_.toDouble).toArray
//  val y2 = x2.map(f2).toArray
//
//  val f3 = Newton.interpolate(x, y)
//  val y3 = x2.map(f3).toArray
//
//  show(line(x2.zip(y4).map(tupled((x, y) => Array(x, y)))))(swing.JWindow(_))
}
