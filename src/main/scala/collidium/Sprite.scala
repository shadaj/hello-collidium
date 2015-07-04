package collidium

import org.scalajs.dom.raw.CanvasRenderingContext2D

class Point(val x: Int, val y: Int)

abstract class Sprite(color: String) {
  def render(context: CanvasRenderingContext2D): Unit = {
    context.fillStyle = color
    context.strokeStyle = color
  }
}

class Circle(location: Point, radius: Int, color: String) extends Sprite(color) {
  override def render(context: CanvasRenderingContext2D): Unit = {
    super.render(context)

    context.beginPath()
    context.arc(location.x, location.y, radius, 0, Math.PI * 2, false)
    context.fill()
    context.stroke()
  }
}

class Box(topLeft: Point, width: Int, height: Int, color: String) extends Sprite(color) {
  override def render(context: CanvasRenderingContext2D): Unit = {
    super.render(context)

    context.beginPath()
    context.strokeRect(topLeft.x, topLeft.y, width, height)
  }
}