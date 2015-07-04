package collidium

import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js
import org.scalajs.dom

object HelloCollidium extends js.JSApp {
  def main(): Unit = {
    val canvasElem = dom.document.getElementById("canvas").asInstanceOf[html.Canvas]
    val canvasContext = canvasElem.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    val sprites = Seq(new Circle(new Point(250, 250), 50, "lime"), new Box(new Point(10, 10), 480, 480, "black"))

    sprites.foreach(_.render(canvasContext))
  }
}
