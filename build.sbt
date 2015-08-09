enablePlugins(ScalaJSPlugin)

name := "hello-collidium"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"

persistLauncher in Compile := true

libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0",
    "com.lihaoyi" %%% "upickle" % "0.3.4"
)

jsDependencies += "org.webjars.bower" % "physicsjs" % "0.7.0" / "dist/physicsjs-full.js"
