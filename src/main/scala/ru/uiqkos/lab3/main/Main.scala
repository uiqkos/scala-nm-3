package ru.uiqkos.lab3.main

import ru.uiqkos.lab3.interpolation.Interpolator

import scala.language.postfixOps

object Main extends App {

  val interpolator = new Interpolator(
    Array(0.351, 0.867, 3.315, 5.013, 6.432),
    Array(-0.572, -2.015, -3.342, -5.752, -6.911),
  )

  interpolator.writeAll()(Seq())
//  interpolator.writeSplines(2, 3, 4)

//  val t = (2.4 X ^ (3))

}
