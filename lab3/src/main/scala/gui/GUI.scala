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
            interpolator.writeAll()(2, 3, 4)
            interpolator.writeSplines(2, 3, 4)

            Seq(
              "C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\venv\\Scripts\\python.exe",
              "C:/Users/Uiqkos/IdeaProjects/scala-nm-3/lab3/src/main/python/view.py",
              "2", "3", "4"
            ).!

//            sleep(2000)

            val htmlPlots = Seq("all.html", "spline2.html", "spline3.html", "spline4.html")
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
