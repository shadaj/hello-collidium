package collidium

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class Point(x: Int, y: Int)

abstract class Sprite(color: String) {
  def location: Point

  def render(context: CanvasRenderingContext2D): Unit = {
    context.fillStyle = color
    context.strokeStyle = color
  }
}

class Circle(_location: Point, radius: Int, color: String) extends Sprite(color) {
  def location = _location

  override def render(context: CanvasRenderingContext2D): Unit = {
    super.render(context)

    context.beginPath()
    context.arc(location.x, location.y, radius, 0, Math.PI * 2, false)
    context.fill()
    context.stroke()
  }
}

class Box(_location: Point, width: Int, height: Int, color: String) extends Sprite(color) {
  def location = _location

  override def render(context: CanvasRenderingContext2D): Unit = {
    super.render(context)

    context.beginPath()
    context.strokeRect(location.x, location.y, width, height)
  }
}