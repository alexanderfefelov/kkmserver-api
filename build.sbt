name := "kkmserver-api"
organization := "alexanderfefelov.github.com"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play-ws_2.11" % "2.4.11",
  "joda-time" % "joda-time" % "2.9.9"
)

lazy val root = project in file(".")

doc in Compile := target.map(_ / "none").value
