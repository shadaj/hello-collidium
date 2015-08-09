package collidium

import org.scalajs.dom.html
import org.scalajs.dom.raw.{DragEvent, MouseEvent, CanvasRenderingContext2D}
import physicsjs.{Ticker, Physics}

import scala.scalajs.js
import org.scalajs.dom

object HelloCollidium extends js.JSApp {
  val levelToPickle = Level(
    PhysicsCircleSpriteInfo(Point(400, 400), 20, "lime"),
    CircleSpriteInfo(Point(80, 80), 40, "red"),
    List(PhysicsBoxSpriteInfo(Point(10, 10), 480, 480, "black"))
  )

  val json = LevelStorage.writeLevel(levelToPickle)
  val level: Level = LevelStorage.readLevel(json)

  val SLING_START = level.ball.location
  val MAX_SLING_DRAG = 50

  var dragging = false
  var started = false
  var slingEnd: Option[Point] = None

  def main(): Unit = {
    val canvasElem = dom.document.getElementById("canvas").asInstanceOf[html.Canvas]
    val canvasContext = canvasElem.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    val world = Physics()
    world.add(Physics.behavior("constant-acceleration"))
    world.add(Physics.behavior("body-collision-detection"))
    world.add(Physics.behavior("body-impulse-response"))
    world.add(Physics.behavior("sweep-prune"))

    val ball = level.ball.createSprite(world)
    val hole = level.hole.createSprite(world)

    val sprites = level.obstacles.map(_.createSprite(world))

    Ticker.on((time: Double) => {
      world.step(time)
      canvasContext.clearRect(0, 0, 500, 500)

      hole.render(canvasContext)
      ball.render(canvasContext)

      sprites.foreach(_.render(canvasContext))

      slingEnd.foreach(end => new Line(SLING_START, end, "black").render(canvasContext))

      if (started) {
        if (hole.contains(ball)) world.pause()
      }
    })

    Ticker.start()

    implicit def eventToPoint(e: MouseEvent) = Point(
      e.clientX - canvasElem.offsetLeft,
      e.clientY - canvasElem.offsetTop
    )

    def updateSlingEnd(maybeNewEnd: Point) = {
      val distance = SLING_START.distanceTo(maybeNewEnd)
      if (distance <= MAX_SLING_DRAG) {
        slingEnd = Some(maybeNewEnd)
      } else {
        slingEnd = Some(SLING_START + ((maybeNewEnd - SLING_START) * (MAX_SLING_DRAG / distance)))
      }

      ball.body.state.pos = slingEnd.get
    }

    canvasElem.onmousedown = (e: MouseEvent) => {
      if (!started && ball.contains(e)) {
        dragging = true
        updateSlingEnd(e)
      }
    }

    canvasElem.onmousemove = (e: MouseEvent) => {
      if (dragging) {
        updateSlingEnd(e)
      }
    }

    canvasElem.onmouseup = (e: MouseEvent) => {
      if (dragging) {
        dragging = false
        started = true

        ball.body.treatment = "dynamic"
        ball.body.applyForce((SLING_START - slingEnd.get) * 1.0 / 100)

        slingEnd = None
      }
    }
  }
}
