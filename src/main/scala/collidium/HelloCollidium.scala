package collidium

import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D
import physicsjs.{Ticker, Physics}

import scala.scalajs.js
import org.scalajs.dom

object HelloCollidium extends js.JSApp {
  def main(): Unit = {
    val canvasElem = dom.document.getElementById("canvas").asInstanceOf[html.Canvas]
    val canvasContext = canvasElem.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    val world = Physics()
    world.add(Physics.behavior("constant-acceleration"))
    world.add(Physics.behavior("body-collision-detection"))
    world.add(Physics.behavior("body-impulse-response"))
    world.add(Physics.behavior("sweep-prune"))

    val sprites = Seq(new PhysicsCircle(Point(250, 100), 50, "lime", world), new PhysicsBox(Point(10, 10), 480, 480, "black", world))

    Ticker.on((time: Double) => {
      world.step(time)
      canvasContext.clearRect(0, 0, 500, 500)
      sprites.foreach(_.render(canvasContext))
    })

    Ticker.start()
  }
}
