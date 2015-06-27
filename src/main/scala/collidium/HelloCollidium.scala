package collidium

import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom

object HelloCollidium extends js.JSApp {
  def main(): Unit = {
    val canvasElem = dom.document.getElementById("canvas").asInstanceOf[html.Canvas]
    val canvasContext = canvasElem.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    canvasContext.beginPath()
    canvasContext.moveTo(0, 0)
    canvasContext.lineTo(500, 500)
    canvasContext.stroke()
  }
}
