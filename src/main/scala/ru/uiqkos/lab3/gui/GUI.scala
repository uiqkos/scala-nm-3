package ru.uiqkos.lab3.gui

import ru.uiqkos.lab3.interpolation.Interpolator

import java.awt.Desktop
import java.io.File
import scala.swing._
import scala.sys.process._

object GUI extends App {
  new Frame {
    title = ""

//    val XTextArea = new TextArea("0.259, 0.841, 1.562, 2.304, 2.856, 3.0")
//    val YTextArea = new TextArea("0.018, -1.259, -1.748, -0.532, 0.911, 1.0")
    val XTextArea = new TextArea("0.5, 2.0, 3.5, 5.0, 6.5, 8.0, 9.5, 11.0, 12.5, 14.0, 15.5")
    val YTextArea = new TextArea("0.018, -1.259, -1.748, -0.532, 0.911, 1.0, -4.3, -3.0, -1.0, -2.5, 1.6")

    val pane = new Label()

    contents = new FlowPanel {
      contents += new Label("Введите x:")
      contents += XTextArea

      contents += new Label("Введите y:")
      contents += YTextArea

      contents += pane

      contents += new Button("Интерполировать") {
        reactions += {
          case event.ButtonClicked(_) =>
            val X = XTextArea.text.split(", ").map(_.toDouble)
            val Y = YTextArea.text.split(", ").map(_.toDouble)

            val interpolator = new Interpolator(X, Y)
            val splineDims = Seq() //2, 3, 4, 7
            interpolator.writeAll(1.5)(splineDims)
            interpolator.writeSplines(splineDims)

            val (fLagrange, fNewton, splines) = interpolator.writeAll(1.5)(splineDims)


            println(fLagrange(X(1) + X(2)))
            println(fNewton(X(1) + X(2)))

            {
              Seq(
                "C:\\Users\\uiqko\\IdeaProjects\\scala-nm\\venv\\Scripts\\python.exe",
                "C:/Users/uiqko/IdeaProjects/scala-nm/src/main/python/view.py",
              ) ++ splineDims.map(_.toString)
            }.!

            val htmlPlots = (Seq("all.html") ++ splineDims.map(dim => s"spline$dim.html"))
              .map(name => new File(s"${Interpolator.plotsPath}/${name}").toURI)

            htmlPlots.foreach {
              html => Desktop.getDesktop.browse(html)
            }
        }
      }
    }

    pack()
    centerOnScreen()
    open()
  }
}
