package collidium

import org.scalajs.dom.raw.CanvasRenderingContext2D
import physicsjs.{CircleSetup, Physics, World, ShapeSetup}

case class Point(x: Int, y: Int)

abstract class Sprite(color: String) {
  def location: Point

  def render(context: CanvasRenderingContext2D): Unit = {
    context.fillStyle = color
    context.strokeStyle = color
  }
}

trait Physics { this: Sprite =>
  val world: World
  val shapeType: String
  val shapeSetup: ShapeSetup

  lazy val body = {
    val ret = Physics.body(shapeType, shapeSetup)
    world.add(ret)
    ret
  }

  override def location = Point(body.state.pos.get(0).toInt, body.state.pos.get(1).toInt)
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

class PhysicsCircle(startLocation: Point, radius: Int, color: String, val world: World) extends
  Circle(startLocation, radius, color) with Physics {
  val shapeType = "circle"
  val shapeSetup = CircleSetup(
    startLocation.x,
    startLocation.y,
    0,
    0,
    "dynamic",
    radius
  )
}

class Box(_location: Point, width: Int, height: Int, color: String) extends Sprite(color) {
  def location = _location

  override def render(context: CanvasRenderingContext2D): Unit = {
    super.render(context)

    context.beginPath()
    context.strokeRect(location.x, location.y, width, height)
  }
}