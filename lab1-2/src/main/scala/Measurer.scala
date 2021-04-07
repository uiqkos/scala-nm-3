import breeze.plot.Figure
import breeze.plot._

object Measurer {
  def plotResults(results: Result*): Unit = {
      var fig = Figure()
      var plt = fig.subplot(0)
      results.foreach(result => plt += plot(result.x, result.y))
      plt.refresh()
  }
}
