package gui

import interpolation.Interpolator

import java.awt.Desktop
import java.io.File
import java.lang.Thread.sleep
import scala.io.Source
import scala.reflect.io.Path
import sys.process._
import scala.swing._

object GUI extends App {
  new Frame {
    title = ""

    val XTextArea = new TextArea("0.259, 0.841, 1.562, 2.304, 2.856, 3.0")
    val YTextArea = new TextArea("0.018, -1.259, -1.748, -0.532, 0.911, 1.0")
//    val XTextArea = new TextArea("0.5, 2.0, 3.5, 5.0, 6.5, 8.0, 9.5, 11.0, 12.5, 14.0, 15.5")
//    val YTextArea = new TextArea("0.018, -1.259, -1.748, -0.532, 0.911, 1.0, -4.3, -3.0, -1.0, -2.5, 1.6")

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
            interpolator.writeAll(1.5)(2, 3, 4)
            interpolator.writeSplines(2, 3, 4)

            Seq(
              "C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\venv\\Scripts\\python.exe",
              "C:/Users/Uiqkos/IdeaProjects/scala-nm-3/lab3/src/main/python/view.py",
              "2", "3", "4"
            ).!

            val htmlPlots = Seq("all.html") //, "spline2.html", "spline3.html", "spline4.html"
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
