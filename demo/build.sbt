name := "demo"
organization := "alexanderfefelov.github.com"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "alexanderfefelov.github.com" %% "kkmserver-api" % "0.0-SNAPSHOT",
  "com.lihaoyi" %% "pprint" % "0.5.0"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

doc in Compile := target.map(_ / "none").value
