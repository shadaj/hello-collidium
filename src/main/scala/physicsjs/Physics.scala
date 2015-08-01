package physicsjs

import scala.scalajs.js
import scala.scalajs.js.Dynamic
import scala.scalajs.js.annotation.JSName

@JSName("Physics")
object Physics extends js.Object {
  def apply(): World = js.native
  def body(shape: String, setup: ShapeSetup): Body = js.native
  def behavior(name: String): Behavior = js.native
}

trait Behavior extends js.Object

trait ShapeSetup extends js.Object {
  var x: Double = js.native
  var y: Double = js.native

  var vx: Double = js.native
  var vy: Double = js.native

  var treatment: String = js.native
}

trait CircleSetup extends ShapeSetup {
  var radius: Double = js.native
}

object CircleSetup {
  def apply(x: Double, y: Double,
            vx: Double, vy: Double,
            treatment: String, radius: Double, restitution: Double): CircleSetup = {
    Dynamic.literal(
      x = x,
      y = y,
      vx = vx,
      vy = vy,
      treatment = treatment,
      radius = radius,
      restitution = restitution
    ).asInstanceOf[CircleSetup]
  }
}

trait PolygonSetup extends ShapeSetup {
  var vertices: js.Array[Vector] = js.native
}

object PolygonSetup {
  def apply(x: Double, y: Double, vx: Double, vy: Double,
            treatment: String, vertices: js.Array[Vector]): PolygonSetup = {
    Dynamic.literal(
      x = x,
      y = y,
      vx = vx,
      vy = vy,
      treatment = treatment,
      vertices = vertices
    ).asInstanceOf[PolygonSetup]
  }
}

trait World extends js.Object {
  def add(body: Body): Unit = js.native
  def add(behavior: Behavior): Unit = js.native
  def step(time: Double): Unit = js.native
}

trait Body extends js.Object {
  var state: BodyState = js.native
  var treatment: String = js.native
  def applyForce(f: Vector): Unit = js.native
}

trait BodyState extends js.Object {
  var pos: Vector = js.native
}

trait Vector extends js.Object {
  def get(i: Int): Double = js.native
  def set(i: Double, value: Double): Unit = js.native
}

@JSName("Physics.vector")
object Vector extends js.Object {
  def apply(x: Double, y: Double): Vector = js.native
}

@JSName("Physics.util.ticker")
object Ticker extends js.Object {
  def on(function: js.Function1[Double, Unit]): Unit = js.native
  def off(function: js.Function1[Double, Unit]): Unit = js.native
  def start(): Unit = js.native
}