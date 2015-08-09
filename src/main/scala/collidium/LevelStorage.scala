package collidium

import upickle.default._

object LevelStorage {
  def readLevel(string: String) = {
    read[Level](string)
  }

  def writeLevel(level: Level) = {
    write(level)
  }
}
