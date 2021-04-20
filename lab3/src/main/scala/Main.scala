

object Main extends App {

  val interpolator = new Interpolator(
    Array(0.259, 0.841, 1.562, 2.304, 2.856, 3.0),
    Array(0.018, -1.259, -1.748, -0.532, 0.911, 1.0)
  )

  interpolator.writeAll()(2, 3, 4)
  interpolator.writeSplines(2, 3, 4)

}
