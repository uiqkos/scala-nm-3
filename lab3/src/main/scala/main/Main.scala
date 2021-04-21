package main

import interpolation.Interpolator

object Main extends App {

  val interpolator = new Interpolator(
    Array(0.351, 0.867, 3.315, 5.013, 6.432),
    Array(-0.572, -2.015, -3.342, -5.752, -6.911),
  )

  interpolator.writeAll()(2, 3, 4)
//  interpolator.writeSplines(2, 3, 4)

}
