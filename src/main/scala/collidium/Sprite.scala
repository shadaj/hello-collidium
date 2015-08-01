package collidium

import org.scalajs.dom.raw.CanvasRenderingContext2D
import physicsjs._

import scala.scalajs.js

case class Point(x: Double, y: Double) {
  def distanceTo(that: Point) = {
    val xDiff = that.x - x
    val yDiff = that.y - y

    Math.sqrt(xDiff*xDiff + yDiff*yDiff)
  }

  def +(that: Point) = {
    Point(x + that.x, y + that.y)
  }

  def -(that: Point) = {
    Point(x - that.x, y - that.y)
  }

  def *(scale: Double) = {
    Point(x * scale, y * scale)
  }

  def /(scale: Double) = {
    Point(x / scale, y / scale)
  }
}

object Point {
  implicit def pointToVector(p: Point) = physicsjs.Vector(p.x, p.y)
}

abstract class Sprite(color: String) {
  def location: Point

  def contains(point: Point): Boolean

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

  def contains(point: Point) = location.distanceTo(point) <= radius
}

class PhysicsCircle(startLocation: Point, radius: Int, color: String, val world: World) extends
  Circle(startLocation, radius, color) with Physics {
  val shapeType = "circle"
  val shapeSetup = CircleSetup(
    startLocation.x,
    startLocation.y,
    0,
    0,
    "static",
    radius,
    0.5
  )
}

class Line(startLocation: Point, endLocation: Point, color: String) extends Sprite(color) {
  def location = startLocation

  override def render(context: CanvasRenderingContext2D): Unit = {
    super.render(context)

    context.beginPath()
    context.moveTo(startLocation.x, startLocation.y)
    context.lineTo(endLocation.x, endLocation.y)
    context.stroke()
  }

  def contains(point: Point) = false
}

class PhysicsLine(startLocation: Point, endLocation: Point, color: String, val world: World) extends
  Line(startLocation, endLocation, color) with Physics {
  val shapeType = "convex-polygon"
  val shapeSetup = PolygonSetup(
    (startLocation.x + endLocation.x)/2,
    (startLocation.y + endLocation.y)/2,
    0,
    0,
    "static",
    js.Array(
      Vector(startLocation.x, startLocation.y),
      Vector(startLocation.x - 1, startLocation.y - 1),
      Vector(endLocation.x - 1, endLocation.y - 1),
      Vector(endLocation.x, endLocation.y)
    )
  )
}

class Box(_location: Point, width: Int, height: Int, color: String) extends Sprite(color) {
  def location = _location

  override def render(context: CanvasRenderingContext2D): Unit = {
    super.render(context)

    context.beginPath()
    context.strokeRect(location.x, location.y, width, height)
  }

  def contains(point: Point) =
    point.x >= location.x && point.x <= location.x + width &&
    point.y >= location.y && point.y <= location.y + height
}

class PhysicsBox(_location: Point, width: Int, height: Int, color: String, val world: World) extends
  Box(_location, width, height, color) {
  val lines = Array(
    _location,
    Point(_location.x + width, _location.y),
    Point(_location.x + width, _location.y + height),
    Point(_location.x, _location.y + height),
    _location
  ).sliding(2).toList.map(a => new PhysicsLine(a(0), a(1), color, world))

  lines.foreach(_.body)
}