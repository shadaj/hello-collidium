package collidium

import physicsjs.World

sealed trait SpriteInfo {
  def createSprite(world: World): Sprite
}

case class CircleSpriteInfo(location: Point, radius: Int, color: String) extends SpriteInfo {
  def createSprite(world: World) = new Circle(location, radius, color)
}

case class PhysicsCircleSpriteInfo(location: Point, radius: Int, color: String) extends SpriteInfo {
  def createSprite(world: World) = new PhysicsCircle(location, radius, color, world)
}

case class LineSpriteInfo(start: Point, end: Point, color: String) extends SpriteInfo {
  def createSprite(world: World) = new Line(start, end, color)
}

case class PhysicsLineSpriteInfo(start: Point, end: Point, color: String) extends SpriteInfo {
  def createSprite(world: World) = new PhysicsLine(start, end, color, world)
}

case class BoxSpriteInfo(location: Point, width: Int, height: Int, color: String) extends SpriteInfo {
  def createSprite(world: World) = new Box(location, width, height, color)
}

case class PhysicsBoxSpriteInfo(location: Point, width: Int, height: Int, color: String) extends SpriteInfo {
  def createSprite(world: World) = new PhysicsBox(location, width, height, color, world)
}

case class Level(ball: PhysicsCircleSpriteInfo, hole: CircleSpriteInfo, obstacles: Seq[SpriteInfo])
