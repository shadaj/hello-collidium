package collidium

import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js
import org.scalajs.dom

object HelloCollidium extends js.JSApp {
  def main(): Unit = {
    val canvasElem = dom.document.getElementById("canvas").asInstanceOf[html.Canvas]
    val canvasContext = canvasElem.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    var curX = 0
    var curY = 0
    dom.setInterval(() => {
      canvasContext.clearRect(0, 0, 500, 500)
      val sprites = Seq(new Circle(Point(curX, curY), 50, "lime"), new Box(Point(10, 10), 480, 480, "black"))
      sprites.foreach(_.render(canvasContext))

      curX += 1
      curY += 1
    }, 0)
  }
}
