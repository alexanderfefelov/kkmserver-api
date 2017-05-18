name := "kkmserver-api"
organization := "alexanderfefelov.github.com"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.6.0-M1",
  "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.0.0-M10",
  "com.typesafe" % "config" % "1.3.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "joda-time" % "joda-time" % "2.9.9"
)

lazy val root = project in file(".")

doc in Compile := target.map(_ / "none").value
