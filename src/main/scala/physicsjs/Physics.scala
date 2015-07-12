package physicsjs

import scala.scalajs.js
import scala.scalajs.js.Dynamic
import scala.scalajs.js.annotation.JSName

@JSName("Physics")
object Physics extends js.Object {
  def apply(): World = js.native
  def body(shape: String, setup: ShapeSetup): Body = js.native
}

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
            treatment: String, radius: Double): CircleSetup = {
    Dynamic.literal(
      x = x,
      y = y,
      vx = vx,
      vy = vy,
      treatment = treatment,
      radius = radius
    ).asInstanceOf[CircleSetup]
  }
}

trait World extends js.Object {
  def add(body: Body): Unit = js.native
}

trait Body extends js.Object {
  var state: BodyState = js.native
}

trait BodyState extends js.Object {
  var pos: Vector = js.native
}

trait Vector extends js.Object {
  def get(i: Int): Double = js.native
  def set(i: Double, value: Double): Unit = js.native
}